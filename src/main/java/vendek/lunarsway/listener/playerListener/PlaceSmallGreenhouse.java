package vendek.lunarsway.listener.playerListener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PlaceSmallGreenhouse  implements Listener {

    // HashMap для хранения времени последнего размещения блока для каждого игрока.
    Map<Player, Long> lastPlacedTimes = new HashMap<>();
    int placeInterval = 4;


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Player player = event.getPlayer();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            // Задержка на установку блока.
            long currentTime = System.currentTimeMillis();  // Получаем текущее время.
            if (lastPlacedTimes.containsKey(player)) {
                long lastPlacedTime = lastPlacedTimes.get(player);
                // Проверяем, прошло ли достаточно времени с последнего размещения блока для текущего игрока.
                if (currentTime - lastPlacedTime < placeInterval * 50) {
                    return;
                }
            }

            if (
                itemInHand.getType() == Material.PAPER &&
                itemInHand.getItemMeta().hasCustomModelData() &&
                itemInHand.getItemMeta().getCustomModelData() == 24001
            )
            {
                Block clickedBlock = event.getClickedBlock(); // Блок, на который кликнули.
                BlockFace clickedFace = event.getBlockFace();  // Грань блока, на которую кликнули.
                Block placedBlock = clickedBlock.getRelative(clickedFace);  // Блок, который мы устанавливаем.

                List<Material> allowedBlocks = new ArrayList<>(Arrays.asList(Material.AIR, Material.SHORT_GRASS));
                // Ставим блок только если сверху воздух или трава.
                if (allowedBlocks.contains(placedBlock.getType())) {
                    // Установка блока барьера.
                    placedBlock.setType(Material.BARRIER);

                    // СПАВН АРМОРСТЕНДА.
                    Location location = placedBlock.getLocation();
                    ArmorStand tempPlacer = placedBlock.getWorld().spawn(location, ArmorStand.class);
                    // Получение UUID Area Effect Cloud
                    String uuid = tempPlacer.getUniqueId().toString();
                    String command = String.format(
                            "execute at " + uuid + " run summon armor_stand ~0.5 ~ ~0.5 {Tags:[\"barrier\",\"space_pot\"],Small:1b,NoGravity:1b,Silent:1b,Invulnerable:1b,HasVisualFire:0b,Invisible:1b,ArmorItems:[{},{},{},{id:\"minecraft:paper\",Count:1b,tag:{CustomModelData:24001}}]}"
                    );
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                    tempPlacer.remove();
                }

                // Удаляем предмет только если игрок не в креативе.
                if (player.getGameMode() != GameMode.CREATIVE) {
                    ItemStack itemToRemove = new ItemStack(itemInHand);
                    itemToRemove.setAmount(1);
                    player.getInventory().removeItem(itemToRemove);
                }
                // Обновляем время последнего размещения блока для текущего игрока.
                lastPlacedTimes.put(player, currentTime);
            }

        }
    }
}
