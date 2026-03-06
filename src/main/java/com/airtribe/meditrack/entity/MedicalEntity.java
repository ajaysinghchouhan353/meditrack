package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.iface.Searchable;
import java.io.Serializable;

/**
 * Abstract base class for all medical entities.
 * Demonstrates abstraction with common medical entity behavior.
 */
public abstract class MedicalEntity implements Searchable, Serializable {
    private static final long serialVersionUID = 1L;

    protected String id;
    protected long createdTimestamp;
    protected long lastModifiedTimestamp;

    /**
     * Constructor for MedicalEntity.
     * @param id the unique identifier
     */
    protected MedicalEntity(String id) {
        this.id = id;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }

    /**
     * Get creation timestamp.
     * @return creation timestamp in milliseconds
     */
    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    /**
     * Get last modification timestamp.
     * @return last modified timestamp in milliseconds
     */
    public long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    /**
     * Update the last modified timestamp.
     * Called whenever entity is modified.
     */
    protected void updateModifiedTimestamp() {
        this.lastModifiedTimestamp = System.currentTimeMillis();
    }

    /**
     * Abstract method for creating a copy of the entity.
     * @return a copy of this entity
     */
    public abstract Object clone() throws CloneNotSupportedException;
}
