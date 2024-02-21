import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function EditArticle() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [article, setArticle] = useState({ title: '', content: '' });

  useEffect(() => {
    const fetchArticle = async () => {
      const token = localStorage.getItem('token');
      const response = await fetch(`/api/articles/${id}`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        console.error("Failed to fetch article details.");
        return;
      }

      const data = await response.json();
      setArticle(data);
    };
    fetchArticle();
  }, [id]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const token = localStorage.getItem('token');
    const response = await fetch(`/api/articles/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(article)
    });

    if (!response.ok) {
      console.error("Failed to update the article.");
      return;
    }

    navigate(`/board/articles/${id}`); // 수정 후 상세 페이지로 리다이렉션
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setArticle(prevArticle => ({ ...prevArticle, [name]: value }));
  };

  return (
      <div className="article-edit-page">
        <form onSubmit={handleSubmit}>
          <label>
            제목:
            <input type="text" name="title" value={article.title} onChange={handleChange} />
          </label>
          <label>
            내용:
            <textarea name="content" value={article.content} onChange={handleChange} />
          </label>
          <button type="submit">글 수정</button>
        </form>
      </div>
  );
}

export default EditArticle;
