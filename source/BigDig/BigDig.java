package com.javaminecraft;
 
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
 
public class BigDig extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
      
    @Override
    public boolean onCommand(CommandSender sender,
        Command command, String label, String[] arguments) {
         
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("bigdig")) {
                executeCommand(sender, arguments);
            }
            return true;
        }
        return false;
    }
     
    public void executeCommand(CommandSender sender,
        String[] arguments) { 
        
        // set the default dig radius
        double rad = 15;        
        if (arguments.length > 0) {
            try {
                // get the user's choice of radius (if any)
                rad = Double.parseDouble(arguments[0]);
                // make sure it's from 5 to 30
                if ((rad < 5) || (rad > 30)) {
                    rad = 15;
                }
            } catch (NumberFormatException exception) {
                // do nothing
            }
        }
 
        Player me = (Player) sender;
        Location spot = me.getLocation();
        World world = me.getWorld();
         
        // loop through a square with sides twice the radius
        for (double x = spot.getX() - rad; x < spot.getX() + rad;
            x++) {
            
            for (double y = spot.getY() - rad; y < spot.getY()
                + rad; y++) {
                
                for (double z = spot.getZ() - rad; z <
                    spot.getZ() + rad; z++) {
                    
                    // get a location in that square
                    Location loc = new Location(world, x, y, z);
                    // is it close to the bottom of the world?
                    if (y < 2) {
                        // yes, so don't dig here
                        continue;
                    }
                    // see how far it is from the player
                    double xd = x - spot.getX();
                    double yd = y - spot.getY();
                    double zd = z - spot.getZ();
                    double distance = Math.sqrt(xd * xd + yd * yd
                        + zd * zd);
                    // is it within the radius?
                    if (distance < rad) {
                        // yes, so turn that block into air
                        Block current = world.getBlockAt(loc);
                        current.setType(Material.AIR);
                    }
                }
            }
        }
        
        // play a sound after the dig is dug
        world.playSound(spot, Sound.BURP, 30, 5);
        LOG.info("[BigDig] Dug at ("
            + (int) spot.getX() + ", "
            + (int) spot.getY() + ", "
            + (int) spot.getZ() + ")");
    }
}