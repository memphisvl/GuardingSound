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
    LOW_1_SEC("17500Hz_fade_filtered.mp3"),
    HIGH_1_SEC("20600Hz_filtered.mp3");

    private static Map<Integer, SoundType> soundItems = new HashMap<Integer, SoundType>() {
        {
            put(0, DRAGON_FLY);
            put(1, DRAGON_FLY_ULTRA);
            put(2, LOW_1_SEC);
            put(3, HIGH_1_SEC);
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