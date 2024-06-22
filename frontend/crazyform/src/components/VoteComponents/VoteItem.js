import React from 'react';
import '../style/Form.css'
import { useNavigate } from 'react-router-dom';

export default function VoteItem({formId, title}) {

  // formId 로 API 주소 접근해 table 의 아이템 삭제하기

  const navigate = useNavigate();
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
      <button className="deleteForm">삭제</button>
    </div>
  );
}