package com.airtribe.meditrack.util;

import com.airtribe.meditrack.iface.Searchable;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generic DataStore for storing and retrieving entities.
 * Thread-safe implementation using synchronization.
 * @param <T> the entity type
 */
public class DataStore<T extends Searchable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<T> store = Collections.synchronizedList(new ArrayList<>());

    /**
     * Add an entity to the store.
     * @param entity the entity to add
     */
    public void add(T entity) {
        if (entity != null) {
            store.add(entity);
        }
    }

    /**
     * Remove an entity from the store by ID.
     * @param id the entity ID
     * @return true if removed, false otherwise
     */
    public boolean remove(String id) {
        return store.removeIf(entity -> entity.getId().equals(id));
    }

    /**
     * Find an entity by ID.
     * @param id the entity ID
     * @return Optional containing the entity or empty
     */
    public Optional<T> findById(String id) {
        return store.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    /**
     * Get all entities.
     * @return list of all entities
     */
    public List<T> getAll() {
        return new ArrayList<>(store);
    }

    /**
     * Get the number of entities in the store.
     * @return the size
     */
    public int size() {
        return store.size();
    }

    /**
     * Clear all entities from the store.
     */
    public void clear() {
        store.clear();
    }

    /**
     * Check if store is empty.
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return store.isEmpty();
    }

    /**
     * Filter entities using a predicate.
     * @param predicate the filter predicate
     * @return filtered list
     */
    public List<T> filter(java.util.function.Predicate<T> predicate) {
        return store.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Check if an entity with given ID exists.
     * @param id the entity ID
     * @return true if exists, false otherwise
     */
    public boolean exists(String id) {
        return store.stream()
                .anyMatch(entity -> entity.getId().equals(id));
    }

    /**
     * Update an entity in the store.
     * @param id the entity ID to update
     * @param entity the updated entity
     * @return true if updated, false otherwise
     */
    public boolean update(String id, T entity) {
        int index = -1;
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            store.set(index, entity);
            return true;
        }
        return false;
    }

    /**
     * Serialize the store to a file.
     * @param filePath the file path
     */
    public void serialize(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(store));
        }
    }

    /**
     * Deserialize the store from a file.
     * @param filePath the file path
     */
    @SuppressWarnings("unchecked")
    public void deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<T> data = (List<T>) ois.readObject();
            store.clear();
            store.addAll(data);
        }
    }
}
