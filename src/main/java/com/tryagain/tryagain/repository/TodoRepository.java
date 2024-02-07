package com.tryagain.tryagain.repository;

import com.tryagain.tryagain.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Article,Long> {
    Optional<Article> findById(Long id);
}
