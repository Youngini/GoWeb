import React from 'react';
import Form from './Form';
import './style/FormList.css';
import { useNavigate } from 'react-router-dom';

export default function FormList({ activeCategory }) {

  const forms = [
    { id : 1, title: '2024 MT 수요조사', hashtag: '각종 MT' },
    { id : 2, title: '산사랑 MT 수요조사', hashtag: '각종 MT' },
    { id : 3, title: '산사랑 멘토링 신청', hashtag: '멘토링' },
    { id : 4, title: '멘토링 신청', hashtag: '멘토링' },
    { id : 5, title: '야식마차 신청', hashtag: '야식마차' }
  ];

  const filteredForms = activeCategory === '전체' || !activeCategory
    ? forms
    : forms.filter(form => form.hashtag === activeCategory);

  const navigate = useNavigate();

  return (
    <div className='formlistWrapper'>
      <div className='formlistInfo'>
        <div className='listCategoryWrap'>
          <div className='listCategory'># {activeCategory || '전체'}</div>
          <button 
            className='addSurveybutton'
            onClick={() => navigate('/AdminSurveyReg')}
            >설문조사 추가하기</button>
        </div>
        <div className='listCount'>{filteredForms.length}개의 항목</div>
      </div>
      {filteredForms.map((form) => (
        <Form key={form.id} formId={form.id} hashtag={form.hashtag} title={form.title}/>
      ))}
    </div>
  );
}
