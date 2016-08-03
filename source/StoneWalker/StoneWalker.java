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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StoneWalker extends JavaPlugin implements Listener {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    private boolean isStoneWalking = false;
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
            if (label.equalsIgnoreCase("stonewalk")) {
                isStoneWalking = true;
                LOG.info("[StoneWalker] Command on");
                return true;
            }
            if (label.equalsIgnoreCase("stopstonewalk")) {
                isStoneWalking = false;
                LOG.info("[StoneWalker] Command off");
                return true;
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
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        if (player != me) {
            // some other player is moving, ignore
            return;
        }
        if (!isStoneWalking) {
            // stonewalk is off
            return;
        }        
        LOG.info("[StoneWalker] Player moved to ("
            + from.getBlockX() + ", " + from.getBlockY() + ", "
            + from.getBlockZ() + ")");
        // get block player is in
        Block block = from.getBlock();
        // get block and material underfoot
        Block down = block.getRelative(BlockFace.DOWN);
        Material below = down.getType();
        if (okToTransform(below)) {
            // turn underfoot block to stone
            LOG.info("[StoneWalker] Transforming to stone");
            down.setType(Material.STONE);
        }
    }
    
    private boolean okToTransform(Material mat) {
        if (mat == Material.DIRT) {
            return true;
        }
        if (mat == Material.COBBLESTONE) {
            return true;
        }
        if (mat == Material.GRAVEL) {
            return true;
        }
        if (mat == Material.GRASS) {
            return true;
        }
        return false;
    }
}