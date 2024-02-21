package com.tryagain.tryagain.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name ="users")
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class User{
    //유저 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="email" ,nullable = false,unique = true)
    private String email;
    @Column(name="username" ,nullable = false)
    private String username;
    @Column(name="password")
    private String password;
    @Builder
    public User(String username,String email, String password){
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public User(UserSignupRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.email = requestDto.getEmail();
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonManagedReference
    private final List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonManagedReference
    private final List<Comment> comments = new ArrayList<>();

}
