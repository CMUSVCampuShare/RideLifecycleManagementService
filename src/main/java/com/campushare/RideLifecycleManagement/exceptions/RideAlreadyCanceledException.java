package com.campushare.RideLifecycleManagement.exceptions;

public class RideAlreadyCanceledException extends RuntimeException {
    public RideAlreadyCanceledException(String message) {
        super(message);
    }
}
