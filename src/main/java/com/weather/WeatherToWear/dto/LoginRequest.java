package com.weather.WeatherToWear.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String member_id;
    private String password;
}