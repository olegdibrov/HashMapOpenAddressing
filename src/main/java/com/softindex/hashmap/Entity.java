package com.softindex.hashmap;

import java.util.Objects;

public class Entity {
    private int key;
    private long value;

    public Entity() {
    }

    public Entity(int key, long value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public long getValue() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return key == entity.key &&
                value == entity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
