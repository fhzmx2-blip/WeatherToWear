package com.weather.WeatherToWear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherToWearApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherToWearApplication.class, args);
	}

}
