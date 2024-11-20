package com.example.OnlineStore.DTO;

import java.io.Serializable;
import com.example.OnlineStore.model.User; 

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String name;
    private String email;
    private String address;
    private String userType;
    private String phone;

    public UserDTO() {}

    public UserDTO(Integer userId, String name, String email, String address, String userType, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.userType = userType;
        this.phone = phone;
    }

    
    public UserDTO(User user) {
        this.userId = user.getUserId(); 
        this.name = user.getName();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.userType = user.getUserType();
        this.phone = user.getPhone();
    }

    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
