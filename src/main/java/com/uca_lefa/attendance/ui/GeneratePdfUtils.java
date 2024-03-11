package com.uca_lefa.attendance.ui;

import android.content.Context;
import com.uca_lefa.attendance.model.AttendanceRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GeneratePdfUtils {

    public static void generateAttendancePdf(Context context, List<AttendanceRecord> attendanceRecords, String outputPath) {
        try {
            // Load the preset PDF template
            InputStream inputStream = context.getAssets().open("preset_template.pdf");
            PDDocument document = PDDocument.load(inputStream);
            inputStream.close();

            // Access the first page of the document
            PDPage page = document.getPage(0);

            // Create a content stream to write text to the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            // Set font and font size for header
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);

            // Write header text
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750); // Adjust position as needed
			contentStream.showText("module: " + attendanceRecords.get(0).getSubject())
			contentStream.moveTo(150, 700)
            contentStream.showText("Rapport de Presence en " + attendanceRecords[0].getSessionDate());
            contentStream.endText();

            // Set font and font size for table
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            // Draw table headers
            drawTableCell(contentStream, "Id", 50, 650); // Adjust position as needed
            drawTableCell(contentStream, "Nom, Prenom", 100, 650); // Adjust position as needed
            drawTableCell(contentStream, "Status d'Absence", 300, 650); // Adjust position as needed

            // Set font and font size for table
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Draw horizontal line below table headers
            contentStream.moveTo(50, 640); // Adjust position as needed
            contentStream.lineTo(400, 640); // Adjust position as needed
            contentStream.stroke();

            // Set initial position for writing text in the table
            float yPosition = 610; // Adjust position as needed

            // Write student names and attendance status to the PDF
            for (AttendanceRecord record : attendanceRecords) {
				int studentId = record.getStudent().getStudentId();
                String studentName = record.getStudent().getLastName() + " " + record.getStudent().getFirstName();
                boolean isPresent = record.getIsPresent();

                // Draw table cell for student id
                drawTableCell(contentStream, studentId, 50, yPosition);

                // Draw table cell for student name
                drawTableCell(contentStream, studentName, 100, yPosition);

                // Draw table cell for attendance status
                drawTableCell(contentStream, isPresent ? "P" : "A", 350, yPosition);

                // Move to the next row
                yPosition -= 20; // Adjust row height as needed
            }

            // Close the content stream
            contentStream.close();

            // Save the modified PDF to the specified output path
            document.save(new File(outputPath));

            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to draw a table cell with text at specified coordinates
    private static void drawTableCell(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
}
