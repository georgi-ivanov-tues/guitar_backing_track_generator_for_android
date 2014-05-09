package com.example.guitarbacktrackgenerator;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A menu where the user can view the people credited for the creating of this application
 * @author Georgi Ivanov
 *
 */
public class CreditsMenu extends Activity {
	Button visitWebside, exit;
	TextView title;
	LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits_menu);
		visitWebside = (Button) findViewById(R.id.buttonVisitWebsite);
		exit = (Button) findViewById(R.id.buttonExit);
		title = (TextView) findViewById(R.id.Title);
		linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);

		prepareCreditsMenu();

		visitWebside.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("https://github.com/georgi-ivanov-tues/guitar_backing_track_generator_for_android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
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

	/**
	 * Displays all the text in the menu
	 */
	public void prepareCreditsMenu(){
		TextView[] textView = new TextView[3];
		textView[0] = new TextView(this);
		textView[1] = new TextView(this);
		textView[2] = new TextView(this);
		textView[0].setText("Created by: Georgi Ivanov and Nedelcho Delechev students from the 'Technological School of Electronic Systems' in Sofia, Bulgaria as a part of a school project for the subject 'Technology of Programing' with teacher Kiril Mitov." + "\n");
		textView[1].setText("We don not own any of the music used in this application. All rights belong to their respective owners!");

		textView[0].setTextSize(18);
		textView[1].setTextSize(18);

		textView[0].setTextColor(Color.parseColor("#FFFFFF"));
		textView[1].setTextColor(Color.parseColor("#FFFFFF"));
		linearLayout.setBackgroundColor(Color.TRANSPARENT);
		linearLayout.addView(textView[0]);
		linearLayout.addView(textView[1]);
	}
}
/*

Created by: Georgi Ivanov and Nedelcho Delechev students from 
the "Technological School of Electronic Systems" in Sofia, Bulgaria as a part of a school project 
for the subject "Technology of Programing" with teacher Kiril Mitov.

We don not own any of the music used in this application.
All rights belong to their respective owners.

 */