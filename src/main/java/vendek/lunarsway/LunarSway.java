package vendek.lunarsway;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.scheduler.BukkitRunnable;
import vendek.lunarsway.commands.WorldChanger;
import vendek.lunarsway.events.VoidFishing;

public final class LunarSway extends JavaPlugin {
    private static LunarSway instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("ls-fly_to").setExecutor(new WorldChanger());
        getServer().getPluginManager().registerEvents(new VoidFishing(), this);
    }

    public static LunarSway getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
    }
}
