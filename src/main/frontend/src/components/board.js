import React from 'react';
import {Routes, Route, useNavigate} from 'react-router-dom';
import AllArticles from "./allArticle";
import AddArticle from "./addArticles";
import ArticleDetail from "./articleDetail";
import EditArticle from "./editArticle";
import DeleteArticle from "./deleteArticles";
function Board() {
  // useNavigate 훅을 사용하여 navigate 함수를 생성합니다.
  const navigate = useNavigate();

  return (
      <div>
        <div className="board-btn">
              {/* onClick 이벤트 핸들러에서 navigate 함수를 호출합니다. */}
              <button onClick={() => navigate('/board/addArticles')}>게시글 추가
              </button>
              <button onClick={() => navigate('/board/articles')}>게시글 전체조회
              </button>
        </div>

        {/* 라우트 설정 */}
        <Routes>
          <Route path="articles" element={<AllArticles />} />
          <Route path="articles/:id" element={<ArticleDetail />} />
          <Route path="addArticles" element={<AddArticle />} />
          <Route path="editArticles/:id" element={<EditArticle />} />
          <Route path="deleteArticles/:id" element={<DeleteArticle />} />
        </Routes>
      </div>
  );
}

export default Board;
