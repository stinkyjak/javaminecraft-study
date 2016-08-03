package com.javaminecraft;

import java.util.ArrayList;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
 
public class TreeChopper extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    Player me;
    World world;
    Location spot;
    // trees found inside the 40 by 40 by 40 cube
    ArrayList<Loc> trees = new ArrayList<>();
    // locations that have been searched for trees
    ArrayList<Loc> searched = new ArrayList<>();
    // inner class to hold (x,y,z) coordinates
    class Loc {
        int x, y, z;
 
        Loc(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
        
    public boolean onCommand(CommandSender sender, 
        Command command, String label, String[] arguments) {
         
        me = (Player) sender;
        world = me.getWorld();
        spot = me.getLocation();
         
        if (label.equalsIgnoreCase("choptrees")) { 
            if (sender instanceof Player) {
                executeCommand();                
            }
            return true;
        }
        return false;
    }
     
    // chop down trees in a 40 by 40 by 40 cube around the player
    private void executeCommand() {
        int spotX = spot.getBlockX();
        int spotY = spot.getBlockY();
        int spotZ = spot.getBlockZ();
        // loop through all possible (x,y,z) locations in cube
        for (int x = spotX - 20; x < spotX + 21; x++) {
            for (int z = spotZ - 20; z < spotZ + 21; z++) {
                for (int y = spotY - 20; y < spotY + 21; y++) {
                    Location searchLoc = new Location(world,
                        x, y, z);
                    Block here = searchLoc.getBlock();
                    Loc loc = new Loc(x, y, z);
                    // remember this location has been searched
                    searched.add(loc);
                    if (here.getType() == Material.LOG
                        | here.getType() == Material.LOG_2) {
                        
                        // this is a tree, so chop it down
                        here.setType(Material.AIR);
                        // remember where the tree was found
                        trees.add(loc);
                    }
                }
            }
        }
        for (Loc loc : trees) {
            chopAdjacentTrees(loc);
        }
        LOG.info("Chopping down trees around (" + spotX + ", "
            + spotY + ", " + spotZ + ")");
    }
     
    // finish chopping down trees that grew outside the cube
    private void chopAdjacentTrees(Loc chopLoc) {
        Location spot = new Location(world, chopLoc.x, chopLoc.y,
            chopLoc.z);
        int spotX = spot.getBlockX();
        int spotY = spot.getBlockY();
        int spotZ = spot.getBlockZ();
        // examine all locations adjacent to this one
        for (int x = spotX - 1; x < spotX + 2; x++) {
            for (int y = spotY - 1; y < spotY + 2; y++) {
                for (int z = spotZ - 1; z < spotZ + 2; z++) {
                    Loc loc = new Loc(x, y, z);
                    if (searched.contains(loc)) {
                        // skip locations searched previously
                        continue;
                    }
                    // remember this location has been searched
                    searched.add(loc);
                    Location searchLoc = new Location(world,
                        x, y, z);
                    Block here = searchLoc.getBlock();
                    if (here.getType() == Material.LOG
                        | here.getType() == Material.LOG_2) {
                         
                        // this is a tree, so chop it down
                        here.setType(Material.AIR);
                        // recursion to look for adjacent trees
                        chopAdjacentTrees(loc);
                    }
                }
            }
        }
    }
}