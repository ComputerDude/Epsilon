package com.epsilon.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public abstract class EItem {

    private Map<BuffType, Integer> buffs = new HashMap<>();
    private Map<RequirementType, Object> requirements = new HashMap<>();
    private int durability;
    private ItemStack item;
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
        item = createItem(getMaterial(), 1, (short) 0, getName());
        updateLore();
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
        return item;
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

    public void updateLore() {
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
                    lore.add(colorf(value <= 20 ? "&4%d%s %s" : value < 0 ? "&c%d%s %s" : value < 20 ? "&a%d%s %s"
                            : "&2%d%s %s", value, type.getUnit(), type.getName()));
                }
            }
        }
        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    @Override
    public boolean equals(Object that) {
        return that instanceof EItem && this.material == ((EItem) that).material && this.maxDurability == ((EItem)
                that).maxDurability && this.name.equals(((EItem) that).name) && this.durability == ((EItem) that)
                .durability && this.buffs.equals(((EItem) that).buffs);
    }

    // Not going to add a hashCode() for now. I would mark to-do but it's not a priority

}
