package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
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
}
