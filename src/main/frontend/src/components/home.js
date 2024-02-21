import logo from "../logo.svg";
import React from "react";
import { useNavigate } from 'react-router-dom'; // useNavigate 훅을 임포트합니다.

function Home() {
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 navigate 함수를 초기화합니다.

  // 로컬 스토리지에서 토큰을 확인하여 로그인 상태 결정
  const isLoggedIn = localStorage.getItem('token') ? true : false;

  const handleLogout = () => {
    // 로컬 스토리지에서 토큰 삭제
    localStorage.removeItem('token');
    // 로그아웃 후 홈페이지로 리다이렉트
    navigate('/');
  };

  return (
      <div>
        <img src={process.env.PUBLIC_URL + './my_img/logo2.png'} className="App-logo"
             alt="logo"/>
        {/*<img src={logo} className="App-logo" alt="logo"/>*/}
        <p>TODO BLOG에 오신 것을 환영합니다!</p>
        <div className="btn-box">
          {isLoggedIn ? (
              // 로그아웃 버튼
              <>
                {/* 게시판 바로가기 버튼 추가 */}
                <button onClick={() => navigate('/board')}
                        className="App-link">게시판 바로가기
                </button>
                {/* 로그아웃 버튼 */}
                <button onClick={handleLogout} className="App-link">로그아웃</button>
              </>
          ) : (
              // 로그인 및 회원가입 버튼
              <>
                <button onClick={() => navigate('/login')}
                        className="App-link">로그인
                </button>
                <button onClick={() => navigate('/signup')}
                        className="App-link">회원가입
                </button>
              </>
          )}
        </div>
      </div>
  );
}

export default Home;
