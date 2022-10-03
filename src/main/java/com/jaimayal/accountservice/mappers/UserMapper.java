package com.jaimayal.accountservice.mappers;

import com.jaimayal.accountservice.dtos.UserDTO;
import com.jaimayal.accountservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
    
    UserDTO fromEntityToDto(User user);
    List<UserDTO> fromEntitiesToDtos(List<User> users);
    User fromDtoToEntity(UserDTO userDTO);
}
