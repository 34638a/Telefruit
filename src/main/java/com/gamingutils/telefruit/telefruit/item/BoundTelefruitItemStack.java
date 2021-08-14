package com.gamingutils.telefruit.telefruit.item;

import com.gamingutils.telefruit.telefruit.event.TelefruitTeleportationEvent;
import lombok.Data;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public final class BoundTelefruitItemStack {

    private static final Material CHORUS_FRUIT_MATERIAL = Material.CHORUS_FRUIT;

    private final ItemStack itemStack;
    private final String anchorName;
    private final Location location;
    private final boolean infinite;


    /**
     * Teleport a player using this TeleFruit.
     * @param player Player to teleport.
     * @return this for chaining.
     */
    public BoundTelefruitItemStack teleport(Player player) {
        Bukkit.getPluginManager().callEvent(new TelefruitTeleportationEvent(this, player));
        return this;
    }

    /**
     * Consume 1 of the telefruit item.
     * @param player Player that consumed the item.
     * @return this for chaining.
     */
    public BoundTelefruitItemStack consumeTeleFruit(Player player) {
        if (!(player.getGameMode() == GameMode.CREATIVE || isInfinite())) {
            ItemStack temp = null;
            for (ItemStack i : player.getInventory().getContents()) {
                if (itemStack.equals(i)) {
                    temp = i;
                    break;
                }
            }
            assert temp != null;
            if (temp.getAmount() > 1) {
                temp.setAmount(temp.getAmount() - 1);
            } else player.getInventory().remove(temp);
        }

        player.playSound(player.getLocation(), Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1, 1);
        player.teleport(location);
        player.playSound(location, Sound.ITEM_CHORUS_FRUIT_TELEPORT, 1, 1);
        return this;
    }

    /**
     * create a new ItemStack from this.
     * @param size Size of the new ItemStack.
     * @return new ItemStack
     */
    public ItemStack createItemStack(int size) {
        return createTeleFruit(size, anchorName, location, infinite);
    }

    /**
     * Create a new Telefruit from a ItemStack.
     * @param source ItemStack source.
     * @return new FruitBuilder.
     */
    public static BoundTelefruitItemStack createFromItemStack(ItemStack source) {
        assert source.getType() == CHORUS_FRUIT_MATERIAL;
        ItemMeta meta = source.getItemMeta();
        assert Objects.requireNonNull(meta).hasLore();
        List<String> lore = meta.getLore();
        assert lore != null;
        String anchorLine = lore.get(0);
        String[] anchorLineSplits = anchorLine.split("\\s", 2);
        String worldLine = lore.get(1);
        String[] worldLineSplits = worldLine.split("\\s", 2);
        String xyzLine = lore.get(2);
        String[] xyzLineSplits = xyzLine.split("\\s");
        double[] xyz = new double[] {
                Double.parseDouble(xyzLineSplits[1].substring(0,xyzLineSplits[1].length()-1)),
                Double.parseDouble(xyzLineSplits[2].substring(0,xyzLineSplits[2].length()-1)),
                Double.parseDouble(xyzLineSplits[3]),
        };
        boolean isInfinite = lore.size() > 3;

        return new BoundTelefruitItemStack(
                source,
                anchorLineSplits[1],
                new Location(
                        Bukkit.getServer().getWorld(worldLineSplits[1]),
                        xyz[0], xyz[1], xyz[2]
                ),
                isInfinite
        );
    }

    /**
     *
     * @param stackSize
     * @param anchorName
     * @param location
     * @param isInfinite
     * @return
     */
    public static ItemStack createTeleFruit(int stackSize, String anchorName, Location location, boolean isInfinite) {
        int createStackAmount = Math.max(stackSize, 1);
        ItemStack fruitStack = new ItemStack(CHORUS_FRUIT_MATERIAL, createStackAmount);
        ItemMeta itemMeta = fruitStack.getItemMeta();

        assert itemMeta != null;

        List<String> lore = new ArrayList<>();
        lore.add("Anchor: " + anchorName);
        lore.add("World: " + location.getWorld().getName());
        lore.add("XYZ: " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        if (isInfinite) lore.add(ChatColor.GOLD + "Unlimited Uses");

        itemMeta.setLore(lore);
        itemMeta.setDisplayName("TeleFruit");

        fruitStack.setItemMeta(itemMeta);
        return fruitStack;
    }

}
