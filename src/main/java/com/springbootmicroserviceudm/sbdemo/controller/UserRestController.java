package com.springbootmicroserviceudm.sbdemo.controller;

import com.springbootmicroserviceudm.sbdemo.dto.NewUserRequestDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UpdatedUserDTO;
import com.springbootmicroserviceudm.sbdemo.dto.UserDTO;
import com.springbootmicroserviceudm.sbdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jdk.jfr.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Tag(name = "User API")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserRestController {

    @Value("${apikey}")
    private List<String> API_KEYs;

    //private static final List<UserDTO> users = new ArrayList<UserDTO>();
    private static final String BAD_API_KEY = "{\"status\":\"Authorization Failed \",\"message\":\"Invalid APIkey\"}";


    private UserService userService;
    public UserRestController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    @Operation(summary = "Retrieve All Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found zero or more Users",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))) }),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = "apikey", required = false) String apikey) {

        log.info("getAllUsers started");

        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }
        log.info("getAllUsers completed");

        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }


    @GetMapping("/users/{id}")
    @Operation(summary = "Retrieve User by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User matching this Id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getUserById(@RequestHeader(value = "apikey", required = false) String apikey, @Parameter(description = "id of User to be found") @PathVariable String id) {

        log.info("getUsers started");

        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }

       // List<UserDTO> userDTOList = users.stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList());
        UserDTO user = userService.getUserByID(id);

        log.info("getUsers completed");
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
    }


    @GetMapping("/userByEmail/{emailID}")
    @Operation(summary = "Retrieve User by Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User matching this Email",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> getUserByEmail(@RequestHeader(value = "apikey", required = false) String apikey, @Parameter(description = "email of User to be found") @PathVariable String emailID) {

        log.info("getUserByEmail started");

        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }

        // List<UserDTO> userDTOList = users.stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList());
        UserDTO user = userService.getUserByEmail(emailID);

        log.info("getUserByEmail completed");
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");
    }



    @PostMapping("/users")
    @Operation(summary = "Create New User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created new User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Failed to Create new User",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> createUser(@RequestHeader(value = "apikey", required = false) String apikey, @Parameter(description = "New User Body Content to be created") @Valid @RequestBody NewUserRequestDTO newUserRequest) {

        log.info("createUser started");

        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }
        log.info("newUserRequest {}", newUserRequest);
        UserDTO userDTO = userService.createUSer(newUserRequest);

//        UUID uuid = UUID.randomUUID();
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(uuid.toString());
//        userDTO.setFirstName(newUserRequest.getFirstName());
//        userDTO.setLastName(newUserRequest.getLastName());
//        userDTO.setEmail(newUserRequest.getEmail());
//       // userDTO.setp(newUserRequest.getPassword());
//        users.add(userDTO);

        log.info("createUser completed");

        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }



    @PutMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Updated User",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Failed to Update User",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Id does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> updateUser(@RequestHeader(value = "apikey", required = false) String apikey,
                                        @Parameter(description = "User Id to be updated") @PathVariable String id,  @Parameter(description = "User Elements/Body Content to be updated") @Valid @RequestBody UpdatedUserDTO submittedUserDTO) {


        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }
       // List<UserDTO> userDTOList = users.stream().filter(u -> u.getId().equals(id)).collect(Collectors.toList());

        log.info("updateUser started");

        UserDTO updatedUserDTO = userService.updateUser(id ,submittedUserDTO);
        if(updatedUserDTO != null){
            ResponseEntity.status(HttpStatus.CREATED).body(updatedUserDTO);
        }

        log.info("updateUser completed");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");

    }



    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Deleted User",
                    content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "Failed to Delete User",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User Id does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Authorization Failed",
                    content = @Content) })
    public ResponseEntity<?> deleteUser(@RequestHeader(value = "apikey", required = false) String apikey, @Parameter(description = "User Id to be deleted") @PathVariable String id) {


        if (apikey == null || !API_KEYs.contains(apikey)) {
            //return invalid apikey
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(BAD_API_KEY);

        }


        log.info("deleteUser started");

       UserDTO deletedUser =  userService.deleteUserByID(id);

        if(deletedUser != null){
            ResponseEntity.status(HttpStatus.OK).body("{}");
        }

       // List<UserDTO> userDTOList = users.stream().filter(u -> !u.getId().equals(id)).collect(Collectors.toList());
       // users.clear();
        //users.addAll(userDTOList);


        log.info("deleteUser completed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{}");

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidtionExceptions(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName,errorMessage);
                }
                );
        return errors;
    }

}
