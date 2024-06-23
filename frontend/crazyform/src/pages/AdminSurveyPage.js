import React, { useState } from 'react';
import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList';
import FormList from "../components/FormList";
import '../components/style/AdminSurveyPage.css';
import PathCategory from "../components/pathCategory";

const AdminSurveyPage = () => {
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
          <FormList activeCategory={activeCategory} />
        </div>
        <div className="path-Category">
          <PathCategory />
        </div> 
      </div>
    </div>
  );
};

export default AdminSurveyPage;
