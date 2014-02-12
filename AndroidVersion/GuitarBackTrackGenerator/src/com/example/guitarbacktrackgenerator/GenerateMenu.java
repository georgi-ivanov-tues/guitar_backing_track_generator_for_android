package com.example.guitarbacktrackgenerator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GenerateMenu extends Activity {

		Button start;
		TextView display;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_generate_menu);
			start = (Button) findViewById(R.id.buttonStart);
			display = (TextView) findViewById(R.id.Title);

			start.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
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
