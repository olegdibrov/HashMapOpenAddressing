package com.softindex.hashmap;

/**
 *
 */
public interface HashMap {

    /**
     * Associates the long value with the int key in this map.
     *
     * @param key
     * @param value
     */
    void put(int key, long value);

    /**
     * Returns the value to which the specified key is mapped, or throw NoSuchElementException on no mapping for the key.
     *
     * @param key
     * @return
     */
    long get(int key);

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return
     */
    int size();

}
