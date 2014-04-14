package com.example.guitarbacktrackgenerator;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends Activity implements OnErrorListener, OnPreparedListener{
	Button buttonExit, buttonPlay, buttonPause, buttonStop, buttonShare;
	TextView title, displayUserChoice;
	String path;
	String[] userChoice;
	
	private static final String CLIENT_ID = "fdb037658f862774d00e2f94816ec0e4";
	private static final int SHARE_SOUND  = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonPause = (Button) findViewById(R.id.buttonPause);
		buttonStop = (Button) findViewById(R.id.buttonStop);
		buttonShare = (Button) findViewById(R.id.buttonShare);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		displayUserChoice = (TextView) findViewById(R.id.Choice);

		changeTextViewColors();

		Bundle newBundle = this.getIntent().getExtras();
		userChoice = newBundle.getStringArray(null);

		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " "
				+ userChoice[2] + " " + userChoice[3]);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/GuitarRecordings/" +  userChoice[4] + ".3gp";
		try {
			mediaPlayer.setDataSource(path);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.prepareAsync();
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mediaPlayer.isPlaying()){
					File file = new File(path);
					if(file.exists()){
						mediaPlayer.start();
					}else{
						String text = Boolean.toString(file.exists());
						Toast toast = Toast.makeText(MusicPlayer.this, text, Toast.LENGTH_LONG);
						toast.show();
					}
						
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
						mediaPlayer.setDataSource(path);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (SecurityException e1) {
						e1.printStackTrace();
					} catch (IllegalStateException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
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

		buttonShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_SEND).setType("audio/soundcloud");
//				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(audioFile));
//				startActivity(Intent.createChooser(intent, "Share to"));
				shareSound();
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				mediaPlayer.release();
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

	void changeTextViewColors() {
		title.setTextColor(Color.parseColor("#FFFFFF"));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
	}

	private void shareSound() {
		File audioFile = new File(path);
		Intent intent = new Intent("com.soundcloud.android.SHARE")
			.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(audioFile))
			.putExtra("com.soundcloud.android.extra.title", userChoice[3])
			.putExtra("com.soundcloud.android.extra.where", userChoice[0] + ", " + userChoice[1] + ", " +  userChoice[2] + ", Backing Track")
			.putExtra("com.soundcloud.android.extra.description", "Thanks to: Guitar Backing Track Generator For Android")
			.putExtra("com.soundcloud.android.extra.public", true)
			.putExtra("com.soundcloud.android.extra.tags", new String[] {
			"demo",
			"post lolcat bluez",
			"soundcloud:created-with-client-id="+CLIENT_ID
		})
		.putExtra("com.soundcloud.android.extra.genre", "Easy Listening");
		//.putExtra("com.soundcloud.android.extra.location", getLocation());
		
		try {
			startActivityForResult(intent, SHARE_SOUND);
		}catch (ActivityNotFoundException notFound) {
			String text = "You should give up life!";
			Toast toast = Toast.makeText(MusicPlayer.this, text, Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		buttonPlay.setEnabled(true);
	}

	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		return false;
	}
}
