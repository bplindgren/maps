package com.sb.maps.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotEmpty(message="First Name should not be empty")
    private String firstName;

    @NotEmpty(message="Last Name should not be empty")
    private String lastName;

    @NotEmpty(message="Username should not be empty")
    private String username;

    @NotEmpty(message="Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message="Token should not be empty")
    private String token;
}
