package com.springbootmicroserviceudm.sbdemo.service;

import com.springbootmicroserviceudm.sbdemo.convert.UserConverter;
import com.springbootmicroserviceudm.sbdemo.dto.NewUserRequestDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UpdatedUserDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UserDTO;
import com.springbootmicroserviceudm.sbdemo.model.User;
import com.springbootmicroserviceudm.sbdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }


    public List<UserDTO> getAllUsers() {

        log.info("getAllUsers started");
        List<User> users = userRepository.findAll();
       return  users.stream().map(user -> userConverter.convertUsertoUserDTO(user)).collect(Collectors.toList());


    }


    public UserDTO getUserByID(String id) {

        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            return userConverter.convertUsertoUserDTO(user);
        }
        return null;
    }

    @Override
    public UserDTO getUserByEmail(String id) {

        User user = userRepository.findByEmail(id);
        if(user != null){
            return userConverter.convertUsertoUserDTO(user);
        }
        return null;
    }


    public UserDTO createUSer(NewUserRequestDTO userRequestDTO) {

        log.info("createUSer started" + userRequestDTO);
        User newUser = new User();
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setFirstName(userRequestDTO.getFirstName());
        newUser.setLastName(userRequestDTO.getLastName());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser= userRepository.save(newUser);
        return userConverter.convertUsertoUserDTO(newUser);
    }

    @Override
    public UserDTO updateUser(String id, UpdatedUserDTO userRequestDTO) {

        User updatedUser = userRepository.findById(id).orElse(null);

        if(updatedUser == null){
            return null;
        }

        updatedUser.setEmail(userRequestDTO.getEmail());
        updatedUser.setFirstName(userRequestDTO.getFirstName());
        updatedUser.setLastName(userRequestDTO.getLastName());
        updatedUser= userRepository.save(updatedUser);
        return userConverter.convertUsertoUserDTO(updatedUser);


    }

    @Override
    public UserDTO deleteUserByID(String id) {

        User deletedUser = userRepository.findById(id).orElse(null);
        if(deletedUser == null){
            return null;
        }

        userRepository.deleteById(id);

        return userConverter.convertUsertoUserDTO(deletedUser);
    }
}
