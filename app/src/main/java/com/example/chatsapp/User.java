package com.example.chatsapp;

public class User {
    private String userID;
    private String Name;
    private String phoneNumber;
    private String imageUrl;
    private String numberOfFriend;

    User()
    {

    }

    public User(String userID, String name, String phoneNumber, String imageUrl,String numberOfFriend) {
        this.userID = userID;
        Name = name;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.numberOfFriend=numberOfFriend;
    }

    public String getNumberOfFriend() {
        return numberOfFriend;
    }

    public void setNumberOfFriend(String numberOfFriend) {
        this.numberOfFriend = numberOfFriend;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
