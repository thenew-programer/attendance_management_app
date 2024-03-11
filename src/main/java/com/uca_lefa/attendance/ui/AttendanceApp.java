package com.uca_lefa.attendance.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceApp extends AppCompatActivity {

    private static final String PREF_NAME = "attendance_prefs";
    private static final String KEY_TEACHER_NAME = "teacher_name";
    private static final String KEY_SUBJECT = "subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the teacher's name and subject are set
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String teacherName = prefs.getString(KEY_TEACHER_NAME, null);
        String subject = prefs.getString(KEY_SUBJECT, null);

        if (teacherName != null && subject != null) {
            // Launch AttendanceUI activity if both teacher's name and subject are set
            startAttendanceUI(teacherName, subject);
        } else {
            // Launch SetupActivity to prompt the user to set teacher's name and subject
            startActivity(new Intent(this, SetupActivity.class));
        }

        // Finish this activity to prevent going back to it when pressing back button
        finish();
    }

    // Method to start AttendanceUI activity
    private void startAttendanceUI(String teacherName, String subject) {
        Intent intent = new Intent(this, AttendanceUI.class);
        intent.putExtra("teacher_name", teacherName);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}

