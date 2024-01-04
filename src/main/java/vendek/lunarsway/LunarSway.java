package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import vendek.lunarsway.commands.SummonMeteor;
import vendek.lunarsway.commands.WorldChanger;
import vendek.lunarsway.events.CobleFall;
import vendek.lunarsway.events.HookMeteor;
import vendek.lunarsway.events.PlaceCoble;
import vendek.lunarsway.tasks.TaskSpawnMeteor;

import java.util.Random;

public final class LunarSway extends JavaPlugin {
    private static LunarSway instance;


    @Override
    public void onEnable() {
        instance = this;

        getCommand("ls-summon_meteor").setExecutor(new SummonMeteor());
        getCommand("ls-fly_to").setExecutor(new WorldChanger());

        getServer().getPluginManager().registerEvents(new HookMeteor(), this);
        getServer().getPluginManager().registerEvents(new CobleFall(), this);
        getServer().getPluginManager().registerEvents(new PlaceCoble(), this);


        Random random = new Random();
        int minTicks = 20; // Минимальное количество тиков (1 секунд)
        int maxTicks = 200; // Максимальное количество тиков (10 секунд)
        int interval = random.nextInt(maxTicks - minTicks + 1) + minTicks;
        TaskSpawnMeteor spawnMeteor = new TaskSpawnMeteor();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, spawnMeteor, 1, interval);
    }

    public static LunarSway getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }
}
