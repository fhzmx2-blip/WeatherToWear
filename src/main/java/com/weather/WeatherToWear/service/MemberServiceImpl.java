package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.config.JwtTokenProvider;
import com.weather.WeatherToWear.dto.LoginRequest;
import com.weather.WeatherToWear.dto.LoginResponse;
import com.weather.WeatherToWear.mapper.MemberMapper;
import com.weather.WeatherToWear.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public MemberVO findByMemberId(String memberId) {
        return memberMapper.findByMemberId(memberId);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        MemberVO member = memberMapper.findByMemberId(loginRequest.getMember_id());
        if (member == null ||
                !passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        String accessToken = jwtTokenProvider.createAccessToken(member.getMember_id());

        return new LoginResponse(accessToken, member.getNickname());
    }
}