package com.weather.WeatherToWear.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentVO {

    private Long comment_id;
    private Long board_id;
    private String member_id;
    private String nickname;
    private String content;

    private LocalDateTime created_at;
}