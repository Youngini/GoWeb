import React, { useEffect, useState } from 'react';
import Form from './Form';
import './style/FormList.css';
import { useNavigate } from 'react-router-dom';
import { ApiAddress } from '../constants';

export default function FormList({ activeCategory, token }) {
  const [forms, setForms] = useState([]);
  const [activeCount, setActiveCount] = useState(0);

  useEffect(() => {
    async function fetchForms() {
      try {
        const response = await fetch(`${ApiAddress}/surveys`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`,
          },
        });
        const data = await response.json();
        setForms(data);
      } catch (error) {
        console.error('Error fetching forms:', error);
      }
    }

    fetchForms();
  }, []);

  useEffect(() => {
    function countActive() {
      const count = forms.filter(form => form.activation).length;
      setActiveCount(count);
      console.log(count)
    }

    countActive();
  }, [forms]);

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
            onClick={() => navigate(`/AdminSurveyReg/${token}`)}
          >설문조사 추가하기</button>
        </div>
        <div className='listCount'>{filteredForms.length}개의 항목</div>
      </div>
      {filteredForms.map((form) => (
        <Form 
          key={form.id} 
          formId={form.id} 
          hashtag={form.hashtag} 
          title={form.title} 
          activation={form.activation}
          activeCount={activeCount} // 활성화된 폼의 개수를 Form 컴포넌트로 전달
        />
      ))}
    </div>
  );
}
