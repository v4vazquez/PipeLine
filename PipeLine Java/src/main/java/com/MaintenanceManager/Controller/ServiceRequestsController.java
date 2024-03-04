package com.MaintenanceManager.Controller;

import com.MaintenanceManager.dao.ServiceRequestsDao;
import com.MaintenanceManager.model.ServiceRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceRequestsController {

    @Autowired
    ServiceRequestsDao serviceRequestsDao;

    @GetMapping("/serviceRequests")
    public ResponseEntity<List<ServiceRequests>> getAllServiceRequests(@RequestParam(required = false)String name){
        try{
            List<ServiceRequests> serviceRequests;
            if(name == null){
                serviceRequests = serviceRequestsDao.findAll();
            }else{
                serviceRequests = serviceRequestsDao.findByServiceRequestName(name);
            }
            if(serviceRequests.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(serviceRequests);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("serviceRequests/{id}")
    public ResponseEntity<ServiceRequests> getServiceRequestById(@PathVariable("id")String id){
        Optional<ServiceRequests>serviceRequestData = serviceRequestsDao.findById(id);

        if(serviceRequestData.isPresent()){
            return new ResponseEntity<>(serviceRequestData.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

   @PostMapping("/serviceRequests")
    public ResponseEntity<ServiceRequests> createAServiceRequest(@RequestBody ServiceRequests serviceRequests ){
        try{
            ServiceRequests serviceRequestInfo = serviceRequestsDao.save(new ServiceRequests(serviceRequests.getUserId()
                    ,serviceRequests.getApartmentNumber(),serviceRequests.getServiceRequestName()
                    ,serviceRequests.getServiceDate(),serviceRequests.getDescription(),serviceRequests.getStatus()));
            return new ResponseEntity<>(serviceRequestInfo,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

   @DeleteMapping("serviceRequests/{id}")
    public ResponseEntity<ServiceRequests> deleteServiceRequest(@PathVariable("id")String id){
        try{
            serviceRequestsDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

}
