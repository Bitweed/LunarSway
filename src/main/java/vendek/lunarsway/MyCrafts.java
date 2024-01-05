package vendek.lunarsway;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MyCrafts {
    public static void smallGreenHouse() {
        ItemStack smallGreenhouse = new ItemStack(Material.GLASS);
        ItemMeta meta = smallGreenhouse.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Маленькая теплица");
        meta.setCustomModelData(1);
        smallGreenhouse.setItemMeta(meta);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(LunarSway.getInstance(), "small_greenhouse"), smallGreenhouse);
        recipe.shape("G", "D");
        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('D', Material.DIRT);

        Bukkit.getServer().addRecipe(recipe);
    }
}
