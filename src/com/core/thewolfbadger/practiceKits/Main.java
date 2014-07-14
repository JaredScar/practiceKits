package com.core.thewolfbadger.practiceKits;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: TheWolfBadger
 * Date: 7/14/14
 * Time: 1:50 PM
 */
public class Main extends JavaPlugin implements Listener {
    private FileConfiguration settings;
    public void onEnable() {
        this.saveDefaultConfig();
        this.settings = getConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
    }
    public void onDisable() {}
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getClickedBlock() !=null) {
        if(e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.WALL_SIGN && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Sign s = (Sign) e.getClickedBlock().getState();
            if(s.getLine(0).equalsIgnoreCase(""+ ChatColor.translateAlternateColorCodes('&', this.settings.getString("SignLines.SaveKit1")))) {
                if(!s.getLine(1).isEmpty()) {
                    if(p.hasPermission("practiceKits.save."+s.getLine(1))) {
                    //Armor and Inventory adding
                    List<ItemStack> list = new ArrayList<ItemStack>();
                    for(ItemStack items : p.getInventory().getContents()) {
                        if(items !=null) {
                            list.add(items);
                        }
                    }
                    this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Inv", list);
                    if(p.getInventory().getHelmet() !=null) {
                    this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Helmet", p.getInventory().getHelmet());
                    } else {
                        this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Helmet", null);
                    }
                        if(p.getInventory().getChestplate() !=null) {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Chest", p.getInventory().getChestplate());
                        } else {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Chest", null);
                        }
                        if(p.getInventory().getLeggings() !=null) {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Leggings", p.getInventory().getLeggings());
                        } else {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Leggings", null);
                        }
                        if(p.getInventory().getBoots() !=null) {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Boots", p.getInventory().getBoots());
                        } else {
                            this.settings.set("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Boots", null);
                        }
                    this.saveConfig();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.settings.getString("Messages.KitSaved")));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.settings.getString("Messages.PermissionMessageForSave")));
                    }
                }
            } else
                if(s.getLine(0).equalsIgnoreCase(""+ChatColor.translateAlternateColorCodes('&', this.settings.getString("SignLines.LoadKit1")))) {
                    if(!s.getLine(1).isEmpty()) {
                        if(p.hasPermission("practiceKits.load."+s.getLine(1))) {
                            if(this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Inv") || this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor")) {
                                p.getInventory().clear();
                            for(ItemStack stacks : (ArrayList<ItemStack>) this.settings.get("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Inv")) {
                                p.getInventory().addItem(stacks);
                            }
                                //Set armor
                                if(this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Helmet")) {
                                    p.getInventory().setHelmet(this.settings.getItemStack("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Helmet"));
                                }
                                if(this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Chest")) {
                                    p.getInventory().setChestplate(this.settings.getItemStack("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Chest"));
                                }
                                if(this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Leggings")) {
                                    p.getInventory().setLeggings(this.settings.getItemStack("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Leggings"));
                                }
                                if(this.settings.contains("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Boots")) {
                                    p.getInventory().setBoots(this.settings.getItemStack("Signs."+s.getLine(1)+"."+p.getUniqueId().toString()+".Armor.Boots"));
                                }
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.settings.getString("Messages.KitLoaded")));
                            p.updateInventory();
                            } else {
                                p.sendMessage(ChatColor.RED+"You must have saved a kit to proceed using this commands!");
                            }
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.settings.getString("Messages.PermissionMessageForLoad")));
                        }
                    }
                }
            }
        }
    }
}
