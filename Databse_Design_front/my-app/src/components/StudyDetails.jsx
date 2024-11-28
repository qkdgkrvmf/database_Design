import React from 'react';

const StudyPlus = () => {
  return (
    <div style={{
      position: 'relative',
      width: '347px',
      height: '124px',
      background: '#FFF0C2',
      borderRadius: '10px'
    }}>
      <div style={{
        position: 'absolute',
        width: '182px',
        height: '40px',
        left: '78px',
        top: '22px',
        background: '#FFD764',
        borderRadius: '14px'
      }}>
        <div style={{
          position: 'absolute',
          width: '105px',
          height: '18px',
          left: '121px',
          top: '34px',
          fontFamily: 'Inter',
          fontStyle: 'normal',
          fontWeight: '600',
          fontSize: '14px',
          lineHeight: '17px',
          color: '#000000'
        }}>
          모집 중인 스터디
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '182px',
        height: '40px',
        left: '78px',
        top: '72px'
      }}>
        <div style={{
          position: 'absolute',
          width: '182px',
          height: '40px',
          left: '78px',
          top: '72px',
          background: '#FFD764',
          borderRadius: '14px'
        }}>
          <div style={{
            position: 'absolute',
            width: '70px',
            height: '18px',
            left: '134px',
            top: '83px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '600',
            fontSize: '14px',
            lineHeight: '17px',
            color: '#000000'
          }}>
            스터디 생성
          </div>
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '20px',
        height: '20px',
        left: '317.86px',
        top: '17px',
        background: 'url(image.png)',
        transform: 'rotate(-45deg)'
      }} />
    </div>
  );
};

export default StudyPlus;