package com.example.android.ZeroIron;

import java.util.Random;
import java.util.Calendar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ZeroIronGamesList extends ListActivity implements OnItemLongClickListener {

	public GestureDetector mGestureDetector;
	
	private ZeroIronDbAdapter mDbAdapter;
	public static final int NEW_ID = Menu.FIRST;
	public static final int GEN_ID = Menu.FIRST+1;
	public static final int DEL_ID = Menu.FIRST+2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_gameslist);

		mDbAdapter = new ZeroIronDbAdapter(this);
		mDbAdapter.open();
		
		mDbAdapter.createGamesTableIfRequired();
		
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
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
    
    	//Testing!
    	int ert=0;
    	ert++;
    	
    	return true;
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//Test
		int ert=0;
		ert++;
		//AbstractWindowedCursor awc = (AbstractWindowedCursor)getListAdapter().getItem(position);
		/*
		//retrieve record data
		int index = ((AbstractWindowedCursor)
						getListAdapter().getItem(position)).getInt(0);
		int hole = ((AbstractWindowedCursor)
						getListAdapter().getItem(position)).getInt(1);
		int par = ((AbstractWindowedCursor)
						getListAdapter().getItem(position)).getInt(2);
		int score = ((AbstractWindowedCursor)
						getListAdapter().getItem(position)).getInt(3);

		//build intent for edit screen
		Intent i = new Intent(ZeroIronGamesList.this, ZeroIronEditScore.class);
		i.putExtra("index", index);
		i.putExtra("hole", hole);
		i.putExtra("par", par);
		i.putExtra("score", score);
		
		//launch intent
		startActivityForResult(i, EDIT_SCORE);
		*/
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		/*
		if (resultCode == Activity.RESULT_OK && requestCode == EDIT_SCORE) {
			//do stuff with the "hole" value...
			Bundle bundle = data.getExtras();
			
			int index = bundle.getInt("index");
			int hole = bundle.getInt("hole");
			int newPar = bundle.getInt("par");
			int newScore = bundle.getInt("score");
			
			//call an update to the DB adapter with the new data
			mDbAdapter.updateScore(index, hole, newPar, newScore);
		}
		*/
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
        	//TODO add activity for new game.
        	//...
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
		// TODO Auto-generated method stub

		//toggle the new context menu!
		//...
		
		
		
		return true;
	}
	
}
