package com.MaintenanceManager.Controller;

import com.MaintenanceManager.dao.ResidentDao;
import com.MaintenanceManager.model.Resident;
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
public class ResidentController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    @Qualifier("residentDao")
    ResidentDao residentDao;

    @GetMapping("/residents")
    public ResponseEntity<List<Resident>> getAllResidents(@RequestParam(required =false)String name){
        try{
            logger.info("GET request received for all users.");
            List<Resident> residents;
            if(name == null){
                logger.info("Retrieving all users from database.");
                residents = residentDao.findAll();
            }else{
                logger.info("Retrieving users with username containing: {}", name);
                residents = residentDao.findByUserName(name);
            }
            if(residents.isEmpty()){
                logger.info("No users found.");
                return ResponseEntity.noContent().build();
            }
            logger.info("Returning {} users." , residents.size());
            return ResponseEntity.ok(residents);
        }catch(Exception e){
            logger.error("Error occurred while retrieving users.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/residents/{id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable("id")String id) {
        Optional<Resident> residentData = residentDao.findById(id);

        if (residentData.isPresent()) {
            return new ResponseEntity<>(residentData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/residents")
    public ResponseEntity<Resident> createAResident(@RequestBody Resident resident){
        try{
            Resident residentInfo = residentDao.save(new Resident(resident.getUserName(),
                   resident.getEmail(),resident.getPassword(),resident.getPhoneNumber(),
                    resident.getApartmentNumber()));
            return new ResponseEntity<>(residentInfo,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/residents/{id}")
    public ResponseEntity<Resident> deleteResident(@PathVariable("id")String id){
        try{
            residentDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
