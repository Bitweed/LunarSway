package vendek.lunarsway.events;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import vendek.lunarsway.DropManager;
import vendek.lunarsway.LunarSway;

import java.util.Set;

public class PunchMeteor implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        // Проверяем, является ли сущность falling_block
        if (entity instanceof FallingBlock) {
            Set<String> tags = entity.getScoreboardTags();
            // Проверяем наличие тега "mytag"
            if (tags.contains("ls-meteor")) {
                entity.remove();
                DropManager dropManager = new DropManager(LunarSway.getInstance());
                dropManager.dropRandomItem(player);
            }
        }
    }
}
