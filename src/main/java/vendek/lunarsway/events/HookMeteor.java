package vendek.lunarsway.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HookMeteor implements Listener {

    // Выдача награды игроку.
    void givePlayerRevard(Player player) {
        Random random = new Random();
        int count = random.nextInt(3) + 1;
        ItemStack itemStack = new ItemStack(Material.COBBLESTONE, count);
        player.getInventory().addItem(itemStack);
        player.sendMessage("Вы получили: " + ChatColor.GREEN + "⛏ булыжник " + ChatColor.WHITE + count + "x");
    }

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

                givePlayerRevard(player);
            }
        }
    }
}