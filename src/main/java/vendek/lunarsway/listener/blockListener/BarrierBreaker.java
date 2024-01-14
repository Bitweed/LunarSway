package vendek.lunarsway.listener.blockListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import java.util.HashMap;

public class BarrierBreaker implements Listener {
    public static final int BREAKING_TICKS = 20;
    public static HashMap<Block, Integer> breakingBlocks = new HashMap<>();

    public ArmorStand getArmorStandInBlock(Block block) {
        Location centerLoc = block.getLocation().toCenterLocation();
        for (Entity entity : centerLoc.getWorld().getEntities()) {
            if (entity instanceof ArmorStand && entity.getScoreboardTags().contains("someTag")
                    && entity.getLocation().distance(centerLoc) <= 0.5)
                return (ArmorStand) entity;
        }
        return null;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.BARRIER && getArmorStandInBlock(block) != null) {
            breakingBlocks.put(block, 1);
            Bukkit.broadcastMessage("(DEBUG) " + e.getPlayer().getName() + " started breaking block");
        }
    }

    @EventHandler
    public void onBlockDamageAbort(BlockDamageAbortEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.BARRIER && breakingBlocks.remove(block) != null)
            Bukkit.broadcastMessage("(DEBUG) " + e.getPlayer().getName() + " stopped breaking block");
    }
}
