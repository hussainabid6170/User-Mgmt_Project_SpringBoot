package com.springbootmicroserviceudm.sbdemo.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewUserRequestDTO {

    @Valid
    @NotNull(message = "lastName is mandatory")
    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @Valid
    @NotNull(message = "FirstName is mandatory")
    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @Valid
    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    private String email;

    @Valid
    @NotNull(message = "password is mandatory")
    @NotBlank(message = "password is mandatory")
    private String password;

}
