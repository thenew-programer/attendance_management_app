package com.uca_lefa.attendance.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetupActivity extends AppCompatActivity {

    private EditText teacherNameEditText;
    private Spinner subjectSpinner;
    private Button saveButton;

    private static final String PREF_NAME = "attendance_prefs";
    private static final String KEY_TEACHER_NAME = "teacher_name";
    private static final String KEY_SUBJECT = "subject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Initialize views
        teacherNameEditText = findViewById(R.id.teacherNameEditText);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        saveButton = findViewById(R.id.saveButton);

        // Populate subject spinner with values from string array
        populateSubjectSpinner();

        // Set up save button click listener
        saveButton.setOnClickListener(view -> onSaveButtonClicked());
    }

    private void populateSubjectSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.subjects_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(adapter);
    }

    private void onSaveButtonClicked() {
        String teacherName = teacherNameEditText.getText().toString().trim();
        String subject = subjectSpinner.getSelectedItem().toString();

        if (teacherName.isEmpty()) {
            showToast("Please enter your name");
        } else {
            saveData(teacherName, subject);
            startAttendanceUI(teacherName, subject);
            finish(); // Finish this activity to prevent going back to it when pressing back button
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveData(String teacherName, String subject) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_TEACHER_NAME, teacherName);
        editor.putString(KEY_SUBJECT, subject);
        editor.apply();
    }

    private void startAttendanceUI(String teacherName, String subject) {
        Intent intent = new Intent(this, AttendanceUI.class);
        intent.putExtra("teacher_name", teacherName);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}

