package com.tryagain.tryagain.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Table(name ="users")
@NoArgsConstructor
@Getter
@Entity
public class User implements UserDetails {
    //유저 엔티티
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",updatable = false)
    private Long id;

    @Column(name="email" ,nullable = false,unique = true)
    private String email;
    @Column(name="name" ,nullable = false)
    private String name;
    @Column(name="password")
    private String password;
    @Builder
    public User(String name,String email, String password){
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private final List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private final List<Comment> comments = new ArrayList<>();

    public void addArticle(Article article) {
        this.articles.add(article);
        article.setUser(this);
    }
    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }
    //id 반환
    @Override
    public String getUsername(){
        return email;
    }
    //password 반환
    @Override
    public String getPassword() {
        return password;
    }
    //계정 만료 여부
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    //계정 잠금 여부
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }
}
