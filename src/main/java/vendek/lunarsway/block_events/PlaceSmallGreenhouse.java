package vendek.lunarsway.block_events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;

public class PlaceSmallGreenhouse  implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();
        Location location = block.getLocation();

        // Проверяем, является ли размещенный блок блоком стекла
        if (block.getType() == Material.GLASS) {
            // Проверяем название предмета
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("Маленькая теплица")) {
                // Создаем новый невидимый armor_stand на координатах размещения блока
                ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
                // Устанавливаем невидимость armor_stand
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                // Uuid
                String entityUuid = armorStand.getUniqueId().toString();

                // Вызываем функцию чата Minecraft с использованием сущности стойки в аргументах
                String command = "execute at " + entityUuid + " run function lunarsway:place_block/small_greenhouse";
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

                // Удаление временной стойки
                armorStand.remove();
            }
        }

    }
}
