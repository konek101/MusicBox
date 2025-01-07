package com.gitub.musicbox;

import org.bukkit.Material;
import java.util.HashMap;
import java.util.Map;

public class MusicDiscLengthMapper {
    private static final Map<Material, Integer> DISC_LENGTH_MAP = new HashMap<>();

    static {
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_13, 178);       // 2:58
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_CAT, 185);      // 3:05
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_BLOCKS, 345);   // 5:45
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_CHIRP, 185);    // 3:05
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_FAR, 174);      // 2:54
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_MALL, 197);     // 3:17
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_MELLOHI, 96);   // 1:36
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_STAL, 150);     // 2:30
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_STRAD, 188);    // 3:08
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_WARD, 251);     // 4:11
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_11, 71);        // 1:11
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_WAIT, 238);     // 3:58
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_OTHERSIDE, 202);// 3:22
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_5, 312);        // 5:12
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_PIGSTEP, 149);  // 2:29

        // Add durations for new discs in Minecraft 1.21
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_RELIC, 301);    // 5:01
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_PRECIPICE, 248);// 4:08
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_CREATOR, 263);  // 4:23
        DISC_LENGTH_MAP.put(Material.MUSIC_DISC_CREATOR_MUSIC_BOX, 95); // 1:35
    }

    public static int getDiscLength(Material discMaterial) {
        return DISC_LENGTH_MAP.getOrDefault(discMaterial, -1); // Return -1 if not found
    }
}

