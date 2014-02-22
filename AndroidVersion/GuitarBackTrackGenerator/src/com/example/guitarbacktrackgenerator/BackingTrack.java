package com.example.guitarbacktrackgenerator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javazoom.jl.player.Player;


public class BackingTrack{
    private String filename;
    private Player player; 
    private Thread thread;

    // constructor that takes the name of an MP3 file
    public BackingTrack(){
        this.filename = "";
    }

    public void setFileName(String filename){
    	this.filename = filename;
    }
    
    public void close() { 
    	if (player != null)
    		player.close(); 
    }

    // play the MP3 file to the sound card
    public void play(){
        try{
            FileInputStream fis = new FileInputStream(filename);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (Exception e){
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread(){
           public void run(){
                try { player.play(); }
                catch (Exception e){ System.out.println(e); }
                thread.start();
            }
        }.start();
    }
    
    
	public void stop(){thread.interrupt();} // FIX!
	
	public void generateBackingTrack(ArrayList<String[]> tracksThatMatchUserChoice){
		int randomNum = (int)(Math.random()*tracksThatMatchUserChoice.size());		
		// Key, Mode, Style, Speed, Name, Path, Link to original track
		setFileName(tracksThatMatchUserChoice.get(randomNum)[5]); //filename = Linka na random track
	}
}

// SOURCE: http://introcs.cs.princeton.edu/java/faq/mp3/mp3.html

