package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends YouTubeBaseActivity
implements YouTubePlayer.OnInitializedListener{
	
	Button buttonExit, buttonPlay, buttonPause, buttonAddToFavourites, buttonAddToRecordings, buttonStop, buttonPlayRec, buttonStartRec, buttonStopRec;
	TextView title, trackName;
	String[] userChoice;
	static private final String DEVELOPER_KEY = "AIzaSyA53I5DUsgUvpBwOyeqfIkl9N0g9cxcHCA";
	static private String VIDEO = "dKLftgvYsVU";
	//private static String mFileName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		buttonAddToFavourites = (Button) findViewById(R.id.buttonAddToFavourites);
		buttonAddToRecordings = (Button) findViewById(R.id.buttonAddToRecordings);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonPlayRec = (Button) findViewById(R.id.buttonPlayRec);
		buttonStartRec = (Button) findViewById(R.id.buttonStartRec);
		buttonStopRec = (Button) findViewById(R.id.buttonStopRec);
		title = (TextView) findViewById(R.id.Title);
		trackName = (TextView) findViewById(R.id.trackName);

		changeTextViewColors();

		Bundle newBundle = this.getIntent().getExtras();
		userChoice = newBundle.getStringArray(null);
		
		trackName.setText(userChoice[3]);
		String[] parsed = (userChoice[4].split("be/"));
		Log.d("", parsed[1]);
		VIDEO = parsed[1];
		
		YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
		youTubeView.initialize(DEVELOPER_KEY, this);
		
		final AudioRecord rec = new AudioRecord();
		
		//recorder.setOnErrorListener(errorListener);
		//recorder.setOnInfoListener(infoListener);
		
		
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
		
		//final boolean mStartPlaying = true;
		//final boolean mStartRecording = true;
		
		buttonPlayRec.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				boolean mStartPlaying = true;
                rec.onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setTitle("Stop playing");
                } else {
                    setTitle("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        });
		
		buttonStartRec.setOnClickListener(new View.OnClickListener() {
			@Override
			 public void onClick(View v) {
				/*boolean mStartRecording = true;
                rec.onRecord(mStartRecording);
                if (mStartRecording) {
                    setTitle("Stop recording");
                } else {
                    setTitle("Start recording");
                }
                mStartRecording = !mStartRecording;*/
				rec.startPlaying();
            }
		});
		
		buttonStopRec.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
		 
	public boolean onCreateMusicPlayer(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

/*	public void AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }*/
	
	void changeTextViewColors() {
		title.setTextColor(Color.parseColor("#FFFFFF"));
		trackName.setTextColor(Color.parseColor("#FFFFFF"));
	}

	public void addTrackToCsv(String fileName){
		CsvWriter newCsvWriter = new CsvWriter();
		try {
			if(newCsvWriter.writeInInternalStorageCsv(userChoice, fileName+".csv",MusicPlayer.this)){
				String text = "Track successfully added to " + fileName + "!";
				Toast toast = Toast.makeText(MusicPlayer.this, text, Toast.LENGTH_LONG);
				toast.show();
			}else{
				String text = "Track already added to " + fileName + "!";
				Toast toast = Toast.makeText(MusicPlayer.this, text, Toast.LENGTH_LONG);
				toast.show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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