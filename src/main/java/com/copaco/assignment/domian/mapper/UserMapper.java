package com.copaco.assignment.domian.mapper;

import com.copaco.assignment.domian.dto.UserDTO;
import com.copaco.assignment.domian.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);

    User toEntity(UserDTO quote);

    List<UserDTO> toDtoList(List<User> quotes);

    List<User> toEntityList(List<UserDTO> quotes);
}