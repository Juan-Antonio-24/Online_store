package com.example.OnlineStore.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String resourceId) {
        super("Resource '" + resourceName + "' with ID '" + resourceId + "' was not found.");
    }
}
