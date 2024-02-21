package com.tryagain.tryagain.service.aboutComment;

import com.tryagain.tryagain.domain.Article;
import com.tryagain.tryagain.domain.Comment;
import com.tryagain.tryagain.domain.User;
import com.tryagain.tryagain.dto.aboutComment.AddCommentRequest;
import com.tryagain.tryagain.dto.aboutComment.CommentResponse;
import com.tryagain.tryagain.dto.aboutComment.UpdateCommentRequest;
import com.tryagain.tryagain.dto.aboutTodo.UpdateArticleRequest;
import com.tryagain.tryagain.repository.CommentRepository;
import com.tryagain.tryagain.repository.TodoRepository;
import com.tryagain.tryagain.repository.UserRepository;
import com.tryagain.tryagain.security.CustomUserDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    //댓글 저장 메서드
    public Comment save(AddCommentRequest request, Long articleId) {
        // 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            userEmail = ((CustomUserDetails) principal).getEmail();
        } else {
            throw new IllegalArgumentException("인증된 사용자 정보를 가져올 수 없습니다.");
        }

        // 사용자 정보로부터 User 엔티티를 찾음
        User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));

        // Article ID로 Article 엔티티를 찾음
        Article article = todoRepository.findById(articleId)
            .orElseThrow(() -> new IllegalArgumentException("게시글이 없습니다."));

        // DTO로부터 Comment 객체를 생성
        Comment comment = request.toEntity();

        // Comment 객체에 User와 Article 엔티티를 설정
        comment.setUser(user);
        comment.setArticle(article);

        // Comment 객체를 저장
        return commentRepository.save(comment);
    }

    //댓글을 다 조회해주는 메서드
    public List<CommentResponse> findAllByArticleIdComment(Long articleId) {
        //게시글 아이디를 조회해서 댓글을 가져옴
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
            .map(CommentResponse::new)
            .collect(Collectors.toList());
    }

    //댓글 삭제 메서드
    public void deleteComment(Long articleId, Long commentId) {
        //지금 로그인된 유저 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //유저정보에서 이메일을 가져옴
        String userEmail = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            userEmail = ((CustomUserDetails) principal).getEmail();
        } else {
            throw new IllegalArgumentException("인증된 사용자 정보를 가져올 수 없습니다.");
        }
        System.out.println(userEmail + "**************************************");
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("comment 가 없습니다."));
        //현재 comment가 갖고있는 유저정보와 지금 로그인된 유저 이메일을 비교함
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할수 있습니다.");
        } else {
            if (!comment.getArticle().getId().equals(articleId)) {
                throw new IllegalArgumentException("삭제하려는 댓글의 id가 일치하지 않습니다.");
            } else {
                commentRepository.delete(comment);
            }
        }
    }
    @Transactional
    public Comment update(long id, UpdateCommentRequest request,long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //유저정보에서 이메일을 가져옴
        String userEmail = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            userEmail = ((CustomUserDetails) principal).getEmail();
        } else {
            throw new IllegalArgumentException("인증된 사용자 정보를 가져올 수 없습니다.");
        }
        System.out.println(userEmail + "**************************************");
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("comment 가 없습니다."));
        //현재 comment가 갖고있는 유저정보와 지금 로그인된 유저 이메일을 비교함
        if (!comment.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인의 댓글만 수정할수 있습니다.");
        } else {
            if (!comment.getArticle().getId().equals(id)) {
                throw new IllegalArgumentException("삭제하려는 댓글의 id가 일치하지 않습니다.");
            } else {
                comment.update(request.getContent());
                System.out.println(comment.getContent());
                return comment;
            }
        }
    }

}
