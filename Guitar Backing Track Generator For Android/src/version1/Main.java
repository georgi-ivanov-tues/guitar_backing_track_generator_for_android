package version1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static Scanner newScanner;
	private static int option;

	public static void main(String[] args) throws IOException {
		while(true){
			printMainMenu();
			newScanner = new Scanner(System.in);
			option = newScanner.nextInt();
			
			if(option == 1){ // Generate 
				System.out.println("Generate");
				BackingTrack newBackingTrack = new BackingTrack();
				newBackingTrack.generateBackingTrack(parseCsv(userChoice())); // Vikame userChoice i predavame rezultata na parseCsv, koeto sled kato se izpalni predava rezutltata na generateBackingTrack
				newBackingTrack.play();
			}else if(option == 2){ // Options
				System.out.println("Options");
			}else if(option == 3){ // Credits
				System.out.println("Created by: Georgi Ivanov and Nedelcho Delchev");
			}else if(option == 4){ // Exit
				System.out.println("Exiting...");
				break;
			}else{
				System.out.println("Please choose a valid option!");
			}
		}
	}
	
	public static void printMainMenu(){
		System.out.println("GUITAR BACKING TRACK GENERATOR FOR ANDROID");
		System.out.println("Menu");
		System.out.println("1.Generate");
		System.out.println("2.Options");
		System.out.println("3.Credits");
		System.out.println("4.Exit");
	}
	
	public static String[] userChoice(){
		newScanner = new Scanner(System.in);
		String[] userChoice = new String[4];
		System.out.println("Please choose a key (A,A#,B,C,C#,D,D#,E,F,F#,G,G#)");
		userChoice[0] = newScanner.nextLine();
		System.out.println("Please choose a mode (maj, min)");
		userChoice[1] = newScanner.nextLine();
		System.out.println("Please choose a style (calm, heavy)");
		userChoice[2] = newScanner.nextLine();
		System.out.println("Please choose the speed of the track (slow, fast)");
		userChoice[3] = newScanner.nextLine();
		
		return userChoice;
	}
	
	public static ArrayList<String[]> parseCsv(String[] userChoice) throws IOException{
		ArrayList<String[]> tracksThatMatchUserChoice = new ArrayList<String[]>();
		BufferedReader in = null;
		try{
		    in = new BufferedReader(new FileReader("backingTracks.csv"));
		    String read = null;
		    while((read = in.readLine()) != null){
		        String[] splited = read.split(",");
		        
		        // Key, Mode, Style, Speed, Name, Path, Link to original track
		        if(splited[0].equals(userChoice[0]) && splited[1].equals(userChoice[1]) 
	 			   && splited[2].equals(userChoice[2]) && splited[3].equals(userChoice[3])){
		        	tracksThatMatchUserChoice.add(splited);
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
