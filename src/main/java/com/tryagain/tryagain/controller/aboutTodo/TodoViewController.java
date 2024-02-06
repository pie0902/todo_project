package com.tryagain.tryagain.controller;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.dto.ArticleListViewResponse;
import com.tryagain.tryagain.dto.ArticleViewResponse;
import com.tryagain.tryagain.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TodoViewController {
    private final TodoService todoService;

    @GetMapping("/articles")
    public String getArticles(Model model) {
        List<ArticleListViewResponse> articles = todoService.findAll().stream()
                .map(ArticleListViewResponse::new).toList();
        model.addAttribute("articles", articles);
        return "articleList";
    }
    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id,Model model){
        Article article = todoService.findById(id);
        model.addAttribute("article",new ArticleViewResponse(article));
        return "article";
    }
    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id,Model model){
        if(id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        }else{
            Article article = todoService.findById(id);
            model.addAttribute("article",new ArticleViewResponse(article));
        }
        return "newArticle";
    }

}
