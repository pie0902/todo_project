package com.tryagain.tryagain.repository;

import com.tryagain.tryagain.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    List<Comment> findByArticleId(Long articleId);
}
