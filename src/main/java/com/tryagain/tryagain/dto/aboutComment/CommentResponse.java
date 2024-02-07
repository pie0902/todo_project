package com.tryagain.tryagain.dto.aboutComment;

import java.time.LocalDateTime;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private final String content;
    private final String username;
    private final LocalDateTime createdAt;
    private final Long id;
    private final Article article;
    public CommentResponse(Comment comment){
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.id = comment.getId();
        this.article = comment.getArticle();
    }
}
