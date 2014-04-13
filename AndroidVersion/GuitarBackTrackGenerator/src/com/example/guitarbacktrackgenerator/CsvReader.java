package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList; 
import java.util.Scanner;

import android.content.Context;
import android.util.Log;

public class CsvReader{
	public ArrayList<String[]> parseCsv(String[] userChoice, InputStream inputStream) throws IOException{
		ArrayList<String[]> tracksThatMatchUserChoice = new ArrayList<String[]>();
		try{
		    Scanner newScanner = new Scanner(inputStream);
		    while(newScanner.hasNextLine()){
		    	String[] parsed = (newScanner.nextLine()).split(",");
		    	if(parsed[0].equals(userChoice[0]) && parsed[1].equals(userChoice[1]) && parsed[2].equals(userChoice[2])){
		    		tracksThatMatchUserChoice.add(parsed);
		    	}
		    }
		}catch (Exception e) {}
		return tracksThatMatchUserChoice;
	}
	
	public ArrayList<String[]> readFromInternalStorageCsv(String fileName, Context context) throws IOException{
		ArrayList<String[]> tracks = new ArrayList<String[]>();
		try {
			InputStream instream = context.openFileInput(fileName);
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			      
			String line;
			while ((line = buffreader.readLine()) != null) {
				String[] parsed = (line.split(","));
				tracks.add(parsed);
			}
			instream.close();
		} catch (java.io.FileNotFoundException e) {}	
	
		return tracks;
	}
	
	public String[] findTrackByName(String fileName, String trackName, Context context) throws IOException{
		String[] track = null;
		try {
			InputStream instream = context.openFileInput(fileName);
			InputStreamReader inputreader = new InputStreamReader(instream);
			BufferedReader buffreader = new BufferedReader(inputreader);
			String line;
			while ((line = buffreader.readLine()) != null) {
				String[] parsed = (line.split(","));
				if(parsed[3].equals(trackName)){
					track = parsed;
				}
			}
			instream.close();
		} catch (java.io.FileNotFoundException e) {}
		
		return track;
	}
}

