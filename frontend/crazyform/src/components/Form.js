import React from 'react';
import './style/Form.css';
import { useNavigate } from 'react-router-dom';
import { ApiAddress } from '../constants';

export default function Form({ formId, title, hashtag, activation, activeCount }) {
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  const handleDetailClick = () => {
    console.log('formId:', formId); // formId를 콘솔에 출력
    navigate(`/AdminSurveyDetail/${token}/${formId}`);
    localStorage.setItem('surveyid', formId);
  };

  const handleActivation = async () => {
    if (activeCount === 0){
      try {
        const response = await fetch(`${ApiAddress}/surveys/${formId}/activate`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        });

        if (!response.ok) {
          throw new Error('네트워크 응답이 올바르지 않습니다.');
        }
        else{
        if(activation){
          alert('폼을 비활성화했습니다.')
          window.location.reload();
        }else{
        alert('폼을 활성화했습니다.')
        window.location.reload();
        }
        }
      } catch (error) {
        console.error('Activation error:', error);
      }
    }
    else{
      if (activation){
        const response = await fetch(`${ApiAddress}/surveys/${formId}/activate`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
          }
        })

        if (!response.ok) {
          throw new Error('네트워크 응답이 올바르지 않습니다.');
        }
        else{
          alert('폼을 비활성화했습니다.')
          window.location.reload(); 
        }
      }
      else{
      alert('폼 활성화는 하나의 설문만 가능합니다')
      }
    }
  };

  return (
    <div className="formWrapper">
      <div className="formTitle">{title}</div>
      <div className='hashtag'># {hashtag}</div>
      <button className="detail" onClick={handleDetailClick}>상세 보기</button>
      <button className={activation ? "activeFormTrue" : "activeForm"}
      onClick={handleActivation}>폼 활성화</button>
    </div>
  );
}
