package com.tryagain.tryagain.service;

import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.AddArticleRequest;
import com.tryagain.tryagain.dto.AddUserRequest;
import com.tryagain.tryagain.dto.SignUpResponse;
import com.tryagain.tryagain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest dto){
        return  userRepository.save(User.builder()
                        .email(dto.getEmail())
                        .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();

    }
    public User saveUser(AddUserRequest dto){
        return  userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build());
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을수 없습니다."));
    }
}
