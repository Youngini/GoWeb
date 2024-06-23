import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import VoteItem from './VoteItem';
import '../style/VoteList.css';
import { ApiAddress } from '../../constants'

export default function VoteList() {

  const navigate = useNavigate();
  const token = localStorage.getItem('token');
  // useEffect 로 voteForms 배열 바뀌면 re-rendering
  // API 주소에서 voteForms 객체 리스트 가져오기

  const [voteForms, setVoteForms] = useState([]);

  useEffect(() => {
    async function fetchVoteForms() {
      try {
        const response = await fetch(`${ApiAddress}/admins/votes`, {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`,
          },
        });
        // const responseText = await response.text();
        // console.log('Response Text:', responseText);  // 응답 텍스트를 출력하여 확인
        // const data = JSON.parse(responseText);
        const data = await response.json();
        setVoteForms(data);
      } catch (error) {
        console.error('Error fetching forms:', error);
      }
    }
    fetchVoteForms();
  }, [])

  return (
    <div className='voteListWrapper'>
      <div className='voteListInfo'>
        <div className='listCount'>{voteForms.length}개의 항목</div>
        <button 
            className='addVotebutton'
            onClick={() => navigate(`/AdminVoteReg/${token}`)}
        >투표 폼 생성하기</button>
      </div>
      {voteForms.map((voteForm) => (
        <VoteItem key={voteForm.id} voteformId={voteForm.id} title={voteForm.name}/>
      ))}
    </div>
  );
}
