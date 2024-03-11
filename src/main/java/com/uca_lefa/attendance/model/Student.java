package com.uca_lefa.attendance.model;

public class Student {
	private int studentId;
	private String firstName;
	private String lastName;
	private String apogee;
	private String nfcUID;

	// Constructor
	public Student(int studentId, String firstName, String lastName, String apogee, String nfcUID) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.apogee = apogee;
		this.nfcUID = nfcUID;
	}

	// Getters and Setters
	public int getStudentId() {
		return this.studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getFirsName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getApogee() {
		return this.apogee;
	}

	public void setApogee(String apogee) {
		this.apogee = apogee;
	}

	public String getNfcUID() {
		return this.nfcUID;
	}

	public void setNfcUID(String nfcUID) {
		this.nfcUID = nfcUID;
	}
	// End Getters and Setters


    // toString method to represent the student object as a string
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + this.studentId +
                ", firstName='" + this.firstName + '\'' +
                ", lastName='" + this.lastName + '\'' +
                ", Apogge='" + this.apogee+ '\'' +
                ", nfcCardId='" + this.nfcUID + '\'' +
                '}';
    }
}
