package com.uca_lefa.attendance.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceUI extends AppCompatActivity {

    private ImageView logoImageView;
    private TextView teacherNameTextView;
    private TextView subjectTextView;
    private Button absenceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_ui);

        // Initialize views
        logoImageView = findViewById(R.id.logoImageView);
        teacherNameTextView = findViewById(R.id.teacherNameTextView);
        subjectTextView = findViewById(R.id.subjectTextView);
        absenceButton = findViewById(R.id.absenceButton);

        // Get teacher name and subject from intent
        Intent intent = getIntent();
        if (intent != null) {
            String teacherName = intent.getStringExtra("teacher_name");
            String subject = intent.getStringExtra("subject");

            // Set teacher name and subject in TextViews
            teacherNameTextView.setText(teacherName);
            subjectTextView.setText(subject);
        }

        // Set click listener for Absence button
        absenceButton.setOnClickListener(v -> {
            // Navigate to ScanActivity
            startActivity(new Intent(AttendanceUI.this, ScanActivity.class));
        });
    }
}
