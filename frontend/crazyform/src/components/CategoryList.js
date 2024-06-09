import React, { useState } from 'react';
import Category from './Category';
import './style/CategoryList.css';

export default function CategoryList({ onCategoryChange }) {
  const [activeCategory, setActiveCategory] = useState(null);

  const categories = [
    { name: '전체' }, // 고정된 전체 카테고리
    { name: '각종 MT' },
    { name: '멘토링' },
    { name: '야식마차' }
  ];

  const handleCategoryClick = (categoryName) => {
    const newActiveCategory = activeCategory === categoryName ? null : categoryName;
    setActiveCategory(newActiveCategory);
    onCategoryChange(newActiveCategory);
  };

  const renderCategoryList = categories.map(category => {
    return (
      <Category
        categoryName={category.name}
        key={category.name}
        isActive={category.name === activeCategory}
        onClick={() => handleCategoryClick(category.name)}
      />
    );
  });

  return (
    <div className='CategoryFrame'>
      <div className='CategoryTitle'>
        Category
      </div>
      <div className='CategoryList'>
        {renderCategoryList}
      </div>
    </div>
  );
}
