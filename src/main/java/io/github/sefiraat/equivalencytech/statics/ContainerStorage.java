package io.github.sefiraat.equivalencytech.statics;

import io.github.sefiraat.equivalencytech.EquivalencyTech;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ContainerStorage {

    private ContainerStorage() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean containerHasData(ItemStack i, NamespacedKey key, PersistentDataType<?, ?> type) {
        if (i != null && i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            return im.getPersistentDataContainer().has(key, type);
        }
        return false;
    }

    public static Integer getDataInteger(ItemStack i, NamespacedKey key) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            if (im.getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                return Objects.requireNonNull(i.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.INTEGER));
            }
        }
        return 0;
    }

    public static Long getDataLong(ItemStack i, NamespacedKey key) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            if (im.getPersistentDataContainer().has(key, PersistentDataType.LONG)) {
                return Objects.requireNonNull(i.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.LONG));
            }
        }
        return 0L;
    }

    public static void setData(ItemStack i, NamespacedKey key, int value) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            PersistentDataContainer c = im.getPersistentDataContainer();
            c.set(key, PersistentDataType.INTEGER, value);
            i.setItemMeta(im);
        }
    }

    public static void setData(ItemStack i, NamespacedKey key, String value) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            PersistentDataContainer c = im.getPersistentDataContainer();
            c.set(key, PersistentDataType.STRING, value);
            i.setItemMeta(im);
        }
    }

    public static void setData(ItemStack i, NamespacedKey key, long value) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            PersistentDataContainer c = im.getPersistentDataContainer();
            c.set(key, PersistentDataType.LONG, value);
            i.setItemMeta(im);
        }
    }

    public static void removeData(ItemStack i, NamespacedKey key) {
        if (i.hasItemMeta()) {
            ItemMeta im = i.getItemMeta();
            assert im != null;
            PersistentDataContainer c = im.getPersistentDataContainer();
            c.remove(key);
            i.setItemMeta(im);
        }
    }

    public static boolean isTransmutationOrb(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_TRANSMUTATION_ORB);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeTransmutationOrb(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_TRANSMUTATION_ORB);
        setData(i, key, 1);
    }

    public static boolean isCrafting(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_CRAFTING);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeCrafting(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_CRAFTING);
        setData(i, key, 1);
    }

    public static boolean isAlchemicalCoal(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_1);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeAlchemicalCoal(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_1);
        setData(i, key, 1);
    }

    public static boolean isMobiusFuel(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_2);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeMobiusFuel(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_2);
        setData(i, key, 1);
    }

    public static boolean isAeternalisFuel(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_3);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeAeternalisFuel(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_COAL_3);
        setData(i, key, 1);
    }

    public static boolean isDarkMatter(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_DRK);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeDarkMatter(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_DRK);
        setData(i, key, 1);
    }

    public static boolean isRedMatter(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_RED);
        return containerHasData(i, key, PersistentDataType.INTEGER);
    }

    public static void makeRedMatter(ItemStack i, EquivalencyTech plugin) {
        NamespacedKey key = new NamespacedKey(plugin.getInstance(), Constants.CS_IS_RED);
        setData(i, key, 1);
    }
}