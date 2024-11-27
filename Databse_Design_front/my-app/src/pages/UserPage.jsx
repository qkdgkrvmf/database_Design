import React from 'react';
import '../css/UserPage.css';

const UserPage = () => {
  return (
    <div className="user-page">
      <header className="header">
        <div className="header-logo">
          <img src="image.png" alt="Logo" className="logo-image" />
          <span className="logo-text">마이 페이지</span>
        </div>
      </header>

      <section className="study-section">
        <div className="study-header">
          <h2 className="study-title">STUDY</h2>
          <div className="study-info">
            <div className="study-time">
              <span className="study-time-text">135 : 00 : 00</span>
            </div>
            <div className="todo-list">
              <h3 className="todo-list-title">To Do List</h3>
              <div className="todo-item">
                <div className="todo-progress-bar"></div>
                <span className="todo-text">데이터베이스설계 A+ 받기</span>
              </div>
              <div className="todo-item">
                <div className="todo-progress-bar"></div>
                <span className="todo-text">10km 러닝하기</span>
              </div>
              <div className="todo-item">
                <div className="todo-progress-bar"></div>
                <span className="todo-text">알고리즘 공부하기</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <section className="group-section">
        <div className="group-header">
          <h2 className="group-title">MY GROUP</h2>
        </div>
        <div className="group-list">
          <div className="group-item">
            <img src="image.png" alt="Group Icon" className="group-icon" />
            <span className="group-text">코딩 테스트 스터디</span>
          </div>
          <div className="group-item">
            <img src="image.png" alt="Group Icon" className="group-icon" />
            <span className="group-text">정보처리기사 스터디</span>
          </div>
          <div className="group-item">
            <img src="image.png" alt="Group Icon" className="group-icon" />
            <span className="group-text">TOEIC 스터디</span>
          </div>
        </div>
      </section>

      <footer className="footer">
        <button className="timer-button">Go to Timer !</button>
      </footer>
    </div>
  );
};

export default UserPage;