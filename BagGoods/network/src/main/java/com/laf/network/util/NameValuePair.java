package com.laf.network.util;

import java.io.Serializable;

/**
 * Created by apple on 17/2/18.
 */
public class NameValuePair<K extends Serializable, V extends Serializable> {
    public final K key;
    public final V value;

    public NameValuePair(K k, V v) {
        key = k;
        value = v;
    }
}
