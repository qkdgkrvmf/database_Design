import React from 'react';
import '../css/UserJoinStudy.css';

const UserJoinStudy = () => {
  return (
    <div className="container">
      <header className="header">
        <div className="header-top">
          <div className="logo">앱 이름~~</div>
          <div className="menu-icon">
            <img src="/image.png" alt="Menu" />
          </div>
        </div>
        <div className="header-bottom">
          <div className="notice">
            이강만 교수님, 이우진 교수님 수업 필수로 듣고 인증하기!!
          </div>
        </div>
      </header>

      <main>
        <section className="study-info">
          <div className="study-leader">
            <div className="leader-circle"></div>
            <div className="leader-name">스터디장 : 김삐약삐약</div>
          </div>
          <div className="rules">
            <div className="rule">규칙 1. 사담 금지</div>
            <div className="rule">규칙 2. 열심히 하기ㅋㅋ</div>
          </div>
        </section>

        <section className="board">
          <div className="board-title">게시판</div>
          {/* Board content */}
        </section>

        <section className="ranking">
          <div className="ranking-title">회원 공부 순위</div>
          <div className="ranking-list">
            {/* Ranking items */}
          </div>
        </section>
      </main>

      <footer className="footer">
        <button className="timer-button">Go to Timer !</button>
      </footer>
    </div>
  );
};

export default UserJoinStudy;