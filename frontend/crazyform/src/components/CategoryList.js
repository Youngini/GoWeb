import React from 'react'
import Category from './Category'
import './style/CategoryList.css'

export default function CategoryList() {
    
    return (
    <div className='CategoryFrame'>
      <div className='CategoryTitle'>
        Catergory
      </div>
      <div className='CategoryList'>
        <Category categoryName='각종 MT'/>
        <Category categoryName='멘토링'/>
        <Category categoryName='야식마차'/>
      </div>
    </div>
  )
}
