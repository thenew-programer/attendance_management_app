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

    private static final String TAG = "GeneratePdfUtils";

    public static PDDocument generateAttendancePdf(Context context, List<AttendanceRecord> attendanceRecords) {
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
            contentStream.showText("module: " + attendanceRecords.get(0).getSubject());
            contentStream.newLineAtOffset(150, -50); // Adjust position as needed
            contentStream.showText("Rapport de Presence en " + attendanceRecords.get(0).getSessionDate());
            contentStream.endText();

            // Set font and font size for table
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

            // Draw table headers
            drawTableCell(contentStream, "Id", 50, 650); // Adjust position as needed
            drawTableCell(contentStream, "Nom, Prenom", 100, 650); // Adjust position as needed
            drawTableCell(contentStream, "Status d'Absence", 300, 650); // Adjust position as needed

            // Set font and font size for table
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Draw vertical lines for table
            contentStream.moveTo(50, 650); // Adjust position as needed
            contentStream.lineTo(50, 200); // Adjust position as needed
            contentStream.moveTo(100, 650); // Adjust position as needed
            contentStream.lineTo(100, 200); // Adjust position as needed
            contentStream.stroke();

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
                boolean isPresent = record.isPresent();

                // Check if there is enough space for another row
                if (yPosition < 200) {
                    // Create a new page
                    contentStream.close(); // Close the current content stream
                    page = new PDPage(); // Create a new page
                    document.addPage(page); // Add the new page to the document
                    contentStream = new PDPageContentStream(document, page); // Create a new content stream for the new page

                    // Set font and font size for table
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);

                    // Draw table headers on the new page
                    drawTableCell(contentStream, "Id", 50, 750); // Adjust position as needed
                    drawTableCell(contentStream, "Nom, Prenom", 100, 750); // Adjust position as needed
                    drawTableCell(contentStream, "Status d'Absence", 300, 750); // Adjust position as needed

                    // Set font and font size for table
                    contentStream.setFont(PDType1Font.HELVETICA, 12);

                    // Draw vertical lines for table on the new page
                    contentStream.moveTo(50, 750); // Adjust position as needed
                    contentStream.lineTo(50, 200); // Adjust position as needed
                    contentStream.moveTo(100, 750); // Adjust position as needed
                    contentStream.lineTo(100, 200); // Adjust position as needed
                    contentStream.stroke();

                    // Reset yPosition for the new page
                    yPosition = 710; // Adjust position as needed
                }

                // Draw table cell for student id
                drawTableCell(contentStream, String.valueOf(studentId), 50, yPosition);

                // Draw table cell for student name
                drawTableCell(contentStream, studentName, 100, yPosition);

                // Draw table cell for attendance status
                drawTableCell(contentStream, isPresent ? "P" : "A", 300, yPosition);

                // Move to the next row
                yPosition -= 20; // Adjust row height as needed
            }

            // Close the content stream
            contentStream.close();

            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return document;
    }

    // Helper method to draw a table cell with text at specified coordinates
    private static void drawTableCell(PDPageContentStream contentStream, String text, float x, float y) throws IOException {
        contentStream.setNonStrokingColor(220, 220, 220); // Light gray color for highlighting
        contentStream.fillRect(x, y - 15, 50, 20); // Highlighting rectangle

        contentStream.setNonStrokingColor(0, 0, 0); // Black color for text
		contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }
}

