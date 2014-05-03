package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList; 
import java.util.Scanner;
import android.content.Context;
import android.util.Log;

/**
 * A class for parsing and reading from csv files
 * @author Georgi Ivanov
 */
public class CsvReader{
	/**
	 * Parses a csv file from the assets folder of the device
	 * @param userChoice The users choice of key, mode and style
	 * @param inputStream The location to the file that'll be parsed
	 * @return An ArrayList of string arrays which contains the data from the csv file
	 * @throws IOException
	 */
	public ArrayList<String[]> parseCsv(final String[] userChoice, final InputStream inputStream) throws IOException{

		final ArrayList<String[]> tracksThatMatchUserChoice = new ArrayList<String[]>();
		Thread newThread = new Thread(new Runnable() {
			public void run() {
				try{
					Scanner newScanner = new Scanner(inputStream);
					while(newScanner.hasNextLine()){
						String[] parsed = (newScanner.nextLine()).split(",");
						if(parsed[0].equals(userChoice[0]) && parsed[1].equals(userChoice[1]) && parsed[2].equals(userChoice[2])){
							tracksThatMatchUserChoice.add(parsed);
						}
					}
				}catch (Exception e) {}
				Log.d("Thread Name = ", Thread.currentThread().getName());
			}
		});

		newThread.start();
		try {
			newThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return tracksThatMatchUserChoice;
	}

	/**
	 * Parses a file from the internal storage of the device
	 * @param fileName The name of the file to be parsed
	 * @param context The activity from which it should be executed
	 * @return An ArrayList of string arrays which contains the data from the csv file
	 * @throws IOException
	 */
	public ArrayList<String[]> readFromInternalStorageCsv(final String fileName, final Context context) throws IOException{
		final ArrayList<String[]> tracks = new ArrayList<String[]>();

		Thread newThread = new Thread(new Runnable() {
			public void run() {
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
				} catch (java.io.FileNotFoundException e) {} catch (IOException e) {
					e.printStackTrace();
				}	
				Log.d("Thread Name = ", Thread.currentThread().getName());
			}
		});
		
		newThread.start();
		try {
			newThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return tracks;
	}

	
	String[] track = null;
	/**
	 * Finds a track by it's name and returns the whole information for the track
	 * @param fileName The name of the file to be searched
	 * @param trackName The name of the track that is searched
	 * @param context he activity from which it should be executed
	 * @return An array of stings holding the information for the searched track
	 * @throws IOException
	 */
	public String[] findTrackByName(final String fileName, final String trackName, final Context context) throws IOException{

		Thread newThread = new Thread(new Runnable() {
			public void run() {
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
				} catch (java.io.FileNotFoundException e) {} catch (IOException e) {
					e.printStackTrace();
				}
				Log.d("Thread Name = ", Thread.currentThread().getName());
			}
		});

		newThread.start();
		try {
			newThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return track;
	}
}

