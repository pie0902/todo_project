package com.tryagain.tryagain.controller.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.Comment;
import com.tryagain.tryagain.dto.aboutComment.CommentResponse;
import com.tryagain.tryagain.dto.aboutTodo.ArticleListViewResponse;
import com.tryagain.tryagain.dto.aboutTodo.ArticleViewResponse;
import com.tryagain.tryagain.service.aboutComment.CommentService;
import com.tryagain.tryagain.service.aboutTodo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class TodoViewController {
    private final TodoService todoService;
    private final CommentService commentService;
    //게시글 조회 관련 컨트롤러
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
        List<CommentResponse> comments = commentService.findAllByArticleIdComment(id);
        model.addAttribute("article",new ArticleViewResponse(article));
        model.addAttribute("comments", comments);
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
