package com.campushare.RideLifecycleManagement.repository;

import com.campushare.RideLifecycleManagement.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;
public interface RideRepository extends MongoRepository<Ride, UUID> {
}