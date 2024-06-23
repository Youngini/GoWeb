import React from "react";
import './style/pathCategory.css';
export default function PathCategory() {

  const gotoKPlearningpage = () => {
    window.location.href = "https://lms1.knu.ac.kr/"
  };
  const gotoKPmainpage = () => {
    window.location.href = "https://www.knu.ac.kr/wbbs/wbbs/main/u_service02.action"
  };

  const gotoCSEmainpage = () => {
    window.location.href = "https://cse.knu.ac.kr/index.php"
  }

  const gotoCSEnoticepage = () => {
    window.location.href = "https://cse.knu.ac.kr/bbs/board.php?bo_table=sub5_1&lang=kor"
  }

  return (
    <div className="pathWrapper">
      <span className='pathtitle'>Page Link</span>
      <div className="link kpLearningPage" onClick={gotoKPlearningpage}>경북대 학습관리시스템</div>
      <div className="link kpMainPage" onClick={gotoKPmainpage}>경북대 통합정보시스템</div>
      <div className="link cseMainPage" onclick={gotoCSEmainpage}>컴퓨터학부 홈페이지</div>
      <div className="link cseNoticePage" onClick={gotoCSEnoticepage}>컴퓨터학부 공지사항</div>
    </div>
  );
}

