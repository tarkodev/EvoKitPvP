package fr.tarkod.kitpvp.item.itemspecificity;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class ItemSpecificityManager {

    private KitPvP main;

    private static String key = "KitPvPItemSpecificity";
    private static Gson gson = new Gson();

    public ItemSpecificityManager(KitPvP main) {
        this.main = main;
    }

    /*
    Create a new ItemSpecificity if he don't have one.
     */
    public ItemStack setItemSpecificity(ItemStack itemStack, Consumer<ItemSpecificity> itemSpecificityConsumer){
        ItemSpecificity itemSpecificity = hasItemSpecificity(itemStack) ? getItemSpecificity(itemStack) : new ItemSpecificity();
        itemSpecificityConsumer.accept(itemSpecificity);

        return setItemSpecificity(itemStack, itemSpecificity);
    }

    public ItemStack setItemSpecificity(ItemStack itemStack, ItemSpecificity itemSpecificity) {
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
    public ItemSpecificity getItemSpecificity(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound itemNBT = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        return itemNBT.hasKey(key) ? gson.fromJson(itemNBT.getString(key), ItemSpecificity.class) : null;
    }

    public boolean hasItemSpecificity(ItemStack itemStack){
        return getItemSpecificity(itemStack) != null;
    }

    public ItemStack updateItemBeauty(ItemStack itemStack) {
        if (!hasItemSpecificity(itemStack)) {
            return itemStack;
        }

        ItemSpecificity itemSpecificity = getItemSpecificity(itemStack);

        return itemSpecificity.getItem(itemStack, main);
    }
}