package vendek.lunarsway.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import vendek.lunarsway.LunarSway;

public class CobleFall implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.isOnGround() && player.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.COBBLESTONE) {
            Location first_location = player.getLocation().subtract(0, 1, 0).getBlock().getLocation();
            Bukkit.getScheduler().runTaskLater(LunarSway.getInstance(), () -> {
                if (first_location.getBlock().getType() != Material.COBBLESTONE) {
                    return;
                }
                first_location.getBlock().setType(Material.AIR);
                player.getWorld().spawnFallingBlock(first_location.add(new Vector(0.5, 0, 0.5)), Material.COBBLESTONE.createBlockData());
                player.playSound(player.getLocation(), "minecraft:block.polished_deepslate.fall", 1.0f, 0.1f);
            }, 6);
        }
    }
}