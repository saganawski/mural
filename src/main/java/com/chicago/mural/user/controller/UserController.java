package com.chicago.mural.user.controller;

import com.chicago.mural.user.dto.UserDto;
import com.chicago.mural.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public UserDto createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
}
