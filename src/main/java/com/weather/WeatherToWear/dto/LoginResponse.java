package com.weather.WeatherToWear.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String nickname;

    public LoginResponse(String accessToken, String nickname) {
        this.accessToken = accessToken;
        this.nickname = nickname;
    }
}