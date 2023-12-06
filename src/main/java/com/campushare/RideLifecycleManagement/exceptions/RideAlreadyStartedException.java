package com.campushare.RideLifecycleManagement.exceptions;

public class RideAlreadyStartedException extends RuntimeException {
    public RideAlreadyStartedException(String message) {
        super(message);
    }
}
