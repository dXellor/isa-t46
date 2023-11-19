package com.isat46.isaback.mappers;

import com.isat46.isaback.dto.user.AdminRegistrationDto;
import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private static ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static UserDto UserToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    public static User UserDtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
    public static User UserRegistrationDtoToUser(UserRegistrationDto userRegistrationDto){
        return modelMapper.map(userRegistrationDto, User.class);
    }

    public static User AdminRegistrationDtoToUser(AdminRegistrationDto adminRegistrationDto){
        return modelMapper.map(adminRegistrationDto, User.class);
    }

}
