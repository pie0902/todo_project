package com.tryagain.tryagain.dto.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;
    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
