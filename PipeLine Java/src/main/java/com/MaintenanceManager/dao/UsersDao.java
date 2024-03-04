package com.MaintenanceManager.dao;

import com.MaintenanceManager.model.Users;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
@Qualifier("usersDao")
public interface UsersDao extends MongoRepository<Users,String> {

    List<Users> findByUserNameContaining(String userName);
    List<Users> findByUserNameAndPassword(String userName, String password);
    List<Users> findByEmailContaining(String email);
    List<Users> findByPhoneNumber(int phoneNumber);
}
