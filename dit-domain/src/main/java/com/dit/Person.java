package com.dit;

public abstract class Person {

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String restaurantName;
    private String restaurantRegNo;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantRegNo() {
        return restaurantRegNo;
    }

    public void setRestaurantRegNo(String restaurantRegNo) {
        this.restaurantRegNo = restaurantRegNo;
    }
}
