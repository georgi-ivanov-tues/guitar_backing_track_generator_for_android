package com.example.guitarbacktrackgenerator;

import java.io.File;
import java.io.IOException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A music player for playing the user's recordings
 * @author Georgi Ivanov and Nedelcho Delchev
 */
public class MusicPlayer extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {

	private ImageButton buttonPlay, buttonForward, buttonBackward;
	private Button buttonShare, buttonExit;
	private TextView songCurrentDurationLabel, songTotalDurationLabel, displayUserChoice, title;
	private SeekBar songProgressBar;
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private Handler mHandler = new Handler();;
	private Utilities utils;
	private int seekForwardTime = 5000, seekBackwardTime = 5000; // 5000 milliseconds
	private boolean backPressed = false;
	private String[] userChoice;
	private String path;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		buttonPlay = (ImageButton) findViewById(R.id.btnPlay);
		buttonForward = (ImageButton) findViewById(R.id.btnForward);
		buttonBackward = (ImageButton) findViewById(R.id.btnBackward);
		buttonShare = (Button) findViewById(R.id.buttonShare);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.title);
		displayUserChoice = (TextView) findViewById(R.id.songName);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		utils = new Utilities();

		Bundle newBundle = this.getIntent().getExtras();
		userChoice = newBundle.getStringArray(null);

		displayUserChoice.setText(userChoice[0] + " " + userChoice[1] + " "
				+ userChoice[2] + " " + userChoice[3]);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		path = Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/GuitarRecordings/" +  userChoice[4] + ".3gp";

		// Listeners
		songProgressBar.setOnSeekBarChangeListener(this); // Important
		mediaPlayer.setOnCompletionListener(this); // Important

		prepareMusicPlayer();

		/**
		 * Play button click event
		 * plays a song and changes button to pause image
		 * pauses a song and changes button to play image
		 * */
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// check for already playing
				if(mediaPlayer.isPlaying()){
					if(mediaPlayer != null){
						mediaPlayer.pause();
						// Changing button image to play button
						buttonPlay.setImageResource(R.drawable.btn_play);
					}
				}else{
					// Resume song
					if(mediaPlayer != null){
						mediaPlayer.start();
						updateProgressBar();
						// Changing button image to pause button
						buttonPlay.setImageResource(R.drawable.btn_pause);
					}
				}
			}
		});

		/**
		 * Forward button click event
		 * Forwards song specified seconds
		 * */
		buttonForward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// get current song position				
				int currentPosition = mediaPlayer.getCurrentPosition();
				// check if seekForward time is lesser than song duration
				if(currentPosition + seekForwardTime <= mediaPlayer.getDuration()){
					// forward song
					mediaPlayer.seekTo(currentPosition + seekForwardTime);
				}else{
					// forward to end position
					mediaPlayer.seekTo(mediaPlayer.getDuration());
				}
			}
		});

		/**
		 * Backward button click event
		 * Backward song to specified seconds
		 * */
		buttonBackward.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// get current song position				
				int currentPosition = mediaPlayer.getCurrentPosition();
				// check if seekBackward time is greater than 0 sec
				if(currentPosition - seekBackwardTime >= 0){
					// forward song
					mediaPlayer.seekTo(currentPosition - seekBackwardTime);
				}else{
					// backward to starting position
					mediaPlayer.seekTo(0);
				}

			}
		});

		/**
		 * A button for sharing the current recording
		 */
		buttonShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//File audioFile = new File(path);
				File audioFile = new File("raw/test.mp3");
				Intent intent = new Intent(Intent.ACTION_SEND).setType("audio/*");
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(audioFile));
				startActivity(Intent.createChooser(intent, "Share to"));
				String text = "Please make sure you are logged in before sharing!";
				Toast toast = Toast.makeText(MusicPlayer.this, text, Toast.LENGTH_LONG);
				toast.show();
			}
		});

		/**
		 * A button for exiting and going back to the previous activity
		 */
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(backPressed == false){
					backPressed = true;
					mediaPlayer.stop();
					mediaPlayer.release();
				}
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if(backPressed == false){
			backPressed = true;
			mediaPlayer.stop();
			mediaPlayer.release();
		}
		finish();
	}

	/**
	 * Update timer on seek bar
	 * */
	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);        
	}	

	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			if(backPressed == false){
				long totalDuration = mediaPlayer.getDuration();
				long currentDuration = mediaPlayer.getCurrentPosition();

				// Displaying Total Duration time
				songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
				// Displaying time completed playing
				songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

				// Updating progress bar
				int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
				//Log.d("Progress", ""+progress);
				songProgressBar.setProgress(progress);

				// Running this thread after 100 milliseconds
				mHandler.postDelayed(this, 100);
			}
		}
	};

	/**
	 * Prepares the music player
	 * Loads the songs length
	 * Changes the text's color
	 */
	public void prepareMusicPlayer(){
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

		long totalDuration = mediaPlayer.getDuration();
		long currentDuration = mediaPlayer.getCurrentPosition();
		songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
		songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));
		displayUserChoice.setTextColor(Color.parseColor("#FFFFFF"));
		title.setTextColor(Color.parseColor("#FFFFFF"));
	}

	/**
	 * Best fuction ever!
	 * */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {

	}

	/**
	 * When user starts moving the progress handler
	 * */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * When user stops moving the progress handler
	 * */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mediaPlayer.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

		// forward or backward to certain seconds
		mediaPlayer.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	@Override
	public void onDestroy(){
		super.onDestroy();
		mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {

	}
}