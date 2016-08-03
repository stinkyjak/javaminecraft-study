package com.javaminecraft;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class MobCensus extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    Player me; // player
    Location spot; // player's location
    World world; // game world
     
    @Override
    public boolean onCommand(CommandSender sender, Command command, 
        String label, String[] arguments) {
         
        if (sender instanceof Player) {
            me = (Player) sender;
            spot = me.getLocation();
            world = me.getWorld();
         
            if (label.equalsIgnoreCase("census")) {
                executeCensusCommand();
                return true;
            }
             
            if (label.equalsIgnoreCase("villager")) {
                executeVillagerCommand();
                return true;
            }
        }
        return false;
    }
     
    // count all mobs around the player
    public void executeCensusCommand() {     
        // store the mobs in a list
        List<LivingEntity> current = world.getLivingEntities();
        // store the mob count in a hash map
        HashMap<EntityType, Integer> mobs = new HashMap<>();       
 
        // loop through all the mobs found
        for (Entity mob : current) {
            int count = 1;
            if (mobs.containsKey(mob.getType())) {
                // mob type found in hash map, so get the count
                count = mobs.get(mob.getType());
                count++;
            }
            // store new count in hash map
            mobs.put(mob.getType(), count);
        }
        // display the count for each mob
        for (Entry entry : mobs.entrySet()) {
            EntityType type = (EntityType) entry.getKey();
            int count = (int) entry.getValue();
            me.sendMessage(type + ": " + count);
        }
    }
     
    // count villagers within a 400-block radius
    public void executeVillagerCommand() {
        List<LivingEntity> mobs = world.getLivingEntities();
        int adultCount = 0;
        int kidCount = 0;
        int golemCount = 0;
        // loop through all mobs
        for (LivingEntity mob : mobs) {
            Location mobSpot = mob.getLocation();
            // calculate mob's distance away from player
            double xd = mobSpot.getX() - spot.getX();
            double yd = mobSpot.getY() - spot.getY();
            double zd = mobSpot.getZ() - spot.getZ();
            double distance = Math.sqrt(xd * xd + yd * yd + zd * zd);
            if (distance > 400) {
                // skip mobs too far away
                continue;
            }
            // see if the mob is a villager
            if (mob.getType() == EntityType.VILLAGER) {
                Villager dude = (Villager) mob;
                if (dude.isAdult()) {
                    adultCount++;
                } else {
                    kidCount++;
                }
            }
            // see if the mob is an iron golem
            if (mob.getType() == EntityType.IRON_GOLEM) {
                golemCount++;
            }
        }
        me.sendMessage("There are " + adultCount
            + " adult villagers and " + kidCount
            + " children nearby, protected by "
            + golemCount + " iron golem");
    }
}
