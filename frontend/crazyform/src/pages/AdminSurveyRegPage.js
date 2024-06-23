import { useState } from "react";
import AdminTopNavbar from "../components/AdminTopNavBar";
import "../components/style/AdminSurveyRegPage.css";
import SurveyForm from "../components/SurveyForm";

const AdminSurveyReg = () => {
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
