package vendek.lunarsway.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import vendek.lunarsway.LunarSway;

import java.util.concurrent.ThreadLocalRandom;


public class VoidFishing implements Listener {


    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getItemInHand();

        if (item.getType().equals(Material.FISHING_ROD)) {

            // Генерируем рандомное время задержки от 3 до 10 секунд
            int delay = ThreadLocalRandom.current().nextInt(3, 11);

            // Задержка в 3 секунды.
            Bukkit.getScheduler().runTaskLater(LunarSway.getInstance(), () -> {
                event.getHook().setVelocity(new Vector(0, 0, 0));
                // Берем позицию под поплавком.
                Location floatLocation = event.getHook().getLocation();
                floatLocation.setY(floatLocation.getY() - 1);

                // Если там воздух, то продолжим.
                if (floatLocation.getBlock().getType().equals(Material.AIR)) {
                    Block barrierBlock = floatLocation.getBlock();
                    barrierBlock.setType(Material.BARRIER);

                    Bukkit.getScheduler().runTaskLater(LunarSway.getInstance(), () -> {
                        barrierBlock.setType(Material.AIR);

                        event.getHook().remove();

                        player.getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                        player.sendMessage("Вы получили: " + ChatColor.GREEN + "⛏ булыжник");
                    }, delay * 20L);
                } else {
                    event.setCancelled(true);
                }
            }, 2 * 20L);
        }
    }
}