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
import android.widget.Toast;

public class GenerateMenu extends Activity {
	
	Button buttonPrev,buttonNext, buttonMaj, buttonMin, buttonCalm, buttonHeavy, buttonRadomize, buttonClear, buttonPlay, buttonExit;
	TextView title,textKey,textMode,textStyle, textKeyChosen,warning; 
	
	String key = "A", mode, style;
	String[] userChoice = new String[3];
	
	int keyCounter = 0;
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
		buttonRadomize = (Button) findViewById(R.id.buttonRandomize);
		buttonClear = (Button) findViewById(R.id.buttonClear);
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		
		title = (TextView) findViewById(R.id.Title);
		textKey = (TextView) findViewById(R.id.textKey);
		textMode = (TextView) findViewById(R.id.textMode);
		textStyle = (TextView) findViewById(R.id.textStyle);
		textKeyChosen = (TextView) findViewById(R.id.Key);
		warning = (TextView) findViewById(R.id.textWarning);
		
		changeTextViewColors();
		
		buttonPrev.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(keyCounter == 0)
					keyCounter = 11;
				else
					keyCounter--;
				
				changeKey(keyCounter);
			}
		});
		
		buttonNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(keyCounter == 11)
					keyCounter = 0;
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
		
		buttonRadomize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				changeKey(getRandomNumber(12));
				if(getRandomNumber(2) == 0){
					mode = "min";
				}else{
					mode = "maj";
				}
				
				if(getRandomNumber(2) == 0){
					style = "calm";
				}else{
					style = "heavy";
				}
			}
		});
		
		buttonClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				key = "A";
				keyCounter = 0;
				textKeyChosen.setText("A");
				mode = null;
				style = null;
			}
		});
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userChoice[0] = key;
				userChoice[1] = mode;
				userChoice[2] = style;
				
				if(mode == null || style == null){
					String text = "Please choose key, mode and style before clicking play!";
					Toast toast = Toast.makeText(GenerateMenu.this, text, Toast.LENGTH_LONG);
					toast.show();
				}else{
					try {
						ArrayList<String[]>tracksThatMatchUserChoice = FindBackingTracks(userChoice);
						
						if(tracksThatMatchUserChoice.size() == 0){
							String text = "No tracks matching your input... Sorry :(";
							Toast toast = Toast.makeText(GenerateMenu.this, text, Toast.LENGTH_LONG);
							toast.show();
						}else{
							String[] track = getRandomTrack(tracksThatMatchUserChoice);
							Bundle newBundle=new Bundle();
							newBundle.putStringArray(null, track);
							Intent VideoPlayer = new Intent(GenerateMenu.this, VideoPlayer.class);
							VideoPlayer.putExtras(newBundle);
							GenerateMenu.this.startActivity(VideoPlayer); 
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish(); 
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
		key = currentKey[keyCounter];
		textKeyChosen.setText(key);
	}
	
	ArrayList<String[]> FindBackingTracks(final String[] userChoice) throws IOException{
		final CsvReader newCsvReader = new CsvReader();
		return newCsvReader.parseCsv(userChoice,getAssets().open(userChoice[0]+"_Backing_Tracks.csv"));
	}
	
	String[] getRandomTrack(ArrayList<String[]> tracksThatMatchUserChoice){
		int randomNum = (int)(Math.random()*tracksThatMatchUserChoice.size());	
		String[] randomTrack = tracksThatMatchUserChoice.get(randomNum);
		return randomTrack;		
	}
	
	int getRandomNumber(int max){
		return (int)(Math.random()*max);
	}
	
	void changeTextViewColors(){
		textKey.setTextColor(Color.parseColor("#FFFFFF"));
		textMode.setTextColor(Color.parseColor("#FFFFFF"));
		textStyle.setTextColor(Color.parseColor("#FFFFFF"));
		textKeyChosen.setTextColor(Color.parseColor("#FFFFFF"));
		warning.setTextColor(Color.parseColor("#FFFFFF"));
	}
}
