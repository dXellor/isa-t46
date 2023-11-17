package com.isat46.isaback.service;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.mappers.UserMapper;
import com.isat46.isaback.model.Role;
import com.isat46.isaback.model.User;
import com.isat46.isaback.repository.RoleRepository;
import com.isat46.isaback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto){
        User newUser = UserMapper.UserRegistrationDtoToUser(userRegistrationDto);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(false);
        List<Role> roles = roleRepository.findByName("ROLE_USER");
        return UserMapper.UserToUserDto(userRepository.save(newUser));
    }

}
