package com.campushare.RideLifecycleManagement.dto;

import lombok.Data;

import java.util.List;
import java.util.Date;


@Data
public class PostRideDTO {
    private Post post;

    public enum Type {
        RIDE,
        FOODPICKUP,
        LUNCH
    }

    public enum Status {
        CREATED,
        FULL,
        ONGOING,
        COMPLETED,
        CANCELED
    }

    @Data
    public class Post {
        private String postId;
        private String userId;
        private String title;
        private String from;
        private String to;
        private String details;
        private Type type;
        private Integer noOfSeats;
        private Status status;
        private Date timestamp;
        private List<String> comments;
    }
}
