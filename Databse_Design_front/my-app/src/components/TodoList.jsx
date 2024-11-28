import React from 'react';

const TodoList = () => {
  return (
    <div style={{
      position: 'relative',
      width: '393px',
      height: '327px',
      background: '#FFF0C2',
      borderRadius: '20px'
    }}>
      <div style={{
        position: 'absolute',
        width: '397px',
        height: '80.12px',
        left: '0px',
        top: '1.88px',
        background: 'rgba(255, 211, 83, 0.29)',
        transform: 'matrix(1, 0, 0, 1, 0, 0)'
      }} />
      <div style={{
        position: 'absolute',
        width: '20px',
        height: '20px',
        left: '349px',
        top: '46.14px',
        background: 'url(image.png)',
        transform: 'rotate(-45deg)'
      }} />
      <div style={{
        position: 'absolute',
        width: '97px',
        height: '30px',
        left: '155px',
        top: '41px',
        fontFamily: 'Inter',
        fontStyle: 'normal',
        fontWeight: '400',
        fontSize: '20px',
        lineHeight: '24px',
        color: '#5A5858',
        border: '0.5px solid #5A5858'
      }}>
        할 일 No. 1
      </div>
      <div style={{
        position: 'absolute',
        width: '168px',
        height: '35px',
        left: '115px',
        top: '272px'
      }}>
        <div style={{
          boxSizing: 'border-box',
          position: 'absolute',
          width: '168px',
          height: '35px',
          left: '115px',
          top: '272px',
          background: '#FFD764',
          border: '3px solid #FFD764',
          borderRadius: '20px'
        }}>
          <div style={{
            position: 'absolute',
            width: '26px',
            height: '17px',
            left: 'calc(50% - 26px/2 + 2.5px)',
            top: '281px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '600',
            fontSize: '14px',
            lineHeight: '17px',
            color: '#000000'
          }}>
            완료
          </div>
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '124px',
        height: '22px',
        left: '34px',
        top: '98px',
        fontFamily: 'Inter',
        fontStyle: 'normal',
        fontWeight: '600',
        fontSize: '18px',
        lineHeight: '22px',
        color: '#000000'
      }}>
        할 일
      </div>
      <div style={{
        position: 'absolute',
        width: '329px',
        height: '24.27px',
        left: '34px',
        top: '128px'
      }}>
        <div style={{
          boxSizing: 'border-box',
          position: 'absolute',
          width: '329px',
          height: '22.19px',
          left: '34px',
          top: '128px',
          background: '#FFD764',
          border: '1px solid #000000',
          borderRadius: '14px'
        }}>
          <div style={{
            position: 'absolute',
            width: '276.58px',
            height: '18.27px',
            left: '46px',
            top: '134px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '400',
            fontSize: '8px',
            lineHeight: '10px',
            color: '#000000'
          }}>
            데이터베이스설계 A+ 받기
          </div>
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '329px',
        height: '24.27px',
        left: '34px',
        top: '158px'
      }}>
        <div style={{
          position: 'absolute',
          width: '329px',
          height: '22.19px',
          left: '34px',
          top: '158px',
          background: '#FFD764',
          borderRadius: '14px'
        }}>
          <div style={{
            position: 'absolute',
            width: '276.58px',
            height: '18.27px',
            left: '46px',
            top: '164px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '400',
            fontSize: '8px',
            lineHeight: '10px',
            color: '#000000'
          }}>
            10km 러닝하기
          </div>
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '329px',
        height: '24.27px',
        left: '34px',
        top: '188px'
      }}>
        <div style={{
          position: 'absolute',
          width: '329px',
          height: '22.19px',
          left: '34px',
          top: '188px',
          background: '#FFD764',
          borderRadius: '14px'
        }}>
          <div style={{
            position: 'absolute',
            width: '276.58px',
            height: '18.27px',
            left: '46px',
            top: '194px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '400',
            fontSize: '8px',
            lineHeight: '10px',
            color: '#000000'
          }}>
            알고리즘 공부하기
          </div>
        </div>
      </div>
      <div style={{
        position: 'absolute',
        width: '329px',
        height: '24.27px',
        left: '34px',
        top: '218px'
      }}>
        <div style={{
          position: 'absolute',
          width: '329px',
          height: '22.19px',
          left: '34px',
          top: '218px',
          background: '#FFD764',
          borderRadius: '14px'
        }}>
          <div style={{
            position: 'absolute',
            width: '276.58px',
            height: '18.27px',
            left: '46px',
            top: '224px',
            fontFamily: 'Inter',
            fontStyle: 'normal',
            fontWeight: '400',
            fontSize: '8px',
            lineHeight: '10px',
            color: '#000000'
          }}>
            알고리즘 공부하기
          </div>
        </div>
      </div>
    </div>
  );
};

export default TodoList;