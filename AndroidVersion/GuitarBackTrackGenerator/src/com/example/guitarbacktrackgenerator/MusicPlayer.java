package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
		
		changeTextViewColors();
		
		Bundle newBundle=this.getIntent().getExtras();
		final String[] userChoice = newBundle.getStringArray(null);
		
		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " " + userChoice[2]);
		
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
		Log.d("GuitarBackingTrackGenerator",userChoice[0] + " " + userChoice[1] + " " + userChoice[2]);
		
		ParseCSV newParseCSV = new ParseCSV();
		
		ArrayList<String []> tracksThatMatchUserChoice = newParseCSV.parseCsv(userChoice,getAssets().open("backingTracks.csv"));
		
		String tracks = Integer.toString(tracksThatMatchUserChoice.size());
		Log.d("GuitarBackingTrackGenerator",tracks); // Printign in LogCat
		
		displayUserChoice.setText("Tracks that match = " + tracksThatMatchUserChoice.size());
		
		//BackingTrack newBackingTrack = new BackingTrack();
		//newBackingTrack.generateBackingTrack(tracksThatMatchUserChoice);
		//newBackingTrack.play();
	}
	
	void changeTextViewColors(){
		title.setTextColor(Color.parseColor("#FFFFFF"));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
	}
}