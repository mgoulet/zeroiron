package com.example.android.ZeroIron;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ZeroIronGamesList extends ListActivity implements OnItemLongClickListener, EditDeletePopupInvoker {

	//to be refactored
	public static ZeroIronDbAdapter mDbAdapter;
	
	protected static final int NEW_ID = Menu.FIRST;
	protected static final int GEN_ID = Menu.FIRST+1;
	protected static final int DEL_ID = Menu.FIRST+2;
	
	String[] from = new String[] {  ZeroIronDbAdapter.KEY_GAME_COURSE_ID,
			ZeroIronDbAdapter.KEY_GAME_NAME,        								
			ZeroIronDbAdapter.KEY_GAME_DATE,
			ZeroIronDbAdapter.KEY_GAME_NOTES,
			ZeroIronDbAdapter.KEY_GAME_STATUS};
	
	public static final int GAME_EDIT_ACTIVITY_ID = 0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_gameslist);

		mDbAdapter = ZeroIronApplication.getDbAdapter();
		
		mDbAdapter.createGamesTableIfRequired();
		mDbAdapter.createCoursesTableIfRequired();
		
		ListView listView = this.getListView();
		listView.setOnItemLongClickListener(this);
		

	}

    private void fillData() {

        // Get all of the notes from the database and create the item list
        Cursor c = mDbAdapter.fetchAllGames();
        startManagingCursor(c);

        String[] from = new String[] {  ZeroIronDbAdapter.KEY_GAME_COURSE_ID,
        								ZeroIronDbAdapter.KEY_GAME_NAME,        								
        								ZeroIronDbAdapter.KEY_GAME_DATE,
        								ZeroIronDbAdapter.KEY_GAME_NOTES,
        								ZeroIronDbAdapter.KEY_GAME_STATUS};
        
        int[] to = new int[] { R.id.textCourseName,
        					   R.id.textGameName,
        					   R.id.textGameDate,
        					   R.id.textGameNotes,
        					   R.id.imageStatus};
      
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter cursor =
            new SimpleCursorAdapter(this, R.layout.zeroiron_gameslist_item, c, from, to);
        
        cursor.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
        	@Override
        	public boolean setViewValue(View view, Cursor cursor, int colIndex) {
        		
        		String colName = cursor.getColumnName(colIndex);
        		
        		if (colName.equals(ZeroIronDbAdapter.KEY_GAME_NAME)) {
        			
        			TextView nameText = (TextView) view;
        			nameText.setText(cursor.getString(colIndex));
        			
        		} else if (colName.equals(ZeroIronDbAdapter.KEY_GAME_COURSE_ID)) {
        			
        			//first, retrieve the course name from it's ID currently stored in the cursor.
        			String courseName = ZeroIronGamesList.mDbAdapter.fetchCourseNameFromId(cursor.getInt(colIndex));
        			
        			//shove that shit in there, muffu'gah
        			TextView textView = (TextView) view;
        			textView.setText(courseName);
        			
        		} else if (colName.equals(ZeroIronDbAdapter.KEY_GAME_DATE)) {
        			
        			TextView dateText = (TextView) view;
        			dateText.setText(cursor.getString(colIndex));
        			
        		} else if (colName.equals(ZeroIronDbAdapter.KEY_GAME_NOTES)) {
        			
        			TextView notesText = (TextView) view;
        			notesText.setText(cursor.getString(colIndex));
        			
        		} else if (colName.equals(ZeroIronDbAdapter.KEY_GAME_STATUS)) {
        			
        			//1 - retrieve value
        			int status = cursor.getInt(colIndex);
        			
        			//2 - get a handle on the imageView from the xml layout
        			ImageView imageView = (ImageView) findViewById(R.id.imageStatus);
        			
        			
        			//3 - shove value in the view handle...
        			if (status == 0) {
        				
        			} else {
        				
        			}
        			
        		}
        		
        		return true;
        	}
        });

        setListAdapter(cursor);
       
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == GAME_EDIT_ACTIVITY_ID) {
			//do stuff with the "hole" value...
			Bundle bundle = data.getExtras();
			
			//retrieve the data structure from the bundle
			ZeroIronGameStructure newGameStructure = (ZeroIronGameStructure)bundle.getSerializable(ZeroIronDbAdapter.NEW_RECORD);
			ZeroIronGameStructure oldGameStructure = null;
			
			//retrieve old values if applicable
			if (bundle.containsKey(ZeroIronDbAdapter.OLD_RECORD)) {
				oldGameStructure = (ZeroIronGameStructure) bundle.getSerializable(ZeroIronDbAdapter.OLD_RECORD);
			}
			
			mDbAdapter.writeGame(oldGameStructure, newGameStructure);
			
			fillData();
			
		}

	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, NEW_ID, 0, R.string.menu_new_game);
        menu.add(0, GEN_ID, 0, R.string.menu_gen_games);
        menu.add(0, DEL_ID, 0, R.string.menu_clear_games);
        return true;
    }	

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case NEW_ID:

    		//build intent for edit screen
    		Intent i = new Intent(ZeroIronGamesList.this, ZeroIronGameEdit.class);
    		
    		//launch intent
    		startActivityForResult(i, GAME_EDIT_ACTIVITY_ID);
  
        	break;
        case GEN_ID:
           	generateGames();
           	break;
        case DEL_ID:
           	mDbAdapter.deleteAllGames();
           	mDbAdapter.dropGamesTable();
           	mDbAdapter.createGamesTableIfRequired();
           	break;
        }
        
        fillData();

        return super.onMenuItemSelected(featureId, item);
    }
    
    //This method to be updated drastically to reinforce integrity with courses table.
    private void generateGames() {
    	//build scores    	
    	for (int i=0; i<3; i++) {
    		//create game structure and send over to the dbAdapter.
    		ZeroIronGameStructure newGame = new ZeroIronGameStructure();
    		newGame.setCourseId(0);
    		
    		mDbAdapter.writeGame(null, newGame);
    		
    	}
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		//Display current data from database
		fillData();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		ZeroIronEditDeletePopup popup = new ZeroIronEditDeletePopup(arg1, arg2); 
		popup.showLikeQuickAction();

		return true;
	}
	
	public void editButtonClicked(int rowId) {

		//1 - retrieve game name from user selection
		ListView listView = this.getListView();
		Cursor cursor = (Cursor) listView.getItemAtPosition(rowId); 
		String gameName = cursor.getString(ZeroIronDbAdapter.GAME_NAME_COLUMN);
		
		//2 - fetch game name's GameStructure from DB
		Cursor gameCursor = mDbAdapter.fetchGameFromName(gameName);
		ZeroIronGameStructure game = new ZeroIronGameStructure(
				gameCursor.getInt(ZeroIronDbAdapter.GAME_COURSE_ID_COLUMN),
				gameCursor.getString(ZeroIronDbAdapter.GAME_NAME_COLUMN),
				gameCursor.getString(ZeroIronDbAdapter.GAME_DATE_COLUMN),
				gameCursor.getString(ZeroIronDbAdapter.GAME_NOTES_COLUMN),
				gameCursor.getInt(ZeroIronDbAdapter.GAME_STATUS_COLUMN));
		
		//3 - fetch course name from GameStructure's CourseId
		String courseName = mDbAdapter.fetchCourseNameFromId(game.getCourseId());
				
		//4 - send to edit activity via intent		
		Intent i = new Intent(ZeroIronGamesList.this, ZeroIronGameEdit.class);
		i.putExtra(ZeroIronGameStructure.GAME_STRUCTURE, game);
		i.putExtra(ZeroIronDbAdapter.KEY_COURSE_NAME, courseName);
		startActivityForResult(i, GAME_EDIT_ACTIVITY_ID);

	}
	
	public void deleteButtonClicked(int rowId) {
		//get a handle on the name of the course at location 'rowId'
		ListView listView = this.getListView();
		Cursor cursor = (Cursor) listView.getItemAtPosition(rowId); 
		String gameName = cursor.getString(ZeroIronDbAdapter.GAME_NAME_COLUMN);
		
		//send it off for deletion from the database
		
		mDbAdapter.deleteGame(gameName);
		fillData();
	}
	
}


