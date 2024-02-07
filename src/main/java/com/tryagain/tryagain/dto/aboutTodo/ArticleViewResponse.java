package com.tryagain.tryagain.dto.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}
