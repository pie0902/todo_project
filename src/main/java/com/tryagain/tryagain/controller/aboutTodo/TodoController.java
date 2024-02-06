package com.tryagain.tryagain.controller;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.dto.AddArticleRequest;
import com.tryagain.tryagain.dto.ArticleResponse;
import com.tryagain.tryagain.dto.UpdateArticleRequest;
import com.tryagain.tryagain.service.TodoService;
import com.tryagain.tryagain.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest result, UserDetailService userDetailService){
        Article saveArticle = todoService.save(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = todoService.findAll()
                .stream().map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = todoService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id, Authentication authentication) {
        String userEmail = authentication.getName();
        todoService.delete(id,userEmail);

        return ResponseEntity.ok().build();
    }
    @PutMapping("api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request ,Authentication authentication){
        String userEmail = authentication.getName();
        Article updateArticle = todoService.update(id,request,userEmail);
        return ResponseEntity.ok().body(updateArticle);
    }
}
