import React from 'react'
import './style/Logo.css'
export default function Logo() {
  return (
    <div className='logo'>
        <div className='front'>
            <img src="/images/CSE_Logo small.png" alt='로고 이미지'/>
        </div>
        <div className='back'>
            <div>컴퓨터 학부 학생회 전용 폼 만들기 웹</div>
        </div>
    </div>
  )
}
