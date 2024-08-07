package com.springbootmicroserviceudm.sbdemo.service;

import com.springbootmicroserviceudm.sbdemo.dto.NewUserRequestDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UpdatedUserDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UserDTO;

import java.util.List;

public interface UserService {

    public List<UserDTO> getAllUsers();
    public UserDTO getUserByID(String id);
    public UserDTO getUserByEmail(String id);
    public UserDTO createUSer(NewUserRequestDTO userRequestDTO);
    public UserDTO updateUser (String id , UpdatedUserDTO updatedUserDTO);
    public UserDTO deleteUserByID(String id);

}
