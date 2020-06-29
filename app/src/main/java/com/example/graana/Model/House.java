package com.example.graana.Model;

public class House {

    private String OwnerName;
    private String OwnerPhone;
    private String Rent;
    private String Address;
    private String Catagory;

    public House(String ownerName, String ownerPhone, String rent, String address, String catagory) {
        OwnerName = ownerName;
        OwnerPhone = ownerPhone;
        Rent=rent;
        Address = address;
        Catagory = catagory;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getOwnerPhone() {
        return OwnerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        OwnerPhone = ownerPhone;
    }

    public String getRent() {
        return Rent;
    }

    public void setRent(String rent) {
        Rent = rent;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }
}
