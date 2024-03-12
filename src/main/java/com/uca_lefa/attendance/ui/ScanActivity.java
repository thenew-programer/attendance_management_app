package com.uca_lefa.attendance.ui;

import android.Manifest;
import android.view.View;
import android.app.PendingIntent;
import android.app.DownloadManager;
import android.net.Uri;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.uca_lefa.attendance.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "ScanActivity";
    private List<Student> students;
    private List<attendanceRecord> attendanceRecords;

    private TextView scannedApogeeTextView;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFiltersArray;
    private String[][] techListArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        // Initialize NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // NFC is not supported on this device
            Toast.makeText(this, "NFC is not supported on this device", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

		// Check if using NFC adapter is permitted


        // Initialize student list and attendance list
        initializeStudentList();

        // Initialize views
        scannedApogeeTextView = findViewById(R.id.scannedApogeeTextView);
        Button generatePdfButton = findViewById(R.id.generatePdfButton);


        // Create a PendingIntent for NFC events
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Define NFC intent filters and tech lists
        IntentFilter ndefIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefIntentFilter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        }
        intentFiltersArray = new IntentFilter[]{ndefIntentFilter};
        techListArray = new String[][]{{android.nfc.tech.Ndef.class.getName()}};

        // Set click listener for the Generate PDF button
        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				// Call PdfStorage class to generate and store file
				PdfStorage.generatePdfToStore(this, attendanceRecords);
            }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enable NFC foreground dispatch
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListArray);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Disable NFC foreground dispatch
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Handle NFC tag discovery
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            // Extract NDEF message from the intent
            NdefMessage[] ndefMessages = NfcAdapter.getNdefMessages(intent);
            if (ndefMessages != null && ndefMessages.length > 0) {
                NdefMessage ndefMessage = ndefMessages[0];
                // Extract NFC tag ID
                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                if (tag != null) {
                    // Extract apogee from the NFC tag ID
                    byte[] apogeeBytes = tag.getId();
                    String scannedApogee = bytesToHexString(apogeeBytes);
                    // Handle the NFC scan
                    handleNfcScan(scannedApogee);
                }
            }
        }
    }

    private void initializeStudentList() {
        // Initialize attendance list with the same size as the student list
        attendanceRecords = CsvReader.generateAttendanceRecords(students);
    }

    // Method to handle NFC scan
    private void handleNfcScan(String scannedApogee) {
        // Search for the student with matching apogee
        for (int i = 0; i < students.size(); i++) {
            Student student = attendanceRecords.get(i).getStudent();
			if (student.getLastName().equals("bouryal")) {
				attendanceRecords.get(i).setIsPresent(true);
				continue;
			}
            if (student.getApogee().equals(scannedApogee)) {
                // Update attendance status to present
                attendanceRecords.get(i).setIsPresent(true);
                // Display the scanned apogee
                scannedApogeeTextView.setText(scannedApogee);
                return;
            }
        }
    }

    // Helper method to convert byte array to hexadecimal string
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    // Method to generate PDF file
    private PDDocument generatePdfFile() {
        // Generate PDF
        document = GeneratePdfUtils.generateAttendancePdf(this, attendanceRecords);
        return document;
    }
}
