package com.uca_lefa.attendance.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import android.content.SharedPreferences;

public class AttendanceRecord {
    private static final String KEY_SUBJECT = "subject";
    private static final String PREF_NAME = "attendance_prefs";
	private int attendanceId;
	private String subject;
	private Student student;
	private String sessionDate;
	private boolean isPresent;

	// Constructor
	public AttendanceRecord(int attendanceId, Student student, boolean isPresent) {
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.subject = prefs.getString(KEY_SUBJECT, null);
		this.attendanceId = attendanceId;
		this.student = student;
		this.sessionDate = this.getCurrentDate();
		this.isPresent = isPresent;
	}
}

    // Getters and setters
    public int getAttendanceId() {
        return this.attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getSessionDate() {
        return this.sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public void setPresent(boolean present) {
        this.isPresent = present;
    }

	public String getSubject() {
		return this.subject;
	}

	public String setSubject(String subject) {
		this.subject = subject;
	}

    // toString method to represent the attendance record object as a string
    @Override
    public String toString() {
        return "AttendanceRecord{" +
                "attendanceId=" + this.attendanceId +
                ", student=" + this.student +
                ", sessionDate=" + this.sessionDate +
                ", isPresent=" + this.isPresent +
                '}';
    }


	// get the current date
	public static void getCurrentDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDateTime = currentDateTime.format(formatter);
		return formattedDateTime;
	}
}
