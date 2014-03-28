package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Context;

public class CsvWriter {

	public boolean writeInInternalStorageCsv(String[] data,String fileName, Context context) throws IOException{
		boolean shouldTrackBeAdded = true;
		FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
		
		InputStream instream = context.openFileInput(fileName);
		InputStreamReader inputreader = new InputStreamReader(instream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		             
		String line;
		while ((line = buffreader.readLine()) != null) {
			String[] parsed = (line.split(","));
			if(parsed[3].equals(data[3])) // Checking if the track is already added to favorites
				shouldTrackBeAdded = false;
		}
	 
		instream.close();
		if(shouldTrackBeAdded){
			for(int i = 0; i < data.length; i++){
				fos.write(data[i].getBytes());
				fos.write(",".getBytes());
			}
			fos.write("\n".getBytes());
			fos.close();
		}
		
		return shouldTrackBeAdded;
	}
	
	public boolean removeTrack(String trackName ,String fileName, Context context) throws IOException{
		InputStream instream = context.openFileInput(fileName);
		InputStreamReader inputreader = new InputStreamReader(instream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		
		ArrayList<String[]> tracks = new ArrayList<String[]>();
		String line;
		while ((line = buffreader.readLine()) != null) {
			String[] parsed = (line.split(","));
			if(!parsed[3].equals(trackName)){ // Fix to search better!
				tracks.add(parsed);
			}
		}
		instream.close();
		
		FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE); // Replacing favourites.csv		              
		for(int i = 0; i < tracks.size(); i++){
			String[] temp = tracks.get(i);
			for(int i1 = 0; i1 <= 4; i1++){
				fos.write(temp[i1].getBytes());
				fos.write(",".getBytes());
			}
			fos.write("\n".getBytes());
		}
		fos.close();
		
		return true;
	}
}
