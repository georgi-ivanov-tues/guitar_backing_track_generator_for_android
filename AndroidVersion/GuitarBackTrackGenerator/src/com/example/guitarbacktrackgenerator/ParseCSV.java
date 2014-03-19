package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList; 
import java.util.Scanner;

public class ParseCSV{
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
}
