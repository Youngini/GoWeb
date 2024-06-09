import React from 'react';
import './style/Form.css';

export default function Form({ title, hashtag }) {
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <div>{hashtag}</div>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
    </div>
  );
}