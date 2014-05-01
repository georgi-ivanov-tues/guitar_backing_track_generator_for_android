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

/**
 * A class for writing in csv files
 * @author Georgi Ivanov
 */
public class CsvWriter {

	/**
	 * Writes in a csv file that is in the internal storage
	 * @param data The data to be written
	 * @param fileName The name of the file where the data should be written
	 * @param context The activity from which it should be executed
	 * @param newTrack True if the track is new, false if the track is already there
	 * @return True if the track is added, false if it's not
	 * @throws IOException
	 */
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
				
				fos.write(strDate.getBytes());
				fos.write(",0".getBytes()); // 0 for 0 times played. Fix later maybe... Fuck it...
			}
			fos.write("\n".getBytes());
			fos.close();
		}
		
		return shouldTrackBeAdded;
	}
	
	/**
	 * Removes a track from a given csv file
	 * @param trackName The name of the track that is to be removed
	 * @param fileName The name of the file from where the track should be removed
	 * @param context The activity from which it should be executed
	 * @throws IOException
	 */
	public void removeTrack(String trackName ,String fileName, Context context) throws IOException{
		InputStream instream = context.openFileInput(fileName);
		InputStreamReader inputreader = new InputStreamReader(instream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		
		ArrayList<String[]> tracks = new ArrayList<String[]>();
		String line;
		while ((line = buffreader.readLine()) != null) {
			String[] parsed = (line.split(","));
			if(!parsed[3].equals(trackName)){
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
	}
	
	/**
	 * Changes the number of times a track has been played
	 * @param trackName The name of the track that is played
	 * @param fileName The name of the file from which the track is being played
	 * @param context The activity from which it should be executed
	 * @throws IOException
	 */
	public void changeNumberOfTimesPlayed(String trackName ,String fileName, Context context) throws IOException{
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
	}
	
	/**
	 * Writes how the tracks are sorted in sorting_info.csv
	 * @param favouritesSortedBy The information about the user's favourite tracks
	 * @param recordingsSortedBy The information about the user's recordings
	 * @param context The activity from which it should be executed
	 * @throws IOException
	 */
	public void writeInCsv(String favouritesSortedBy,String recordingsSortedBy, Context context) throws IOException{
		FileOutputStream fos = context.openFileOutput("sorting_info.csv", Context.MODE_PRIVATE);
		fos.write(favouritesSortedBy.getBytes());
		fos.write("\n".getBytes());
		fos.write(recordingsSortedBy.getBytes());
	}
}
