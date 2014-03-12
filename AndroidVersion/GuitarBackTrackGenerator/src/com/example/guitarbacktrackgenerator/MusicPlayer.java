package com.example.guitarbacktrackgenerator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
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
		String[] userChoice = newBundle.getStringArray(null);
		
		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " " + userChoice[2] + " " + userChoice[3]);
		
		int sound_id = this.getResources().getIdentifier(userChoice[3], "raw",this.getPackageName());
		//if(sound_id != 0) {
			final MediaPlayer mediaPlayer = MediaPlayer.create(this, sound_id);
		//}
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.start();
			}
		});
		
		buttonPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.pause();
			}
		});
		
		buttonStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
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
	
	
	void changeTextViewColors(){
		title.setTextColor(Color.parseColor("#FFFFFF"));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
	}
}