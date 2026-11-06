package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.client.OpenMeteoAPIClient;
import com.weather.WeatherToWear.dto.WeatherDTO;
import com.weather.WeatherToWear.mapper.RegionMapper;
import com.weather.WeatherToWear.mapper.WeatherMapper;
import com.weather.WeatherToWear.vo.RegionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final OpenMeteoAPIClient openMeteo;
    private final WeatherMapper weatherMapper;
    private final RegionMapper regionMapper;

    @Override
    @Scheduled(fixedRate = 3600000)
    public void weatherUpdate() {

        System.out.println("날씨 입력 시작");

        List<RegionVO> regionList = regionMapper.getRegionList();
        ObjectMapper objectMapper = new ObjectMapper();

        for (RegionVO region : regionList) {
            try {
                String weatherData = openMeteo.getWeatherData(
                        region.getLatitude(),
                        region.getLongitude()
                );

                JsonNode root = objectMapper.readTree(weatherData);
                JsonNode hourly = root.path("hourly");

                JsonNode times = hourly.path("time");
                JsonNode temperatures = hourly.path("temperature_2m");
                JsonNode apparentTemperatures = hourly.path("apparent_temperature");
                JsonNode relative_humidity = hourly.path("relative_humidity_2m");
                JsonNode precipitation_mm = hourly.path("precipitation");
                JsonNode precipitation_probability = hourly.path("precipitation_probability");
                JsonNode weather_code = hourly.path("weather_code");
                JsonNode wind_speed = hourly.path("wind_speed_10m");

                List<WeatherDTO> weatherList = new ArrayList<>();

                for (int i = 0; i < times.size(); i++) {

                    WeatherDTO wDto = new WeatherDTO();

                    wDto.setRegion_code(region.getRegion_code());

                    wDto.setTime(LocalDateTime.parse(times.get(i).asString()));
                    wDto.setTemperature(temperatures.get(i).asDouble());
                    wDto.setApparent_temperature(apparentTemperatures.get(i).asDouble());
                    wDto.setRelative_humidity(relative_humidity.get(i).asDouble());
                    wDto.setPrecipitation_mm(precipitation_mm.get(i).asDouble());
                    wDto.setPrecipitation_probability(precipitation_probability.get(i).asDouble());
                    wDto.setWeather_code(weather_code.get(i).asInt());
                    wDto.setWind_speed(wind_speed.get(i).asDouble());

                    weatherList.add(wDto);
                }

                weatherMapper.weatherUpdate(weatherList);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("날씨 입력 종료");

    }
}
