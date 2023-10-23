package com.campushare.RideLifecycleManagement.service;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RideKafkaListener {
    private static final Logger logger = LoggerFactory.getLogger(RideKafkaListener.class);
    @Autowired
    private RideService rideService;

    @KafkaListener(topics = "PostRide", groupId = "ride-lifecycle-management-group", containerFactory = "kafkaListenerContainerFactory")
//    public void listenPostRide(PostRideDTO postRideDTO) {
//        logger.info("Received a postRideDTO message from Kafka: {}", postRideDTO);
//        rideService.createRideEntry(postRideDTO);
//    }
    public void listenPostRide(String testDTO) {
        logger.info("Received a postRideDTO message from Kafka: {}", testDTO);
        PostRideDTO postRideDTO = new PostRideDTO(UUID.randomUUID(), testDTO.split(":")[0], Integer.parseInt(testDTO.split(":")[1]), PostRideDTO.RideStatus.ON_GOING);
        rideService.createRideEntry(postRideDTO);
    }
}
