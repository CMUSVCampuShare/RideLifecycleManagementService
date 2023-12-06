package com.campushare.RideLifecycleManagement.controller;
import com.campushare.RideLifecycleManagement.exceptions.NoSeatLeftException;
import com.campushare.RideLifecycleManagement.exceptions.RideNotFoundException;
import com.campushare.RideLifecycleManagement.model.Ride;
import com.campushare.RideLifecycleManagement.service.RideService;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/rides")
public class RideController {
    private static final Logger logger = LoggerFactory.getLogger(RideController.class);

    @Autowired
    private RideService rideService;

    @PostMapping("/approveJoinRequest")
    public String approveJoinRequest(@RequestParam String rideId, @RequestParam String passengerId) throws RideNotFoundException, NoSeatLeftException {
        Ride ride = rideService.getRideEntry(rideId);
        rideService.approveJoinRequest(ride, passengerId);
        return "Join request for " + passengerId + " to join " + rideId + " is approved, available seats reduced by 1";
    }

    @PostMapping("/rejectJoinRequest")
    public String rejectJoinRequest(@RequestParam String rideId, @RequestParam String rideTitle,  @RequestParam String passengerId) throws RideNotFoundException, JsonProcessingException {
        rideService.rejectJoinRequest(rideId, rideTitle, passengerId);
        return "Join request for " + passengerId + " to join " + rideId + " (rideTitle: " + rideTitle + ") is rejected";
    }
}
