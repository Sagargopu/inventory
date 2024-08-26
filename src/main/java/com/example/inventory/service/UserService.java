package com.example.inventory.service;

import com.example.inventory.DTO.AddressDTO;
import com.example.inventory.DTO.UserRequestDTO;
import com.example.inventory.DTO.UsersResponseDTO;
import com.example.inventory.model.Address;
import com.example.inventory.model.Role;
import com.example.inventory.model.Users;
import com.example.inventory.repositoryDAO.UserRepositoryDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepositoryDAO userRepositoryDAO;
    private final PasswordEncoder passwordEncoder;

    public UsersResponseDTO convertToDTO(Users users){
        AddressDTO addressDTO=AddressDTO.builder()
                .id(users.getAddress().getId())
                .houseNum(users.getAddress().getHouseNum())
                .street(users.getAddress().getStreet())
                .unit(users.getAddress().getUnit())
                .city(users.getAddress().getCity())
                .state(users.getAddress().getState())
                .zipcode(users.getAddress().getZipcode()).build();
        return UsersResponseDTO.builder()
                .id(users.getId())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .role(users.getRole().toString())
                .address(addressDTO).build();
    }

    public Users convertToEntity(UserRequestDTO userRequestDTO){
        Role role;
        try {
            role = Role.valueOf(userRequestDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + userRequestDTO.getRole());
        }
        Address address=Address.builder()
                .id(userRequestDTO.getAddress().getId())
                .houseNum(userRequestDTO.getAddress().getHouseNum())
                .street(userRequestDTO.getAddress().getStreet())
                .unit(userRequestDTO.getAddress().getUnit())
                .city(userRequestDTO.getAddress().getCity())
                .state(userRequestDTO.getAddress().getState())
                .zipcode(userRequestDTO.getAddress().getZipcode()).build();
        return Users.builder()
                .id(userRequestDTO.getId())
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .password(userRequestDTO.getPassword())
                .email(userRequestDTO.getEmail())
                .phone(userRequestDTO.getPhone())
                .role(role)
                .address(address).build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepositoryDAO.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user with email not found"));
    }
    public UsersResponseDTO register(UserRequestDTO userRequestDTO) {
        Users user = convertToEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        Users savedUser = userRepositoryDAO.save(user);
        return convertToDTO(savedUser);
    }
    public List<UsersResponseDTO> getAllUsers(){
        return userRepositoryDAO.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Users getUserById(Long id) {
        Users user = userRepositoryDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return user;
    }



}
