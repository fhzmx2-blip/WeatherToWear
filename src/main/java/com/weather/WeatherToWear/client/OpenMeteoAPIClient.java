package com.weather.WeatherToWear.client;



import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class OpenMeteoAPIClient {
// https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.9780&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,precipitation,precipitation_probability,weather_code,wind_speed_10m&timezone=Asia%2FSeoul&forecast_days=1

    public String getWeatherData(double latitude, double longitude) throws IOException, InterruptedException {

        String URL = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude //위도
                + "&longitude=" + longitude //경도
                + "&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,"
                + "precipitation,precipitation_probability,weather_code,wind_speed_10m"
                + "&timezone=Asia%2FSeoul&forecast_days=1";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                              .uri(URI.create(URL))
                              .GET()
                              .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("API 요청 실패: " + response.statusCode());
        }

        return response.body();

    }
}
