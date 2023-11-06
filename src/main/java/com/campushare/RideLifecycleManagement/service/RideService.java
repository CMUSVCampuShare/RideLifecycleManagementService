package com.campushare.RideLifecycleManagement.service;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.campushare.RideLifecycleManagement.exceptions.RideNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class RideService {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);

    @Autowired
    RideRepository rideRepository;
    public void createRideEntry(PostRideDTO postRideDTO) {
        Ride ride = new Ride(postRideDTO.getPost());
        rideRepository.save(ride);
    }

    public void editRideEntry(PostRideDTO editRideDTO) throws RideNotFoundException {
        Optional<Ride> existingRideOpt = rideRepository.findById(UUID.fromString(editRideDTO.getPost().getPostId()));
        if (existingRideOpt.isPresent()) {
            Ride existingRide = existingRideOpt.get();

            existingRide.setRideId(editRideDTO.getPost().getPostId());
            existingRide.setDriverId(editRideDTO.getPost().getUserId());
            existingRide.setType(editRideDTO.getPost().getType());
            existingRide.setNoOfSeats(editRideDTO.getPost().getNoOfSeats());
            existingRide.setStatus(editRideDTO.getPost().getStatus());
            existingRide.setTimestamp(editRideDTO.getPost().getTimestamp());
            rideRepository.save(existingRide);
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + editRideDTO.getPost().getPostId());
        }
    }

    public void approveJoinRequest(String rideId, String passengerId) throws RideNotFoundException {
        Optional<Ride> existingRideOpt = rideRepository.findById(UUID.fromString(rideId));
        if (existingRideOpt.isPresent()) {
            Ride existingRide = existingRideOpt.get();
            existingRide.addPassenger(passengerId);
            rideRepository.save(existingRide);
        } else {
            throw new RideNotFoundException("Cannot find the RideId/PostId that you send: " + rideId);
        }
    }

    public void rejectJoinReqeust(String rideId, String passengerId) {

    }

}
