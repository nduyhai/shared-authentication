package com.nduyhai.rsexample.mapper;

import com.nduyhai.rsexample.dto.UserDTO;
import com.nduyhai.rsexample.dto.UserCreateDTO;
import com.nduyhai.rsexample.dto.UserUpdateDTO;
import com.nduyhai.rsexample.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    
    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreateDTO createDTO);
    
    @Mapping(target = "id", ignore = true)
    User toEntity(UserUpdateDTO updateDTO);
} 