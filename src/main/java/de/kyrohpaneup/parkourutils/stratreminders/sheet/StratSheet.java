package de.kyrohpaneup.parkourutils.stratreminders.sheet;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StratSheet {

    public String map;
    public List<LinkedTreeMap<String, Object>> sheet;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public StratSheet(String mapId) {
        this.map = mapId;
        this.sheet = new ArrayList<>();
    }

    public void updateSheet(List<LinkedTreeMap<String, Object>> newEntries) {
        lock.writeLock().lock();
        try {
            this.sheet.clear();
            if (newEntries != null) {
                this.sheet.addAll(newEntries);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getSize() {
        lock.readLock().lock();
        try {
            return sheet.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }
}
