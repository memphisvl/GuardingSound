package com.weirdapparatus.guardingsound.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum of sound type which can be selected by user
 *
 * @author memphis
 */
public enum SoundType {
    DRAGON_FLY("df_simple.mp3"),
    DRAGON_FLY_ULTRA("dragonfly.mp3"),
    LOW_100_MS("17500Hz100ms.wav"),
    HIGH_100_MS("20600Hz100ms.wav");

    private static Map<Integer, SoundType> soundItems = new HashMap<Integer, SoundType>() {
        {
            put(0, DRAGON_FLY);
            put(1, DRAGON_FLY_ULTRA);
            put(2, LOW_100_MS);
            put(3, HIGH_100_MS);
        }
    };

    private String sound;

    private SoundType(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound;
    }

    public static SoundType getSoundTypeById(int id) {
        return soundItems.get(id);
    }
}