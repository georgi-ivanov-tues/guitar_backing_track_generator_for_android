package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicPlayer extends Activity{
	Button buttonExit, buttonPlay, buttonPause, buttonStop;
	TextView title, displayUserChoice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);
		
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonPause = (Button) findViewById(R.id.buttonPause);
		buttonStop = (Button) findViewById(R.id.buttonStop);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		displayUserChoice = (TextView) findViewById(R.id.Choice);
		
		Bundle newBundle=this.getIntent().getExtras();
		final String[] userChoice = newBundle.getStringArray(null);
		
		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " " + userChoice[2] + " " + userChoice[3]);
		
		String workingDir = System.getProperty("user.dir");
		System.out.println("Current working directory : " + workingDir);
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					GenerateBackingTrack(userChoice);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent BackToGenerateMenuActivity = new Intent(MusicPlayer.this, GenerateMenu.class);
				MusicPlayer.this.startActivity(BackToGenerateMenuActivity); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void GenerateBackingTrack(String[] userChoice) throws IOException{
		Log.d("GuitarBackingTrackGenerator",userChoice[0] + " " + userChoice[1] + " " + userChoice[2] + " " + userChoice[3]);
		ParseCSV newParseCSV = new ParseCSV();
		
		File file = new File("assets/backingTracks.csv");
		String filePath = file.getAbsolutePath();
		if (file.exists()){
			displayUserChoice.setText("Exists");
		}
		else{
			displayUserChoice.setText("Does not exist");
		}
		
//		AssetManager assetManager = getResources().getAssets();
//		InputStream inputStream = null;
//	    try {
//	        inputStream = assetManager.open("backingTracks.csv");
//	            if ( inputStream != null)
//	            	displayUserChoice.setText("It worked!");
//	            else
//	            	displayUserChoice.setText("It did not work!");
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    
	   
		ArrayList<String []> tracksThatMatchUserChoice = newParseCSV.parseCsv(userChoice,filePath);
		
		//displayUserChoice.setText("" + tracksThatMatchUserChoice);
		
		//BackingTrack newBackingTrack = new BackingTrack();
		//newBackingTrack.generateBackingTrack(tracksThatMatchUserChoice);
		//newBackingTrack.play();
	}
}
