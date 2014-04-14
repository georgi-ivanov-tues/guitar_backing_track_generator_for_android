package com.example.guitarbacktrackgenerator;

import java.io.File;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsMenu extends Activity {
	Button exit, shareInSoundcloudButton, doesTrackExistButton;
	TextView display;
	
	private static final String CLIENT_ID = "fdb037658f862774d00e2f94816ec0e4";
	private static final int SHARE_SOUND  = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_menu);

		exit = (Button) findViewById(R.id.buttonExit);
		shareInSoundcloudButton = (Button) findViewById(R.id.shareInSoundcloudButton);
		
		shareInSoundcloudButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				File audio = new File("Environment.getExternalStorageDirectory().getAbsolutePath()"
						   + "/Download/demo.3gp");
				Intent intent = new Intent(Intent.ACTION_SEND).setType("audio/soundcloud");
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(audio));
				startActivity(Intent.createChooser(intent, "Share to"));
				shareSound();
				/*Intent sendFile = new Intent(Intent.ACTION_SEND);
				
				String path = Environment.getExternalStorageDirectory().getAbsolutePath()
						   + "/Download/";
				
				sendFile.putExtra(Intent.EXTRA_STREAM, Uri.parse(path));
				sendFile.setType("demo.3gp"); // the MIME type has to be an audio type for SoundCloud Droid to pick up the intent
				
				Intent intentChooser = Intent.createChooser(sendFile, "Send the file using:");
				        
				try{
				   startActivity(intentChooser);
				}
				catch (ActivityNotFoundException e){
				    String text = "Give up life!";
					Toast toast = Toast.makeText(OptionsMenu.this, text, Toast.LENGTH_LONG);
					toast.show();
				}		*/
			
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
	
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	 private void shareSound() {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					   + "/Download/" + "demo" + ".3gp";
			
			File file = new File(path);
			
	        Intent intent = new Intent("com.soundcloud.android.SHARE")
	                .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
	                // here you can set metadata for the track to be uploaded
	                .putExtra("com.soundcloud.android.extra.title", "SoundCloud Android Intent Demo upload")
	                .putExtra("com.soundcloud.android.extra.where", "Somewhere")
	                .putExtra("com.soundcloud.android.extra.description", "This is a demo track.")
	                .putExtra("com.soundcloud.android.extra.public", true)
	                .putExtra("com.soundcloud.android.extra.tags", new String[] {
	                    "demo",
	                    "post lolcat bluez",
	                    "soundcloud:created-with-client-id="+CLIENT_ID
	                 })
	                .putExtra("com.soundcloud.android.extra.genre", "Easy Listening");
	                //.putExtra("com.soundcloud.android.extra.location", getLocation());

	        // attach artwork if user has picked one
//	        if (mArtwork != null) {
//	            intent.putExtra("com.soundcloud.android.extra.artwork", Uri.fromFile(mArtwork));
//	        }

	        try {
	            startActivityForResult(intent, SHARE_SOUND);
	        } catch (ActivityNotFoundException notFound) {
	            // use doesn't have SoundCloud app installed, show a dialog box
	            //showDialog(DIALOG_NOT_INSTALLED);
	        	String text = "You should give up life!";
				Toast toast = Toast.makeText(OptionsMenu.this, text, Toast.LENGTH_LONG);
				toast.show();
	        }
	    }
	
}

