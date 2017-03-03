package com.epsilon.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epsilon.util.Lazy;
import com.epsilon.util.PacketUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.epsilon.util.ColorUtil.colorf;
import static com.epsilon.util.ItemUtil.createItem;
import static com.epsilon.util.RandomUtil.randInt;
import static com.epsilon.util.RandomUtil.randIntNormal;

/**
 * Represents a weapon or a piece of armour.
 */
public class EItem {

    private Map<BuffType, Integer> buffs = new HashMap<>();
    private Map<RequirementType, Object> requirements = new HashMap<>();
    private int durability;
    private final Lazy<ItemStack> item = new Lazy<ItemStack>() {
        @Override
        public ItemStack compute() {
            final ItemStack item = createItem(material, 1, (short) 0, name);
            final ItemMeta meta = item.getItemMeta();
            final List<String> lore = new ArrayList<>();
            if (!requirements.isEmpty()) {
                lore.add("");
                for (RequirementType type : RequirementType.values()) { // So that they are always in the same order
                    if (requirements.containsKey(type)) {
                        final String value = requirements.get(type).toString();
                        lore.add(colorf("&e%s: %s", type.getName(), value));
                    }
                }
            }
            if (!buffs.isEmpty()) {
                lore.add("");
                for (BuffType type : BuffType.values()) { // So that the buffs are always in the same order
                    if (buffs.containsKey(type)) {
                        final int value = buffs.get(type);
                        lore.add(colorf(value <= buffQuality * 3 / 4 ? "&4%d%s %s" : value < 0 ? "&c%d%s %s" : value
                                < 20 ? "&a%d%s %s" : "&2%d%s %s", value, type.getUnit(), type.getName()));
                    }
                }
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
    };
    private final String name;
    private final int maxDurability;
    private final Material material;
    private final int buffQuality;

    /**
     * Construct a new {@link EItem}, and randomize its buffs.
     */
    public EItem(String name, Material material, int maxDurability, int buffQuality) {
        this.name = name;
        this.material = material;
        this.maxDurability = maxDurability;
        this.buffQuality = buffQuality;
        durability = getMaxDurability();
        randomizeBuffs();
    }

    public Map<String, Object> serialize() {
        final Map<String, Object> root = new HashMap<>();
        root.put("durability", durability);
        root.put("name", name);
        root.put("maxDurability", maxDurability);
        root.put("material", material.name());
        root.put("buffQuality", buffQuality);
        final Map<String, Integer> buffs = new HashMap<>();
        for (Map.Entry<BuffType, Integer> entry : this.buffs.entrySet()) {
            buffs.put(entry.getKey().name(), entry.getValue());
        }
        root.put("buffs", buffs);
        final Map<String, Object> requirements = new HashMap<>();
        for (Map.Entry<RequirementType, Object> entry : this.requirements.entrySet()) {
            requirements.put(entry.getKey().name(), entry.getValue());
        }
        root.put("requirements", requirements);
        return root;
    }

    /**
     * Deserialize an ItemStack into an EItem. If the ItemStack is invalid, {@code null} is returned.
     */
    public static EItem fromItemStack(ItemStack item) {
        try {
            // Me being as safe<?> as possible
            final Map<?, ?> root = (Map<?, ?>) PacketUtil.getItemNBT(item, "epsilon");
            if (root == null) {
                // No 'epsilon' tag
                return null;
            }
            final EItem eitem = new EItem((String) root.get("name"), Material.valueOf((String) root.get("material")),
                    (Integer) root.get("maxDurability"), (Integer) root.get("buffQuality"));
            eitem.durability = (Integer) root.get("durability");
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) root.get("buffs")).entrySet()) {
                eitem.buffs.put(BuffType.valueOf((String) entry.getKey()), (Integer) entry.getValue());
            }
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) root.get("requirements")).entrySet()) {
                eitem.requirements.put(RequirementType.valueOf((String) entry.getKey()), entry.getValue());
            }
            return eitem;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(colorf("&c[EItem] [fromItemStack] While parsing item %s: %s: %s",
                    item, e.getClass().getName(), e.getMessage()));
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getDurability() {
        return durability;
    }

    public ItemStack getItemStack() {
        return item.get();
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public int getBuffQuality() {
        return buffQuality;
    }

    public int getBuff(BuffType type) {
        final Integer value = buffs.get(type);
        return value == null ? 0 : value;
    }

    /**
     * Set a buff's value.
     *
     * @param type the type of buff.
     * @param value the value of the buff (0 to remove the buff).
     */
    public void setBuff(BuffType type, int value) {
        if (value == 0) buffs.remove(type);
        else buffs.put(type, value);
    }

    public void randomizeBuffs() {
        for (BuffType buffType : BuffType.values()) {
            // 20% chance to have this buff
            if (randInt(20) == 0) {
                setBuff(buffType, randIntNormal(0, buffQuality));
            } else {
                setBuff(buffType, 0);
            }
        }
    }

    public void clearBuffs() {
        buffs.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> T getRequirement(RequirementType type) {
        return (T) requirements.get(type);
    }

    public <T> void setRequirement(RequirementType type, T value) {
        requirements.put(type, value);
    }

    public void clearRequirements() {
        requirements.clear();
    }

    public void updateItem() {
        item.invalidate();
    }

    /**
     * @deprecated Use {@link #updateItem()}
     */
    @Deprecated
    public void updateLore() {
        updateItem();
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof EItem && this.material == ((EItem) that).material && this.maxDurability == ((EItem)
                that).maxDurability && this.name.equals(((EItem) that).name) && this.durability == ((EItem) that)
                .durability && this.buffs.equals(((EItem) that).buffs);
    }

    // Not going to add a hashCode() for now. I would mark to-do but it's not a priority

}
