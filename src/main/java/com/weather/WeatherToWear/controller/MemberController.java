package com.weather.WeatherToWear.controller;

import com.weather.WeatherToWear.dto.LoginRequest;
import com.weather.WeatherToWear.dto.LoginResponse;
import com.weather.WeatherToWear.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return memberService.login(loginRequest);
    }
}