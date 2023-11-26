package com.campushare.RideLifecycleManagement.service;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.dto.RejectJoinDTO;
import com.campushare.RideLifecycleManagement.dto.UserPaymentDTO;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.repository.RideRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.campushare.RideLifecycleManagement.exceptions.RideNotFoundException;
import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import java.util.Optional;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RideService {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);

    @Autowired
    RideRepository rideRepository;
    @Autowired
    private KafkaTemplate<String, String> rejectKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, String> userPaymentKafkaTemplate;

    public void createRideEntry(PostRideDTO postRideDTO) {
        Ride ride = new Ride(postRideDTO.getPost());
        rideRepository.save(ride);
    }

    public void editRideEntry(PostRideDTO editRideDTO) throws RideNotFoundException {
        Optional<Ride> existingRideOpt = rideRepository.findById(editRideDTO.getPost().getPostId());
        if (existingRideOpt.isPresent()) {
            Ride existingRide = existingRideOpt.get();
            existingRide.setRideId(editRideDTO.getPost().getPostId());
            existingRide.setDriverId(editRideDTO.getPost().getUserId());
            existingRide.setTitle(editRideDTO.getPost().getTitle());
            existingRide.setFrom(editRideDTO.getPost().getFrom());
            existingRide.setTo(editRideDTO.getPost().getTo());
            existingRide.setType(editRideDTO.getPost().getType());
            existingRide.setNoOfSeats(editRideDTO.getPost().getNoOfSeats());
            existingRide.setStatus(editRideDTO.getPost().getStatus());
            existingRide.setTimestamp(editRideDTO.getPost().getTimestamp());
            rideRepository.save(existingRide);
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + editRideDTO.getPost().getPostId());
        }
    }

    public void approveJoinRequest(String rideId, String passengerId) throws RideNotFoundException, NoSeatLeftException {
        Optional<Ride> existingRideOpt = rideRepository.findById(rideId);
        if (existingRideOpt.isPresent()) {
            Ride existingRide = existingRideOpt.get();
            if (existingRide.getNoOfSeats() < 1) {
                throw new NoSeatLeftException("This ride " + rideId + " has no seat left now! ");
            } else {
                existingRide.addPassenger(passengerId);
                rideRepository.save(existingRide);
            }
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + rideId);
        }
    }

    public void rejectJoinRequest(String rideId, String rideTitle, String passengerId) throws RideNotFoundException, JsonProcessingException {
        Optional<Ride> existingRideOpt = rideRepository.findById(rideId);
        if (existingRideOpt.isPresent()) {
            String rejectMessage = "Sorry, your request to join in the ride " + rideTitle + " was rejected.";
            RejectJoinDTO rejectJoinDTO = new RejectJoinDTO(passengerId, rejectMessage);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRejectJoinDTO = objectMapper.writeValueAsString(rejectJoinDTO);
            rejectKafkaTemplate.send("reject_join_topic", jsonRejectJoinDTO);
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + rideId);
        }
    }

    public void completeRide(String rideId, String driverId, String[] passengerIds) throws RideNotFoundException, JsonProcessingException {
        // Send a user_payment_topic to Kafka
        UserPaymentDTO userPaymentDTO = new UserPaymentDTO(driverId, passengerIds);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUserPaymentDTO = objectMapper.writeValueAsString(userPaymentDTO);
        userPaymentKafkaTemplate.send("user_payment_topic", jsonUserPaymentDTO);

        // Save the record into database
        Optional<Ride> existingRideOpt = rideRepository.findById(rideId);
        if (existingRideOpt.isPresent()) {
            Ride existingRide = existingRideOpt.get();
            existingRide.setStatus(PostRideDTO.Status.COMPLETED); // TODO: Decouple the Status from the PostRideDTO
            existingRide.setTimestamp(new Date());
            rideRepository.save(existingRide);
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + rideId);
        }
    }

}
