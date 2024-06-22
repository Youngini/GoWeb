import React from 'react'
import './style/Logo.css'
export default function Logo() {

  const handleClick = () => {
    window.location.href = 'https://computer.knu.ac.kr/';
  }

  return (
    <div className='logo' onClick={handleClick}>
        <div className='front'>
            <img src="/images/CSE_Logo small.png" alt='로고 이미지'/>
        </div>
        <div className='back'>
            <div>컴퓨터학부 홈페이지로 가기</div>
        </div>
    </div>
  )
}
