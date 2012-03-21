package com.weirdapparatus.guardingsound;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initHandlers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(this, R.raw.dragonfly);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void initHandlers() {
        final Button playButton = (Button) findViewById(R.id.playButton);
        Button stopButton = (Button) findViewById(R.id.stopButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                isPlaying = true;
                playButton.setEnabled(false);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSound();
                isPlaying = false;
                playButton.setEnabled(true);
            }
        });
    }

    private void playSound() {
        if (mediaPlayer != null) {
//            mediaPlayer.setVolume(100.0f, 100.0f);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}