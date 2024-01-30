package com.isat46.isaback.service;

import com.isat46.isaback.dto.user.UserDto;
import com.isat46.isaback.dto.user.UserRegistrationDto;
import com.isat46.isaback.mappers.UserMapper;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.User;
import com.isat46.isaback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<UserDto> findAllPaged(Pageable page){
        return userRepository.findAll(page).map(UserMapper::UserToUserDto);
    }

    public Page<UserDto> findAllCompanyAdminsPaged(Pageable page){
        return userRepository.findAllCompanyAdmins(page).map(UserMapper::UserToUserDto);
    }

    public UserDto findByEmail(String email){
        User newUser = userRepository.findByEmail(email);
        return UserMapper.UserToUserDto(newUser);
    }

    public UserDto update(UserDto user){
        return UserMapper.UserToUserDto(userRepository.save(UserMapper.UserDtoToUser(user)));
    }

    public UserDto findOne(int id) {
        return UserMapper.UserToUserDto(userRepository.findById(id).orElse(null));
    }

    public UserDto punishUserForCancelation(Reservation reservation){
        User userToPunish = reservation.getEmployee();
        int timeDifference = reservation.getDateTime().getHour() - LocalDateTime.now().getHour();
        int penalPointsToGive = timeDifference < 24 ? 2 : 1;

        return punishUser(userToPunish, penalPointsToGive);
    }

    public UserDto punishUser(User userToPunish, int penalPointsToGive){
        userToPunish.setPenalPoints(userToPunish.getPenalPoints() + penalPointsToGive);
        userRepository.save(userToPunish);
        return UserMapper.UserToUserDto(userToPunish);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 1 1/1 *")
    public void resetPenaltyPoints() {
        for (User user: userRepository.findAll()) {
            user.setPenalPoints(0);
            userRepository.save(user);
        }
    }
}
