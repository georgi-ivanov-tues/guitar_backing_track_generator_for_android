package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FavouritesMenu extends Activity{

	Button buttonPlay, buttonSwitchToOtherView, buttonSortBy, buttonRemoveTrack, buttonRemoveAllTracks, buttonExit;
	TextView title, textViewFavouritesMenu;
	TextView[] textView;
	ArrayList<String[]> favouriteTracks = null, recordings = null;
	LinearLayout linearLayout;
	Spinner spinner;
	String trackSelectedName = "", currentView = "favourites";
	int viewCounter = 0, sortByCounter = -1;

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
			takeTracksFromCsv();
			selectingTracks(printTracks(viewCounter));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
//		String[] items = new String[]{"Name", "Key", "Most Played"};
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
//		spinner.setAdapter(adapter);
		
		
		//String spin = spinner.getSelectedItem().toString();
        //Log.d("", spin);
        //tv.setText(spin);
		
		buttonPlay.setOnClickListener(new View.OnClickListener() {
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
						newCsvWriter.ICantThinkOfAName(trackSelectedName, currentView+".csv", FavouritesMenu.this);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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
					selectingTracks(printTracks(viewCounter));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
//		a negative int if this < that
//		0 if this == that
//		a positive int if this > that
		
		//if(Integer.toString(Integer.valueOf(android.os.Build.VERSION.SDK)).compareTo("11") >= 0){

		// 		NEW API SHIT...		
//		buttonSortBy.setOnClickListener(new View.OnClickListener() {
//			@SuppressLint("NewApi")
//			@Override  
//			public void onClick(View v) {  
//				PopupMenu pop_up = new PopupMenu(FavouritesMenu.this, buttonSortBy);  
//				pop_up.getMenuInflater().inflate(R.menu.pop_up_menu, pop_up.getMenu());  
//				pop_up.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
//					@SuppressLint("NewApi")
//					public boolean onMenuItemClick(MenuItem item) {  
//						//Toast.makeText(FavouritesMenu.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
//						if(sortByCounter == 2)
//							sortByCounter = 0;
//						else
//							sortByCounter++;
//							
//						try {
//							buttonSortBy.setText("Sort By: " + sortBy(sortByCounter, viewCounter));
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//						return true;  
//					}  
//				});  
//				pop_up.show();
//			}
//		});
	
		buttonSortBy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(sortByCounter == 2)
					sortByCounter = 0;
				else
					sortByCounter++;
				
				replaceSelected(trackSelectedName);
				
				try {
					buttonSortBy.setText("Sort By: " + sortBy(sortByCounter, viewCounter));
				} catch (IOException e) {
					e.printStackTrace();
				}
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
						selectingTracks(printTracks(viewCounter));
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
	
	public String sortBy(int sortByCounter, int viewCounter) throws IOException{
		sort(viewCounter, sortByCounter);
		String[] sortBy = {"Name", "Key", "Most Played"};
		return sortBy[sortByCounter];
	}
	
	public void sort(int viewCounter, int sortByCounter) throws IOException{
		ArrayList<String []> tracks = null;
		// Different array according to the view
		if(viewCounter == 0)
			tracks = favouriteTracks;
		else if(viewCounter == 1)
			tracks = recordings;
		
		int sortByIndex = -1;
		// Different index to sort by according the clicked button
		if(sortByCounter == 0)
			sortByIndex = 3; // Name is 3 index
		else if(sortByCounter == 1)
			sortByIndex = 0; // Key is 0 index
		//else if(sortByCounter == 2)	
			//sortByIndex = 5; // Not yet...
			
		if(Integer.toString(sortByIndex).equals("-1")){ // Because comparing integers is to mainstream
			String text = "Not yet implemented";
			Toast toast = Toast.makeText(FavouritesMenu.this, text, 5);
			toast.show();
		}else{			
			int shortestStringIndex;
			for(int j=0; j < tracks.size() - 1;j++){
			     shortestStringIndex = j;
			     for (int i=j+1 ; i < tracks.size(); i++){
			         //We keep track of the index to the smallest string
			         if(tracks.get(i)[sortByIndex].trim().compareTo(tracks.get(shortestStringIndex)[sortByIndex].trim())<0)
			             shortestStringIndex = i;  
			     }
			     //We only swap with the smallest string
			     if(shortestStringIndex != j){
					String[] temp = tracks.get(j);
					tracks.add(j, tracks.get(shortestStringIndex));
					tracks.remove(j+1);
					tracks.add(shortestStringIndex, temp);
					tracks.remove(shortestStringIndex+1);
			     }
			 }
			
			selectingTracks(printTracks(viewCounter));
		}
	}
	
	public void takeTracksFromCsv() throws IOException{
		CsvReader newCsvReader = new CsvReader();
		favouriteTracks = newCsvReader.readFromInternalStorageCsv("favourites.csv", FavouritesMenu.this);
		recordings = newCsvReader.readFromInternalStorageCsv("recordings.csv", FavouritesMenu.this);
	}
	
	public int printTracks(int viewCounter) throws IOException{
		ArrayList<String []> tracks = null;
		if(viewCounter == 0)
			tracks = favouriteTracks;
		else if(viewCounter == 1)
			tracks = recordings;
		
		textView = new TextView[tracks.size()];
		int i;
		for(i = 0; i < tracks.size(); i++) {
			textView[i] = new TextView(this);
		}
		
		linearLayout.removeAllViews();
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
	
	public static void replaceSelected(String replaceWith) {
	    try {
	        // input the file content to the String "input"
	        BufferedReader file = new BufferedReader(new FileReader("favourites.csv"));
	        String line;String input = "";

	        while ((line = file.readLine()) != null) 
	        	input += line + '\n';

	        input = input.replace("C,min,calm,c minor rock ballad guitar backing track,https://www.youtube.com/watch?v=t-YVZ8YM1zk,"
	        			, "C,min,calm,c minor KOTKA!!!,https://www.youtube.com/watch?v=t-YVZ8YM1zk,");
	        
	        //System.out.println(input); // check that it's inputed right

	        // this if structure determines whether or not to replace "0" or "1"
//	        if (Integer.parseInt(type) == 0) {
//	            input = input.replace(replaceWith + "1", replaceWith + "0"); 
//	        }
//	        else if (Integer.parseInt(type) == 1) {
//	            input = input.replace(replaceWith + "0", replaceWith + "1");
//	        } 

	        // check if the new input is right
	       // System.out.println("----------------------------------"  + '\n' + input);

	        // write the new String with the replaced line OVER the same file
	        FileOutputStream File = new FileOutputStream("favourites.csv");
	        File.write(input.getBytes());
	        
	        file.close();
	        File.close();

	    } catch (Exception e) {
	        System.out.println("Problem reading file.");
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