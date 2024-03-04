package com.MaintenanceManager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection ="serviceRequests")
public class ServiceRequests {
    @Id
    private String id;

    private String userId; // Reference to the resident's document ID

    private String apartmentNumber;

    private String serviceRequestName;

    private Date serviceDate;

    private String description;

    private String status;

    public ServiceRequests(){

    }

    public ServiceRequests(String userId, String apartmentNumber, String serviceRequestName, Date serviceDate, String description, String status) {
        this.userId = userId;
        this.apartmentNumber = apartmentNumber;
        this.serviceRequestName = serviceRequestName;
        this.serviceDate = serviceDate;
        this.description = description;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserIdtId(String userId) {
        this.userId = userId;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getServiceRequestName() {
        return serviceRequestName;
    }

    public void setServiceRequestName(String serviceRequestName) {
        this.serviceRequestName = serviceRequestName;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceRequests{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", apartmentNumber='" + apartmentNumber + '\'' +
                ", serviceRequestName='" + serviceRequestName + '\'' +
                ", serviceDate=" + serviceDate +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
