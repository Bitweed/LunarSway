package vendek.lunarsway.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import vendek.lunarsway.LunarSway;

import java.util.Random;

public class TaskSpawnMeteor implements Runnable {

    public void spawnMeteor(Player player) {
        Location location = player.getLocation();

        // Генерируем случайные координаты от 10 до 20 в пределах радиуса от текущей позиции
        Random random = new Random();
        int max_pos = 10;
        double x = location.getX() + random.nextInt(max_pos * 2) - max_pos;
        double z = location.getZ() + max_pos * 1.5 + random.nextInt(max_pos);
        double y = location.getY() + random.nextInt(5 * 2) - 5;
        Location spawnLocation = new Location(location.getWorld(), x, y, z);

        // Создаем падающий блок
        FallingBlock fallingBlock = spawnLocation.getWorld().spawnFallingBlock(spawnLocation, Material.COBBLESTONE.createBlockData());
        fallingBlock.setVelocity(new Vector(0, 0, -0.1f)); // Устанавливаем скорость вектора движения
        fallingBlock.setDropItem(false); // Отключаем выпадение предмета
        fallingBlock.setGravity(false); // Отключаем гравитацию
        fallingBlock.addScoreboardTag("ls-meteor"); // Добавляем тег ls-meteor

        // Обновляем положение падающего блока на каждом тике
        Bukkit.getScheduler().runTaskTimer(LunarSway.getInstance(), task -> {
            if (fallingBlock.getScoreboardTags().contains("ls-meteor")) {
                fallingBlock.setVelocity(new Vector(0, 0, -0.1f));
            } else {
                task.cancel();
            }
        }, 1L, 1L);
    }

    public boolean isPlayerInOrbitDimension(Player player) {
        String targetDimensionName = "world_lunarsway_orbit";
        // Bukkit.getLogger().info(player.getName() + " находится в измерении: " + player.getWorld().getName());
        return player.getWorld().getName().equals(targetDimensionName);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            boolean isInOrbitDimension = isPlayerInOrbitDimension(player);
            if (isInOrbitDimension) {
                spawnMeteor(player);
            }
        }
    }
}