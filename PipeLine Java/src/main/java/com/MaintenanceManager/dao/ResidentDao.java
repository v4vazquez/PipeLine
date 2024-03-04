package com.MaintenanceManager.dao;

import com.MaintenanceManager.model.Resident;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
@Qualifier("residentDao")
public interface ResidentDao extends MongoRepository<Resident, String> {

    List<Resident> findByUserName(String userName);
}
