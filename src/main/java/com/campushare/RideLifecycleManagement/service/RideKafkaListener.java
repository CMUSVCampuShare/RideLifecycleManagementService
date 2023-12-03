package com.campushare.RideLifecycleManagement.service;

import com.campushare.RideLifecycleManagement.exceptions.RideNotFoundException;
import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RideKafkaListener {
    private static final Logger logger = LoggerFactory.getLogger(RideKafkaListener.class);
    @Autowired
    private RideService rideService;

    @KafkaListener(topics = "create_post_topic", groupId = "ride-lifecycle-management-group", containerFactory = "kafkaListenerContainerFactory")
    public void listenPostRide(String postRideDTOString) throws JsonProcessingException {
        logger.info("Received a create_post_topic message from Kafka: {}", postRideDTOString);
        ObjectMapper objectMapper = new ObjectMapper();
        PostRideDTO postRideDTO = objectMapper.readValue(postRideDTOString, PostRideDTO.class);
        rideService.createRideEntry(postRideDTO);
    }

    @KafkaListener(topics = "edit_post_topic", groupId = "ride-lifecycle-management-group", containerFactory = "kafkaListenerContainerFactory")
    public void editPostRide(String editRideDTOString) throws JsonProcessingException {
        logger.info("Received an edit_post_topic message from Kafka: {}", editRideDTOString);
        ObjectMapper objectMapper = new ObjectMapper();
        PostRideDTO editRideDTO = objectMapper.readValue(editRideDTOString, PostRideDTO.class);
        try {
            if (editRideDTO.getPost().getStatus() == PostRideDTO.Status.COMPLETED) {
                String rideId = editRideDTO.getPost().getPostId();
                Ride ride = rideService.getRideEntry(rideId);
                rideService.completeRide(rideId, ride.getDriverId(), ride.getPassengerIds().toArray(new String[0]));
                logger.info("Ride " + rideId + " by " + ride.getDriverId() + " is completed.");
            } else {
                rideService.editRideEntry(editRideDTO);
            }
        } catch (RideNotFoundException e) {
            logger.error("Cannot find the RideId/PostId that you send: {}", e);
        }
    }
}
