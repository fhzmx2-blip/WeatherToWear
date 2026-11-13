package com.weather.WeatherToWear.client;



import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class OpenMeteoAPIClient {
// https://api.open-meteo.com/v1/forecast?latitude=37.5665&longitude=126.9780&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,precipitation,precipitation_probability,weather_code,wind_speed_10m&timezone=Asia%2FSeoul&forecast_days=1

    public String getWeatherData(double latitude, double longitude) throws IOException, InterruptedException {

        String URL = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=" + latitude //위도
                + "&longitude=" + longitude //경도
                + "&hourly=temperature_2m,apparent_temperature,relative_humidity_2m,"
                + "precipitation,precipitation_probability,weather_code,wind_speed_10m"
                + "&timezone=Asia%2FSeoul&forecast_days=2";

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                              .uri(URI.create(URL))
                              .timeout(Duration.ofSeconds(30))
                              .header("Accept", "application/json")
                              .GET()
                              .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException(
                    "API 요청 실패: " + response.statusCode() + ", 응답 내용: " + response.body()
            );
        }

        return response.body();

    }
}
