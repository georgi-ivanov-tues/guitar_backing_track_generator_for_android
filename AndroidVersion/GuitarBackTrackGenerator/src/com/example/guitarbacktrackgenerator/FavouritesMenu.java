package com.example.guitarbacktrackgenerator;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FavouritesMenu extends Activity{

	Button buttonRemoveTrack, buttonRemoveAllTracks, buttonPrintTracksInLog, exit;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites_menu);
		
		buttonRemoveTrack = (Button) findViewById(R.id.buttonRemoveTrack);
		buttonRemoveAllTracks = (Button) findViewById(R.id.buttonRemoveAllTracks);
		buttonPrintTracksInLog = (Button) findViewById(R.id.buttonPrintTracksInLog);
		exit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		//changeTextViewColors();

		buttonPrintTracksInLog.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CsvReader newCsvReader = new CsvReader();
				try {
					newCsvReader.readFromInternalStorageCsv("favouriteTracks.csv",FavouritesMenu.this);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		buttonRemoveAllTracks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteFile("favouriteTracks.csv");
			}
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent backToMainActivity = new Intent(FavouritesMenu.this, MainActivity.class);
				FavouritesMenu.this.startActivity(backToMainActivity);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//	void changeTextViewColors(){
//		title.setTextColor(Color.parseColor("#FFFFFF"));
//	}
}