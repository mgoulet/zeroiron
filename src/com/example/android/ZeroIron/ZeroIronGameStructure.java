package com.example.android.ZeroIron;

import java.io.Serializable;
import java.util.Date;

public class ZeroIronGameStructure implements Serializable {
    
	//members
	private String mCourseName;
	private Date mDate;
	private int mGameScore;
	
	//constructor
	public ZeroIronGameStructure() {
		
		mCourseName = new String("");
		mDate = new Date();
		mGameScore = 0;
	}
    
	//getters
	public String getCourseName() {
		return mCourseName;
	}
	
	public Date getDate() {
		return mDate;
	}
    
	public int getGameScore() {
		return mGameScore;
	}
	
	//setters
	public void setCourseName(String newDate) {
		mCourseName = newDate;
	}
	
	public void setDate(Date date) {
		mDate = date;
	}
	
	public void setGameScore(int score) {
		mGameScore = score;
	}
	
}