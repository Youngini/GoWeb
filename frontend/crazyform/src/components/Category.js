import React, { useState, useEffect } from 'react'
import './style/Category.css'

export default function Category({categoryName, isActive , onClick}) {

  return (
    <button onClick={onClick} className={`${isActive ? 'categoryItem-active' : 'categoryItem'}`}>
      # {categoryName}
    </button>
  )
}