package com.MaintenanceManager.Controller;

import com.MaintenanceManager.dao.UsersDao;
import com.MaintenanceManager.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UsersController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UsersController.class);
    @Autowired
    @Qualifier("usersDao")
    UsersDao usersDao;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(@RequestParam(required = false)String userName){
        try{
            logger.info("GET request received for all users.");
            List<Users> users;
            if(userName == null){
                logger.info("Retrieving all users from database.");
                users = usersDao.findAll();
            }else{
                logger.info("Retrieving users with username containing: {}" , userName);
                users = usersDao.findByUserNameContaining(userName);
            }
            if(users.isEmpty()){
                logger.info("No users found.");
                return ResponseEntity.noContent().build();
            }
            logger.info("Returning {} users." , users.size());
            return ResponseEntity.ok(users);
        }catch(Exception e){
            logger.error("Error occurred while retrieving users.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("users/{id}")
    public ResponseEntity<Users> getUsersById(@PathVariable("id")String id){
        Optional<Users> usersData = usersDao.findById(id);
        if(usersData.isPresent()){
            return new ResponseEntity<>(usersData.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/users")
    public ResponseEntity<Users> createUsers(@RequestBody Users users){
        try{
            Users usersInfo = usersDao.save(new Users(users.getUserName(),
                    users.getPassword(),users.getEmail(),users.getPhoneNumber()));
            return new ResponseEntity<>(usersInfo,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Users> deleteUser(@PathVariable("id")String id){
        try{
            usersDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Users> login(@RequestBody Users user) {
        try {
            List<Users> users = usersDao.findByUserNameAndPassword(user.getUserName(), user.getPassword());
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(users.get(0), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
