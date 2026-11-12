package com.weather.WeatherToWear.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String member_id;
    private String password;
    private String nickname;
    private String gender;
}