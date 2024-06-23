import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../components/style/AdminVoteRegPage.css";
import VoteQuestion from "../components/VoteComponents/VoteQuestion";
import { ApiAddress } from "../constants";

export default function AdminVoteRegPage() {
  const [question, setQuestion] = useState({ type: "", options: [""] });
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      navigate(`/AdminVoteReg/${token}`);
    } else {
      navigate("/");
    }
  }, [navigate, token]);

  const handleTitle = (e) => {
    setTitle(e.target.value);
  };

  const handleDescription = (e) => {
    setDescription(e.target.value);
  };

  const handleStartDate = (e) => {
    setStartDate(e.target.value);
  };

  const handleEndDate = (e) => {
    setEndDate(e.target.value);
  };

  const handleButtonClick = () => {
    navigate(`/AdminVote/${token}`);
  };

  const handleSubmit = async () => {
    // console.log(`name : ${title}, description: ${description}, candidates : ${question.options}`);
    const data = {
      name: title,
      description: description,
      startDate: startDate,
      candidates: question.options, // 수정된 부분
      endDate: endDate,
    };

    try {
      const res = await fetch(`${ApiAddress}/admins/votes/create`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(data),
      });
      console.log(data);
      if (res.ok) {
        console.log("Vote Form registered successfully");
        alert("투표 폼이 생성되었습니다.");
        navigate(`AdminVote/${token}`); // 등록하고 다시 돌아가기
      } else {
        console.error("Failed to register Vote Form");
      }
    } catch (error) {
      console.error(error);
      alert("투표 폼 생성에 실패하였습니다.");
    }
  };

  return (
    <div className="backGround">
      <div className="voteRegWrapper">
        <div className="voteRegTitle">투표 폼 생성</div>
        <div className="titleInputWrapper">
          <input
            className="vTitle"
            type="text"
            value={title}
            onChange={handleTitle}
            placeholder="폼 제목을 입력하세요."
          />
          <input
            className="vSubtitle"
            type="text"
            value={description}
            onChange={handleDescription}
            placeholder="투표 질문을 입력하세요."
          />
        </div>
        <div className="questionWrapper">
          <VoteQuestion
            question={question}
            onUpdate={setQuestion}
          />
        </div>
        <div className="dateWrap">
          <span>시작</span>
          <input
            className="startDate"
            type="date"
            value={startDate}
            onChange={handleStartDate}
          />
          <span>마감</span>
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
          <button className="register" onClick={handleSubmit}>등록</button>
        </div>
      </div>
    </div>
  );
}
