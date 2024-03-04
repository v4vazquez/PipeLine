package com.MaintenanceManager.Controller;

import com.MaintenanceManager.dao.VendorsDao;
import com.MaintenanceManager.model.Vendors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class VendorsController {
    @Autowired
    VendorsDao vendorsDao;

    @GetMapping("/vendors")
    public ResponseEntity<List<Vendors>> getAllVendors(@RequestParam(required=false)String name){
        try{
            List<Vendors> vendors;
            if (name == null) {
                vendors = vendorsDao.findAll();
            } else {
                vendors = vendorsDao.findByNameContaining(name);
            }

            if (vendors.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(vendors);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/vendors/{id}")
    public ResponseEntity<Vendors> getVendorById(@PathVariable("id")String id){
        Optional<Vendors>vendorsData = vendorsDao.findById(id);

        if(vendorsData.isPresent()){
            return new ResponseEntity<>(vendorsData.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/vendors")
    public ResponseEntity<Vendors> createVendors(@RequestBody Vendors vendors){
        try{
            Vendors vendorInfo = vendorsDao.save(new Vendors(vendors.getName(),
                    vendors.getEmail(), vendors.getDescription(), vendors.getPhoneNumber()));
            return new ResponseEntity<>(vendorInfo,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/vendors/{id}")
    public ResponseEntity<Vendors> updateVendors(@PathVariable("id")String id, @RequestBody Vendors vendors){
        Optional<Vendors> vendorsData = vendorsDao.findById(id);

        if(vendorsData.isPresent()){
            Vendors vendorInfo = vendorsData.get();
            vendorInfo.setName(vendors.getName());
            vendorInfo.setDescription(vendors.getDescription());
            vendorInfo.setEmail(vendors.getEmail());
            vendorInfo.setPhoneNumber(vendors.getPhoneNumber());
            return new ResponseEntity<>(vendorsDao.save(vendors),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vendors/{id}")
    public ResponseEntity<Vendors> deleteVendors(@PathVariable("id")String id){
        try{
            vendorsDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
