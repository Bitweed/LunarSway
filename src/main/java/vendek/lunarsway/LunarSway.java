package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.SoundCategory;
import org.bukkit.Particle;

import vendek.lunarsway.command.SummonMeteor;
import vendek.lunarsway.command.WorldChanger;
import vendek.lunarsway.craft.MyCrafts;
import vendek.lunarsway.listener.playerListener.CobleFall;
import vendek.lunarsway.listener.playerListener.HookMeteor;
import vendek.lunarsway.listener.playerListener.PlaceSmallGreenhouse;
import vendek.lunarsway.listener.playerListener.PunchMeteor;
import vendek.lunarsway.listener.blockListener.PlaceCoble;
import vendek.lunarsway.task.TaskSpawnMeteor;
// import vendek.lunarsway.listener.blockListener.BarrierBreaker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public final class LunarSway extends JavaPlugin implements Listener {
    private static LunarSway instance;
    public static LunarSway getInstance() {
        return instance;
    }

    public static final int BREAKING_TICKS = 20;
    HashMap<Block, Integer> breakingBlocks = new HashMap<>(); // breaking block -> elapsed ticks of breaking

    private ArmorStand getArmorStandInBlock(Block block) {
        Location centerLoc = block.getLocation().toCenterLocation();
        for (Entity entity : centerLoc.getWorld().getEntities()) {
            if (entity instanceof ArmorStand && entity.getScoreboardTags().contains("barrier")
                    && entity.getLocation().distance(centerLoc) <= 0.5)
                return (ArmorStand)entity;
        }
        return null;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        Block block = e.getBlock();
        if (block.getType() == Material.BARRIER && getArmorStandInBlock(block) != null) {
            breakingBlocks.put(block, 1);
            // Bukkit.broadcastMessage("(DEBUG) " + e.getPlayer().getName() + " started breaking block");
        }
    }

    @EventHandler
    public void onBlackDamageAbort(BlockDamageAbortEvent e) {
        Block block = e.getBlock();
        // if (block.getType() == Material.BARRIER && breakingBlocks.remove(block) != null)
            // Bukkit.broadcastMessage("(DEBUG) " + e.getPlayer().getName() + " stopped breaking block");
    }

    @Override
    public void onEnable() {
        // saveDefaultConfig();

        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);

        // Register commands
        getCommand("ls-summon_meteor").setExecutor(new SummonMeteor());
        getCommand("ls-fly_to").setExecutor(new WorldChanger());

        // Register event listeners
        Bukkit.getPluginManager().registerEvents(new PunchMeteor(), this);
        Bukkit.getPluginManager().registerEvents(new HookMeteor(), this);
        Bukkit.getPluginManager().registerEvents(new CobleFall(), this);
        Bukkit.getPluginManager().registerEvents(new PlaceCoble(), this);
        Bukkit.getPluginManager().registerEvents(new PlaceSmallGreenhouse(), this);

        // Spawn meteors task
        Random random = new Random();
        int minTicks = 20; // Minimum number of ticks
        int maxTicks = 200; // Maximum number of ticks
        int interval = random.nextInt(maxTicks - minTicks + 1) + minTicks;
        TaskSpawnMeteor spawnMeteor = new TaskSpawnMeteor();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, spawnMeteor, 1, interval);

        // Register crafts
        MyCrafts.smallGreenHouse();

        // Breaking barrier
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Iterator<Map.Entry<Block, Integer>> it = breakingBlocks.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Block, Integer> entry = it.next();
                entry.setValue(entry.getValue() + 1);
                Block block = entry.getKey();
                int ticks = entry.getValue();
                if (ticks >= BREAKING_TICKS) {
                    ArmorStand armorStand = getArmorStandInBlock(block);
                    if (armorStand != null) {
                        it.remove();
                        armorStand.remove();
                        block.setType(Material.AIR);

                        // Playsound stone break
                        Location blockLocation = block.getLocation();
                        blockLocation.getWorld().playSound(blockLocation, "minecraft:block.stone.break", SoundCategory.BLOCKS, 1, 1);
                        // Spawn particles
                        blockLocation.getWorld().spawnParticle(Particle.BLOCK_CRACK, blockLocation.add(0.5, 0.5, 0.5), 16, 0.1, 0.1, 0.1, 1, Material.LIGHT_GRAY_CONCRETE.createBlockData());

                        break;
                    }
                }
            }
        }, 0, 1);
    }
}