package com.epsilon.mob;

import org.bukkit.inventory.ItemStack;
import static com.epsilon.util.RandomUtil.randDoubleNormal;
import static com.epsilon.util.RandomUtil.random;

public class MobDrop {

    private final ItemStack item;
    private final double probability;
    private final double quantityMean;
    private final double quantityStdDev;

    public MobDrop(ItemStack item, double probability, double quantityMean, double quantityStdDev) {
        this.item = item;
        this.probability = probability;
        this.quantityMean = quantityMean;
        this.quantityStdDev = quantityStdDev;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getProbability() {
        return probability;
    }

    public double getQuantityMean() {
        return quantityMean;
    }

    public double getQuantityStdDev() {
        return quantityStdDev;
    }

    /**
     * Get the amount of drops. Different calls to this method may return different results, as this method uses
     * pseudorandom number generation.
     */
    public int getDroppedAmount() {
        if (random() >= probability) return 0;
        final int amount = (int) randDoubleNormal(quantityMean, quantityStdDev);
        if (amount <= 0) return 0;
        return amount;
    }

}
