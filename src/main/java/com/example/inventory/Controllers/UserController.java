package com.example.inventory.Controllers;

import com.example.inventory.DTO.UserRequestDTO;
import com.example.inventory.DTO.UsersResponseDTO;
import com.example.inventory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;



    @PostMapping("/register")
    public UsersResponseDTO register(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.register(userRequestDTO);
    }

    @GetMapping
    public List<UsersResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }
}
