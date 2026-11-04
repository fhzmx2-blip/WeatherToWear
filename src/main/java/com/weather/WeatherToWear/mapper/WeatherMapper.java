package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.dto.WeatherDTO;
import org.apache.ibatis.annotations.Param;

public interface WeatherMapper {

    public WeatherDTO weatherUpdate();
}
