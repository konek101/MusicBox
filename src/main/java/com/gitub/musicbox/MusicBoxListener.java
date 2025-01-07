package com.gitub.musicbox;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.gitub.musicbox.MusicBox.*;

public class MusicBoxListener implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        length.putIfAbsent(player, new HashMap<>());
    }

    @EventHandler
    public void onTick(ServerTickStartEvent event) {
        for (Map.Entry<Player, HashMap<Material, Long>> entry : playingDiscs.entrySet()) {
            Player player = entry.getKey();
            HashMap<Material, Long> map = entry.getValue();
            long currenttime = System.currentTimeMillis()/1000;
            if (!player.isOnline() || !isRepeatOn.containsKey(player) || !isRepeatOn.get(player)) {
                for (Map.Entry<Material, Long> entry1 : map.entrySet()) {
                    Material material = entry1.getKey();
                    if (entry1.getValue()+length.get(player).getOrDefault(MusicDiscSoundMapper.getSoundForDisc(material), defaultLength.get(material)) <= currenttime) {
                        player.stopSound(MusicDiscSoundMapper.getSoundForDisc(material));
                        playingDiscs.remove(player);
                    }
                }
                return;
            }


            for (Map.Entry<Material, Long> entry1 : map.entrySet()) {
                Material material = entry1.getKey();
                if (entry1.getValue()+length.get(player).getOrDefault(MusicDiscSoundMapper.getSoundForDisc(material), defaultLength.get(material)) <= currenttime) {
                    player.stopSound(MusicDiscSoundMapper.getSoundForDisc(material));
                    player.playSound(player.getLocation(), MusicDiscSoundMapper.getSoundForDisc(material), 10000000, 1);
                    map.put(material, currenttime);
                }
            }

        }
    }
}
