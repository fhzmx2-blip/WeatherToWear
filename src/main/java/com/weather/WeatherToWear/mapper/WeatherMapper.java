package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.dto.WeatherDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WeatherMapper {
    void weatherUpdate(@Param("weatherList") List<WeatherDTO> weatherList);
    List<WeatherDTO> getWeatherList(@Param("regionCode") String regionCode, @Param("startTime") LocalDateTime startTime);
}
