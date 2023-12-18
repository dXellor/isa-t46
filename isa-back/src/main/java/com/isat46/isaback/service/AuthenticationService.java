package com.isat46.isaback.service;

import com.isat46.isaback.dto.user.AdminRegistrationDto;
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

    private final String ADMIN_DEFAULT_PASSWORD = "admin123";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto registerUser(UserRegistrationDto userRegistrationDto){
        User newUser = UserMapper.UserRegistrationDtoToUser(userRegistrationDto);
        verificationTokenService.GenerateVerificationToken(newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(false);
        List<Role> roles = roleRepository.findByName("ROLE_USER");
        newUser.setRoles(roles);
        newUser.setPendingPasswordReset(false);
        return UserMapper.UserToUserDto(userRepository.save(newUser));
    }

    public UserDto registerNewCompanyAdmin(AdminRegistrationDto adminRegistrationDto){
        User newAdmin = UserMapper.AdminRegistrationDtoToUser(adminRegistrationDto);
        newAdmin.setPassword(passwordEncoder.encode(ADMIN_DEFAULT_PASSWORD));
        newAdmin.setEnabled(true);
        List<Role> roles = roleRepository.findByName("ROLE_COMPADMIN");
        newAdmin.setRoles(roles);
        newAdmin.setPendingPasswordReset(true);
        return UserMapper.UserToUserDto(userRepository.save(newAdmin));
    }

    public UserDto registerNewSystemAdmin(AdminRegistrationDto adminRegistrationDto){
        User newAdmin = UserMapper.AdminRegistrationDtoToUser(adminRegistrationDto);
        newAdmin.setPassword(passwordEncoder.encode(ADMIN_DEFAULT_PASSWORD));
        newAdmin.setEnabled(true);
        List<Role> roles = roleRepository.findByName("ROLE_SYSADMIN");
        newAdmin.setRoles(roles);
        newAdmin.setPendingPasswordReset(true);
        return UserMapper.UserToUserDto(userRepository.save(newAdmin));
    }

    public UserDto verify(String verificationToken){
        String email = verificationTokenService.getEmailAndVerify(verificationToken);
        if(email.equals("")){
            return null;
        }

        User user = userRepository.findByEmail(email);
        if(user.isEnabled()){
            return null;
        }

        user.setEnabled(true);
        userRepository.save(user);
        return UserMapper.UserToUserDto(user);
    }

    public UserDto changePassword(String email, String newPassword){
        User user = userRepository.findByEmail(email);
        if(user == null) return null;

        System.out.println(newPassword);
        user.setPassword(passwordEncoder.encode(newPassword));
        System.out.println(user.getPassword());
        user.setPendingPasswordReset(false);
        userRepository.save(user);
        return UserMapper.UserToUserDto(userRepository.save(user));
    }
}
