import React from 'react';
import { Link } from 'react-router-dom';

function Nav() {
  // 로컬 스토리지에서 토큰을 확인하여 로그인 상태 결정
  const isLoggedIn = localStorage.getItem('token') ? true : false;

  const handleLogout = () => {
    // 로컬 스토리지에서 토큰 삭제
    localStorage.removeItem('token');
    // 로그아웃 후 페이지를 새로고침하거나, 사용자를 홈페이지로 리다이렉트할 수 있습니다.
    window.location.href = '/';
  };

  return (
      <nav>
        <Link to="/"><h3>home</h3></Link>
        <h1>모두의 TODO</h1>
      </nav>
  );
}

export default Nav;
