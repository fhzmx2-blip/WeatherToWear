package com.weather.WeatherToWear.controller;

import com.weather.WeatherToWear.dto.LoginRequest;
import com.weather.WeatherToWear.dto.LoginResponse;
import com.weather.WeatherToWear.dto.SignupRequest;
import com.weather.WeatherToWear.dto.SignupResponse;
import com.weather.WeatherToWear.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@RequestBody SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return new SignupResponse("회원가입이 완료되었습니다.");
    }

    @DeleteMapping("/withdraw")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@AuthenticationPrincipal Jwt jwt) {
        String memberId = jwt.getSubject();
        memberService.withdraw(memberId);
    }
}