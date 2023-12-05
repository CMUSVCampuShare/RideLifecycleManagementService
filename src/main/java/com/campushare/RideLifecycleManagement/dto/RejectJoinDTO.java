package com.campushare.RideLifecycleManagement.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class RejectJoinDTO {
    private String passengerId;
    private RejectMessage message;
    @Data
    @AllArgsConstructor
    public static class RejectMessage {
        private String passengerID;
        private String postId;
        private String postTitle;
        private String notificationBody;
    }
}