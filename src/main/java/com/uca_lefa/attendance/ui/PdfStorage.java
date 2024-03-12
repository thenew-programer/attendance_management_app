package com.uca_lefa.attendance.ui;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfStorage {

    private static final String TAG = "PdfStorage";

    public static void generatePdfToStore(Context context, List<AttendanceRecord> attendanceRecords) {
        try {
            // Generate the PDF document using GeneratePdfUtils class
            PDDocument document = GeneratePdfUtils.generateAttendancePdf(context, attendanceRecords);

            // Save the PDF to the Documents folder
            String filePath = savePdfToDocuments(context, document);
            if (filePath != null) {
                Toast.makeText(context, "PDF généré et sauvegardé: \n" + filePath, Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to save PDF to Documents folder");
                Toast.makeText(context, "Échec de l'enregistrement du PDF", Toast.LENGTH_SHORT).show();
            }

            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error generating PDF: " + e.getMessage());
            Toast.makeText(context, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private static String savePdfToDocuments(Context context, PDDocument document) {
        try {
            // Create a directory for the PDF file in the Documents folder
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Absence");
            if (!directory.exists()) {
                if (!directory.mkdirs()) {
                    Log.e(TAG, "Failed to create directory for PDF");
                    return null;
                }
            }

            // Generate a unique filename for the PDF
			String Date = model.AttendanceRecord.getCurrentDate();
            String fileName = "Rapport D'absence - " + Date +" - .pdf";
            File file = new File(directory, fileName);

            // Save the PDF to the file
            document.save(file);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error saving PDF: " + e.getMessage());
            return null;
        }
    }
}
