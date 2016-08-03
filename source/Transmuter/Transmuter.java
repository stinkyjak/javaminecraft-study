package com.javaminecraft;
 
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
 
public class Transmuter extends JavaPlugin {
    public static final Logger LOG = Logger.getLogger("Minecraft");
    Player me; // player
     
    @Override
    public boolean onCommand(CommandSender sender, Command command,
        String label, String[] arguments) {
         
        me = (Player) sender;
        
        if (label.equalsIgnoreCase("transmute")) {
            if (sender instanceof Player) {
                executeCommand(arguments);
            }
            return true;
        }
        return false;
    }
     
    public void executeCommand(String[] arguments) {
        // set the default to transmute dirt into cobblestone
        Material input = Material.DIRT;
        Material output = Material.COBBLESTONE;
        if (arguments.length > 1) {
            // turn player arguments into materials
            input = Material.getMaterial(arguments[0]);
            output = Material.getMaterial(arguments[1]);
        }
        if (input == null) {
            me.sendMessage(arguments[0] + " is not a known material");
            return;
        }
        if (output == null) {
            me.sendMessage(arguments[1] + " is not a known material");
            return;
        }
        
        // get the player's current inventory
        PlayerInventory stuff = me.getInventory();
        for (int i = 0; i < stuff.getSize(); i++) {
            // examine each stack of items
            ItemStack items = stuff.getItem(i);
            if (items == null) {
                // this inventory slot is empty
                continue;
            }
            if (items.getType() == input) {
                // this slot matches the input material, so transmute it
                items.setType(output);
                // fill the slot with the maximum amount possible
                items.setAmount(output.getMaxStackSize());
            }
        }
        // write a message to the player
        me.sendMessage(input.name() + " transmuted into " + output.name());
    }
}
