package com.campushare.RideLifecycleManagement.exceptions;

public class NoSeatLeftException extends RuntimeException {
    public NoSeatLeftException(String message) {
        super(message);
    }
}
