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
import android.widget.LinearLayout;
import android.widget.TextView;

public class FavouritesMenu extends Activity{

	Button buttonPlay, buttonRemoveTrack, buttonRemoveAllTracks, buttonExit;
	TextView title;
	TextView[] textView;
	LinearLayout linearLayout;
	String trackSelectedName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites_menu);
		
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonRemoveTrack = (Button) findViewById(R.id.buttonRemoveTrack);
		buttonRemoveAllTracks = (Button) findViewById(R.id.buttonRemoveAllTracks);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
		
		int numOfTracks = 0;
		try {
			numOfTracks = printTracks();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i < numOfTracks; i++) {
			final int num = numOfTracks; // Povrashtam na Javata..
			final TextView trackSelected = textView[i];
			trackSelected.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					for(int i = 0; i < num; i++) {
						textView[i].setTextColor(Color.parseColor("#FFFFFF"));
						textView[i].setBackgroundColor(Color.TRANSPARENT);
					}
					trackSelectedName = (String) trackSelected.getText();
					trackSelected.setTextColor(Color.parseColor("#000000"));
					trackSelected.setBackgroundColor(Color.parseColor("#FFFF00"));
				}
			});
		}
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(("").equals(trackSelectedName)){
					Log.d("","NO TRACK SELECTED!!!");
					// Replace with a pop-up message
				}else{
					CsvReader newCsvReader = new CsvReader();
					Bundle newBundle = new Bundle();
					try {
						newBundle.putStringArray(null, newCsvReader.findTrackByName("favouriteTracks.csv", trackSelectedName, FavouritesMenu.this));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Intent MusicPlayer = new Intent(FavouritesMenu.this, MusicPlayer.class);
					MusicPlayer.putExtras(newBundle);
					FavouritesMenu.this.startActivity(MusicPlayer);
				}
			}
		});
		
		buttonRemoveTrack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CsvWriter newCsvWriter = new CsvWriter();
				try {
					newCsvWriter.removeTrack(trackSelectedName, "favouriteTracks.csv", FavouritesMenu.this);
				} catch (IOException e) {
					e.printStackTrace();
				}
				linearLayout.removeAllViews();
				try {
					printTracks();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		buttonRemoveAllTracks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteFile("favouriteTracks.csv");
				linearLayout.removeAllViews(); // Not sure if OK. Find a better way maybe.
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
	
	public int printTracks() throws IOException{
		CsvReader newCsvReader = new CsvReader();
		final ArrayList<String[]> tracks = newCsvReader.readFromInternalStorageCsv("favouriteTracks.csv", FavouritesMenu.this);
		
		textView = new TextView[tracks.size()];
		int i;
		for(i = 0; i < tracks.size(); i++) {
			textView[i] = new TextView(this);
		}
		
		for(i = 0; i < tracks.size(); i++){
			String trackName = tracks.get(i)[3];
			textView[i].setText(trackName);
			textView[i].setTextColor(Color.parseColor("#FFFFFF"));
			linearLayout.setBackgroundColor(Color.TRANSPARENT);
			linearLayout.addView(textView[i]);
		}
		
		return tracks.size();
	}
}