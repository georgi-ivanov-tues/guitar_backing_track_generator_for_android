package com.example.guitarbacktrackgenerator;

import java.util.Arrays;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Sort {
	public void sortByName(TextView[] textView, LinearLayout linearLayout){
		String[] tracks = new String[textView.length];
		for(int i = 0; i < textView.length; i++) {
			tracks[i] = (String) textView[i].getText();
		}
		linearLayout.removeAllViews();
		Arrays.sort(tracks);
		
		for(int i = 0; i < textView.length; i++){
			textView[i].setText(tracks[i]);
			linearLayout.addView(textView[i]);
		}
	}
	
	public void sortByKey(){
		
	}
	
	public void sortByMostPlayed(){
		
	}
}
