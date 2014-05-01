package com.example.guitarbacktrackgenerator;

import java.io.File;
import java.io.IOException;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

/**
 * A class for recording audio through the phone microphone
 * @author The Internet and Nedelcho Delchev
 */
public class AudioRecord {
	private static final String LOG_TAG = "AudioRecordTest";
	private MediaRecorder mRecorder = null;
	boolean success = true;
	
	File folder = new File(Environment.getExternalStorageDirectory() + "/GuitarRecordings");{
		if (!folder.exists()) {
			success = folder.mkdir();
		}
	}

	Long trackName = System.currentTimeMillis();
	
	String path = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/GuitarRecordings/" +  trackName + ".3gp";
	
	/**
	 * Changes the track's name acording to the current time
	 * @param time The current time
	 */
	void setTrackName(Long time){
		trackName = time;
	}
	
	/**
	 * It's called when the record button is clicked
	 * @param start True if the recording should start, false if the recording should stop
	 */
	void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	/**
	 * Starts the recording
	 */
	void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(path);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	/**
	 * Stops the recording
	 */
	public void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}
	
	/**
	 * Gets the name of the track
	 * @return The track's name
	 */
	public Long getTrackName(){
		return trackName;
	}

}