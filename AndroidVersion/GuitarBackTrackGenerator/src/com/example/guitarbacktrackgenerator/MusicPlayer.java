package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends YouTubeBaseActivity
implements YouTubePlayer.OnInitializedListener{
	
	Button buttonExit, buttonPlay, buttonPause, buttonAddToFavourites, buttonAddToRecordings, buttonStop;
	TextView title, trackName;
	String[] userChoice;
	static private final String DEVELOPER_KEY = "AIzaSyA53I5DUsgUvpBwOyeqfIkl9N0g9cxcHCA";
	static private String VIDEO = "dKLftgvYsVU";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music_player);

		buttonAddToFavourites = (Button) findViewById(R.id.buttonAddToFavourites);
		buttonAddToRecordings = (Button) findViewById(R.id.buttonAddToRecordings);
		buttonExit = (Button) findViewById(R.id.buttonExit);
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