package com.example.android.ZeroIron;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

public class ZeroIronGameStructure implements Serializable {
    
	//members
	private int mCourseId;
	private String mName;
	private GregorianCalendar mDate;
	private String mNotes;
	private int mStatus;
	
	//constructor
	public ZeroIronGameStructure() {
		mCourseId = 0;
		mName = new String();
		mDate = new GregorianCalendar();
		mNotes = new String();
		mStatus = 0;
	}
	
	public ZeroIronGameStructure(int courseId, String name, GregorianCalendar date, String notes, int status) {
		mCourseId = courseId;
		mName = name;
		mDate = date;		
		mNotes = notes;
		mStatus = status;
	}
    
	public int getCourseId() {
		return mCourseId;
	}
	
	public String getName() {
		return mName;
	}
	
	public GregorianCalendar getDate() {
		return mDate;
	}

	public String getNotes() {
		return mNotes;
	}
	
	public int getStatus() {
		return mStatus;
	}
	
	public void setCourseId(int courseId) {
		mCourseId = courseId;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public void setDate(GregorianCalendar date) {
		mDate = date;
	}
	
	public void setNotes(String notes) {
		mNotes = notes;
	}
	
	public void setStatus(int status) {
		mStatus = status;
	}
	
}