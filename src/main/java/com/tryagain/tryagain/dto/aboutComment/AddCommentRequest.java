package com.tryagain.tryagain.dto.aboutComment;


import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private String content;
    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .build();
    }
}
