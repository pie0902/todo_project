package com.tryagain.tryagain.service.aboutTodo;

import static org.awaitility.Awaitility.given;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutTodo.AddArticleRequest;
import com.tryagain.tryagain.dto.aboutTodo.UpdateArticleRequest;
import com.tryagain.tryagain.dto.aboutUser.UserSignupRequestDto;
import com.tryagain.tryagain.repository.TodoRepository;
import com.tryagain.tryagain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    TodoService todoService;
    @Test
    @DisplayName("게시글 전체 조회")
    void getAll() {
        // given
        List<Article> articles = new ArrayList<>();
        AddArticleRequest addArticleRequest = new AddArticleRequest("test","testContent");
        Article testArticle = addArticleRequest.toEntity();
        articles.add(testArticle);
        given(todoRepository.findAll()).willReturn(articles);
        // when
        List<Article> foundArticles = todoService.findAll();
        // then
        assertNotNull(foundArticles);
        assertFalse(foundArticles.isEmpty());
        assertEquals(1, foundArticles.size());
        assertEquals(testArticle.getId(), foundArticles.get(0).getId());
        assertEquals(testArticle.getTitle(), foundArticles.get(0).getTitle());
        assertEquals(testArticle.getContent(), foundArticles.get(0).getContent());
    }
    @Test
    @DisplayName("게시글 한개씩 조회")
    void getId(){
        //given
        AddArticleRequest addArticleRequest = new AddArticleRequest("test","testContent");
        Article testArticle = addArticleRequest.toEntity();
        testArticle.setId(1L);
        given(todoRepository.findById(testArticle.getId())).willReturn(Optional.of(testArticle));
        //when
        Article foundArticle = todoService.findById(testArticle.getId());
        //then
        assertNotNull(foundArticle);
        assertEquals(testArticle.getId(), foundArticle.getId());
        assertEquals(testArticle.getTitle(), foundArticle.getTitle());
        assertEquals(testArticle.getContent(), foundArticle.getContent());
    }
    @Test
    @DisplayName("게시글 수정")
    void update(){
        //given
        Long testArticleId = 1L;
        String email = "testcode@naver.com";
        UpdateArticleRequest updateArticleRequest = new UpdateArticleRequest("test","test");
        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto("test","123","testcode@naver.com");
        User articleUser = new User(userSignupRequestDto);
        //수정할 게시글을 만든다.
        AddArticleRequest addArticleRequest = new AddArticleRequest("beforTest","beforTest");
        Article beforArticl = addArticleRequest.toEntity();
        beforArticl.setId(testArticleId);
        beforArticl.setUser(articleUser);
        //업데이트할 dto로 객체 생성
        Article afterArticle = updateArticleRequest.toEntity();
        //업데이트할 게시글의 유저 생성 및 등록
        afterArticle.setUser(articleUser);
        given(todoRepository.findById(testArticleId)).willReturn(Optional.of(beforArticl));
        given(todoRepository.save(any(Article.class))).willReturn(afterArticle);

        //when
        Article result = todoService.update(testArticleId, updateArticleRequest,email);

        //then
        assertNotNull(result);
        assertEquals(updateArticleRequest.getTitle(), result.getTitle());
        assertEquals(updateArticleRequest.getContent(), result.getContent());
    }


}