import React from 'react'
import Form from './Form'
import './style/FormList.css'

export default function FormList() {
  return (
    <div className='formlistWrapper'>
        <div className='formlistInfo'>
            <div className='listCategory'># 각종 MT</div>
            <div className='listCount'>4개의 항목</div>
        </div>
        <Form />
        <Form />
        <Form />
        <Form />
    </div>
  )
}
