import React from 'react';
import '../css/JoinStudy.css';

const JoinStudy = () => {
  return (
    <div className="join-study">
      <div className="name-container">
        <label htmlFor="name">Name:</label>
        <input type="text" id="name" placeholder="Enter your name" />
      </div>

      <div className="occupation-container">
        <label htmlFor="occupation">Occupation:</label>
        <input type="text" id="occupation" placeholder="e.g. Student, Professional, etc." />
      </div>

      <div className="introduction-container">
        <label htmlFor="introduction">Introduction:</label>
        <textarea id="introduction" placeholder="Hi there^^;"></textarea>
      </div>

      <div className="agreement-container">
        <label>
          <input type="checkbox" />
          I have read and agree to the study rules. I will be responsible for any consequences that arise from not reading the rules.
        </label>
      </div>

      <div className="join-button-container">
        <button className="join-button">Join</button>
      </div>
    </div>
  );
};

export default JoinStudy;