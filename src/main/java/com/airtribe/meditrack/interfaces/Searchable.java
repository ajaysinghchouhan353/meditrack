package com.airtribe.meditrack.interfaces;

/**
 * Searchable interface for entities that can be searched by ID.
 */
public interface Searchable {
    /**
     * Get the unique identifier of the entity.
     * @return the ID
     */
    String getId();
}
