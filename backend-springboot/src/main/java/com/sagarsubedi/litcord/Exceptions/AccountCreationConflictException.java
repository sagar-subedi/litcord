package com.sagarsubedi.litcord.Exceptions;

public class AccountCreationConflictException extends RuntimeException {
    public AccountCreationConflictException(String message) {
        super(message);
    }
}
