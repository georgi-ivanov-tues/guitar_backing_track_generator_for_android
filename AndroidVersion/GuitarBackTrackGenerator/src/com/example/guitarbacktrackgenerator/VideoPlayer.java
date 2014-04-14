package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VideoPlayer extends YouTubeBaseActivity
implements YouTubePlayer.OnInitializedListener{
	
	Button buttonExit, buttonPlay, buttonPause, buttonAddToFavourites, buttonAddToRecordings, buttonStop, buttonPlayRec, buttonStartRec, buttonStopRec;
	TextView title, trackName;
	String[] userChoice;
	static private final String DEVELOPER_KEY = "AIzaSyA53I5DUsgUvpBwOyeqfIkl9N0g9cxcHCA";
	static private String VIDEO = "";
	boolean mStartRecording = true;
	boolean mStartPlaying = true;
	final AudioRecord rec = new AudioRecord();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);

		buttonAddToFavourites = (Button) findViewById(R.id.buttonAddToFavourites);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonPlayRec = (Button) findViewById(R.id.buttonPlayRec);
		buttonStartRec = (Button) findViewById(R.id.buttonStartRec);
		title = (TextView) findViewById(R.id.Title);
		trackName = (TextView) findViewById(R.id.trackName);

		changeTextViewColors();

		Bundle newBundle = this.getIntent().getExtras();
		userChoice = newBundle.getStringArray(null);

		trackName.setText(userChoice[3]);
		String[] parsed = (userChoice[4].split("be/"));
		VIDEO = parsed[1];

		YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubeView.initialize(DEVELOPER_KEY, VideoPlayer.this);

		buttonAddToFavourites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				addTrackToCsv("favourites");
			}
		});

		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		buttonPlayRec.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new Thread(new Runnable() {
					public void run() {
						rec.onPlay(mStartPlaying);
					}
				}).start();
			}
		});
		buttonStartRec.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				//new Thread(new Runnable() {
				//	public void run() {
						rec.onRecord(mStartRecording);
				//	}
				//}).start();
			
				if (mStartRecording) {
					String text = "Recording is ON";
					buttonStartRec.setText("Stop Recording");
					Toast toast = Toast.makeText(VideoPlayer.this, text, Toast.LENGTH_LONG);
					toast.show();
				} else {
					String text = "Recording is OFF";
					buttonStartRec.setText("Start Recording");
					Toast toast = Toast.makeText(VideoPlayer.this, text, Toast.LENGTH_LONG);
					toast.show();
			    	askToAddRecordingsToCsv();
				}
				mStartRecording = !mStartRecording;
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
		trackName.setTextColor(Color.parseColor("#FFFFFF"));
	}

	boolean success = false;
	public void addTrackToCsv(final String fileName){
		final CsvWriter newCsvWriter = new CsvWriter();
		Long trackName = rec.getTrackName();
		if(fileName.equals("recordings"))
			userChoice[4] = Long.toString(trackName);
		
			new Thread(new Runnable() {
				public void run() {
					try {
						success = newCsvWriter.writeInInternalStorageCsv(userChoice, fileName+".csv",VideoPlayer.this, true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			try {
				Thread.sleep(100); // Fix it when you learn threads!
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(success){
				String text = "Track successfully added to " + fileName + "!";
				Toast toast = Toast.makeText(VideoPlayer.this, text, Toast.LENGTH_LONG);
				toast.show();
			}else{
				String text = "Track already added to " + fileName + "!";
				Toast toast = Toast.makeText(VideoPlayer.this, text, Toast.LENGTH_LONG);
				toast.show();
			}
	}
	
	public void askToAddRecordingsToCsv(){
		AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayer.this);
		builder
		.setTitle("Save to Recordings")
		.setMessage("Do you want to save recording ?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {			      	
		    	askForTrackName();
		    }
		})
		.setNegativeButton("No", null)					
		.show();
	}
	
	public void askForTrackName(){
		AlertDialog.Builder alert = new AlertDialog.Builder(VideoPlayer.this);
		
    	alert.setTitle("Name your recordings");
    	alert.setMessage("Please name your recording");

    	final EditText input = new EditText(VideoPlayer.this);
    	alert.setView(input);

    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	public void onClick(DialogInterface dialog, int whichButton) {
    	Editable value = input.getText();
    		userChoice[3] = value.toString();
    		addTrackToCsv("recordings");
    	 }
    	});

    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    	 public void onClick(DialogInterface dialog, int whichButton) {
    	     // Canceled.
    	}
    	});

    	alert.show();
	}
	
	@Override
	public void onInitializationFailure(Provider provider, YouTubeInitializationResult error) {
		Toast.makeText(this, "Oh no! "+error.toString(),
				Toast.LENGTH_LONG).show();
	}
	@Override
	public void onInitializationSuccess(Provider provider, YouTubePlayer player,boolean wasRestored) {
		player.loadVideo(VIDEO);
	}
}
