package me.hinsinger.hinz.common.lifecycle.registery;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import me.hinsinger.hinz.common.lifecycle.exception.LifecycleException;
import me.hinsinger.hinz.common.lifecycle.phase.Disposable;
import me.hinsinger.hinz.common.lifecycle.phase.Initializable;
import me.hinsinger.hinz.common.lifecycle.status.LifecycleStatus;

public class LifecycleRegistery {
	
	private final Map<Object, LifecycleStatus> components = new HashMap<>();

    public synchronized void add(Initializable component) {
        components.putIfAbsent(component, LifecycleStatus.NOT_INITIALIZED);
    }

    public synchronized void add(Disposable component) {
        components.putIfAbsent(component, LifecycleStatus.NOT_INITIALIZED);
    }

    public void addAndInit(Initializable component) {
        synchronized (this) {
            components.put(component, LifecycleStatus.NOT_INITIALIZED);
        }
        // attempt init regardless of prior state except when already INITIALIZED
        synchronized (this) {
            LifecycleStatus status = components.get(component);
            if (status == LifecycleStatus.INITIALIZED) {
                return;
            }
        }
        try {
            component.init();
            synchronized (this) {
                components.put(component, LifecycleStatus.INITIALIZED);
            }
        } catch (Exception e) {
            synchronized (this) {
                components.put(component, LifecycleStatus.NOT_INITIALIZED);
            }
            throw new LifecycleException("addAndInit failed for component: " + component, e);
        }
    }

    public synchronized void remove(Initializable component) {
        components.remove(component);
    }

    public synchronized void remove(Disposable component) {
        components.remove(component);
    }

    public void init() {
        Set<Object> snapshot;
        synchronized (this) {
            snapshot = new LinkedHashSet<>(components.keySet());
        }
        LifecycleException aggregate = null;
        for (Object obj : snapshot) {
            if (!(obj instanceof Initializable)) continue;
            synchronized (this) {
                LifecycleStatus current = components.get(obj);
                if (current != LifecycleStatus.NOT_INITIALIZED && current != LifecycleStatus.DISPOSED) continue;
            }
            try {
                ((Initializable) obj).init();
                synchronized (this) {
                    components.put(obj, LifecycleStatus.INITIALIZED);
                }
            } catch (Exception e) {
                if (aggregate == null) {
                    aggregate = new LifecycleException("One or more init() operations failed", e);
                } else {
                    aggregate.addSuppressed(e);
                }
            }
        }
        if (aggregate != null) {
            throw aggregate;
        }
    }

    public void dispose() {
        Set<Object> snapshot;
        synchronized (this) {
            snapshot = new LinkedHashSet<>(components.keySet());
        }
        LifecycleException aggregate = null;
        for (Object obj : snapshot) {
            if (!(obj instanceof Disposable)) continue;
            synchronized (this) {
                if (components.get(obj) != LifecycleStatus.INITIALIZED) continue;
            }
            try {
                ((Disposable) obj).dispose();
                synchronized (this) {
                    components.put(obj, LifecycleStatus.DISPOSED);
                }
            } catch (Exception e) {
                if (aggregate == null) {
                    aggregate = new LifecycleException("One or more dispose() operations failed", e);
                } else {
                    aggregate.addSuppressed(e);
                }
            }
        }
        if (aggregate != null) {
            throw aggregate;
        }
    }

    public synchronized LifecycleStatus getStatus(Object component) {
        return components.get(component);
    }
}
