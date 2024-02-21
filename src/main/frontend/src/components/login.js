import React, { useState } from 'react';
import '../css/login.css';
import {Link, Navigate} from "react-router-dom";

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false); // 로그인 상태 추가

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });
      const data = await response.json();
      if (response.ok) {
        console.log('로그인 성공:', data);
        // JWT 토큰을 로컬 스토리지에 저장
        localStorage.setItem('token', data.token);
        setIsLoggedIn(true); // 로그인 상태를 true로 설정
      } else {
        throw new Error(data.message || '로그인 실패');
      }
    } catch (error) {
      console.error('로그인 에러:', error);
    }
  };

  // 로그인 상태가 true인 경우 <Navigate> 컴포넌트를 통해 홈페이지로 리다이렉션
  if (isLoggedIn) {
    return <Navigate to="/" replace />;
  }

  return (
      <div className="login-container">
        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label htmlFor="email">이메일</label>
            <input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input
                type="password"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
          </div>
          <div className="login-btn-box">
          <button type="submit">로그인</button>
          <Link to="/signup"><button className="App-link">회원가입</button></Link>
          </div>
        </form>
      </div>
  );
}

export default Login;
