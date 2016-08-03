package com.javaminecraft;
 
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class BestFriendOfZeus extends JavaPlugin
    implements Listener  {
 
    public static final Logger LOG = Logger.getLogger("Minecraft");
    Player me;
    World world;
    Location spot;
    boolean on = false;
         
    @Override
    public boolean onCommand(CommandSender sender, 
        Command command, String label, String[] arguments) {
         
        me = (Player) sender;
        world = me.getWorld();
        spot = me.getLocation();
         
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("zeus")) { 
                if (arguments.length > 0) {
                    if (arguments[0].equals("on")) {
                        // Zeus powers activate!
                        on = true;
                        me.sendMessage("Zeus is yer friend!");
                    } else {
                        // Zeus powers deactivate!
                        on = false;
                        me.sendMessage("Zeus is not yer friend!");
                    }
                    return true;
                }
            }
        }
        return false;
    }
     
    // make this class listen to events
    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(this, this);
    }
     
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();
         
        LOG.info("Target " + target + " chosen by " + entity);
        if (target instanceof Player) {
            // the mob's target is a player
            Player playa = (Player) target;
            if (playa == me) {
                // the mob's target is this player
                if (event.getReason() ==
                    TargetReason.CLOSEST_PLAYER) {

                    Location loc = entity.getLocation();
                    // here comes the boom!
                    world.strikeLightning(loc);                        
                }
            }
        }
    }
}