package com.example.android.ZeroIron;

import java.io.Serializable;

public class ZeroIronCourseStructure implements Serializable {

	private String mCourseName;
	private String mCourseLocation;
	private int mCoursePar;
	private int mCourseSize;
	
	public ZeroIronCourseStructure() {
		this.mCourseName = new String("Pineview Golf - Executive Course");
		this.mCourseLocation = new String("Ottawa, ON");
		this.mCoursePar = 5;
		this.mCourseSize = 18;
	}
	
	public ZeroIronCourseStructure(String courseName, String courseLocation, int coursePar, int courseSize) {
		this.mCourseName = courseName;
		this.mCourseLocation = courseLocation;
		this.mCoursePar = coursePar;
		this.mCourseSize = courseSize;
	}

	public String getCourseName() {
		return this.mCourseName;
	}
	
	public void setCourseName(String courseName) {
		this.mCourseName = courseName;
	}
	
	public String getCourseLocation() {
		return this.mCourseLocation;
	}
	
	public void setCourseLocation(String courseLocation) {
		this.mCourseLocation = courseLocation;
	}
	
	public int getCoursePar() {
		return this.mCoursePar;
	}
	
	public void setCoursePar(int coursePar) {
		this.mCoursePar = coursePar;
	}

	public int getCourseSize() {
		return this.mCourseSize;
	}
	
	public void setCourseSize(int courseSize) {
		this.mCourseSize = courseSize;
	}
}
