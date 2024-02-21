import React, { useEffect, useState } from 'react';
import {useNavigate, useParams} from 'react-router-dom';

function ArticleDetail() {
  const { id: articleId } = useParams();
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const navigate = useNavigate();
  const { id } = useParams(); // URL 경로로부터 id 값을 추출

  useEffect(() => {

    fetchArticleAndComments();
    if (id === undefined) {
      console.error("ID 값이 정의되지 않았습니다.");
      // 필요한 처리 수행 (예: 에러 메시지 표시, 이전 페이지로 이동 등)
    } else {
      // 정상적인 로직 수행
    }
  }, [id]);



  const fetchArticleAndComments = async () => {
    // 게시글 상세 정보 가져오기
    fetchArticle();
    // 댓글 목록 가져오기
    fetchComments();
  };


  const fetchComments = async () => {
    const token = localStorage.getItem('token');
    const response = await fetch(`/api/articles/${id}/comments`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });

    if (!response.ok) {
      console.error("Failed to fetch comments.");
      return;
    }

    const data = await response.json();
    setComments(data);
  };

  const handleNewCommentChange = (e) => {
    setNewComment(e.target.value);
  };


  const fetchArticle = async () => {
    // 로컬 스토리지에서 토큰을 가져옵니다.
    const token = localStorage.getItem('token');

    // 요청 시 인증 토큰을 Authorization 헤더에 포함합니다.
    const response = await fetch(`/api/articles/${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    },[id]);

    if (!response.ok) {
      // 요청 실패 처리 (예: 에러 메시지 설정)
      console.error("Failed to fetch article details.");
      return;
    }

    const data = await response.json();
    setArticle(data);
  };



  const submitComment = async () => {
    const token = localStorage.getItem('token');
    const response = await fetch(`/api/articles/${id}/comments`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify({ content: newComment })
    });

    if (!response.ok) {
      console.error("Failed to submit comment.");
      return;
    }

    setNewComment('');
    fetchComments(); // 댓글 목록 새로고침
  };


  const [isEditing, setIsEditing] = useState(false);
  const [editingCommentId, setEditingCommentId] = useState(null);
  const [updatedContent, setUpdatedContent] = useState('');

  const startEditComment = (commentId, currentContent) => {
    setIsEditing(true);
    setEditingCommentId(commentId);
    setUpdatedContent(currentContent); // 현재 댓글 내용으로 초기화
  };
 //댓글 수정
  const submitUpdatedComment = async () => {
    const token = localStorage.getItem('token');
    try {
      console.log(`Article ID: ${articleId}, Comment ID: ${editingCommentId}`);
      const response = await fetch(`/api/articles/${articleId}/comments/${editingCommentId}`, { // 수정: articleId와 editingCommentId 사용
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ content: updatedContent })
      });

      if (!response.ok) {
        throw new Error('Failed to update comment.');
      }

      setIsEditing(false);
      setEditingCommentId(null);
      fetchComments(); // 댓글 목록 새로고침
    } catch (error) {
      console.error(error.message);
    }
  };



  if (!article) return <div>Loading...</div>;



  const handleArticleClick = (articleId) => {
    navigate(`/board/editArticles/${articleId}`); // 게시글 상세보기 페이지로 이동
  };
  const handleArticleClick2 = (articleId) => {
    navigate(`/board/deleteArticles/${articleId}`); // 게시글 상세보기 페이지로 이동
  };


  const handleDelete = async (commentId) => {
    // 사용자에게 삭제를 확인
    const isConfirmed = window.confirm("댓글을 삭제하시겠습니까?");
    if (isConfirmed) {
      // 삭제 로직 실행
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`/api/articles/${articleId}/comments/${commentId}`, {
          method: 'DELETE',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        if (!response.ok) {
          throw new Error('Failed to delete comment.');
        }

        // 성공적으로 삭제되었다면, 댓글 목록 새로고침
        fetchComments();
      } catch (error) {
        console.error(error.message);
        // 에러 처리 로직 (예: 사용자에게 에러 메시지 표시)
      }
    }
  };

  return (
      <>
      <div className="article-detail-page">
        <h4>작성자: {article.username}</h4>
        <h3>제목: {article.title}</h3>
        <div className="text-box">
          <p>글내용: {article.content}</p>
        </div>
        <button onClick={() => handleArticleClick(article.id)}>글 수정하기
        </button>
        <button className="deleteArticle" onClick={() => handleArticleClick2(article.id)}>x
        </button>
      </div>

  {/* 댓글 목록 표시 */}
  <div className="comments-section">
    <h4><span>댓글</span></h4>
    {comments.map((comment) => (
        <div key={comment.id} className="comment">
          <p className="comment-space">{comment.username}: {comment.content}
            <button className="comment-edit-btn"
                    onClick={() => startEditComment(comment.id,
                        comment.content)}>수정
            </button>
            <button className="comment-remove-btn"
                    onClick={() => handleDelete(comment.id)}>x
            </button>
          </p>

        </div>
    ))}
    {/*// JSX에서 수정 폼 표시 (댓글 목록 렌더링 부분에 추가)*/}
    {
        isEditing && (
            <div className="edit-comment-form">
              <textarea value={updatedContent} onChange={(e) => setUpdatedContent(e.target.value)} />
              <button onClick={() => submitUpdatedComment()}>댓글 수정
              </button>
              <button onClick={() => setIsEditing(false)}>취소</button>
            </div>
        )
    }


  </div>

  {/* 댓글 작성 폼 */}
  <div className="comment-form">
    <textarea value={newComment} onChange={handleNewCommentChange} placeholder="댓글을 입력하세요..." />
    <button onClick={submitComment}>댓글 작성</button>
  </div>
  </>
  );
}

export default ArticleDetail;
