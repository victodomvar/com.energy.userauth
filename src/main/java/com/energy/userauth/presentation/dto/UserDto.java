// presentation/dto/UserDto.java
package com.energy.userauth.presentation.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String password; // para registro, no se retorna en GET

    public UserDto() {}

    public UserDto(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
