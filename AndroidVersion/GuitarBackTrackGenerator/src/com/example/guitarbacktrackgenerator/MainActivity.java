package com.example.guitarbacktrackgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button generate, favourites, options, credits, exit;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		generate = (Button) findViewById(R.id.buttonGenerate);
		favourites = (Button) findViewById(R.id.buttonFavourites);
		options = (Button) findViewById(R.id.buttonOptions);
		credits = (Button) findViewById(R.id.buttonCredits);
		exit = (Button) findViewById(R.id.buttonExit);
		
		title = (TextView) findViewById(R.id.Title);

		generate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent GenerateMenu = new Intent(MainActivity.this, GenerateMenu.class);
		        MainActivity.this.startActivity(GenerateMenu);       
			}
		});

		favourites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent FavouritesMenu = new Intent(MainActivity.this, FavouritesMenu.class);
		        MainActivity.this.startActivity(FavouritesMenu); 
			}
		});
		
		options.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent OptionsMenu = new Intent(MainActivity.this, OptionsMenu.class);
		        MainActivity.this.startActivity(OptionsMenu); 
			}
		});
		
		credits.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent CreditsMenu = new Intent(MainActivity.this, CreditsMenu.class);
		        MainActivity.this.startActivity(CreditsMenu);  
			}
		});
		
		exit.setOnClickListener(new View.OnClickListener() {
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
