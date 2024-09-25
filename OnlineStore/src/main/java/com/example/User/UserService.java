package com.example.User;

public interface UserService {

    boolean addUser(User user);

    boolean deleteUser(int userId);

    User getUser(int userId);

    User[] getAllUsers();
}
