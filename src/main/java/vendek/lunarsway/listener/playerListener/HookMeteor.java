package vendek.lunarsway.listener.playerListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import vendek.lunarsway.manager.DropManager;
import vendek.lunarsway.LunarSway;


public class HookMeteor implements Listener {
    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = (Player) event.getPlayer();

        // Проверяем действие игрока - цепляет ли он блок удочкой
        if (event.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            Entity caughtEntity = event.getCaught();

            // Проверяем, является ли перехваченная сущность падающим блоком с тегом "ls-meteor"
            if (caughtEntity instanceof FallingBlock && caughtEntity.getScoreboardTags().contains("ls-meteor")) {
                FallingBlock fallingBlock = (FallingBlock) caughtEntity;
                fallingBlock.remove();

                DropManager dropManager = new DropManager(LunarSway.getInstance());
                dropManager.dropRandomItem(player);

            }
        }
    }
}