package com.example.guitarbacktrackgenerator;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class for sorting csv files
 * @author Georgi Ivanov
 */
public class Sort {
	ArrayList<String[]> tracks;
	int index;

	/**
	 * Taking the necessary information before the start the sorting
	 * @param tracksToBeSorted The tracks that need sorting
	 * @param sortByCounter The counter that counts the amount of times the button is clicked thus showing by which index the tracks should be sorted
	 */
	Sort(ArrayList<String[]> tracksToBeSorted, int sortByCounter){
		tracks = tracksToBeSorted;
		if(sortByCounter == 0)
			index = 3; // Name is 3 index
		else if(sortByCounter == 1)
			index = 0; // Key is 0 index
		else if(sortByCounter == 2)	
			index = 6; // Most Played is 6 index
	}

	/**
	 * Sorts the given csv
	 * @throws IOException
	 */
	@SuppressLint("DefaultLocale")
	public void sort() throws IOException{
		int shortestStringIndex;
		for(int j=0; j < tracks.size() - 1;j++){
			shortestStringIndex = j;
			for (int i=j+1 ; i < tracks.size(); i++){
				if(index == 6){ // Sort by Most Played (We need to compare integers)
					if(Integer.parseInt(tracks.get(i)[index]) > Integer.parseInt(tracks.get(shortestStringIndex)[index]))
						shortestStringIndex = i;  
				}else{
					//We keep track of the index to the smallest string
					if(tracks.get(i)[index].trim().toLowerCase().compareTo(tracks.get(shortestStringIndex)[index].trim().toLowerCase())<0)
						shortestStringIndex = i;  
				}
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
	}

	/**
	 * Returns the tracks
	 * @return Returns the tracks
	 */
	public ArrayList<String[]> getTracks(){
		return tracks;
	}
}
