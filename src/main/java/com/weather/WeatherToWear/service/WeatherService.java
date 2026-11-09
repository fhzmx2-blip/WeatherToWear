package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.dto.WeatherDTO;

import java.util.List;

public interface WeatherService {
    public void weatherUpdate();
    List<WeatherDTO> getWeatherList(String regionCode);

}
