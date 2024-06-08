import React, { useState } from 'react'
import './style/Category.css'

export default function Category({categoryName}) {

  const [isActive, setIsActive] = useState(false)

  const handleClick = () => {
    setIsActive(!isActive)
  }

  return (
    <button onClick={handleClick} className={`${isActive ? 'categoryItem-active' : 'categoryItem'}`}>
      # {categoryName}
    </button>
  )
}
