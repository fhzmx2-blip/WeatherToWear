package com.weather.WeatherToWear.mapper;

import com.weather.WeatherToWear.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    MemberVO findByMemberId(@Param("memberId") String memberId);
    int insertMember(MemberVO member);
}
