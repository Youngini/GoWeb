import React from 'react';
import Form from './Form';
import './style/FormList.css';

export default function FormList({ activeCategory }) {
  const forms = [
    { title: '2024 MT 수요조사', hashtag: '각종 MT' },
    { title: '산사랑 MT 수요조사', hashtag: '각종 MT' },
    { title: '산사랑 멘토링 신청', hashtag: '멘토링' },
    { title: '멘토링 신청', hashtag: '멘토링' },
    { title: '야식마차 신청', hashtag: '야식마차' }
  ];

  const filteredForms = activeCategory === '전체' || !activeCategory
    ? forms
    : forms.filter(form => form.hashtag === activeCategory);

  return (
    <div className='formlistWrapper'>
      <div className='formlistInfo'>
        <div className='listCategory'># {activeCategory || '전체'}</div>
        <div className='listCount'>{filteredForms.length}개의 항목</div>
      </div>
      {filteredForms.map((form, index) => (
        <Form key={index} hashtag={form.hashtag} title={form.title} />
      ))}
    </div>
  );
}
