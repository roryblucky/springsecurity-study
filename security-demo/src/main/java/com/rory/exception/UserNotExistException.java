package com.rory.exception;


public class UserNotExistException extends RuntimeException {
    private String userId;

    public UserNotExistException(String userId) {
        super("User not existed");
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
