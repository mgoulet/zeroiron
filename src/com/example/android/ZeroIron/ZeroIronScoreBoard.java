package com.example.android.ZeroIron;

import java.util.Random;
import java.util.Calendar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class ZeroIronScoreBoard extends ListActivity {

	private ZeroIronDbAdapter mDbAdapter;
	public static final int RESET_ID = Menu.FIRST;
	public static final int RANDOM_ID = Menu.FIRST+1;
	public static final int EDIT_SCORE = 0;
	public static final int SPINNER_PAR_OFFSET = 3;	
	public static final int SPINNER_SCORE_OFFSET = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zeroiron_scoreboard);
		
		mDbAdapter = new ZeroIronDbAdapter(this);
		mDbAdapter.open();
		
		//generate random scores
		//generateRandomScores();
		//fillData();
		
	}
	
    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor c = mDbAdapter.fetchAllScores();
        startManagingCursor(c);

        String[] from = new String[] {  ZeroIronDbAdapter.KEY_HOLE,
        								ZeroIronDbAdapter.KEY_PAR,
        								ZeroIronDbAdapter.KEY_SCORE};
        int[] to = new int[] { R.id.hole,
        					   R.id.par,
        					   R.id.score };
        
        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter cursor =
            new SimpleCursorAdapter(this, R.layout.scoreboard_item, c, from, to);
        
        setListAdapter(cursor);
        
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//Test
		//AbstractWindowedCursor awc = (AbstractWindowedCursor)getListAdapter().getItem(position);
		
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
		Intent i = new Intent(ZeroIronScoreBoard.this, ZeroIronEditScore.class);
		i.putExtra("index", index);
		i.putExtra("hole", hole);
		i.putExtra("par", par);
		i.putExtra("score", score);
		
		//launch intent
		startActivityForResult(i, EDIT_SCORE);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
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
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
        menu.add(0, RESET_ID, 0, R.string.menu_reset);
        menu.add(0, RANDOM_ID, 0, R.string.menu_random);
        return true;
    }	

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case RESET_ID:
            	generateCleanScores();
            	break;
            case RANDOM_ID: //TESTING CASE
            	generateRandomScores();
            	break;
        }
        
        fillData();

        return super.onMenuItemSelected(featureId, item);
    }
    
    private void generateCleanScores() {
    	//delete scores
    	mDbAdapter.deleteAllScores();
    	//build scores    	
    	for (int i=1; i<19; i++) {
    		mDbAdapter.createScore(i, 3, 10);
    	}
    }
    
    private void generateRandomScores() {
    	
    	//delete scores
    	mDbAdapter.deleteAllScores();
    	//random seed
    	Calendar cal = Calendar.getInstance();
    	Random r = new Random(cal.getTimeInMillis());
    	//build scores    	
    	for (int i=1; i<19; i++) {
    		mDbAdapter.createScore(i, r.nextInt(3)+3, r.nextInt(6)+3);
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

	
}
