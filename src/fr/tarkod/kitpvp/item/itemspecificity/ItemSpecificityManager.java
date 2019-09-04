package fr.tarkod.kitpvp.item.itemspecificity;

import com.google.gson.Gson;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemSpecificityManager {

    private static String key = "KitPvPItemSpecificity";
    private static Gson gson = new Gson();

    /*
    Create a new ItemSpecificity if he don't have one.
     */
    public static ItemStack setItemSpecificity(ItemStack itemStack, Consumer<ItemSpecificity> itemSpecificityConsumer){
        ItemSpecificity itemSpecificity = hasItemSpecificity(itemStack) ? getItemSpecificity(itemStack) : new ItemSpecificity();
        itemSpecificityConsumer.accept(itemSpecificity);

        return setItemSpecificity(itemStack, itemSpecificity);
    }

    public static ItemStack setItemSpecificity(ItemStack itemStack, ItemSpecificity itemSpecificity) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound itemNBT = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        itemNBT.setString(key, gson.toJson(itemSpecificity));
        nmsItem.setTag(itemNBT);

        ItemStack result = CraftItemStack.asBukkitCopy(nmsItem);
        result = updateItemBeauty(result);
        return result;
    }

    /*
    Can return null
     */
    public static ItemSpecificity getItemSpecificity(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound itemNBT = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        return itemNBT.hasKey(key) ? gson.fromJson(itemNBT.getString(key), ItemSpecificity.class) : null;
    }

    public static boolean hasItemSpecificity(ItemStack itemStack){
        return getItemSpecificity(itemStack) != null;
    }

    public static ItemStack updateItemBeauty(ItemStack itemStack) {
        if (!hasItemSpecificity(itemStack)) {
            return itemStack;
        }

        ItemStack result = itemStack.clone();

        ItemSpecificity itemSpecificity = getItemSpecificity(result);
        ItemMeta itemMeta = result.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(itemSpecificity.getLoreLine());
        itemMeta.setLore(lore);
        result.setItemMeta(itemMeta);

        return result;
    }
}