package com.example.android.ZeroIron;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ZeroIronGameStructure implements Serializable {
    
	public static final String GAME_STRUCTURE = "GAME_STRUCTURE";
	
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
    
	public ZeroIronGameStructure(int courseId, String name, String date, String notes, int status) {
		mCourseId = courseId;
		mName = name;
		mNotes = notes;
		mStatus = status;
		
		//convert date from string
		SimpleDateFormat sdf = new SimpleDateFormat(ZeroIronDbAdapter.DATE_FORMAT);
		Date dateObject;
		
		try {
			
			dateObject = sdf.parse(date);
			mDate = new GregorianCalendar();			
			mDate.setTime(dateObject);
			
			//test
			/*
			int year = mDate.get(Calendar.YEAR);
			int month = mDate.get(Calendar.MONTH);
			int day = mDate.get(Calendar.DAY_OF_MONTH);
			int hour = mDate.get(Calendar.HOUR);
			int minutes = mDate.get(Calendar.MINUTE);
						
			int ert=0;
			ert++;
			*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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