package com.weirdapparatus.guardingsound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;
import com.weirdapparatus.guardingsound.common.EnergySavingType;
import com.weirdapparatus.guardingsound.common.SoundType;

import java.io.IOException;

/**
 * Main class for application
 *
 * @author memphis
 */
public class MainActivity extends Activity {

    private AssetFileDescriptor descriptor;
    private MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    private static SoundType selectedSound = SoundType.DRAGON_FLY; // Default sound
    private static EnergySavingType selectedEnergyType = EnergySavingType.ENDLESS; // Default energy

    private final Handler playbackHandler = new Handler();
    private final Handler resumeHandler = new Handler();

    private final Runnable pauseSound = new Runnable() {
        @Override
        public void run() {
            pauseSound(true);
        }
    };

    private final Runnable resumeSound = new Runnable() {
        @Override
        public void run() {
            playSound();
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initEnergyOptions();
        initSoundOptions();
        initHandlers();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mediaPlayer = new MediaPlayer();
        try {
            descriptor = getAssets().openFd("df_simple.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void initEnergyOptions() {
        final Spinner energyOptions = (Spinner) findViewById(R.id.energyType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.energy_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        energyOptions.setAdapter(adapter);

        energyOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(
                        parent.getContext(), "Energy settings: " +
                        parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG
                ).show();

                selectedEnergyType = EnergySavingType.getTypeById(pos);
                energyOptions.setSelection(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing here
            }
        });
        // Select default option
        energyOptions.setSelection(3);
    }

    private void initSoundOptions() {
        final Spinner soundOptions = (Spinner) findViewById(R.id.soundType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.sound_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        soundOptions.setAdapter(adapter);

        soundOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Toast.makeText(
                        parent.getContext(), "Selected sound: " +
                        parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG
                ).show();

                selectedSound = SoundType.getSoundTypeById(pos);
                soundOptions.setSelection(pos);

                // If we are playing sound, than stop playing
                final Button playStopButton = (Button) findViewById(R.id.playStopButton);
                playStopButton.setText("Play");
                isPlaying = false;
                mediaPlayer.stop();

                try {
                    mediaPlayer = new MediaPlayer();
                    descriptor = getAssets().openFd(selectedSound.getSound());
                    mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                    descriptor.close();
                    mediaPlayer.prepare();

                } catch (IOException e) {
                    Toast.makeText(parent.getContext(), "Cannot initialise playback", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing here
            }
        });
        // Select default option
        soundOptions.setSelection(0);
    }

    private void initHandlers() {
        final Button playStopButton = (Button) findViewById(R.id.playStopButton);

        playStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    playStopButton.setText("Play");
                    pauseSound(false);
                    isPlaying = false;
                } else {
                    playStopButton.setText("Stop");
                    playSound();
                    isPlaying = true;
                }
            }
        });
    }

    private void playSound() {
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

            if (!EnergySavingType.ENDLESS.equals(selectedEnergyType)) {
                playbackHandler.postDelayed(pauseSound, selectedEnergyType.getPlayTime()); // Play 15 seconds
            }
        }
    }

    private void pauseSound(boolean pausedByHandler) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();

            if (!EnergySavingType.ENDLESS.equals(selectedEnergyType) && pausedByHandler) {
                resumeHandler.postDelayed(resumeSound, selectedEnergyType.getDelayTime()); // Resume after 15 seconds
            }
        }
    }
}