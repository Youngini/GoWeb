import React from 'react';
import '../style/Form.css'
import { useNavigate } from 'react-router-dom';
import { ApiAddress } from '../../constants'


export default function VoteItem({formId, title}) {

  const token = localStorage.getItem("token");
  // formId 로 API 주소 접근해 table 의 아이템 삭제하기

  const handleDeleteForm = async () => {
    try {
      const response = await fetch(`${ApiAddress}/admins/votes/${formId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (!response.ok) {
        console.error('Failed to delete form');
      } else {
        console.log('Form deleted successfully');
        alert('설문조사가 삭제되었습니다.')
        navigate(`/AdminVote/${token}`)
      }
    } catch (error) {
      console.error('Error deleting form:', error);
    }
  }

  const navigate = useNavigate();
  
  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
      <button className="deleteForm" onClick={handleDeleteForm}>X</button>
    </div>
  );
}