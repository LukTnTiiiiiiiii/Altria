package com.el.altria.cache;

import java.util.HashMap;
import java.util.Map;

public abstract class RedisCacheAbstract<K, V> {

    // For this demo,please consider this map as redis cache
    Map<K, V> cache = new HashMap<>();


    // Try to get data from cache . Or fetch data from source database and cache it .
    public V get(K key) {
        V v = cache.get(key);
        if (v == null) {
            v = supply(key);
            cache.putIfAbsent(key, v);
        }
        return v;
    }

    // Fetch data from source database and cache it .
    public abstract V supply(K key) ;

    //  Cache data in advance for something like hot keys or big events
    public V warmUp(K key, V value) {
        return cache.putIfAbsent(key, value);
    }




}
