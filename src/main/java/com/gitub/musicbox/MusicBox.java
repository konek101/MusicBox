package com.gitub.musicbox;

import com.samjakob.spigui.SpiGUI;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import com.samjakob.spigui.menu.SGMenuListener;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class MusicBox extends JavaPlugin {





    public static SpiGUI spiGUI;
    @Override
    public void onEnable() {
        // Plugin startup logic
        spiGUI = new SpiGUI(this);

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("musicbox")) {
            if (sender instanceof Player) {
                openGui((Player) sender);
            }
        }
        return true;
    }




    public static void toggleTimeButton(Player player, SGMenu menu) {
        SGButton Timebutton = new SGButton(
                new ItemBuilder(Material.CLOCK).name("Custom sonh lenght").build()
        ).withListener(event -> {
            event.getWhoClicked().sendMessage("Type the time in seconds this is used to autorepeat the song only intigers are allowed");
            player.closeInventory();

        });
    }

    public void openGui(Player player) {
        SGMenu menu = MusicBox.spiGUI.create("MusicBox", 3);

        int slots = menu.getPageSize();
        for (int i = 0; i < slots; i++) {
            menu.setButton(i, new SGButton(
                    new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build()
            ).withListener(event -> {
            }));
        }
        menu.setButton(8, new SGButton(
                new ItemBuilder(Material.RED_DYE).name("Play").build()
        ).withListener(event -> {
            Material newMaterial = event.getCurrentItem().getType() == Material.RED_DYE
                    ? Material.LIME_DYE
                    : Material.RED_DYE;
            menu.getButton(8).setIcon(
                    new ItemBuilder(newMaterial).name(
                            newMaterial == Material.RED_DYE ? "Play" : "Stop"
                    ).amount(1).build()
            );
            menu.refreshInventory(player);
            player.updateInventory();
        }));

        menu.setButton(17, new SGButton(
                new ItemBuilder(Material.REPEATER).name("Repeat ON").build()
        ).withListener(event -> {
            String newName = event.getCurrentItem().getItemMeta().getDisplayName().equals("Repeat ON") ? "Repeat OFF" : "Repeat ON";
            menu.getButton(17).setIcon(
                    new ItemBuilder(Material.REPEATER).name(
                            newName
                    ).amount(1).build()
            );
            menu.refreshInventory(player);
            player.updateInventory();
        }));

        menu.removeButton(13);
        menu.setButton(13, new SGButton(
                new ItemBuilder(Material.STONE).build()
        ).withListener(event -> {
            if (event.getAction() == InventoryAction.PLACE_ALL || event.getAction() == InventoryAction.PLACE_ONE) {
                ItemStack item = event.getCursor();
                if (item.getType().isRecord()) {
                    event.setCursor(new ItemStack(Material.AIR));
                    menu.getButton(13).setIcon(item);
                    toggleTimeButton(player, menu);
                    menu.refreshInventory(player);


                }
            } else if (event.getAction() == InventoryAction.PICKUP_ALL || event.getAction() == InventoryAction.PICKUP_HALF || event.getAction() == InventoryAction.PICKUP_ONE) {

                menu.getButton(13).setIcon(new ItemBuilder(Material.AIR).build());
                toggleTimeButton(player, menu);
                menu.refreshInventory(player);
                event.setCursor(event.getCurrentItem());
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
