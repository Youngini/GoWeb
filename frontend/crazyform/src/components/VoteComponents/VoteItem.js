import React from 'react';
import '../style/Form.css'
import { useNavigate } from 'react-router-dom';

export default function VoteItem({formId, title}) {
  
  const navigate = useNavigate();
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
    </div>
  );
}