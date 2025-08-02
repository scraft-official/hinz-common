package me.hinsinger.hinz.common.multi.key.map.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.multi.key.MultiKey;
import me.hinsinger.hinz.common.multi.key.map.MultiKeyMap;
import me.hinsinger.hinz.common.multi.key.wildcard.MultiKeyWildcard;
import me.hinsinger.hinz.common.multi.key.wildcard.impl.MultiKeyWildcards;

public class TetraMultiKeyMap<K1, K2, K3, K4, V> extends MultiKeyMap<V> {
    public void put(K1 a, K2 b, K3 c, K4 d, V value) {
        super.put(new MultiKey(a, b, c, d), value);
    }

    public Optional<V> getValue(K1 a, K2 b, K3 c, K4 d) {
        return super.getValue(new MultiKey(a, b, c, d));
    }

    // 16 getValues overloads with wildcard permutations
    public Collection<V> getValues(K1 a, K2 b, K3 c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, K3 c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, K3 c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, K2 b, MultiKeyWildcard c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, K2 b, K3 c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, K3 c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, MultiKeyWildcard c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, K3 c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, MultiKeyWildcard c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, K3 c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, K2 b, MultiKeyWildcard c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, MultiKeyWildcard c, K4 d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, K3 c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, K2 b, MultiKeyWildcard c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(K1 a, MultiKeyWildcard b, MultiKeyWildcard c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    public Collection<V> getValues(MultiKeyWildcard a, MultiKeyWildcard b, MultiKeyWildcard c, MultiKeyWildcard d) {
        return super.getValues(new MultiKey(a, b, c, d));
    }

    // key sets
    public Set<K1> getKeys() {
        return cast(super.getKeysAtLevel(new MultiKey(MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 0));
    }

    public Set<K2> getKeys(K1 a) {
        return cast(super.getKeysAtLevel(new MultiKey(a, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 1));
    }

    public Set<K3> getKeys(K1 a, K2 b) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, MultiKeyWildcards.ANY, MultiKeyWildcards.ANY), 2));
    }

    public Set<K4> getKeys(K1 a, K2 b, K3 c) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, c, MultiKeyWildcards.ANY), 3));
    }

    public Set<K4> getKeys(MultiKeyWildcard a, K2 b, K3 c) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, c, MultiKeyWildcards.ANY), 3));
    }

    public Set<K4> getKeys(K1 a, MultiKeyWildcard b, K3 c) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, c, MultiKeyWildcards.ANY), 3));
    }

    public Set<K4> getKeys(K1 a, K2 b, MultiKeyWildcard c) {
        return cast(super.getKeysAtLevel(new MultiKey(a, b, c, MultiKeyWildcards.ANY), 3));
    }
}
