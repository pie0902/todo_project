import React from 'react';
import logo from './logo.svg'; // 로고 이미지를 사용합니다.
import './App.css'; // 기본 제공된 CSS를 활용합니다.
import { useNavigate } from 'react-router-dom'; //
import { Routes, Route } from 'react-router-dom';
import Login from './components/login';
import Signup from './components/signup';
import Home from './components/home';
import Test from './components/test';
import Board from './components/board';
import Nav from "./components/nav";
function App() {
  // 로그인 페이지를 새 창으로 열기

  let navigate = useNavigate(); //
  return (
      <div className="App">
        <Nav/>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/test" element={<Test />} />
            <Route path="/board/*" element={<Board />} />
            {/* 필요에 따라 추가 라우트를 여기에 정의할 수 있습니다. */}
          </Routes>
      </div>
  );
}

export default App;