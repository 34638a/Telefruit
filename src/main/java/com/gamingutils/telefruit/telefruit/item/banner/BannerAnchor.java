package com.gamingutils.telefruit.telefruit.item.banner;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

@Data
public final class BannerAnchor {

    private static final ArrayList<BannerAnchor> anchors = new ArrayList<>();

    public static final BannerAnchor WHITE = new BannerAnchor(Material.WHITE_BANNER, ChatColor.WHITE);
    public static final BannerAnchor ORANGE = new BannerAnchor(Material.ORANGE_BANNER, ChatColor.RED);
    public static final BannerAnchor MAGENTA = new BannerAnchor(Material.MAGENTA_BANNER, ChatColor.LIGHT_PURPLE);
    public static final BannerAnchor LBLUE = new BannerAnchor(Material.LIGHT_BLUE_BANNER, ChatColor.BLUE);
    public static final BannerAnchor YELLOW = new BannerAnchor(Material.YELLOW_BANNER, ChatColor.YELLOW);
    public static final BannerAnchor LIME = new BannerAnchor(Material.LIME_BANNER, ChatColor.GREEN);
    public static final BannerAnchor PINK = new BannerAnchor(Material.PINK_BANNER, ChatColor.RED);
    public static final BannerAnchor GRAY = new BannerAnchor(Material.GRAY_BANNER, ChatColor.DARK_GRAY);
    public static final BannerAnchor LGRAY = new BannerAnchor(Material.LIGHT_GRAY_BANNER, ChatColor.GRAY);
    public static final BannerAnchor CYAN = new BannerAnchor(Material.CYAN_BANNER, ChatColor.AQUA);
    public static final BannerAnchor PURPLE = new BannerAnchor(Material.PURPLE_BANNER, ChatColor.DARK_PURPLE);
    public static final BannerAnchor BLUE = new BannerAnchor(Material.BLUE_BANNER, ChatColor.DARK_BLUE);
    public static final BannerAnchor BROWN = new BannerAnchor(Material.BROWN_BANNER, ChatColor.GOLD);
    public static final BannerAnchor GREEN = new BannerAnchor(Material.GREEN_BANNER, ChatColor.DARK_GREEN);
    public static final BannerAnchor RED = new BannerAnchor(Material.RED_BANNER, ChatColor.DARK_RED);
    public static final BannerAnchor BLACK = new BannerAnchor(Material.BLACK_BANNER, ChatColor.BLACK);

    public static BannerAnchor toBannerAnchor(Material material) {
        for (BannerAnchor bannerAnchor : anchors) {
            if (material.equals(bannerAnchor.getBanner())) return bannerAnchor;
        }
        return null;
    }

    private final Material banner;
    private final ChatColor chatColor;

    public BannerAnchor(Material banner, ChatColor chatColor) {
        this.banner = banner;
        this.chatColor = chatColor;
        anchors.add(this);
    }
}
