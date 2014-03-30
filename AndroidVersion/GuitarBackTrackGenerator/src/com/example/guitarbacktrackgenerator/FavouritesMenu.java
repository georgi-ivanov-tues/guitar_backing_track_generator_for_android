package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FavouritesMenu extends Activity{

	Button buttonPlay, buttonSwitchToOtherView, buttonSortBy, buttonRemoveTrack, buttonRemoveAllTracks, buttonExit;
	TextView title, textViewFavouritesMenu;
	TextView[] textView;
	ArrayList<String[]> tracks = null;
	LinearLayout linearLayout;
	Spinner spinner;
	String trackSelectedName = "", currentView = "favourites";
	int viewCounter = 0, sortByCounter = -1; // favorites

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourites_menu);
		
		buttonPlay = (Button) findViewById(R.id.buttonPlay);
		buttonRemoveTrack = (Button) findViewById(R.id.buttonRemoveTrack);
		buttonRemoveAllTracks = (Button) findViewById(R.id.buttonRemoveAllTracks);
		buttonSwitchToOtherView = (Button) findViewById(R.id.buttonSwitchToOtherView);
		buttonSwitchToOtherView.setText("Switch To Recordings");
		buttonExit = (Button) findViewById(R.id.buttonExit);
		//spinner = (Spinner)findViewById(R.id.spinner);
		buttonSortBy = (Button) findViewById(R.id.buttonSortBy);
		title = (TextView) findViewById(R.id.Title);
		textViewFavouritesMenu = (TextView) findViewById(R.id.textViewFavouritesMenu);
		linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
				
		try {
			selectingTracks(printTracks(currentView));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				if(("").equals(trackSelectedName)){
					String text = "Please select a track first!";
					Toast toast = Toast.makeText(FavouritesMenu.this, text, 5);
					toast.show();
				}else{
					CsvReader newCsvReader = new CsvReader();
					Bundle newBundle = new Bundle();
					try {
						newBundle.putStringArray(null, newCsvReader.findTrackByName(currentView+".csv", trackSelectedName, FavouritesMenu.this));
					} catch (IOException e) {
						e.printStackTrace();
					}
					Intent MusicPlayer = new Intent(FavouritesMenu.this, MusicPlayer.class);
					MusicPlayer.putExtras(newBundle);
					FavouritesMenu.this.startActivity(MusicPlayer);
				}
			}
		});
				
		buttonSwitchToOtherView.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("DefaultLocale")
			@Override
			public void onClick(View v) {
				if(viewCounter == 0)
					viewCounter++;
				else if(viewCounter == 1)
					viewCounter = 0;
				
				String otherView = currentView;
				currentView = changeViewCounter(viewCounter);
				linearLayout.removeAllViews();
				trackSelectedName = "";
				sortByCounter = -1;
				buttonSortBy.setText("Sort By");
				textViewFavouritesMenu.setText(currentView.substring(0, 1).toUpperCase() + currentView.substring(1));
				buttonSwitchToOtherView.setText("Switch to " + otherView.substring(0, 1).toUpperCase() + otherView.substring(1));
				
				try {
					selectingTracks(printTracks(currentView));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		buttonSortBy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Sort By: Name, Key, Most Played
				if(sortByCounter == 2)
					sortByCounter = 0;
				else
					sortByCounter++;
				
				buttonSortBy.setText("Sort By: " + sortBy(sortByCounter));
				sortBy(sortByCounter);
			}
		});
		
		buttonRemoveTrack.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				if(("").equals(trackSelectedName)){
					String text = "Please select a track first!";
					Toast toast = Toast.makeText(FavouritesMenu.this, text, 5);
					toast.show();
				}else{
					CsvWriter newCsvWriter = new CsvWriter();
					try {
						newCsvWriter.removeTrack(trackSelectedName, currentView+".csv", FavouritesMenu.this);
					} catch (IOException e) {
						e.printStackTrace();
					}
					linearLayout.removeAllViews();
					String text = "Track successfully removed";
					Toast toast = Toast.makeText(FavouritesMenu.this, text, 5);
					toast.show();
					try {
						selectingTracks(printTracks(currentView));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		buttonRemoveAllTracks.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				deleteFile(currentView+".csv");
				linearLayout.removeAllViews(); // Not sure if OK. Find a better way maybe.
				String text = "All tracks successfully removed";
				Toast toast = Toast.makeText(FavouritesMenu.this, text, 5);
				toast.show();
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
	
	public String changeViewCounter(int viewCounter){
		String[] view = {"favourites", "recordings"};
		return view[viewCounter];
	}
	
	public String sortBy(int sortByCounter){
		if(sortByCounter == 0)
			sortByName();
		else if(sortByCounter == 1)
			sortByKey();
		else if(sortByCounter == 2)	
			sortByMostPlayed();
		
		String[] sortBy = {"Name", "Key", "Most Played"};
		return sortBy[sortByCounter];
		
// 		WITH ANOTHER CLASS
//		Sort newSort = new Sort();
//		if(sortByCounter == 0)
//			newSort.sortByName(textView, linearLayout);
//		else if(sortByCounter == 1)
//			newSort.sortByKey();
//		else if(sortByCounter == 2)	
//			newSort.sortByMostPlayed();
//		
//		String[] sortBy = {"Name", "Key", "Most Played"};
//		return sortBy[sortByCounter];
	}
	
	public void sortByName(){
		String[] tracks = new String[textView.length];
		for(int i = 0; i < textView.length; i++) {
			tracks[i] = (String) textView[i].getText();
		}
		linearLayout.removeAllViews();
		Arrays.sort(tracks);
		
		for(int i = 0; i < textView.length; i++){
			textView[i].setText(tracks[i]);
			linearLayout.addView(textView[i]);
		}
	}
	
	public void sortByKey(){
		
	}
	
	public void sortByMostPlayed(){
		
	}
	
	public int printTracks(String fileName) throws IOException{
		CsvReader newCsvReader = new CsvReader();
		tracks = newCsvReader.readFromInternalStorageCsv(fileName+".csv", FavouritesMenu.this);
		
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
	
	public void selectingTracks(int numOfTracks){
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
	}	
}

//String[] items = new String[]{"Aphabeticaly", "Most Played", "Key"};
//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
//spinner.setAdapter(adapter);

// spinner = (Spinner) findViewById(R.id.spinner);
//    List<String> list = new ArrayList<String>();
//    list.add("Android");
//    list.add("Java");
//    list.add("Spinner Data");
//    list.add("Spinner Adapter");
//    list.add("Spinner Example");
//     
//    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
//                  
//    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                  
//    spinner.setAdapter(dataAdapter);


//buttonSortBy.setOnClickListener(new View.OnClickListener() {
//@SuppressLint("NewApi")
//@Override  
//public void onClick(View v) {  
//PopupMenu pop_up = new PopupMenu(FavouritesMenu.this, buttonSortBy);  
//pop_up.getMenuInflater().inflate(R.menu.pop_up_menu, pop_up.getMenu());  
//pop_up.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
// @SuppressLint("NewApi")
//public boolean onMenuItemClick(MenuItem item) {  
//  Toast.makeText(FavouritesMenu.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
//  return true;  
// }  
//});  
//pop_up.show();
//}
//});
    	         