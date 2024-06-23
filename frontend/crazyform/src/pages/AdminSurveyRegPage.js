import { useState } from "react"
import AdminTopNavbar from "../components/AdminTopNavBar"
import '../components/style/AdminSurveyRegPage.css'
import SurveyForm from "../components/SurveyForm"
import { useNavigate } from "react-router-dom"
import { useEffect } from "react"

const AdminSurveyReg = () => {

    const navigate = useNavigate();

    const token = localStorage.getItem('token');

    useEffect(() => {
      if (token) {
          navigate(`/AdminSurveyReg/${token}`); // 토큰이 있으면 /UserList/${refreshToken} 경로로 이동
      } else {
          navigate('/'); // 토큰이 없으면 홈(로그인 페이지)으로 이동
      }
  }, [navigate]);

  return (
    <div>
      <AdminTopNavbar />
      <div className="surveydetailpageContent">
        <div className="buttonContent">
          <div className={"buttonWrap"}>김영인</div>
          <div className={"buttonWrap"}>김현수</div>
          <div className={"buttonWrap"}>윤진노</div>
          <div className={"buttonWrap"}>정지원</div>
        </div>
        <SurveyForm />
      </div>
    </div>
  );
};

export default AdminSurveyReg;
