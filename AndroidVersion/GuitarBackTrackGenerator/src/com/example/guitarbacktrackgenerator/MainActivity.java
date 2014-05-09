package com.example.guitarbacktrackgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The main activity from which the application starts
 * @author Georgi Ivanov and Nedelcho Delechev
 */
public class MainActivity extends Activity {
	/**
	 * A button which opens the generate menu
	 */
	Button buttonGenerate;
	/**
	 * A button which opens the favourites/recordings menu
	 */
	Button buttonFavouritesRecordings;
	/**
	 * A button which opens the help menu
	 */
	Button buttonHelp;
	/**
	 * A button which opens the credits menu
	 */
	Button buttonCredits; 
	/**
	 * A button which exits the application
	 */
	Button buttonExit;
	/**
	 * The title of the menu
	 */
	TextView title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonGenerate = (Button) findViewById(R.id.buttonGenerate);
		buttonFavouritesRecordings = (Button) findViewById(R.id.buttonFavourites);
		buttonHelp = (Button) findViewById(R.id.buttonOptions);
		buttonCredits = (Button) findViewById(R.id.buttonCredits);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		
		title = (TextView) findViewById(R.id.Title);

		buttonGenerate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent GenerateMenu = new Intent(MainActivity.this, GenerateMenu.class);
		        MainActivity.this.startActivity(GenerateMenu);       
			}
		});

		buttonFavouritesRecordings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent FavouritesMenu = new Intent(MainActivity.this, FavouritesMenu.class);
		        MainActivity.this.startActivity(FavouritesMenu); 
			}
		});
		
		buttonHelp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent HelpMenu = new Intent(MainActivity.this, HelpMenu.class);
		        MainActivity.this.startActivity(HelpMenu); 
			}
		});
		
		buttonCredits.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent CreditsMenu = new Intent(MainActivity.this, CreditsMenu.class);
		        MainActivity.this.startActivity(CreditsMenu);  
			}
		});
		
		buttonExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
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
