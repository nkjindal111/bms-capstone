package com.scaler.bms.exceptions;

public class EntityNotFoundException extends Exception {
    private EntityType entityType;
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(EntityType entityType, String message) {
        super(message);
        this.entityType = entityType;
    }

    public EntityType getEntity() {
        return entityType;
    }
}