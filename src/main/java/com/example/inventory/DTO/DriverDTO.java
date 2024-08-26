package com.example.inventory.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDTO {
    private Long id;
    private String firstName;
    private String lastName;
    @JsonProperty("dlnum")
    private String DLnum;
}
