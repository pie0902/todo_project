package com.tryagain.tryagain.service.aboutTodo;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutTodo.AddArticleRequest;
import com.tryagain.tryagain.dto.aboutTodo.UpdateArticleRequest;
import com.tryagain.tryagain.repository.TodoRepository;
import com.tryagain.tryagain.repository.UserRepository;
import com.tryagain.tryagain.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    //게시글 저장
    public Article save(AddArticleRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = null;
        if(authentication!=null&&authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            email = customUserDetails.getEmail(); // 이메일 주소 접근
        }
        User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("유저가 없습니다."));
        Article article = request.toEntity();
        article.setUser(user);
        return todoRepository.save(article);
    }

    //게시글 전체조회
    public List<Article> findAll() {
        return todoRepository.findAll();
    }

    //게시글 한개씩 조회
    public Article findById(long id){
        return todoRepository.findById(id).orElseThrow(()->new IllegalArgumentException("not fonud :" + id));
    }
    //게시글 삭제
    public void delete(long id,String userEmail){
        Optional<Article> article = todoRepository.findById(id);
        if(article.isPresent()) {
            User user = article.get().getUser();
            String articleUserEmail = user.getEmail();
            if(userEmail.equals(articleUserEmail)){
                todoRepository.deleteById(id);
            }
            else{
                throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
            }
        }else{
            throw new IllegalArgumentException("글이 없습니다.");
        }
    }
    //게시글 수정
    @Transactional
    public Article update(long id, UpdateArticleRequest request,String userEmail) {
        Article article = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));
            User user = article.getUser();
            String articleUserEmail = user.getEmail();
            if(userEmail.equals(articleUserEmail)){
                article.update(request.getTitle(), request.getContent());
            }
            else{
                throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
            }
        return article;
    }


}
