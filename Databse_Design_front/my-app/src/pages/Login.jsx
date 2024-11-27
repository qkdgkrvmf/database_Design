import React from 'react';
import '../css/Login.css';

const Login = () => {
  return (
    <div className="login-container">
      <div className="id-group">
        <div className="input-container">
          <input type="text" className="id-input" placeholder="아이디" />
        </div>
      </div>
      <div className="password-group">
        <div className="input-container">
          <input type="password" className="password-input" placeholder="비밀번호" />
        </div>
      </div>
      <div className="login-button-group">
        <button className="login-button">로그인</button>
      </div>
      <div className="signup-password-recovery">
        <a href="#" className="signup-link">회원가입</a>
        <span className="separator"> / </span>
        <a href="#" className="password-recovery-link">비밀번호 찾기</a>
      </div>
      <div className="logo-group">
        <div className="logo-image" />
        <div className="logo-text">공부하자!</div>
      </div>
    </div>
  );
};

export default Login;