package com.javaminecraft; 
 
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
 
public class ZombieChicken extends JavaPlugin { 
    public static final Logger LOG = Logger.getLogger("Minecraft");
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, 
        String label, String[] arguments) {
         
        if (label.equalsIgnoreCase("zombiechicken")) {
            if (sender instanceof Player) {
                executeCommand(sender);
                return true;
            }            
        }
        return false;
    }
          
    public void executeCommand(CommandSender sender) {
        Player me = (Player) sender;
        Location spot = me.getLocation();
        World world = me.getWorld();
 
        // spawn 1-10 chicken-riding zombies
        int quantity = (int) (Math.random() * 10) + 1;
        for (int i = 0; i < quantity; i++) {
            // set chicken and zombie location
            Location chickenSpot = new Location(world,
                spot.getX() + (Math.random() * 15), 
                spot.getY() + 5,
                spot.getZ() + (Math.random() * 15));
            // create the mobs
            Chicken clucky = world.spawn(chickenSpot, Chicken.class);
            Zombie rob = world.spawn(chickenSpot, Zombie.class);
            rob.setBaby(true);
            clucky.setPassenger(rob);
            // increase the chicken's speed
            int speed = (int) (Math.random() * 10);
            PotionEffect potion = new PotionEffect(
                PotionEffectType.SPEED, 
                Integer.MAX_VALUE, 
                speed);
            clucky.addPotionEffect(potion);
        }
        LOG.info("[ZombieChicken] Created " + quantity + " chicken-riding zombies");
    }
}