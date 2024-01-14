package vendek.lunarsway.craft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import vendek.lunarsway.LunarSway;

import java.util.Arrays;

public class MyCrafts {
    public static void smallGreenHouse() {
        ItemStack smallGreenhouse = new ItemStack(Material.PAPER);
        ItemMeta meta = smallGreenhouse.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Маленькая теплица");

        // Описание предмета
        String[] lore = {
                ChatColor.GRAY + "Позволяет выращивать растения в условиях невесомости",
                ChatColor.WHITE + "[" + ChatColor.GREEN + "ПКМ чтобы установить" + ChatColor.WHITE + "]"
        };
        meta.setLore(Arrays.asList(lore));

        // NBT тег CustomModelData
        meta.getPersistentDataContainer().set(new NamespacedKey(LunarSway.getInstance(), "custommodeldata"), PersistentDataType.INTEGER, 24001);

        smallGreenhouse.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(LunarSway.getInstance(), "small_greenhouse"), smallGreenhouse);
        recipe.shape("CCC", "CSC", "JJJ");
        recipe.setIngredient('C', Material.GLASS);
        recipe.setIngredient('S', Material.DIRT);
        recipe.setIngredient('J', Material.IRON_INGOT);

        Bukkit.getServer().addRecipe(recipe);
    }
}