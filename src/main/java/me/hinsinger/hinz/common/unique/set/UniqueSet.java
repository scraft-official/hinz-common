package me.hinsinger.hinz.common.unique.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class UniqueSet<T> implements Set<T> {
	
	private Map<T, T> map;

    public UniqueSet() {
        this.map = new LinkedHashMap<>();
    }

    @Override
    public boolean add(T element) {
        boolean replaced = !map.containsKey(element);
        map.put(element, element);
        return replaced;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) != null;
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return map.values().toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return map.values().toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean changed = false;
        for (T t : c)
            if (add(t)) changed = true;
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c)
            if (remove(o)) changed = true;
        return changed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;
        Set<?> other = (Set<?>) o;
        return this.size() == other.size() && this.containsAll(other);
    }

    @Override
    public int hashCode() {
        return map.values().hashCode();
    }
}
