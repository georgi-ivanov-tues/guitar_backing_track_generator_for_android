package com.example.guitarbacktrackgenerator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A menu where the user can get some help about the application
 * @author Georgi Ivanov
 */
public class HelpMenu extends Activity {
	Button buttonExit;
	TextView display;
	LinearLayout linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_menu);

		linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
		buttonExit = (Button) findViewById(R.id.buttonExit);
		
		prepareHelpMenu();
		
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
	
	/**
	 * Displays all the text in the menu
	 */
	public void prepareHelpMenu(){
		TextView[] textView = new TextView[3];
		textView[0] = new TextView(this);
		textView[1] = new TextView(this);
		textView[2] = new TextView(this);
		textView[0].setText("Before Playing: " + "\n" + "Please make sure that you have the Youtube application installed on your phone before you start playing." + "\n");
		textView[1].setText("Recording: " + "\n" + "Keep in mind that if the volume of the speakers is too high you won't be able to hear your playing on the recording. That's why we recommend using external speakers in order to get a full and better sounding recording and/or turning the volume down" + "\n");
		textView[2].setText("Sharing: " + "\n" + "Before sharing please make sure that you are logged in the desired sharing application in order to be able to share your recording");
		
		//textView[0].
		
		textView[0].setTextSize(18);
		textView[1].setTextSize(18);
		textView[2].setTextSize(18);
		
		textView[0].setTextColor(Color.parseColor("#FFFFFF"));
		textView[1].setTextColor(Color.parseColor("#FFFFFF"));
		textView[2].setTextColor(Color.parseColor("#FFFFFF"));
		linearLayout.setBackgroundColor(Color.TRANSPARENT);
		linearLayout.addView(textView[0]);
		linearLayout.addView(textView[1]);
		linearLayout.addView(textView[2]);
	}

}


/*

About the Application:


Before Playing:
Please make sure that you have the Youtube application installed on your phone before you start playing.

Recording:
Keep in mind that if the volume of the speakers is too high you won't be able to hear your playing on the
recording. That's why we recommend using external speakers in order to get a full and better 
sounding recording and/or turning the volume down.

Sharing:
Before sharing please make sure that you are logged in the desired sharing application
in order to be able to share your recording

*/
