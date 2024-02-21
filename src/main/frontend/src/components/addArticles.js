import React, { useState } from 'react';

function AddArticle() {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token'); // 로컬 스토리지에서 토큰 가져오기
  console.log(token);
    if (!token) {
      console.error("토큰이 없습니다. 로그인이 필요합니다.");
      return;
    }
    const userInfo = parseJwt(token);
    console.log(userInfo);
    const article = { title, content };
    await fetch('/api/articles', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 요청 헤더에 토큰 포함
      },
      body: JSON.stringify(article),
    });
    // Form 제출 후 필드 초기화
    setTitle('');
    setContent('');
    // 추가적인 액션 (예: 게시글 목록 새로고침)

  };

  function parseJwt(token) {
    // JWT 토큰의 페이로드 부분만 추출합니다.
    const base64Url = token.split('.')[1];
    // base64Url을 base64로 변환합니다.
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    // base64 인코딩된 문자열을 디코딩합니다.
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    // JSON 문자열을 객체로 변환합니다.
    return JSON.parse(jsonPayload);
  };


// 사용 예시



  return (
      <div>
      <form onSubmit={handleSubmit}>
        <input
            type="text"
            value={title}
            onChange={e => setTitle(e.target.value)}
            placeholder="Title"
            required
        />
        <textarea
            value={content}
            onChange={e => setContent(e.target.value)}
            placeholder="Content"
            required
        />
        <button type="submit">Submit</button>
      </form>
      </div>
  );
}

export default AddArticle;