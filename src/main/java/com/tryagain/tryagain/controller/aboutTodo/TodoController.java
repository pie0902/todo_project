package com.tryagain.tryagain.controller.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.dto.aboutTodo.AddArticleRequest;
import com.tryagain.tryagain.dto.aboutTodo.ArticleResponse;
import com.tryagain.tryagain.dto.aboutTodo.UpdateArticleRequest;
import com.tryagain.tryagain.service.aboutTodo.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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
    //게시글 등록 메서드
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest result){
        Article saveArticle = todoService.save(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }
    //게시글 전체 조회 메서드
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = todoService.findAll()
                .stream().map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }
    //선택한 게시글 조회 메서드
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable Long id){
        Article article = todoService.findById(id);
        return ResponseEntity.ok().body(new ArticleResponse(article));
    }
    //게시글 삭제 메서드
    @DeleteMapping("api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id, Authentication authentication) {
        String userEmail = authentication.getName();
        todoService.delete(id,userEmail);

        return ResponseEntity.ok().build();
    }
    //게시글 수정 메서드
    @PutMapping("api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleRequest request ,Authentication authentication){
        String userEmail = authentication.getName();
        Article updateArticle = todoService.update(id,request,userEmail);
        return ResponseEntity.ok().body(updateArticle);
    }
}
