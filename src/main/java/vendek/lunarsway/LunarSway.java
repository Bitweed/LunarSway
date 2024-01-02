package vendek.lunarsway;

import org.bukkit.plugin.java.JavaPlugin;

import vendek.lunarsway.commands.WorldChanger;

public final class LunarSway extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("ls-fly_to").setExecutor(new WorldChanger());
    }

    @Override
    public void onDisable() {
    }
}
