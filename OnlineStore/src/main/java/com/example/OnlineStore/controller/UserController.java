package com.example.OnlineStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.OnlineStore.model.User;
import com.example.OnlineStore.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "3. Users", description = "Controller for managing users")
@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Get all users", responses = {
        @ApiResponse(responseCode = "200", description = "Users found",
                     content = @Content(mediaType = "application/json",
                     array = @ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = User.class)))),
        @ApiResponse(responseCode = "404", description = "No users found")
    })
    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @Operation(summary = "Get user by ID", responses = {
        @ApiResponse(responseCode = "200", description = "User found",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("{userId}")
    public ResponseEntity<?> getById(@PathVariable Integer userId) { // Cambiado de getByControlNumber a getById
        User user = service.getById(userId); // Cambiado de getByControlNumber a getById
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Register a new user", responses = {
        @ApiResponse(responseCode = "200", description = "User registered successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody User user) {
        service.save(user);
        return new ResponseEntity<String>("Saved record", HttpStatus.OK);
    }

    @Operation(summary = "Update an existing user", responses = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("{userId}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer userId) {
        User auxUser = service.getById(userId); // Cambiado de getByControlNumber a getById
        if (auxUser != null) {
            user.setUserId(auxUser.getUserId());
            service.save(user);
            return new ResponseEntity<String>("Updated record", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a user", responses = {
        @ApiResponse(responseCode = "200", description = "User deleted successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("{userId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId) {
        service.delete(userId);
        return new ResponseEntity<String>("Deleted record", HttpStatus.OK);
    }
}






