package com.weather.WeatherToWear.service;

import com.weather.WeatherToWear.config.JwtTokenProvider;
import com.weather.WeatherToWear.dto.LoginRequest;
import com.weather.WeatherToWear.dto.LoginResponse;
import com.weather.WeatherToWear.dto.SignupRequest;
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

    private static final String MEMBER_ID_PATTERN = "^[a-zA-Z0-9]{3,10}$";
    private static final String PASSWORD_PATTERN = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?`~]{4,18}$";


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

    @Override
    public void signup(SignupRequest signupRequest) {

        String memberId = signupRequest.getMember_id();
        String password = signupRequest.getPassword();
        String nickname = signupRequest.getNickname();
        String gender = signupRequest.getGender();

        if (memberId == null || !memberId.matches(MEMBER_ID_PATTERN)) {
            throw new IllegalArgumentException("아이디는 영문과 숫자를 사용해 3~10자로 입력해 주세요.");
        }
        if (password == null || !password.matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 사용해 4~18자로 입력해 주세요.");
        }
        if (nickname == null || nickname.isBlank() || nickname.length() > 10) {
            throw new IllegalArgumentException("닉네임은 1~10자로 입력해 주세요.");
        }
        if (memberMapper.findByMemberId(memberId) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("성별을 선택해 주세요.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        MemberVO member = new MemberVO();
        member.setMember_id(memberId);
        member.setPassword(encodedPassword);
        member.setNickname(nickname);
        member.setGender(signupRequest.getGender());

        memberMapper.insertMember(member);
    }
}