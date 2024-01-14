package vendek.lunarsway.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.Bukkit.getServer;


public class WorldChanger implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Проверяем наличие аргумента и то, что команда выполнена игроком.
        if (args.length == 1 && sender instanceof Player) {

            String worldName = args[0];
            Player player = (Player) sender;


            String targetWorldPath = getServer().getWorldContainer().getPath() + File.separator + "planets" + File.separator + worldName;
            File targetWorldFolder = new File(targetWorldPath);

            if (!targetWorldFolder.exists() || !targetWorldFolder.isDirectory()) {
                player.sendMessage("Мир \"" + worldName + "\" не найден в папке планет!");
                return true;
            }

            World targetWorld = Bukkit.getWorld("planets/" + worldName);
//            if (targetWorld == null) {
//                targetWorld = Bukkit.createWorld(new WorldCreator("planets/" + worldName));
//            }

            Location targetLocation = targetWorld.getSpawnLocation();
            player.teleport(targetLocation);
            player.sendMessage("Вы были телепортированы в мир \"" + worldName + "\"!");

            return true;
        }
        return true;
    }
}