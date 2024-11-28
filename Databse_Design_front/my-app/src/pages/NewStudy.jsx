import React from 'react';
import '../css/NewStudy.css';

const NewStudy = () => {
  return (
    <div className="study-creation-container">
      <div className="study-name-container">
        <label htmlFor="study-name">Study Name:</label>
        <input type="text" id="study-name" placeholder="Enter study name" />
      </div>
      <div className="study-description-container">
        <label htmlFor="study-description">Study Description:</label>
        <textarea id="study-description" placeholder="Enter study description"></textarea>
      </div>
      <div className="max-participants-container">
        <label htmlFor="max-participants">Max Participants:</label>
        <input type="number" id="max-participants" placeholder="Enter max participants" />
      </div>
      <div className="study-rules-container">
        <label htmlFor="study-rules">Study Rules:</label>
        <textarea id="study-rules" placeholder="Enter study rules"></textarea>
      </div>
      <div className="study-duration-container">
        <label htmlFor="study-start-date">Start Date:</label>
        <input type="date" id="study-start-date" />
        <label htmlFor="study-end-date">End Date:</label>
        <input type="date" id="study-end-date" />
      </div>
      <button className="create-study-button">Create Study</button>
    </div>
  );
};

export default NewStudy;