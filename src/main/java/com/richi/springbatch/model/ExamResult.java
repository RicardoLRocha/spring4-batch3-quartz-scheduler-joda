package com.richi.springbatch.model;

import org.joda.time.LocalDate;



public class ExamResult {
	

	private String studentName;
	private LocalDate dob;
	private double percentage;
	
	
	private String studentNameModifique;

	public String getStudentNameModifique() {
		return studentNameModifique;
	}
	public void setStudentNameModifique(String studentNameModifique) {
		this.studentNameModifique = studentNameModifique;
	}

	
	
	public String getStudentName() {
		return studentName;
	}
	
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public LocalDate getDob() {
		return dob;
	}
	
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "ExamResult [studentName=" + studentName + ", dob=" + dob + ", percentage=" + percentage + "]";
	}
	
	
}
