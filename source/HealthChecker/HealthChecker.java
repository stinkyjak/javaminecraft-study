package com.javaminecraft;
 
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class HealthChecker extends JavaPlugin
    implements Listener {
 
    public static final Logger LOG = Logger.getLogger("Minecraft");
    private boolean display = false;
    private static int SCALE = 20;
 
    @Override
    public boolean onCommand(CommandSender sender, 
        Command command, String label, String[] arguments) {
         
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("healthcheck")) {
                if (arguments.length > 0) {
                    if (arguments[0].equals("on")) {
                        display = true;
                    }
                    if (arguments[0].equals("off")) {
                        display = false;
                    }
                }
                return true;
            }
        }
        return false;
    }
 
    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(this, this);
    }
     
    // display a mob's current health
    public void showHealth(LivingEntity entity) {
        if (!display) {
            return;
        }
        int maxHealth = (int) entity.getMaxHealth();
        int currentHealth = (int) entity.getHealth();
        String entityName = entity.getType().toString();
        String text = makeBarGraph(currentHealth, maxHealth,  
            entityName);
        entity.setCustomName(text);
    }
     
    // make the health bar graph (x=current, y=maximum
    public String makeBarGraph(int x, int y,
        String prefix) {
 
        int percent = (int) ((x / (float) y) * SCALE);
        StringBuilder output = new StringBuilder(12 + SCALE
            + prefix.length());
        output.append(ChatColor.WHITE);
        output.append(prefix);
        output.append(": [");
        output.append(ChatColor.GREEN);
        if (percent > 0) {
            // show bars in graph
            for (int i = 0; i < (percent); i++) {
                output.append("!");
            }
        }
        output.append(ChatColor.RED);
        if (percent < SCALE) {
            // show bars in graph
            for (int i = 0; i < (SCALE - percent); i++) {
                output.append("!");
            }
        }
        output.append(ChatColor.WHITE);
        output.append("]");
        return output.toString();  
    }
       
    // monitor when an entity takes damage
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            // a living mob has been damaged
            LivingEntity entity = (LivingEntity)
                event.getEntity();
            showHealth(entity);
        }
    }
     
    // monitor when an entity is targeted by a mob
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity)
               event.getEntity();
            showHealth(entity);
        }
    }
}