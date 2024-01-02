package vendek.lunarsway.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;


public class WorldChanger implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Проверяем наличие аргумента и то, что команда выполнена игроком.
        if (args.length == 1 && sender instanceof Player) {

            String worldName = args[0];
            Player player = (Player) sender;

            // Существует ли мир.
            if (Bukkit.getWorld("planets/" + worldName) != null) {
                World world = Bukkit.getWorld("planets/" + worldName);
                // Телепортируем игрока в выбранный мир.
                player.teleport(world.getSpawnLocation());
            } else {
                // Если мир не существует.
                player.sendMessage("World " + worldName + " not found!");
            }
        }
        return false;
    }
}
