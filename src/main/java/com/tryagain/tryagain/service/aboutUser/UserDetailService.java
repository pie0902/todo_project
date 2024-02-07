package com.tryagain.tryagain.service.aboutUser;

import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException((email)));
    }
}
