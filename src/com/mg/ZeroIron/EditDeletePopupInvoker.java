/*
 * Copyright Martin Goulet 2012 - ZeroIron
 */


package com.mg.ZeroIron;

/**
 * Interface for the ZeroIronEditDeletePopup.
 */
public interface EditDeletePopupInvoker {

	abstract public void editButtonClicked(int rowId);
	
	abstract public void deleteButtonClicked(int rowId);
	
}
