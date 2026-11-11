package com.weather.WeatherToWear.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MemberVO {

    private String member_id;
    private String password;
    private String nickname;
    private String gender;
    private LocalDateTime created_at;
}