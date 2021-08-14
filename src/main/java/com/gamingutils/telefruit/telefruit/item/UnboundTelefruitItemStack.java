package com.gamingutils.telefruit.telefruit.item;

import com.gamingutils.telefruit.telefruit.item.banner.BannerAnchor;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BannerMeta;

public final class UnboundTelefruitItemStack {



    public static void convertToBoundTelefruit(Player player, Banner banner, String meta, BannerAnchor anchor) {
        player.getInventory().addItem(BoundTelefruitItemStack.createTeleFruit(1,
                anchor.getChatColor() + (meta != null ? meta : "Banner"),
                banner.getLocation(), false));
    }
}
