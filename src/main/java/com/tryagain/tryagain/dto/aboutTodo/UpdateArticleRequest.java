package com.tryagain.tryagain.dto.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
    public Article toEntity() {
        return Article.builder()
            .title(title)
            .content(content)
            .build();
    }
}
