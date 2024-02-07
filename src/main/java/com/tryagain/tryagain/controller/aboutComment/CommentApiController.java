package com.tryagain.tryagain.controller.aboutComment;


import com.tryagain.tryagain.domain.Comment;
import com.tryagain.tryagain.dto.aboutComment.AddCommentRequest;
import com.tryagain.tryagain.dto.aboutComment.CommentResponse;
import com.tryagain.tryagain.service.aboutComment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    //댓글 등록 메서드
    @PostMapping("/api/articles/{id}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long id,@RequestBody AddCommentRequest request){
        Comment saveComment = commentService.save(request,id);
        return ResponseEntity.ok(saveComment);
    }
    // 댓글 조회 메서드
    @GetMapping("/api/articles/{id}/comments")
    public ResponseEntity<List<CommentResponse>> findAllComment(@PathVariable Long id){
        List<CommentResponse> comments = commentService.findAllByArticleIdComment(id);
        return ResponseEntity.ok(comments);
    }
    //댓글 삭제 메서드
    @DeleteMapping("/api/articles/{articleId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long articleId,@PathVariable Long commentId) {
        commentService.deleteComment(articleId,commentId);
        return ResponseEntity.ok().build();
    }

}
