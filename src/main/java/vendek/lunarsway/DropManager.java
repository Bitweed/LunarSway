package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DropManager {

    // Список, который будет хранить данные предметов из файла CSV.
    private List<ItemData> itemDataList = new ArrayList<>();

    public DropManager(JavaPlugin plugin) {
        loadItemData(plugin.getDataFolder() + File.separator + "meteor_drops.csv");
    }

    private void loadItemData(String filePath) {

        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4) {

                    String item = data[0].trim();
                    float chance = Float.parseFloat(data[1].trim());
                    String label = ChatColor.translateAlternateColorCodes('&', data[2].trim());
                    ChatColor color = ChatColor.valueOf(data[3].trim().toUpperCase());

                    // Сохраняем в класс ItemData.
                    itemDataList.add(new ItemData(item, chance, label, color));
                } else {
                    Bukkit.getLogger().warning("Неправильный формат строки в файле: " + line);
                }
            }
        } catch (IOException e) {
            Bukkit.getLogger().warning("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public void dropRandomItem(Player player) {
        Random random = new Random();
        float chanceSum = (float) itemDataList.stream().mapToDouble(ItemData::getChance).sum();
        float randomValue = random.nextFloat() * chanceSum;

        float cumulativeChance = 0f;

        for (ItemData itemData : itemDataList) {
            cumulativeChance += itemData.getChance();
            if (randomValue < cumulativeChance) {
                // Выдаём игроку предмет.
                Material material = Material.getMaterial(itemData.getItem());
                if (material != null) {
                    ItemStack itemStack = new ItemStack(material);
                    player.getInventory().addItem(itemStack);
                }

                float dropPercent = 100 - (randomValue / chanceSum) * 100;
                player.sendMessage("Вы получили: " + itemData.getColor() + itemData.getLabel() + ChatColor.WHITE +" (DEBUG: " + dropPercent + "%)");
                return;
            }
        }
    }

    private static class ItemData {
        private final String item;
        private final float chance;
        private final String label;
        private final ChatColor color;

        public ItemData(String item, float chance, String label, ChatColor color) {
            this.item = item;
            this.chance = chance;
            this.label = label;
            this.color = color;
        }
        public String getItem() {
            return item;
        }
        public float getChance() {
            return chance;
        }
        public String getLabel() {
            return label;
        }
        public ChatColor getColor() {
            return color;
        }
    }
}