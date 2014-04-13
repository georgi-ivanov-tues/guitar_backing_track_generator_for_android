package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class CsvWriter {

	@SuppressLint("SimpleDateFormat")
	public boolean writeInInternalStorageCsv(String[] data,String fileName, Context context, boolean newTrack) throws IOException{
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
			if(newTrack){
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss ");
				String strDate = sdf.format(c.getTime());
				
				Log.d("",strDate);
				fos.write(strDate.getBytes());
				fos.write(",0".getBytes()); // 0 for 0 times played. Fix later maybe... Fuck it...
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
			for(int i1 = 0; i1 < temp.length; i1++){
				fos.write(temp[i1].getBytes());
				fos.write(",".getBytes());
			}
			fos.write("0\n".getBytes());
		}
		fos.close();
		
		return true;
	}
	
	public boolean changeNumberOfTimesPlayed(String trackName ,String fileName, Context context) throws IOException{
		InputStream instream = context.openFileInput(fileName);
		InputStreamReader inputreader = new InputStreamReader(instream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		
		ArrayList<String[]> tracks = new ArrayList<String[]>();
		String line;
		while ((line = buffreader.readLine()) != null) {
			String[] parsed = (line.split(","));
			if(parsed[3].equals(trackName)){
				int temp = Integer.parseInt(parsed[6]) + 1;
				parsed[6] = Integer.toString(temp);
			}
			tracks.add(parsed);
		}
		instream.close();
		
		FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE); // Replacing favourites.csv		              
		for(int i = 0; i < tracks.size(); i++){
			String[] temp = tracks.get(i);
			for(int i1 = 0; i1 < temp.length; i1++){
				fos.write(temp[i1].getBytes());
				fos.write(",".getBytes());
			}
			fos.write("\n".getBytes());
		}
		fos.close();
		
		return true;
	}
	
	public void writeInCsv(String str1,String str2, Context context) throws IOException{
		FileOutputStream fos = context.openFileOutput("sorting_info.csv", Context.MODE_PRIVATE);
		fos.write(str1.getBytes());
		fos.write("\n".getBytes());
		fos.write(str2.getBytes());
	}
}
