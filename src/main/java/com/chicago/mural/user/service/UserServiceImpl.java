package com.chicago.mural.user.service;

import com.chicago.mural.user.User;
import com.chicago.mural.user.dao.UserRepository;
import com.chicago.mural.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl  implements  UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if(!userDto.getPassword().equalsIgnoreCase(userDto.getConfirmPassword())){
            throw new RuntimeException("Passwords must match!");
        }
        final String encodedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());

        final User user = User.builder()
                .username(userDto.getUsername())
                .password(encodedPassword)
                .isActive(true)
                .roles("GUEST")
                .updatedBy(-1)
                .createdBy(-1)
                .build();

        final User savedUser = userRepository.save(user);
        final UserDto newUserDto = userDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .isActive(savedUser.getIsActive())
                .roles(savedUser.getRoles())
                .updatedDate(savedUser.getUpdatedDate())
                .createdDate(savedUser.getCreatedDate())
                .build();
        return newUserDto;
    }
}
