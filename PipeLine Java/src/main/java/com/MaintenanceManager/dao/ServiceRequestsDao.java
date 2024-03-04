package com.MaintenanceManager.dao;

import com.MaintenanceManager.model.ServiceRequests;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ServiceRequestsDao extends MongoRepository<ServiceRequests,String> {

    List<ServiceRequests> findByServiceRequestName(String name);
}
