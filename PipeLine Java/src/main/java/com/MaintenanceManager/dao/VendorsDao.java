package com.MaintenanceManager.dao;

import com.MaintenanceManager.model.Vendors;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VendorsDao extends MongoRepository<Vendors, String> {

    List<Vendors> findByNameContaining(String name);
    List<Vendors> findByEmailContaining(String email);
    List<Vendors> findByPhoneNumber(int phoneNumber);


}
