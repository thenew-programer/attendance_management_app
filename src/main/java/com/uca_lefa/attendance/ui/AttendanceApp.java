package com.uca_lefa.attendance.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceApp extends AppCompatActivity {

    private static final String PREF_NAME = "attendance_prefs";
    private static final String KEY_TEACHER_NAME = "teacher_name";
    private static final String KEY_SUBJECT = "subject";
    private static final int NFC_PERMISSION_REQUEST_CODE = 100;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the teacher's name and subject are set
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String teacherName = prefs.getString(KEY_TEACHER_NAME, null);
        String subject = prefs.getString(KEY_SUBJECT, null);

        // Check NFC permission
        checkNfcPermission();

        // Check storage permission
        checkStoragePermission();

        if (teacherName.isEmpty() || subject.isEmpty()) {
            // Launch SetupActivity to prompt the user to set teacher's name and subject
            startActivity(new Intent(this, SetupActivity.class));
        } else {
            // Launch AttendanceUI activity if both teacher's name and subject are set
            startAttendanceUI(teacherName, subject);
        }
    }

    // Method to start AttendanceUI activity
    private void startAttendanceUI(String teacherName, String subject) {
        Intent intent = new Intent(this, AttendanceUI.class);
        intent.putExtra("teacher_name", teacherName);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }

    // Check NFC permission
    private void checkNfcPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.NFC}, NFC_PERMISSION_REQUEST_CODE);
        }
    }

    // Check storage permission
    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        }
    }


    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NFC_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // NFC permission granted
                Toast.makeText(this, "NFC permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // NFC permission denied
                Toast.makeText(this, "NFC permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Storage permission granted
                Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Storage permission denied
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

