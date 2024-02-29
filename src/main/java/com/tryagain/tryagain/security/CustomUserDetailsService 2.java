package com.tryagain.tryagain.security;

import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        //DB에서 조회
//        User userData = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("No User found"));
//        if (userData != null) {
//            //UserDetails에 담아서 return하면 AutneticationManager가 검증 함
//            return new CustomUserDetails(userData);
//        }
//        return null;
//    }
//}
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // DB에서 사용자 조회, 사용자가 없으면 UsernameNotFoundException 발생
        User userData = userRepository.findByEmail(email)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email));

        // CustomUserDetails 객체 생성 및 반환
        return new CustomUserDetails(userData);
    }
}