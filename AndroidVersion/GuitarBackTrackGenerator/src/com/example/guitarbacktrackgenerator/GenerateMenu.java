package com.example.guitarbacktrackgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GenerateMenu extends Activity {
	
		Button buttonKey, buttonMaj, buttonMin, buttonCalm, buttonHeavy, buttonSlow, buttonFast, buttonPlay, buttonExit;
		TextView display; 

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
			
			display = (TextView) findViewById(R.id.Title);

			buttonPlay.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
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
