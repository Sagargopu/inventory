package com.example.inventory.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private String role;
    private AddressDTO address;
}
