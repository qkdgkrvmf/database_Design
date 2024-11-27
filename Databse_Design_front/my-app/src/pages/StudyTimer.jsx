import React from 'react';
import '../css/StudyTimer.css'

const StudyTimer = () => {
  return (
    <div className="study-timer">
      <header className="header">
        <div className="back-button">&lt;</div>
        <div className="title">Study Timer</div>
        <div className="home-button">
          <img src="image.png" alt="Home" />
        </div>
      </header>
      <main className="content">
        <section className="section">
          <div className="icon">
            <img src="free-icon-egg-1953809.png" alt="Egg" />
          </div>
          <div className="info">
            <div className="title">정설화</div>
            <div className="time">01 : 55 : 19</div>
          </div>
        </section>
        {/* Add more sections for other students */}
      </main>
      <footer className="footer">
        <div className="progress-bar">
          <div className="progress" style={{ width: '50%' }}></div>
        </div>
        <div className="status">5개의 알이 부화하는 중...</div>
      </footer>
    </div>
  );
};

export default StudyTimer;