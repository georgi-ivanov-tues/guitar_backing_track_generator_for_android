package com.example.guitarbacktrackgenerator;

import android.annotation.SuppressLint;
import java.io.IOException;
import java.util.ArrayList;

public class Sort {
	ArrayList<String[]> tracks;
	int index;
	
	Sort(ArrayList<String[]> tracksToBeSorted, int sortByCounter){
		tracks = tracksToBeSorted;
		if(sortByCounter == 0)
			index = 3; // Name is 3 index
		else if(sortByCounter == 1)
			index = 0; // Key is 0 index
		else if(sortByCounter == 2)	
			index = 5; // Most Played is 5 index
	}
	
	@SuppressLint("DefaultLocale")
	public void sort() throws IOException{
		int shortestStringIndex;
		for(int j=0; j < tracks.size() - 1;j++){
		     shortestStringIndex = j;
		     for (int i=j+1 ; i < tracks.size(); i++){
		    	 if(index == 5){ // Sort by Most Played (We need to compare integers)
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
	
	public ArrayList<String[]> getTracks(){
		return tracks;
	}
}
