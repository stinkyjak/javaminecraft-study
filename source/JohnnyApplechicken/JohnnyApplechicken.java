package com.javaminecraft;
 
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
 
public class JohnnyApplechicken extends JavaPlugin
    implements Listener  {
 
    public static final Logger LOG = Logger.getLogger("Minecraft");
    private boolean commandOn = true;
    Player me;
    World world;
    Location spot;
         
    @Override
    public boolean onCommand(CommandSender sender, 
        Command command, String label, String[] arguments) {
         
        me = (Player) sender;
        world = me.getWorld();
        spot = me.getLocation();
         
        if (sender instanceof Player) {
            if (label.equalsIgnoreCase("applechicken")) {
                if (arguments.length == 0) {
                    for (int i = 0; i < 100; i++) {
                        world.spawn(spot, Chicken.class);
                    }
                    return true;
                }
                if (arguments[0].equals("on")) {
                    commandOn = true;
                    LOG.info("[JohnnyApplechicken] Command on");
                    return true;
                }
                if (arguments[0].equals("off")) {
                    commandOn = false;
                    LOG.info("[JohnnyApplechicken] Command off");
                    return true;
                }
            }
        }
        return false;
    }
     
    @Override
    public void onEnable() {
        // make this class an event listener
        Server server = getServer();
        PluginManager manager = server.getPluginManager();
        manager.registerEvents(this, this);
        LOG.info("[JohnnyApplechicken] Command on");
    }
     
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (!commandOn) {
            // the command is not active, so ignore
            return;
        }
        // get the item that was spawned
        Item item = (Item) event.getEntity();
        ItemStack stack = item.getItemStack();
        Material type = stack.getType();
        if (type == Material.EGG) {
            // it is a newly laid egg
            event.setCancelled(true);
            Location cloc = item.getLocation();
            Block block = cloc.getBlock();
            Block under = block.getRelative(BlockFace.DOWN);
            Material below = under.getType();
            if (below == Material.DIRT
                || below == Material.GRASS) {

                // plant a tree sapling
                block.setType(Material.SAPLING);
            }
            LOG.info("[JohnnyAppleChicken] Tree planted");
        }
    }
}
