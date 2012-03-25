package com.weirdapparatus.guardingsound.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum of energi saving types which can be selected by user
 *
 * @author memphis
 */
public enum EnergySavingType {
    PLAY_15_PAUSE_45(15000, 45000),
    PLAY_30_PAUSE_30(30000, 30000),
    PLAY_45_PAUSE_15(45000, 15000),
    ENDLESS;

    private static Map<Integer, EnergySavingType> energyItems = new HashMap<Integer, EnergySavingType>() {
        {
            put(0, PLAY_15_PAUSE_45);
            put(1, PLAY_30_PAUSE_30);
            put(2, PLAY_45_PAUSE_15);
            put(3, ENDLESS);
        }
    };

    // All in millis
    private int playTime;
    private int delayTime;

    private EnergySavingType() {
    }

    private EnergySavingType(int playTime, int delayTime) {
        this.playTime = playTime;
        this.delayTime = delayTime;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public static EnergySavingType getTypeById(int id) {
        return energyItems.get(id);
    }
}