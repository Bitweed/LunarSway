package vendek.lunarsway.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import vendek.lunarsway.LunarSway;

import java.util.Random;

public class SummonMeteor implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Получаем координаты исполнителя команды
        Location location = sender instanceof Player ? ((Player) sender).getLocation() : null;
        if (location != null) {
            // Генерируем случайные координаты от 10 до 20 в пределах радиуса от текущей позиции
            Random random = new Random();
            int max_pos = 10;
            double x = location.getX() + random.nextInt(max_pos * 2) - max_pos;
            double z = location.getZ() + max_pos + random.nextInt(max_pos);
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

            sender.sendMessage(ChatColor.GRAY + "Метеорит был создан.");
            return true;
        }
        return false;
    }
}
