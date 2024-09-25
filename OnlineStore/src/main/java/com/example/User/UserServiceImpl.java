package com.example.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserServiceImpl implements UserService {

    private static Map<Integer, User> users = new HashMap<>();

    @Override
    @GetMapping("{userId}")
    public User getUser(@PathVariable("userId") int userId) {
        return users.get(userId);
    }

    @Override
    @PostMapping
    public boolean addUser(@RequestBody User user) {
        if (users.get(user.getUserId()) != null)
            return false;
        users.put(user.getUserId(), user);
        return true;
    }

    @Override
    @DeleteMapping("{userId}")
    public boolean deleteUser(@PathVariable("userId") int userId) {
        if (users.get(userId) == null)
            return false;
        users.remove(userId);
        return true;
    }

    @Override
    @GetMapping
    public User[] getAllUsers() {
        Set<Integer> ids = users.keySet();
        User[] userArray = new User[ids.size()];
        int i = 0;
        for (Integer id : ids) {
            userArray[i] = users.get(id);
            i++;
        }
        return userArray;
    }
}
