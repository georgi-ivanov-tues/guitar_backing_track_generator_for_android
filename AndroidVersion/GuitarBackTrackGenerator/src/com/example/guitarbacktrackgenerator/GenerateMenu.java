package com.example.guitarbacktrackgenerator;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener; 
import android.widget.TextView;

public class GenerateMenu extends Activity {
	
		Button buttonKey, buttonMaj, buttonMin, buttonCalm, buttonHeavy, buttonSlow, buttonFast, buttonPlay, buttonExit;
		TextView title; 

		String key, mode, style, speed;
		String[] userChoice = new String[4];
		
		@SuppressLint("NewApi")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_generate_menu);
			buttonKey = (Button) findViewById(R.id.buttonKey);
			buttonMaj = (Button) findViewById(R.id.buttonMaj);
			buttonMin = (Button) findViewById(R.id.buttonMin);
			buttonCalm = (Button) findViewById(R.id.buttonCalm);
			buttonHeavy = (Button) findViewById(R.id.buttonHeavy);
			buttonSlow = (Button) findViewById(R.id.buttonSlow);
			buttonFast = (Button) findViewById(R.id.buttonFast);
			buttonPlay = (Button) findViewById(R.id.buttonPlay);
			buttonExit = (Button) findViewById(R.id.buttonExit);
			
			title = (TextView) findViewById(R.id.Title);
			
			buttonKey.setOnClickListener(new OnClickListener() {
				 @SuppressLint("NewApi")
				@Override  
		           public void onClick(View v) {  
		            PopupMenu pop_up = new PopupMenu(GenerateMenu.this, buttonKey);  
		            pop_up.getMenuInflater().inflate(R.menu.pop_up_menu, pop_up.getMenu());  
		            pop_up.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
		             @SuppressLint("NewApi")
					public boolean onMenuItemClick(MenuItem item) {  
		              Toast.makeText(GenerateMenu.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
		              return true;  
		             }  
		            });  
		            pop_up.show();
		           } 
			});
			
			buttonMaj.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mode = "maj";
				}
			});
			
			buttonMin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					mode = "min";
				}
			});
			
			buttonCalm.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					style = "calm";
				}
			});
			
			buttonHeavy.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					style = "heavy";
				}
			});
			
			buttonSlow.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					speed = "slow";
				}
			});
			
			buttonFast.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					speed = "fast";
				}
			});
			
			buttonPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					userChoice[0] = key;
					userChoice[1] = mode;
					userChoice[2] = style;
					userChoice[3] = speed;
					
					Bundle newBundle=new Bundle();
					newBundle.putStringArray(null, userChoice);
					Intent MusicPlayer = new Intent(GenerateMenu.this, MusicPlayer.class);
					MusicPlayer.putExtras(newBundle);
					GenerateMenu.this.startActivity(MusicPlayer); 
				}
			});
			
			buttonExit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent BackToMainActivity = new Intent(GenerateMenu.this, MainActivity.class);
					GenerateMenu.this.startActivity(BackToMainActivity); 
				}
			});
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.main, menu);
			return true;
		}
}
