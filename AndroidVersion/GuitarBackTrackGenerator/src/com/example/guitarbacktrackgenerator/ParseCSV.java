package com.example.guitarbacktrackgenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class ParseCSV{
	public ArrayList<String[]> parseCsv(String[] userChoice, String filePath) throws IOException{
		ArrayList<String[]> tracksThatMatchUserChoice = new ArrayList<String[]>();
		BufferedReader in = null;
		try{
			in = new BufferedReader(new FileReader(filePath));
		    String read = null;
		    
		    while((read = in.readLine()) != null){
		        String[] splited = read.split(",");
		        //System.out.println(splited);
		        
		        // Key, Mode, Style, Speed, Name, Path, Link to original track
		        if(splited[0].equals(userChoice[0]) && splited[1].equals(userChoice[1]) 
	 			   && splited[2].equals(userChoice[2]) && splited[3].equals(userChoice[3])){
		        	tracksThatMatchUserChoice.add(splited);
		        	System.out.println(splited);
		        }
		    }
		}catch (IOException e) {
		    System.out.println("There was a problem: " + e);
		    e.printStackTrace();
		}finally{
		    try{
		        in.close();
		    }catch (Exception e) {}
		}
	  		
		return tracksThatMatchUserChoice;
	}
}
