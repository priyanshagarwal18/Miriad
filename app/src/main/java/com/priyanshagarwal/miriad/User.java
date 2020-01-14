package com.priyanshagarwal.miriad;

import java.util.List;

public class User {
    private String firstName,lastName;

    private List<String> best;
    private String username,password;
    private String email,phoneNo;

    public User(String firstName,String lastName,List<String> best,String username,String password,String email,String phoneNo)
    {
        this.best=best;
        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phoneNo=phoneNo;
        this.username=username;
        this.password=password;

    }
    public User()
    {

    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getBest() {
        return best;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
