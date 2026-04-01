package com.kuxln.smartinsulinbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    private String email = "";
    private String password = "";
    private String fullName = "";
    private Integer diabetesType;
}
