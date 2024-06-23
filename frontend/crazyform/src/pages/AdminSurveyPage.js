import React, { useState } from 'react';
import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList';
import FormList from "../components/FormList";
import '../components/style/AdminSurveyPage.css';
import PathCategory from "../components/pathCategory";
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

const AdminSurveyPage = () => {

  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  useEffect(() => {
    if (token) {
        navigate(`/AdminSurvey/${token}`); // 토큰이 있으면 /UserList/${refreshToken} 경로로 이동
    } else {
        navigate('/'); // 토큰이 없으면 홈(로그인 페이지)으로 이동
    }
}, [navigate]);

  const [activeCategory, setActiveCategory] = useState(null);

  return (
    <div>
      {/* <Logo /> */}
      <AdminTopNavbar />
      <div className='surveypageContent'>
        <div className="hashCategory">
          <CategoryList onCategoryChange={setActiveCategory} />
        </div>
        <div className="FormList">
          <FormList activeCategory={activeCategory} token={token} />
        </div>
        <div className="path-Category">
          <PathCategory />
        </div> 
      </div>
    </div>
  );
};

export default AdminSurveyPage;
