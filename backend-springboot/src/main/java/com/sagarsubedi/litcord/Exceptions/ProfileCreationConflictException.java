package com.sagarsubedi.litcord.Exceptions;

public class ProfileCreationConflictException extends RuntimeException {
    public ProfileCreationConflictException(String message) {
        super(message);
    }
}
