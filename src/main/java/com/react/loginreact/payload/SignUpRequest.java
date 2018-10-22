package com.react.loginreact.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank
    @Size(min = 4 ,max =40)
    private String name;


    @NotBlank
    @Size(min = 3 ,max =15)
    private String userName;

    @NotBlank
    @Size(max =40)
    private String mail;

    @NotBlank
    @Size(min = 4 ,max =40)
    private String password;

}
