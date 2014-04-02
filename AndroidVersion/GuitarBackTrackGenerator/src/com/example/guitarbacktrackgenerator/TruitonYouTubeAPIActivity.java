package com.example.guitarbacktrackgenerator;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import android.os.Bundle;
import android.widget.Toast;

public class TruitonYouTubeAPIActivity extends YouTubeBaseActivity implements
YouTubePlayer.OnInitializedListener {

	// RANDOM COMMENT
    static private final String DEVELOPER_KEY = "AIzaSyA53I5DUsgUvpBwOyeqfIkl9N0g9cxcHCA";
    static private final String VIDEO = "dKLftgvYsVU";
    // Link na nqkav track = "hYc657vDySs"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_api);
        YouTubePlayerView youTubeView = (YouTubePlayerView)
        findViewById(R.id.youtube_view);
        youTubeView.initialize(DEVELOPER_KEY, this);
    }
    @Override
    public void onInitializationFailure(Provider provider,
        YouTubeInitializationResult error) {
        Toast.makeText(this, "Oh no! "+error.toString(),
            Toast.LENGTH_LONG).show();
    }
    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer player,
        boolean wasRestored) {
        player.loadVideo(VIDEO);
    }
}

