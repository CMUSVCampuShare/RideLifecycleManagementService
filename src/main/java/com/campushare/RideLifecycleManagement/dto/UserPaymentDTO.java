package com.campushare.RideLifecycleManagement.dto;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserPaymentDTO {
    String driverId;
    String[] passengerIds;
}