package com.tryagain.tryagain.service.aboutUser;

import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import com.tryagain.tryagain.dto.aboutUser.UserSignupResponseDto;
import com.tryagain.tryagain.repository.UserRepository;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserSignupResponseDto signUp(UserSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        String pattern = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";

        // 회원이름 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        boolean isValid = validateEmail(email, pattern);
        if (isValid) {
            System.out.println(email + "회원가입 완료");
            //비밀번호 암호화
            requestDto.setPassword(bCryptPasswordEncoder.encode(password));
            //유저 객체 생성
            User user = new User(requestDto);
            //유저 정보 저장
            User saveUser = userRepository.save(user);
            return new UserSignupResponseDto(saveUser);
        } else {
            System.out.println(email + "이메일 형식을 확인해주세요");
        }
        return null;
    }


    //이메일 검증 메서드
    public static boolean validateEmail(String email, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}