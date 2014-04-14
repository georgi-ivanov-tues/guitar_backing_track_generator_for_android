package com.example.guitarbacktrackgenerator;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class AudioRecord {
	private static final String LOG_TAG = "AudioRecordTest";
	private MediaRecorder mRecorder = null;
	private MediaPlayer   mPlayer = null;
	boolean success = true;
	
	File folder = new File(Environment.getExternalStorageDirectory() + "/GuitarRecordings");{
		if (!folder.exists()) {
			success = folder.mkdir();
		}
	}

	Long trackName = System.currentTimeMillis();
	
	String path = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/GuitarRecordings/" +  trackName + ".3gp";
	
	void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	void startPlaying() {

		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(path);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}


	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

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

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	public void onPause() {
		//super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	public Long getTrackName(){
		return trackName;
	}

}