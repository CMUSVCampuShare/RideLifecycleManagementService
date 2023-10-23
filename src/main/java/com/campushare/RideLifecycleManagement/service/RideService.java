package com.campushare.RideLifecycleManagement.service;

import com.campushare.RideLifecycleManagement.dto.PostRideDTO;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RideService {
    private static final Logger logger = LoggerFactory.getLogger(RideService.class);

    @Autowired
    RideRepository rideRepository;
    public void createRideEntry(PostRideDTO postRideDTO) {
        Ride ride = new Ride(postRideDTO);
        rideRepository.save(ride);
    }
}
