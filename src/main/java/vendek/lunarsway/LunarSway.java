package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.util.Vector;
import vendek.lunarsway.commands.SummonMeteor;
import vendek.lunarsway.commands.WorldChanger;
import vendek.lunarsway.events.*;
import vendek.lunarsway.block_events.PlaceSmallGreenhouse;
import vendek.lunarsway.tasks.TaskSpawnMeteor;
import java.util.Random;

public final class LunarSway extends JavaPlugin implements Listener {
    private static LunarSway instance;
    public static LunarSway getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getPluginManager().registerEvents(this, this);

        /*
            Команды
        */
        getCommand("ls-summon_meteor").setExecutor(new SummonMeteor());
        getCommand("ls-fly_to").setExecutor(new WorldChanger());

        /*
            Ивенты
        */
        getServer().getPluginManager().registerEvents(new PunchMeteor(), this);
        getServer().getPluginManager().registerEvents(new HookMeteor(), this);
        getServer().getPluginManager().registerEvents(new CobleFall(), this);
        getServer().getPluginManager().registerEvents(new PlaceCoble(), this);
        // getServer().getPluginManager().registerEvents(new ItemsGravity(), this);
        // block_events
        getServer().getPluginManager().registerEvents(new PlaceSmallGreenhouse(), this);

        /*
            Спавн метеоритов
        */
        Random random = new Random();
        int minTicks = 20; // Минимальное количество тиков (1 секунд)
        int maxTicks = 200; // Максимальное количество тиков (10 секунд)
        int interval = random.nextInt(maxTicks - minTicks + 1) + minTicks;
        TaskSpawnMeteor spawnMeteor = new TaskSpawnMeteor();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, spawnMeteor, 1, interval);

        /*
            Крафты
        */
        MyCrafts.smallGreenHouse();
    }

    @Override
    public void onDisable() {
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        int radius = 5; // радиус проверки вокруг игрока
        int height = 10; // высота проверки комнаты
        Material[] blockedMaterials = {Material.AIR};

        int minX = location.getBlockX() - radius;
        int minY = location.getBlockY();
        int minZ = location.getBlockZ() - radius;

        int maxX = location.getBlockX() + radius;
        int maxY = location.getBlockY() + height;
        int maxZ = location.getBlockZ() + radius;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = world.getBlockAt(x, y, z);
                    Material blockType = block.getType();
                    if (contains(blockedMaterials, blockType)) {
                        Vector playerPos = player.getLocation().toVector();
                        Vector blockPos = block.getLocation().toVector();
                        Vector blockToPlayer = playerPos.subtract(blockPos);
                        if (blockToPlayer.length() <= radius) {
                            player.sendMessage("Комната замкнута");
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean contains(Material[] materials, Material material) {
        for (Material m : materials) {
            if (m.equals(material)) {
                return true;
            }
        }
        return false;
    }
}
