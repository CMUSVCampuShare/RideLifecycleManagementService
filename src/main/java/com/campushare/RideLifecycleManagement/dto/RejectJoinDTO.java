package com.campushare.RideLifecycleManagement.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RejectJoinDTO {
    private String passengerId;
    private String message;
}
