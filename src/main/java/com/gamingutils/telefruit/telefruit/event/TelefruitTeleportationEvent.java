package com.gamingutils.telefruit.telefruit.event;

import com.gamingutils.telefruit.telefruit.item.BoundTelefruitItemStack;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

@EqualsAndHashCode
@Getter
public class TelefruitTeleportationEvent extends PlayerTeleportEvent {

    private final BoundTelefruitItemStack boundTelefruitItemStack;

    public TelefruitTeleportationEvent(BoundTelefruitItemStack boundTelefruitItemStack, Player player) {
        super(player, player.getLocation(), boundTelefruitItemStack.getLocation(), TeleportCause.PLUGIN);
        this.boundTelefruitItemStack = boundTelefruitItemStack;
    }

    /**
     * Consume the telefruit.
     */
    public void consumeFruit() {
        this.boundTelefruitItemStack.consumeTeleFruit(this.getPlayer());
    }
}
