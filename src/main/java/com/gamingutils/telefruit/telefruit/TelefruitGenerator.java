package com.gamingutils.telefruit.telefruit;

import com.gamingutils.telefruit.telefruit.item.BoundTelefruitItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TelefruitGenerator implements CommandExecutor, TabCompleter {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Command can be sent by anyone.
        if (args.length >= 1) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                try {
                    int amount = args.length > 1 ? Integer.parseInt(args[1]) : 1;
                    assert amount > 0;
                    //Parse Coords
                    double x = args.length > 2 ? Double.parseDouble(args[2]) : p.getLocation().getX();
                    double y = args.length > 3 ? Double.parseDouble(args[3]) : p.getLocation().getY();
                    double z = args.length > 4 ? Double.parseDouble(args[4]) : p.getLocation().getZ();

                    //Parse World
                    World world = p.getWorld();
                    if (args.length > 5) {
                        World temp = Bukkit.getWorld(args[5]);
                        if (temp != null) world = temp;
                    }
                    //Parse Anchor
                    String anchorText = args.length > 6 ? args[6] : "Command Generated";
                    //Parse infinite uses
                    boolean isInfinite = args.length > 7 && Boolean.parseBoolean(args[7]);

                    Location location = new Location(world, x, y, z);
                    p.getInventory().addItem(BoundTelefruitItemStack.createTeleFruit(amount, anchorText, location, isInfinite));
                    return true;
                } catch (Exception ignored) {
                    sender.sendMessage(String.format("Error: Unable to parse arguments {amount:%s, x:%s, y%s, z%s}", args[1], args[2], args[3], args[4]));
                }
            } else {
                sender.sendMessage(String.format("Error: Player not found %s", args[0]));
            }
        }

        return false;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside of a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        switch (args.length) {
            case 1:
                return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
            case 2:
                return Collections.singletonList("1");
            case 3:
                if (sender instanceof Player) {
                    return Collections.singletonList("" + ((Player) sender).getLocation().getX());
                }
                return null;
            case 4:
                if (sender instanceof Player) {
                    return Collections.singletonList("" + ((Player) sender).getLocation().getY());
                }
                return null;
            case 5:
                if (sender instanceof Player) {
                    return Collections.singletonList("" + ((Player) sender).getLocation().getZ());
                }
                return null;
            case 6:
                return Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList());
            case 7:
                return Collections.singletonList("Anchor_Name");
            case 8:
                return Arrays.asList("true", "false");
            default:
                return null;
        }
    }


}
