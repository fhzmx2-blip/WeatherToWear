package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.dto.LoginRequest;
import com.weather.WeatherToWear.dto.LoginResponse;
import com.weather.WeatherToWear.dto.SignupRequest;
import com.weather.WeatherToWear.vo.MemberVO;
import org.springframework.security.authentication.BadCredentialsException;

public interface MemberService {
    MemberVO findByMemberId(String memberId);
    public LoginResponse login(LoginRequest loginRequest);
    void signup(SignupRequest signupRequest);
    void withdraw(String memberId);
}