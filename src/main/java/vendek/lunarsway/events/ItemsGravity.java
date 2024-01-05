package vendek.lunarsway.events;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import vendek.lunarsway.LunarSway;

public class ItemsGravity implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        String targetDimensionName = "world_lunarsway_orbit";
        if (event.getEntity() instanceof Item && event.getEntity().getWorld().getName().equals(targetDimensionName)) {
            Item item = (Item) event.getEntity();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (item.isDead()) {
                        cancel();
                        return;
                    }
                    item.setGravity(false);
                    item.setVelocity(new Vector(0, 0, 0));
                }
            }.runTaskLater(LunarSway.getInstance(), 8L);
        }
    }
}
