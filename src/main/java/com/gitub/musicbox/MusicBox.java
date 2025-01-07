package com.gitub.musicbox;

import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class MusicBox extends JavaPlugin implements Listener {

    public static HashMap<Material, Integer> defaultLength = new HashMap<>();
    public static HashMap<Player, HashMap<Sound, Integer>> length = new HashMap<>();
    public static HashMap<Player, Material> selectedDisc = new HashMap<>();
    public static HashMap<Player, HashMap<Material, Long>> playingDiscs = new HashMap<>();
    public static HashMap<Player, Boolean> isRepeatOn = new HashMap<>();
    public List<Material> getAllMusicDiscs() {
        List<Material> musicDiscs = new ArrayList<>();

        for (Material material : Material.values()) {
            if (material.isRecord()) { // Check if the material is a music disc
                musicDiscs.add(material);
            }
        }

        return musicDiscs;
    }

    public static SpiGUI spiGUI;
    @Override
    public void onEnable() {
        // Plugin startup logic
        spiGUI = new SpiGUI(this);
        for (Material material : getAllMusicDiscs()) {
            Sound sound = MusicDiscSoundMapper.getSoundForDisc(material);
            if (sound != null) {
                defaultLength.put(material, MusicDiscLengthMapper.getDiscLength(material));
            }
        }
        Bukkit.getServer().getPluginManager().registerEvents(new MusicBoxListener(), this);
    }





    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("musicbox")) {
            if (sender instanceof Player) {
                openGui((Player) sender);
            }
        }
        return true;
    }



    public static void toggleTimeButton(Player player, SGMenu menu) {
        SGButton TimeButton = new SGButton(
                new ItemBuilder(Material.CLOCK).name("Custom song length").build()
        ).withListener(event -> {

            String lore = "&aLeft Click to confirm&f | &cRight Click to cancel";
            for (int i = 0; i < 27; i++) {
                menu.setButton(2,i, new SGButton(
                        new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build()
                ));
            }
            AtomicInteger CurrentTime = new AtomicInteger(length.get(player).getOrDefault(MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)), defaultLength.get(selectedDisc.get(player))));
            menu.setButton(2, 10, new SGButton(
                    new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("-100s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(-100);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 11, new SGButton(
                    new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("-10s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(-10);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 12, new SGButton(
                    new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name("-1s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(-1);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 13, new SGButton(
                    new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                            lore
                    ).build()
            ).withListener(e -> {
                if (e.isLeftClick()) {
                    length.get(player).put(MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)), CurrentTime.get());
                    menu.setCurrentPage(0);
                    menu.refreshInventory(player);
                } else if (e.isRightClick()) {
                    menu.setCurrentPage(0);
                    menu.refreshInventory(player);
                }
            }));
            menu.setButton(2, 0, new SGButton(
                    new ItemBuilder(Material.NETHER_STAR).name("Set to default").build()
            ).withListener( e -> {
                length.get(player).put(MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)), defaultLength.get(selectedDisc.get(player)));
                menu.setCurrentPage(0);
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 14, new SGButton(
                    new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name("+1s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(1);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 15, new SGButton(
                    new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name("+10s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(10);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setButton(2, 16, new SGButton(
                    new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).name("+100s").build()
            ).withListener( e -> {
                CurrentTime.addAndGet(100);
                menu.getButton(2,13).setIcon(
                        new ItemBuilder(Material.CLOCK).name(CurrentTime + "s").lore(
                                lore
                        ).build()
                );
                menu.refreshInventory(player);
            }));
            menu.setCurrentPage(2);
            menu.refreshInventory(player);
        });
        if (menu.getButton(26) == TimeButton) {
            menu.setButton(26, new SGButton(
                    new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build())
            );
        } else {
            menu.setButton(26, TimeButton);
        }
    }

    public static void openGui(Player player) {
        SGMenu menu = MusicBox.spiGUI.create("MusicBox", 3);

        int slots = menu.getPageSize();
        for (int i = 0; i < slots; i++) {
            menu.setButton(0, i, new SGButton(
                    new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build()
            ).withListener(event -> {
            }));
        }
        menu.setButton(8, new SGButton(
                new ItemBuilder(playingDiscs.containsKey(player) ? Material.LIME_DYE : Material.RED_DYE).name("Play").build()
        ).withListener(event -> {
            Material newMaterial;
            if (Objects.requireNonNull(event.getCurrentItem()).getType() == Material.RED_DYE) {
                newMaterial = Material.LIME_DYE;
                HashMap<Material, Long> DiscThing = new HashMap<>();
                DiscThing.put(selectedDisc.get(player), System.currentTimeMillis()/1000);
                playingDiscs.put(player, DiscThing);
                player.playSound(player.getLocation(), MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)), 10000000, 1);
            } else {
                newMaterial = Material.RED_DYE;
                playingDiscs.remove(player);
                player.stopSound(MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)));
            }
            menu.getButton(8).setIcon(
                    new ItemBuilder(newMaterial).name(
                            newMaterial == Material.RED_DYE ? "Play" : "Stop"
                    ).amount(1).build()
            );
            menu.refreshInventory(player);
        }));

        menu.setButton(17, new SGButton(
                new ItemBuilder(Material.REPEATER).name(isRepeatOn.getOrDefault(player, false) ? "Repeat ON" : "Repeat OFF").amount(1).build()
        ).withListener(event -> {
            String name = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta().displayName()));
            String newName = name.equals("Repeat ON") ? "Repeat OFF" : "Repeat ON";
            menu.getButton(17).setIcon(
                    new ItemBuilder(Material.REPEATER).name(
                            newName
                    ).amount(1).build()
            );
            menu.refreshInventory(player);
            player.updateInventory();
        }));
        if (selectedDisc.get(player) != null) {
            toggleTimeButton(player, menu);
        }
        menu.setButton(13, new SGButton(
                new ItemBuilder(selectedDisc.getOrDefault(player, Material.AIR)).build()
        ).withListener(event -> {
            if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_ONE) {
                ItemStack item = event.getCursor();
                if (item.getType().isRecord()) {
                    event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                    menu.getButton(13).setIcon(item);
                    selectedDisc.put(player, item.getType());
                    menu.getButton(8).setIcon(
                            new ItemBuilder(Material.RED_DYE).name(
                                    "Play"
                            ).amount(1).build()
                    );

                    toggleTimeButton(player, menu);
                    menu.refreshInventory(player);


                }
            } else if (event.getAction() == InventoryAction.PICKUP_ALL || event.getAction() == InventoryAction.PICKUP_HALF || event.getAction() == InventoryAction.PICKUP_ONE) {

                menu.getButton(13).setIcon(new ItemBuilder(Material.AIR).build());
                player.stopSound(MusicDiscSoundMapper.getSoundForDisc(selectedDisc.get(player)));
                selectedDisc.remove(player);
                toggleTimeButton(player, menu);
                menu.getButton(8).setIcon(
                        new ItemBuilder(Material.RED_DYE).name(
                                "Play"
                        ).amount(1).build()
                );
                playingDiscs.remove(player);

                menu.refreshInventory(player);
                event.getWhoClicked().setItemOnCursor(event.getCurrentItem());
                event.setResult(Event.Result.DEFAULT);
            }


            player.updateInventory();
        }));
        player.openInventory(menu.getInventory());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
