package com.epsilon.util;

/**
 * A lazy variable.
 */
public abstract class Lazy<T> {

    private boolean hasValue = false;
    private T value;

    /**
     * Compute the value of this lazy variable. May return {@code null}.
     */
    public abstract T compute();

    /**
     * Get the value of this lazy variable. If it has not yet been computed, or it has been invalidated, the value
     * will be computed again.
     */
    public T get() {
        if (!hasValue) value = compute();
        return value;
    }

    public void invalidate() {
        hasValue = false;
        value = null;
    }

}
