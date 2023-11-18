package com.isat46.isaback.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDto {

    private String accessToken;
    private Long expiresIn;

    public JwtDto(){}

    public JwtDto(String accessToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }
}
