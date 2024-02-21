package com.tryagain.tryagain.dto.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private final String title;
    private final String content;
    private final String username;
    private final Long id;

    public ArticleResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.username = article.getUser().getUsername();
    }
}
