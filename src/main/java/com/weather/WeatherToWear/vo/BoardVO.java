package com.weather.WeatherToWear.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BoardVO {

    private Long board_id;
    private String member_id;
    private String nickname;
    private String title;
    private String content;

    private String image_url;
    private String image_public_id;
    private Long image_size;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}