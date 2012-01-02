package com.example.android.ZeroIron;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class ZeroIronSplash extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zeroiron_splash);
        
        ImageView image = (ImageView) findViewById(R.id.splash);
        image.setImageResource(R.drawable.splash1);
        
        image.setOnClickListener(splash_click);
        
    }
    
    //This callback launches the main activity
    private OnClickListener splash_click = new OnClickListener() {
    	public void onClick(View v) {
    		Intent i = new Intent(ZeroIronSplash.this, ZeroIronMain.class);
    		startActivity(i);
    	}
    };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//DO NOTHING
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		//DO NOTHING
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Toast.makeText(ZeroIronSplash.this, "Martin Goulet", Toast.LENGTH_SHORT).show();
		
		//DO NOTHING
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		//DO NOTHING		
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		//DO NOTHING
	}
    
    
    
    
}