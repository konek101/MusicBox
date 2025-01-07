package com.gitub.musicbox;

import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.HashMap;
import java.util.Map;

public class MusicDiscSoundMapper {
    private static final Map<Material, Sound> DISC_TO_SOUND_MAP = new HashMap<>();

    static {
        // Existing music discs
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_13, Sound.MUSIC_DISC_13);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_CAT, Sound.MUSIC_DISC_CAT);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_BLOCKS, Sound.MUSIC_DISC_BLOCKS);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_CHIRP, Sound.MUSIC_DISC_CHIRP);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_FAR, Sound.MUSIC_DISC_FAR);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_MALL, Sound.MUSIC_DISC_MALL);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_MELLOHI, Sound.MUSIC_DISC_MELLOHI);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_STAL, Sound.MUSIC_DISC_STAL);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_STRAD, Sound.MUSIC_DISC_STRAD);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_WARD, Sound.MUSIC_DISC_WARD);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_11, Sound.MUSIC_DISC_11);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_WAIT, Sound.MUSIC_DISC_WAIT);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_OTHERSIDE, Sound.MUSIC_DISC_OTHERSIDE);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_5, Sound.MUSIC_DISC_5);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_PIGSTEP, Sound.MUSIC_DISC_PIGSTEP);

        // New music discs introduced in version 1.21
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_RELIC, Sound.MUSIC_DISC_RELIC);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_PRECIPICE, Sound.MUSIC_DISC_PRECIPICE);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_CREATOR, Sound.MUSIC_DISC_CREATOR);
        DISC_TO_SOUND_MAP.put(Material.MUSIC_DISC_CREATOR_MUSIC_BOX, Sound.MUSIC_DISC_CREATOR_MUSIC_BOX);
    }

    public static Sound getSoundForDisc(Material discMaterial) {
        return DISC_TO_SOUND_MAP.get(discMaterial);
    }
}
