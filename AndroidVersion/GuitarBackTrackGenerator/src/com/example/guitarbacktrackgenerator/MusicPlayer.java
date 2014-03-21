package com.example.guitarbacktrackgenerator;


import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MusicPlayer extends Activity implements OnErrorListener, OnPreparedListener{
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

		Bundle newBundle = this.getIntent().getExtras();
		String[] userChoice = newBundle.getStringArray(null);

		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " "
				+ userChoice[2] + " " + userChoice[3]);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		/*
		 * int sound_id = this.getResources().getIdentifier(userChoice[3],
		 * "raw",this.getPackageName()); if(sound_id != 0) { MediaPlayer
		 * mediaPlayer = MediaPlayer.create(this, sound_id); }
		 */
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mediaPlayer.setDataSource("http://www.hubharp.com/web_sound/BachGavotte.mp3");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.prepareAsync();
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mediaPlayer.isPlaying()){
					mediaPlayer.start();
				}
			}
		});

		buttonPause.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
			}
		});

		buttonStop.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()){
					mediaPlayer.reset();
					try {
						mediaPlayer.setDataSource("http://www.hubharp.com/web_sound/BachGavotte.mp3");
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						mediaPlayer.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	});

		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				mediaPlayer.release();
				Intent BackToGenerateMenuActivity = new Intent(
						MusicPlayer.this, GenerateMenu.class);
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

	void changeTextViewColors() {
		title.setTextColor(Color.parseColor("#FFFFFF"));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		buttonPlay.setEnabled(true);
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}
}