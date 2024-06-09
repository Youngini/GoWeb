import React, { useState } from 'react';
import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList';
import FormList from "../components/FormList";
import '../components/style/AdminSurveyPage.css';

const AdminSurveyPage = () => {
  const [activeCategory, setActiveCategory] = useState(null);

  return (
    <div>
      {/* <Logo /> */}
      <AdminTopNavbar />
      <div className='surveypageContent'>
        <CategoryList onCategoryChange={setActiveCategory} />
        <FormList activeCategory={activeCategory} />
      </div>
    </div>
  );
};

export default AdminSurveyPage;
