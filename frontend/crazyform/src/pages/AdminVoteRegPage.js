import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import "../components/style/AdminVoteRegPage.css";
import "../components/VoteComponents/VoteQuestion";
import VoteQuestion from "../components/VoteComponents/VoteQuestion";
import { useNavigate } from "react-router-dom";

export default function AdminVoteRegPage() {
  const [questions, setQuestions] = useState([{}]);
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const navigate = useNavigate();
  const addQuestion = () => {
    setQuestions([...questions, { text: "", type: "", options: [] }]);
  };

  const updateQuestion = (index, updatedQuestion) => {
    const updatedQuestions = questions.map((question, i) =>
      i === index ? updatedQuestion : question
    );
    setQuestions(updatedQuestions);
  };

  const deleteQuestion = (index) => {
    const updatedQuestions = questions.filter((_, i) => i !== index);
    setQuestions(updatedQuestions);
  };

  const handleStartDate = (e) => {
    setStartDate(e.target.value);
  };

  const handleEndDate = (e) => {
    setEndDate(e.target.value);
  };

  const location = useLocation();
  const isAdminVoteDetail = location.pathname.includes("/AdminVoteDetail");

  const handleButtonClick = () => {
    navigate("/AdminVote");
  };
  return (
    <div className="backGround">
      <div className="voteRegWrapper">
        <div className="voteRegTitle">Create your Vote Form</div>
        <div className="titleInputWrapper">
          <input className="vTitle" type="text" placeholder="Input Title" />
          <input
            className="vSubtitle"
            type="text"
            placeholder="Type Question"
          />
        </div>
        <div className="questionWrapper">
          {questions.map((question, index) => (
            <VoteQuestion
              key={index}
              question={question}
              onUpdate={(updatedQuestion) =>
                updateQuestion(index, updatedQuestion)
              }
              onDelete={() => deleteQuestion(index)}
            />
          ))}
        </div>
        <div className="dateWrap">
          <span>시작 날짜</span>
          <input
            className="startDate"
            type="date"
            value={startDate}
            onChange={handleStartDate}
          />
          <span>마지막 날짜</span>
          <input
            className="endDate"
            type="date"
            value={endDate}
            onChange={handleEndDate}
          />
        </div>
        
        <div className="footer">
          <button className="cancel" onClick={handleButtonClick}>
            취소
          </button>
          <button className="register">등록</button>
        </div>
      </div>
    </div>
  );
}
