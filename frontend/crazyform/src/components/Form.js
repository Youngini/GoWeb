import React from 'react';
import './style/Form.css';
import { useNavigate } from 'react-router-dom';

export default function Form({formId, title, hashtag}) {
  const navigate = useNavigate();
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <div>{hashtag}</div>
      <button className="editSurvey" onClick={() => navigate(`/AdminSurveyDetail/${formId}`)}>설문조사 수정</button>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
    </div>
  );
}