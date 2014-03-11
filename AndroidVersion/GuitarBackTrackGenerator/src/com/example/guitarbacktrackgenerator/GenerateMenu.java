package com.example.guitarbacktrackgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
				
				Bundle newBundle=new Bundle();
				newBundle.putStringArray(null, userChoice);
				Intent MusicPlayer = new Intent(GenerateMenu.this, MusicPlayer.class);
				MusicPlayer.putExtras(newBundle);
				GenerateMenu.this.startActivity(MusicPlayer); 
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
		String currentKey;
		switch (keyCounter) {
	        case 1:  currentKey = "A";
	                 break;
	        case 2:  currentKey = "A#";
	                 break;
	        case 3:  currentKey = "B";
	                 break;
	        case 4:  currentKey = "C";
	                 break;
	        case 5:  currentKey = "C#";
	                 break;
	        case 6:  currentKey = "D";
	                 break;
	        case 7:  currentKey = "D#";
	                 break;
	        case 8:  currentKey = "E";
	                 break;
	        case 9:  currentKey = "F";
	                 break;
	        case 10: currentKey = "F#";
	                 break;
	        case 11: currentKey = "G";
	                 break;
	        case 12: currentKey = "G#";
	                 break;
	        default: currentKey = "null";
	                 break;
	    }
		textKeyChosen.setText("" + currentKey);
		key = currentKey;
	}
	
	void changeTextViewColors(){
		textKey.setTextColor(Color.parseColor("#FFFFFF"));
		textMode.setTextColor(Color.parseColor("#FFFFFF"));
		textStyle.setTextColor(Color.parseColor("#FFFFFF"));
		textKeyChosen.setTextColor(Color.parseColor("#FFFFFF"));
	}
}
