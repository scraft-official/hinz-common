package me.hinsinger.hinz.common.multi.key.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import me.hinsinger.hinz.common.multi.key.MultiKey;
import me.hinsinger.hinz.common.multi.key.map.impl.BiMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.map.impl.TetraMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.map.impl.TriMultiKeyMap;
import me.hinsinger.hinz.common.multi.key.wildcard.impl.MultiKeyWildcards;

public class MultiKeyMap<V> {
	
	public static <A, B, OUT> BiMultiKeyMap<A, B, OUT> bi() {
		return new BiMultiKeyMap<>();
	}
	
	public static <A, B, C, OUT> TriMultiKeyMap<A, B, C, OUT> tri() {
		return new TriMultiKeyMap<>();
	}
	
	public static <A, B, C, D, OUT> TetraMultiKeyMap<A, B, C, D, OUT> tetra() {
		return new TetraMultiKeyMap<>();
	}
	
    private final TrieNode<V> root = new TrieNode<>();

    private static class TrieNode<V> {
        final Map<Object, TrieNode<V>> children = new HashMap<>();
        V value = null;
    }

    // helpers
    private static boolean isWildcard(Object o) {
        return o == MultiKeyWildcards.ANY;
    }

    private void validateNoWildcardInPut(MultiKey key) {
        for (int i = 0; i < key.size(); i++) {
            Object k = key.get(i);
            if (isWildcard(k)) throw new IllegalArgumentException("Wildcard not allowed in put key");
            if (k == null) throw new IllegalArgumentException("Null not allowed as key component");
        }
    }

    // protected core operations
    protected void put(MultiKey key, V value) {
        validateNoWildcardInPut(key);
        TrieNode<V> cur = root;
        for (int i = 0; i < key.size(); i++) {
            Object k = key.get(i);
            cur = cur.children.computeIfAbsent(k, x -> new TrieNode<>());
        }
        cur.value = value;
    }

    protected Optional<V> getValue(MultiKey key) {
        TrieNode<V> cur = root;
        for (int i = 0; i < key.size(); i++) {
            Object k = key.get(i);
            if (isWildcard(k)) return Optional.empty();
            cur = cur.children.get(k);
            if (cur == null) return Optional.empty();
        }
        return Optional.ofNullable(cur.value);
    }

    protected Collection<V> getValues(MultiKey pattern) {
        List<V> out = new ArrayList<>();
        collectWildcard(root, pattern, 0, out);
        return out;
    }

    protected boolean containsKey(MultiKey key) {
        return getValue(key).isPresent();
    }

    protected boolean containsAnyValues(MultiKey pattern) {
        return containsWildcard(root, pattern, 0);
    }

    protected void remove(MultiKey key) {
        removeRecursive(root, key, 0);
    }

    protected Collection<Object> getKeysAtLevel(MultiKey pattern, int targetLevel) {
        if (targetLevel < 0 || targetLevel >= pattern.size())
            throw new IllegalArgumentException("Level out of bounds");
        Set<Object> result = new HashSet<>();
        collectKeysAtLevel(root, pattern, 0, targetLevel, result);
        return result;
    }

    protected void clear() {
        root.children.clear();
        root.value = null;
    }

    // recursion
    private void collectWildcard(TrieNode<V> node, MultiKey pattern, int depth, List<V> out) {
        if (node == null) return;
        if (depth == pattern.size()) {
            if (node.value != null) out.add(node.value);
            return;
        }
        Object k = pattern.get(depth);
        if (isWildcard(k)) {
            for (TrieNode<V> child : node.children.values()) {
                collectWildcard(child, pattern, depth + 1, out);
            }
        } else {
            TrieNode<V> child = node.children.get(k);
            if (child != null) collectWildcard(child, pattern, depth + 1, out);
        }
    }

    private boolean containsWildcard(TrieNode<V> node, MultiKey pattern, int depth) {
        if (node == null) return false;
        if (depth == pattern.size()) return node.value != null;
        Object k = pattern.get(depth);
        if (isWildcard(k)) {
            for (TrieNode<V> child : node.children.values()) {
                if (containsWildcard(child, pattern, depth + 1)) return true;
            }
        } else {
            TrieNode<V> child = node.children.get(k);
            if (child != null && containsWildcard(child, pattern, depth + 1)) return true;
        }
        return false;
    }

    private boolean removeRecursive(TrieNode<V> node, MultiKey key, int depth) {
        if (depth == key.size()) {
            node.value = null;
            return node.children.isEmpty();
        }
        Object k = key.get(depth);
        if (isWildcard(k)) return false;
        TrieNode<V> child = node.children.get(k);
        if (child == null) return false;
        boolean shouldDelete = removeRecursive(child, key, depth + 1);
        if (shouldDelete) node.children.remove(k);
        return node.children.isEmpty() && node.value == null;
    }

    private void collectKeysAtLevel(TrieNode<V> node, MultiKey pattern, int depth, int target, Set<Object> out) {
        if (node == null) return;
        if (depth == target) {
            Object k = pattern.get(depth);
            if (isWildcard(k)) {
                out.addAll(node.children.keySet());
            } else if (node.children.containsKey(k)) {
                out.add(k);
            }
            return;
        }
        Object k = pattern.get(depth);
        if (isWildcard(k)) {
            for (Map.Entry<Object, TrieNode<V>> e : node.children.entrySet()) {
                collectKeysAtLevel(e.getValue(), pattern, depth + 1, target, out);
            }
        } else {
            TrieNode<V> child = node.children.get(k);
            if (child != null) collectKeysAtLevel(child, pattern, depth + 1, target, out);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> Set<T> cast(Collection<Object> raw) {
        return (Set<T>) new HashSet<>(raw);
    }
}
