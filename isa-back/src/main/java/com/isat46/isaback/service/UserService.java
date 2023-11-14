package com.isat46.isaback.service;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.mappers.UserMapper;
import com.isat46.isaback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    public Page<UserDto> findAllPaged(Pageable page){
        return userRepository.findAll(page).map(UserMapper::UserToUserDto);
    }
}
