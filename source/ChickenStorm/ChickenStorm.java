package com.javaminecraft;

import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
 
public class ChickenStorm extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    // the maximum number of chickens
    private static final int NUM_CHICKENS = 30;
     
    @Override
    public boolean onCommand(CommandSender sender, Command command, 
        String label, String[] arguments) {
          
        if (label.equalsIgnoreCase("chickenstorm")) {
            if (sender instanceof Player) {
                executeCommand(sender);
            }
            return true;
        }
        return false;
    }
     
    // handle the chickenstorm command
    public void executeCommand(CommandSender sender) {
        Player me = (Player) sender;
        Location spot = me.getLocation();
        World world = me.getWorld();
        
        int quantity = 0;
        // loop from 1 to NUM_CHICKENS times
        for (int i = 0; i < Math.random() * NUM_CHICKENS + 1; i++) {
            // pick a spot for the chicken above the player
            Location chickenSpot = new Location(world,
                spot.getX() - 15 + Math.random() * 30,
                spot.getY() + 10 + Math.random() * 100,
                spot.getZ() - 15 + Math.random() * 30);
            if (chickenSpot.getBlock().getType() != Material.AIR) {
                // don't put the chicken in a solid block
                continue;
            }
            // create the chicken
            Chicken clucky = world.spawn(chickenSpot, Chicken.class);
            if (Math.random() < .4) {
                // make 40% of them babies
                clucky.setBaby();
            } else {
                // make the rest adults
                clucky.setAdult();
            }
            quantity++;
        }
        // tell the server log how many were created
        LOG.info(quantity + " chickens summoned");
    }
}