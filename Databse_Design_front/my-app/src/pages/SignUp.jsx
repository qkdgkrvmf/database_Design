import React from 'react';
import '../css/SignUp.css';

const SignUp = () => {
  return (
    <div className="signup-container">
      <div className="signup-title">회원가입</div>
      <div className="id-group">
        <div className="id-label">아이디</div>
        <input type="text" className="id-input" placeholder="아이디를 작성해 주세요." />
      </div>
      <div className="password-group">
        <div className="password-label">비밀번호</div>
        <input type="password" className="password-input" placeholder="비밀번호를 작성해 주세요." />
        <input type="password" className="password-confirm-input" placeholder="비밀번호를 한 번 더 작성해 주세요." />
      </div>
      <div className="phone-group">
        <div className="phone-label">전화번호</div>
        <input type="text" className="phone-input" placeholder="전화번호를 작성해 주세요." />
      </div>
      <div className="birthday-group">
        <div className="birthday-label">생년월일</div>
        <input type="text" className="birthday-input" placeholder="YYYY - MM - DD" />
      </div>
      <div className="name-group">
        <div className="name-label">이름</div>
        <input type="text" className="name-input" placeholder="이름을 작성해 주세요." />
      </div>
      <div className="signup-button-group">
        <button className="signup-button">가입하기</button>
      </div>
    </div>
  );
};

export default SignUp;