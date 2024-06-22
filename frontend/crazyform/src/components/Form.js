import React from 'react';
import './style/Form.css';
import { useNavigate } from 'react-router-dom';

export default function Form({formId, title, hashtag}) {
  const navigate = useNavigate();
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <div className='hashtag'># {hashtag}</div>
      <button className="detail" onClick={() => navigate(`/AdminSurveyDetail/${formId}`)}>상세 보기</button>
      <button className="activeForm">폼 활성화</button>
    </div>
  );
}