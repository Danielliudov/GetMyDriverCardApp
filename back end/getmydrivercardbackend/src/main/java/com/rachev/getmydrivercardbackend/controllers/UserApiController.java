package com.rachev.getmydrivercardbackend.controllers;

import com.rachev.getmydrivercardbackend.models.UserDTO;
import com.rachev.getmydrivercardbackend.services.base.GetMyDriverCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final GetMyDriverCardService getMyDriverCardService;

    @Autowired
    public UserApiController(GetMyDriverCardService getMyDriverCardService) {
        this.getMyDriverCardService = getMyDriverCardService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return getMyDriverCardService.getAllUsers();
    }
    
    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
         getMyDriverCardService.createUser(userDTO);
    }
}