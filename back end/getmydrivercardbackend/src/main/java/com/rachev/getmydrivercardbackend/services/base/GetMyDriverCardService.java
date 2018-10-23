package com.rachev.getmydrivercardbackend.services.base;

import com.rachev.getmydrivercardbackend.models.UserDTO;

import java.util.List;

public interface GetMyDriverCardService {
    List<UserDTO> getAllUsers();

    void createUser(UserDTO userDTO);
}