package com.jaimayal.accountservice.mappers;

import com.jaimayal.accountservice.dtos.UserDTO;

public interface UserMapper {
    UserDTO fromEntityToDto(User user);
    User fromDtoToEntity(UserDTO userDTO);
}
