package com.fibikky.vehicle.network.util.maps;

import java.util.HashMap;

/**
 * 缓存最近一次访问记录的{@link HashMap}
 *
 * @author Aminor_z
 */
public class CachedHashMap<K, V> extends HashMap<K, V> {
    private Object lastKey = null;
    private V lastValue = null;

    /**
     * 以 O(1) 访问与最近一次键值相同的记录
     *
     * @param key 查询的键
     * @return 值
     */
    @Override
    public V get(Object key) {
        if (key.equals(lastKey)) {
            return lastValue;
        } else {
            V value = super.get(key);
            this.lastKey = key;
            this.lastValue = value;
            return value;
        }
    }
}
