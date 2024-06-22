import React from 'react';
import { useNavigate } from 'react-router-dom';
import VoteItem from './VoteItem';
import '../style/VoteList.css';

export default function VoteList() {

  const voteForms = [
    { id : 1, title: '2024 회장 선거'},
    { id : 2, title: '동아리 가입 투표'},
  ];

  const navigate = useNavigate();

  return (
    <div className='voteListWrapper'>
      <div className='voteListInfo'>
        <div className='listCount'>{voteForms.length}개의 항목</div>
        <button 
            className='addVotebutton'
            onClick={() => navigate('/AdminVoteReg')}
        >투표 폼 생성하기</button>
      </div>
      {voteForms.map((voteForm) => (
        <VoteItem key={voteForm.id} voteformId={voteForm.id} title={voteForm.title}/>
      ))}
    </div>
  );
}
