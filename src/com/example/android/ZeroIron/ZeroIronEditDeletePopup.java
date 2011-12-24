package com.example.android.ZeroIron;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class ZeroIronEditDeletePopup implements OnClickListener {

	//members
	protected final View anchor;
	private final PopupWindow window;
	private final WindowManager windowManager;
	private View root;
	private Drawable background = null;
	
	private final int rowId;
	
	public ZeroIronEditDeletePopup(View anchor, int rowId) {
		this.anchor = anchor;
		this.window = new PopupWindow(anchor.getContext());
		this.rowId = rowId;
		
		//remove window when user touches outside this menu
		this.window.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					ZeroIronEditDeletePopup.this.window.dismiss();
					return true;
				}
				return false;
			}
		});
		
		this.windowManager = (WindowManager) this.anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
		onCreate();
	}

	protected void onCreate() {
		// inflate layout
		LayoutInflater inflater =
				(LayoutInflater) this.anchor.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.zeroiron_popup, null);
		
		// setup button events
		for(int i = 0, icount = root.getChildCount() ; i < icount ; i++) {
			View v = root.getChildAt(i);

			if(v instanceof TableRow) {
				TableRow row = (TableRow) v;

				for(int j = 0, jcount = row.getChildCount() ; j < jcount ; j++) {
					View item = row.getChildAt(j);
					if(item instanceof ImageButton) {
						ImageButton b = (ImageButton) item;
						b.setOnClickListener(this);
					}
				}
			}
		}

		// set the inflated view as what we want to display
		this.setContentView(root);
	}
	
	private void preShow() {
		if(this.root == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}

		if(this.background == null) {
			this.window.setBackgroundDrawable(new BitmapDrawable());
		} else {
			this.window.setBackgroundDrawable(this.background);
		}

		// if using PopupWindow#setBackgroundDrawable this is the only values of the width and hight that make it work
		// otherwise you need to set the background of the root viewgroup
		// and set the popupwindow background to an empty BitmapDrawable
		this.window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		this.window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.window.setTouchable(true);
		this.window.setFocusable(true);
		this.window.setOutsideTouchable(true);

		this.window.setContentView(this.root);
	}
	
	public void setBackgroundDrawable(Drawable background) {
		this.background = background;
	}
	
	/**
	 * Sets the content view. Probably should be called from {@link onCreate}
	 * 
	 * @param root
	 *            the view the popup will display
	 */
	public void setContentView(View root) {
		this.root = root;
		this.window.setContentView(root);
	}

	/**
	 * Will inflate and set the view from a resource id
	 * 
	 * @param layoutResID
	 */
	public void setContentView(int layoutResID) {
		LayoutInflater inflator =
				(LayoutInflater) this.anchor.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setContentView(inflator.inflate(layoutResID, null));
	}

	/**
	 * If you want to do anything when {@link dismiss} is called
	 * 
	 * @param listener
	 */
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		this.window.setOnDismissListener(listener);
	}

	/**
	 * Displays like a popdown menu from the anchor view
	 */
	public void showLikePopDownMenu() {
		this.showLikePopDownMenu(0, 0);
	}

	/**
	 * Displays like a popdown menu from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in X direction
	 * @param yOffset
	 *            offset in Y direction
	 */
	public void showLikePopDownMenu(int xOffset, int yOffset) {
		this.preShow();

		this.window.setAnimationStyle(R.style.Animations_GrowFromBottom);

		this.window.showAsDropDown(this.anchor, xOffset, yOffset);
	}

	/**
	 * Displays like a QuickAction from the anchor view.
	 */
	public void showLikeQuickAction() {
		this.showLikeQuickAction(0, 0);
	}

	/**
	 * Displays like a QuickAction from the anchor view.
	 * 
	 * @param xOffset
	 *            offset in the X direction
	 * @param yOffset
	 *            offset in the Y direction
	 */
	public void showLikeQuickAction(int xOffset, int yOffset) {
		this.preShow();

		this.window.setAnimationStyle(R.style.Animations_GrowFromBottom);

		int[] location = new int[2];
		this.anchor.getLocationOnScreen(location);

		Rect anchorRect =
				new Rect(location[0], location[1], location[0] + this.anchor.getWidth(), location[1]
					+ this.anchor.getHeight());

		this.root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootWidth = this.root.getMeasuredWidth();
		int rootHeight = this.root.getMeasuredHeight();

		int screenWidth = this.windowManager.getDefaultDisplay().getWidth();
		int screenHeight = this.windowManager.getDefaultDisplay().getHeight();

		int xPos = ((screenWidth - rootWidth) / 2) + xOffset;
		int yPos = anchorRect.top - rootHeight + yOffset;
/*
		// display on bottom
		if(rootHeight > anchorRect.top) {
			yPos = anchorRect.bottom + yOffset;
		}
*/
		this.window.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}

	public void dismiss() {
		this.window.dismiss();
	}
	
	@Override
	public void onClick(View arg0) {
		ImageButton b = (ImageButton) arg0;
		
		if (b.getId() == R.id.editButton) {
			Toast.makeText(this.anchor.getContext(), "Course Edited!", Toast.LENGTH_SHORT).show();
			this.dismiss();
			EditDeletePopupInvoker sourceView = (EditDeletePopupInvoker)arg0.getContext();
			sourceView.editGameButtonClicked(rowId);
			return;
		}
		if (b.getId() == R.id.deleteButton) {
			Toast.makeText(this.anchor.getContext(), "Course Deleted!", Toast.LENGTH_SHORT).show();
			this.dismiss();
			EditDeletePopupInvoker sourceView = (EditDeletePopupInvoker)arg0.getContext();
			sourceView.deleteGameButtonClicked(rowId);
			return;
		}
	}

}
