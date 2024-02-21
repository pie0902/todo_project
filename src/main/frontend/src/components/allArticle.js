import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AllArticles() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [articlesPerPage] = useState(10); // 한 페이지당 게시글 수

  const navigate = useNavigate();

  useEffect(() => {
    fetchArticles();
  }, []);

  const fetchArticles = async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('Authentication token not found');
      }

      const response = await fetch('/api/articles', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Something went wrong');
      }

      const data = await response.json();
      setArticles(data);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  const indexOfLastArticle = currentPage * articlesPerPage;
  const indexOfFirstArticle = indexOfLastArticle - articlesPerPage;
  const currentArticles = articles.slice(indexOfFirstArticle, indexOfLastArticle);

  const handleArticleClick = (articleId) => {
    navigate(`/board/articles/${articleId}`);
  };

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
      <div>
        <h2>게시글</h2>
        {currentArticles.map(article => (
            <div className="article-card" key={article.id} onClick={() => handleArticleClick(article.id)}>
              <h4>작성자: {article.username}</h4>
              <h3>제목: {article.title}</h3>
              {/*<p>내용: {article.content}</p>*/}
            </div>
        ))}
        <div className="pagination">
          {[...Array(Math.ceil(articles.length / articlesPerPage)).keys()].map(number => (
              <button className="page-btn" key={number + 1} onClick={() => paginate(number + 1)}>
                {number + 1}
              </button>
          ))}
        </div>
      </div>
  );
}

export default AllArticles;
