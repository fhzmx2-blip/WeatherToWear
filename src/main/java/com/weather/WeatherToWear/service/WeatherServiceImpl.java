package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.client.OpenMeteoAPIClient;
import com.weather.WeatherToWear.mapper.RegionMapper;
import com.weather.WeatherToWear.vo.RegionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private final OpenMeteoAPIClient openMeteo;
    private final RegionMapper regionMapper;

    @Override
    @Scheduled(fixedRate = 600000)
    public void weatherUpdate() {

        //for 문으로 한바퀴 돌리는 것으로 변경
        List<RegionVO> regionList = regionMapper.getRegionList();

        for (RegionVO region : regionList) {
            String weatherData = openMeteo.getWeatherData(
                    region.getLatitude(),
                    region.getLongitude()
            );

            // JSON 파싱 및 DB 저장
        }


        //이 부분은 삭제해야
        try {
            String weatherData = openMeteo.getWeatherData(37.5665, 126.9780);
            //(37.5665, 126.9780)는 나중에 값 받아서 쓸 수 있도록 수정
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //그걸 나눠서 테이블에 넣을 수 있게 쪼개기
        //테이블에 넣기
    }
}
