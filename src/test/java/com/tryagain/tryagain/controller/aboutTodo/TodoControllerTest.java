package com.tryagain.tryagain.controller.aboutTodo;
import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutTodo.AddArticleRequest;
import com.tryagain.tryagain.dto.aboutTodo.ArticleResponse;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import com.tryagain.tryagain.service.aboutTodo.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
    @ExtendWith(SpringExtension.class)
    @WebMvcTest(controllers = TodoController.class) // ArticleController를 대상으로 테스트
    public class TodoControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TodoService todoService; // 실제 서비스 대신 사용할 모의 객체

        @BeforeEach
        void setUp(WebApplicationContext webApplicationContext) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

        @Test
        @DisplayName("게시글 전체 조회")
        void findAllArticles() throws Exception {
            // Given
            AddArticleRequest addArticleRequest1 = new AddArticleRequest("test1","test1");
            AddArticleRequest addArticleRequest2 = new AddArticleRequest("test2","test2");
            Article article1 = addArticleRequest1.toEntity();
            Article article2 = addArticleRequest2.toEntity();
            List<Article> articles = List.of(article1, article2);

            given(todoService.findAll()).willReturn(articles);

            // When & Then
            mockMvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(articles.get(0).getTitle()))
                .andExpect(jsonPath("$[1].title").value(articles.get(1).getTitle()));
        }
    }
