package com.uca_lefa.attendance.data;

import android.content.Context;

import com.uca_lefa.attendance.model.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String DELIMITER = ",";

    public static List<Student> readStudentsFromCsv(Context context) {
        List<Student> students = new ArrayList<>();
		List<AttendanceRecord> attendanceRecords = new ArrayLis<>();

        try {
            InputStream inputStream = context.getAssets().open("students.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skip header line
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length >= 2) {
                    int studentId = Integer.parseInt(data[0].trim());
                    String firstName = data[1].trim();
                    String lastName = data[2].trim();
                    String apogge = data[3].trim();
					String nfcUID = data[4].trim();
                    students.add(new Student(studentId, firstName, lastName, apogge, nfcUID));
                }
            }

            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		for (Student student : students) {
			attendanceRecords.add(new AttendanceRecord(student.getStudentId(), student, false));
		}
		return attendanceRecords;
    }
}
