package com.weather.WeatherToWear.dto;


import java.time.LocalDateTime;

public class WeatherDTO {
    private String region_code;
    private LocalDateTime time;
    private double temperature;
    private double apparent_temperature;
    private double relative_humidity;
    private double precipitation_mm;
    private double precipitation_probability;
    private int weather_code;
    private double wind_speed;
}


/*

날씨 코드

    "0": "Clear sky",
	"1": "Mainly clear",
	"2": "Partly cloudy",
	"3": "Overcast",
	"45": "Fog",
	"48": "Depositing rime fog",
	"51": "Light drizzle",
	"53": "Moderate drizzle",
	"55": "Dense drizzle",
	"56": "Light freezing drizzle",
	"57": "Dense freezing drizzle",
	"61": "Slight rain",
	"63": "Moderate rain",
	"65": "Heavy rain",
	"66": "Light freezing rain",
	"67": "Heavy freezing rain",
	"71": "Slight snowfall",
	"73": "Moderate snowfall",
	"75": "Heavy snowfall",
	"77": "Snow grains",
	"80": "Slight rain showers",
	"81": "Moderate rain showers",
	"82": "Violent rain showers",
	"85": "Slight snow showers",
	"86": "Heavy snow showers",
	"95": "Thunderstorm",
	"96": "Thunderstorm with slight hail",
	"97": "Heavy thunderstorm",
	"99": "Thunderstorm with heavy hail"

location에 쓸 지역 코드
전국	00
서울특별시	11
부산광역시	21
대구광역시	22
인천광역시	23
광주광역시	24
대전광역시	25
울산광역시	26
세종특별자치시	29
경기도	31
강원특별자치도	32
충청북도	33
충청남도	34
전북특별자치도	35
전라남도	36
경상북도	37
경상남도	38
제주특별자치도	39
 */