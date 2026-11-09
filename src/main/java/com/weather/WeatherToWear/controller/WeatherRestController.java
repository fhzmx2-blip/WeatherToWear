package com.weather.WeatherToWear.controller;

import com.weather.WeatherToWear.dto.WeatherDTO;
import com.weather.WeatherToWear.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:5173")
public class WeatherRestController {
    private final WeatherService weatherService;

    @GetMapping
    public List<WeatherDTO> getWeatherList(@RequestParam(defaultValue = "11") String regionCode) {
        return weatherService.getWeatherList(regionCode);
    }
}
