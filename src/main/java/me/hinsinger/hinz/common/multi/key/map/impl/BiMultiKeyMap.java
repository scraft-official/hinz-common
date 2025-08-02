package me.hinsinger.hinz.common.multi.key.map.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.multi.key.MultiKey;
import me.hinsinger.hinz.common.multi.key.map.MultiKeyMap;
import me.hinsinger.hinz.common.multi.key.wildcard.MultiKeyWildcard;
import me.hinsinger.hinz.common.multi.key.wildcard.impl.MultiKeyWildcards;

public class BiMultiKeyMap<K1, K2, V> extends MultiKeyMap<V> {
    public void put(K1 a, K2 b, V value) {
        super.put(new MultiKey(a, b), value);
    }

    public Optional<V> getValue(K1 a, K2 b) {
        return super.getValue(new MultiKey(a, b));
    }

    public Collection<V> getValues(K1 a, K2 b) {
        return super.getValues(new MultiKey(a, b));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b) {
        return super.getValues(new MultiKey(a, b));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b) {
        return super.getValues(new MultiKey(a, b));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b) {
        return super.getValues(new MultiKey(a, b));
    }
    
    public Set<K1> getKeys() {
    	return cast(super.getKeysAtLevel(new MultiKey(MultiKeyWildcards.ANY), 0));
    }

    public Set<K2> getKeys(K1 a) {
        return cast(super.getKeysAtLevel(new MultiKey(a, MultiKeyWildcards.ANY), 1));
    }
    
    public Set<K2> getKeys(MultiKeyWildcard a) {
        return cast(super.getKeysAtLevel(new MultiKey(a, MultiKeyWildcards.ANY), 1));
    }
}
