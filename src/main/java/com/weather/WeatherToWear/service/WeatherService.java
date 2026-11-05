package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.dto.WeatherDTO;

public interface WeatherService {
    public void weatherUpdate(String latitude, String longitude);
}
