package dev.onyxstudios.minefactoryrenewed.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomMap<E> {

    private static final Random random = new Random();
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private double total = 0;

    public RandomMap<E> add(int weight, E item) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, item);

        return this;
    }

    public E randomEntry() {
        if (map.isEmpty())
            return null;

        double key = random.nextDouble() * total;
        return map.higherEntry(key).getValue();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
        total = 0;
    }
}
