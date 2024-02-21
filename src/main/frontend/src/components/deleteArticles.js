import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';

function DeleteArticle() {
  const { id } = useParams(); // URL에서 게시글 ID를 가져옵니다.
  const navigate = useNavigate();

  const deleteArticle = async () => {
    const token = localStorage.getItem('token'); // 로컬 스토리지에서 토큰을 가져옵니다.
    if (!token) {
      alert("로그인이 필요합니다.");
      navigate('/login');
      return;
    }

    try {
      const response = await fetch(`/api/articles/${id}`, {
        method: 'DELETE',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error("글을 삭제할 수 없습니다.");
      }

      alert("글이 성공적으로 삭제되었습니다.");
      navigate('/'); // 삭제 후 홈페이지나 게시글 목록 페이지로 리다이렉트합니다.
    } catch (error) {
      alert(error.message);
    }
  };

  return (
      <div>
        <h2>게시글 삭제</h2>
        <p>이 게시글을 정말로 삭제하시겠습니까?</p>
        <button onClick={deleteArticle}>삭제하기</button>
      </div>
  );
}

export default DeleteArticle;
