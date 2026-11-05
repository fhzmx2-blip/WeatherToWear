package com.weather.WeatherToWear.vo;

import lombok.Data;

@Data
public class RegionVO {
    private String region_code;
    private String region_name;
    private double latitude;
    private double longitude;
}