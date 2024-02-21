// Signup.js
import React, { useState } from 'react';
import '../css/signup.css'; // 스타일시트 임포트
function Signup() {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('/api/users/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email,username, password }),
      });
      const data = await response.json();
      if (response.ok) {
        console.log('회원가입 성공:', data);
        // 회원가입 성공 후 처리 (예: 로그인 페이지로 리다이렉트)
      } else {
        throw new Error(data.message || '회원가입 실패');
      }
    } catch (error) {
      console.error('회원가입 에러:', error);
    }
  };

  return (
      <form onSubmit={handleSubmit}>
        <input
            type="email"
            placeholder="이메일"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
        />
        <input
            type="username"
            placeholder="유저이름"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
        />
        <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
        />
        <button type="submit">회원가입</button>
      </form>
  );
}

export default Signup;
