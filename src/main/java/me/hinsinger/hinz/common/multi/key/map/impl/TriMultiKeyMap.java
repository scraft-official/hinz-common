package me.hinsinger.hinz.common.multi.key.map.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.multi.key.MultiKey;
import me.hinsinger.hinz.common.multi.key.map.MultiKeyMap;
import me.hinsinger.hinz.common.multi.key.wildcard.MultiKeyWildcard;
import me.hinsinger.hinz.common.multi.key.wildcard.impl.MultiKeyWildcards;

public class TriMultiKeyMap<K1, K2, K3, V> extends MultiKeyMap<V> {
    public void put(K1 a, K2 b, K3 c, V value) {
        super.put(new MultiKey(a, b, c), value);
    }

    public V get(K1 a, K2 b, K3 c) {
        return super.getValue(new MultiKey(a, b, c)).orElseGet(() -> null);
    }
    
    public Optional<V> getOptional(K1 a, K2 b, K3 c) {
        return super.getValue(new MultiKey(a, b, c));
    }
    
    public boolean contains(K1 a, K2 b, K3 c) {
    	return getOptional(a, b, c).isPresent();
    }

    // 8 combinations of wildcards
    public Collection<V> getValues(K1 a, K2 b, K3 c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, K3 c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, K3 c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(K1 a, K2 b, MultiKeyWildcard c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, K3 c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, MultiKeyWildcard c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, MultiKeyWildcard c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, MultiKeyWildcard c) {
        return super.getValues(new MultiKey(a, b, c));
    }

    // key sets
    public Set<K1> getKeys() {
        return cast(super.getKeysAtLevel(new MultiKey(MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 0));
    }

    public Set<K2> getKeys(K1 a) {
        return cast(super.getKeysAtLevel(new MultiKey(a, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 1));
    }

    public Set<K2> getKeys(MultiKeyWildcard a) {
        return cast(super.getKeysAtLevel(new MultiKey(a, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 1));
    }

    public Set<K3> getKeys(K1 a, K2 b) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, MultiKeyWildcards.ANY), 2));
    }

    public Set<K3> getKeys(MultiKeyWildcard a, K2 b) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, MultiKeyWildcards.ANY), 2));
    }

    public Set<K3> getKeys(K1 a, MultiKeyWildcard b) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, MultiKeyWildcards.ANY), 2));
    }
}