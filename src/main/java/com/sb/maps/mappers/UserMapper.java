package com.sb.maps.mappers;

import com.sb.maps.dtos.SignUpDto;
import com.sb.maps.dtos.UserDto;
import com.sb.maps.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
