package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.vo.RegionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionMapper {
    public List<RegionVO> getRegionList();
}
