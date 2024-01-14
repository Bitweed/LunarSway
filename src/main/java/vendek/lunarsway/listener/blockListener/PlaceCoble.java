package vendek.lunarsway.listener.blockListener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceCoble implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.COBBLESTONE) {
            if (event.getBlock().getRelative(0, -1, 0).getType() == Material.COBBLESTONE && event.getBlock().getRelative(0, -2, 0).getType() == Material.AIR) {
                event.setCancelled(true);
            }
        }
    }
}
