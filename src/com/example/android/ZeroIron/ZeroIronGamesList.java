package com.example.android.ZeroIron;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;

public class ZeroIronGamesList extends ListActivity implements OnItemLongClickListener {

	private ZeroIronDbAdapter mDbAdapter;
	public static final int NEW_ID = Menu.FIRST;
	public static final int GEN_ID = Menu.FIRST+1;
	public static final int DEL_ID = Menu.FIRST+2;
	
	public static final int GAME_EDIT_ACTIVITY_ID = 0;
	public static final String GAME_STRUCTURE = "GAME_STRUCTURE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_gameslist);

		mDbAdapter = new ZeroIronDbAdapter(this);
		mDbAdapter.open();
		
		mDbAdapter.createGamesTableIfRequired();
		mDbAdapter.createCoursesTableIfRequired();
		
		ListView listView = this.getListView();
		listView.setOnItemLongClickListener(this);
		

	}

    private void fillData() {

        // Get all of the notes from the database and create the item list
        Cursor c = mDbAdapter.fetchAllGames();
        startManagingCursor(c);

        String[] from = new String[] {  ZeroIronDbAdapter.KEY_GAME_COURSE_NAME,
        								ZeroIronDbAdapter.KEY_GAME_DATE,
        								ZeroIronDbAdapter.KEY_GAME_SCORE/*,
        								ZeroIronDbAdapter.KEY_GAME_WEATHER*/};
        int[] to = new int[] { R.id.textCourseName,
        					   R.id.textGameDate,
        					   R.id.textGameScore/*,
        					   R.id.textGameWeather*/};
      
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter cursor =
            new SimpleCursorAdapter(this, R.layout.zeroiron_gameslist_item, c, from, to);
        
        setListAdapter(cursor);
       
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);


		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK && requestCode == GAME_EDIT_ACTIVITY_ID) {
			//do stuff with the "hole" value...
			Bundle bundle = data.getExtras();
			
			//retrieve the data structure from the bundle
			ZeroIronGameStructure gameStructure = (ZeroIronGameStructure)bundle.getSerializable(GAME_STRUCTURE);
			
			int score = gameStructure.getGameScore();
			int ert=0;
			ert++;
			
			mDbAdapter.writeGame(gameStructure);
			
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
    
    private void generateGames() {
    	//build scores    	
    	for (int i=0; i<3; i++) {
    		//create game structure and send over to the dbAdapter.
    		ZeroIronGameStructure game = new ZeroIronGameStructure();
    		game.setCourseName("DEMO COURSE");
    		game.setGameScore(100);
    		
    		boolean result = mDbAdapter.writeGame(game);
    		
    		int ert=0;
    		ert++;
    	}
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		ZeroIronCustomGamePopupWindow popup = new ZeroIronCustomGamePopupWindow(arg1, arg2); 
		popup.showLikeQuickAction();

		return true;
	}
	
	public void editGameButtonClicked(int rowId) {
		int ert=0;
		ert++;
	}
	
	public void deleteGameButtonClicked(int rowId) {
		int ert=0;
		ert++;
	}
	
}


