package com.gamingutils.telefruit.telefruit;

import com.gamingutils.telefruit.telefruit.event.TelefruitTeleportationEvent;
import com.gamingutils.telefruit.telefruit.item.BoundTelefruitItemStack;
import com.gamingutils.telefruit.telefruit.item.UnboundTelefruitItemStack;
import com.gamingutils.telefruit.telefruit.item.banner.BannerAnchor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.logging.Level;

public class TelefruitInterceptor implements Listener {


    @EventHandler
    public void onEatEvent(PlayerItemConsumeEvent onPlayerEatEvent) {
        ItemStack itemStack = onPlayerEatEvent.getItem();
        if (itemStack.getType() == Material.CHORUS_FRUIT) {
            Bukkit.getLogger().log(Level.INFO, "Fruit Eaten by: " + onPlayerEatEvent.getPlayer().getName());
            try {
                BoundTelefruitItemStack boundTelefruitItemStack = BoundTelefruitItemStack.createFromItemStack(itemStack);
                boundTelefruitItemStack.teleport(onPlayerEatEvent.getPlayer());
                onPlayerEatEvent.setCancelled(true);
            } catch (Exception ignored) {}
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerTeleport(PlayerTeleportEvent teleportEvent) {
        if (teleportEvent instanceof TelefruitTeleportationEvent) {
            TelefruitTeleportationEvent event = ((TelefruitTeleportationEvent) teleportEvent);
            event.consumeFruit();
        }
    }

    @EventHandler
    public void anchorToBanner(PlayerInteractEvent event) {
        BannerAnchor bannerAnchor = BannerAnchor.toBannerAnchor(event.getClickedBlock().getType());
        event.getPlayer().sendMessage("Clicked On: " + event.getClickedBlock());
        if (bannerAnchor != null) {
            event.getPlayer().sendMessage("Found Banner");
            Banner banner = (Banner) event.getClickedBlock().getState();
            event.getPlayer().sendMessage(banner.getBlockData().getAsString());
            String meta = null; //banner.getBlockData().getAsString();
            UnboundTelefruitItemStack.convertToBoundTelefruit(event.getPlayer(), banner, meta, bannerAnchor);
        }
    }
}
