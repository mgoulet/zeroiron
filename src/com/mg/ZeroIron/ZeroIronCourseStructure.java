/*
 * Copyright Martin Goulet 2012 - ZeroIron
 */

package com.mg.ZeroIron;

import java.io.Serializable;

/**
 * This class contains the contents of a course for the application.
 */
public class ZeroIronCourseStructure implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COURSE_STRUCTURE = "COURSE_STRUCTURE";
	
	protected String mCourseName;
	protected String mCourseLocation;
	protected int mCoursePar;
	protected int mCourseSize;
	
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
