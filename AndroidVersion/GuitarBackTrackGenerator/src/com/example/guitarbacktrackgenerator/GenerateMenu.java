package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GenerateMenu extends Activity {
	
	Button buttonPrev,buttonNext, buttonMaj, buttonMin, buttonCalm, buttonHeavy, buttonPlay, buttonExit;
	TextView title,textKey,textMode,textStyle, textKeyChosen; 
	
	String key = "A", mode, style; // Key is A by default
	String[] userChoice = new String[3];
	
	int keyCounter = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_menu);
		buttonPrev = (Button) findViewById(R.id.buttonPrev);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		buttonMaj = (Button) findViewById(R.id.buttonMaj);
		buttonMin = (Button) findViewById(R.id.buttonMin);
		buttonCalm = (Button) findViewById(R.id.buttonCalm);
		buttonHeavy = (Button) findViewById(R.id.buttonHeavy);
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		
		title = (TextView) findViewById(R.id.Title);
		textKey = (TextView) findViewById(R.id.textKey);
		textMode = (TextView) findViewById(R.id.textMode);
		textStyle = (TextView) findViewById(R.id.textStyle);
		textKeyChosen = (TextView) findViewById(R.id.Key);
		
		changeTextViewColors();
		
		buttonPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(keyCounter == 1)
					keyCounter = 12;
				else
					keyCounter--;
				
				changeKey(keyCounter);
			}
		});
		
		buttonNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(keyCounter == 12)
					keyCounter = 1;
				else
					keyCounter++;
				
				changeKey(keyCounter);
			}
		});
		
		buttonMaj.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mode = "maj";
			}
		});
		
		buttonMin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mode = "min";
			}
		});
		
		buttonCalm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				style = "calm";
			}
		});
		
		buttonHeavy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				style = "heavy";
			}
		});
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userChoice[0] = key;
				userChoice[1] = mode;
				userChoice[2] = style;
				
				try {
					String[] track = GenerateBackingTrack(userChoice);
					Bundle newBundle=new Bundle();
					newBundle.putStringArray(null, track);
					Intent MusicPlayer = new Intent(GenerateMenu.this, MusicPlayer.class);
					MusicPlayer.putExtras(newBundle);
					GenerateMenu.this.startActivity(MusicPlayer); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent BackToMainActivity = new Intent(GenerateMenu.this, MainActivity.class);
				GenerateMenu.this.startActivity(BackToMainActivity); 
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	void changeKey(int keyCounter){
		String[] currentKey = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		textKeyChosen.setText("" + currentKey);
		key = currentKey[keyCounter];
	}
	
	String[] GenerateBackingTrack(String[] userChoice) throws IOException{
		ParseCSV newParseCSV = new ParseCSV();
		
		ArrayList<String []> tracksThatMatchUserChoice = newParseCSV.parseCsv(userChoice,getAssets().open("backingTracks.csv"));
		
		String tracks = Integer.toString(tracksThatMatchUserChoice.size());
		Log.d("GuitarBackingTrackGenerator",tracks); 
		
		return getRandomTrack(tracksThatMatchUserChoice);
	}
	
	String[] getRandomTrack(ArrayList<String[]> tracksThatMatchUserChoice){
		int randomNum = (int)(Math.random()*tracksThatMatchUserChoice.size());		
		// Key, Mode, Style, Speed, Name, Path, Link to original track
		String[] randomTrack = tracksThatMatchUserChoice.get(randomNum);
		return randomTrack;		
	}
	
	void changeTextViewColors(){
		textKey.setTextColor(Color.parseColor("#FFFFFF"));
		textMode.setTextColor(Color.parseColor("#FFFFFF"));
		textStyle.setTextColor(Color.parseColor("#FFFFFF"));
		textKeyChosen.setTextColor(Color.parseColor("#FFFFFF"));
	}
}
