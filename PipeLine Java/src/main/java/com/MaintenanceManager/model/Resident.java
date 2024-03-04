package com.MaintenanceManager.model;

public class Resident extends Users {
    private String apartmentNumber;

    public Resident(String userName, String password, String email,int phoneNumber, String apartmentNumber) {
        super(userName,password, email, phoneNumber);
        this.apartmentNumber = apartmentNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }


    @Override
    public String toString() {
        return "Resident{" +
                "apartmentNumber='" + apartmentNumber + '\'' +
                '}';
    }
}
