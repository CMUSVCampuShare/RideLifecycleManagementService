package com.campushare.RideLifecycleManagement.exceptions;

public class RideAlreadyCompletedException extends RuntimeException {
    public RideAlreadyCompletedException(String message) {
        super(message);
    }
}
