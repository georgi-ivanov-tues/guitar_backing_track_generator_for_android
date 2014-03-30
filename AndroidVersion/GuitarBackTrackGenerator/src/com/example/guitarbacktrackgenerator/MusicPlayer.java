package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

public class MusicPlayer extends Activity implements OnErrorListener, OnPreparedListener{
	Button buttonExit, buttonPlay, buttonPause, buttonAddToFavourites, buttonAddToRecordings, buttonStop;
	TextView title, displayUserChoice;
	String[] userChoice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonPause = (Button) findViewById(R.id.buttonPause);
		buttonStop = (Button) findViewById(R.id.buttonStop);
		buttonAddToFavourites = (Button) findViewById(R.id.buttonAddToFavourites);
		buttonAddToRecordings = (Button) findViewById(R.id.buttonAddToRecordings);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		displayUserChoice = (TextView) findViewById(R.id.Choice);

		changeTextViewColors();

		Bundle newBundle = this.getIntent().getExtras();
		userChoice = newBundle.getStringArray(null);

		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " " + userChoice[2] + "\n" + userChoice[3]);
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
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						mediaPlayer.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		buttonAddToFavourites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTrackToCsv("favourites");
			}
		});
		
		buttonAddToRecordings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTrackToCsv("recordings");
			}
		});

		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public boolean onCreateMusicPlayer(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void changeTextViewColors() {
		title.setTextColor(Color.parseColor("#FFFFFF"));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
	}

	@SuppressLint("ShowToast")
	public void addTrackToCsv(String fileName){
		CsvWriter newCsvWriter = new CsvWriter();
		CsvReader newCsvReader = new CsvReader();
		try {
			if(newCsvWriter.writeInInternalStorageCsv(userChoice, fileName+".csv",MusicPlayer.this)){
				String text = "Track successfully added to " + fileName + "!";
				Toast toast = Toast.makeText(MusicPlayer.this, text, 5);
				toast.show();
			}else{
				String text = "Track already added to " + fileName + "!";
				Toast toast = Toast.makeText(MusicPlayer.this, text, 5);
				toast.show();
			}
			newCsvReader.readFromInternalStorageCsv(fileName+".csv",MusicPlayer.this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		buttonPlay.setEnabled(true);
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2){
		return false;
	}
}