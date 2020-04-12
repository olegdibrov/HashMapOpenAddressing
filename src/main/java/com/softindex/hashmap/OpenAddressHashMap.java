package com.softindex.hashmap;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OpenAddressHashMap implements HashMap {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75F;

    private Entity[] table;
    private int size;
    private float loadFactor;
    private int capacity;


    public OpenAddressHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.capacity = DEFAULT_INITIAL_CAPACITY;
        this.table = new Entity[DEFAULT_INITIAL_CAPACITY];
    }

    public OpenAddressHashMap(float loadFactor, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }
        if (loadFactor <= 0) {
            throw new IllegalArgumentException("Illegal loadFactor: " + loadFactor);
        }
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        this.table = new Entity[capacity];
    }

    @Override
    public void put(int key, long value) {
        if (size == loadFactor * capacity) {
            resizeCapacity();
        }
        putVal(key, value);
    }

    @Override
    public long get(int key) {
        try {
            int bucketNumber = getBucketNumber(key);
            return table[bucketNumber].getValue();
        } catch (NullPointerException e) {
            throw new NoSuchElementException(String.format("Key %s doesn't exist in the map", key));
        }
    }

    @Override
    public int size() {
        return size;
    }

    private int hashFunction(int key) {
        return Math.abs(key % capacity);
    }

    private void putVal(int key, long value) {
        int hash = hashFunction(key);
        int bucketNumber = hash;
        boolean newKey = true;
        while (table[bucketNumber] != null && (newKey = table[bucketNumber].getKey() != key)) {
            bucketNumber++;
            if (bucketNumber == capacity) {
                resizeCapacity();
            }
        }
        table[bucketNumber] = new Entity(key, value);
        if (newKey) {
            size++;
        }
    }

    private void resizeCapacity() {
        Entity[] oldTable = table;
        capacity *= 2;
        size = 0;
        table = new Entity[capacity];
        Arrays.stream(oldTable).filter(Objects::nonNull).forEach(entity -> putVal(entity.getKey(), entity.getValue()));
    }

    protected int getBucketNumber(int key) {
        int bucketNumber = hashFunction(key);
        while (table[bucketNumber].getKey() != key) {
            bucketNumber++;
        }
        return bucketNumber;
    }

    protected int getCapacity() {
        return capacity;
    }
}
