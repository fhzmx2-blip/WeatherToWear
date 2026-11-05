package com.weather.WeatherToWear.client;

public class OpenMeteoAPIClient {
// https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.9780&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,precipitation,precipitation_probability,weather_code,wind_speed_10m&timezone=Asia%2FSeoul&forecast_days=1
/*

    "https://api.open-meteo.com/v1/forecast"
    +"?latitude="+a //위도
    +"&longitude="+b //경도
    +"&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,"
    +"precipitation,precipitation_probability,weather_code,wind_speed_10m"
    +"&timezone=Asia%2FSeoul&forecast_days=1"

 */

    double a = 37.5665;
    double b = 126.9780;
    //a와 b값은 db에서 가져와야 함. 임시 하드코딩


    String URL = "https://api.open-meteo.com/v1/forecast"
                +"?latitude="+a //위도
                +"&longitude="+b //경도
                +"&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,"
                +"precipitation,precipitation_probability,weather_code,wind_speed_10m"
                +"&timezone=Asia%2FSeoul&forecast_days=1";

    //URL값으로 JSON을 돌려받고, 받은 JSON을 넣는 것은 Service에서 해결
}
