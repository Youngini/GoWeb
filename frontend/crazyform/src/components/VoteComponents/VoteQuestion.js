import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import "../style/VoteQuestion.css";

const CHOICE_TYPES = {
  SINGLE_CHOICE: "single_choice",
  MULTIPLE_CHOICE: "multiple_choice",
};

export default function VoteQuestion({ question, onUpdate, onDelete }) {
  // 기본적으로 단일 선택 상태로 설정
  useEffect(() => {
    if (!question.type) {
      onUpdate({
        ...question,
        type: CHOICE_TYPES.SINGLE_CHOICE,
        options: [""],
      });
    }
  }, [question, onUpdate]);

  const handleTypeChange = (e) => {
    const updatedQuestion = {
      ...question,
      type: e.target.value,
      options: [""],
    };
    onUpdate(updatedQuestion);
  };

  const handleOptionChange = (index, value) => {
    const updatedOptions = [...question.options];
    updatedOptions[index] = value;
    onUpdate({ ...question, options: updatedOptions });
  };

  const addOption = () => {
    const updatedOptions = [...question.options, ""];
    onUpdate({ ...question, options: updatedOptions });
  };

  const removeOption = (index) => {
    const updatedOptions = question.options.filter((_, i) => i !== index);
    onUpdate({ ...question, options: updatedOptions });
  };

  return (
    <div className="voteItemWrapper">
      <div className="choiceOption">
        <div className="typeSingle">
          <input
            className="radioBtn single"
            type="radio"
            name="type"
            onClick={handleTypeChange}
            value={CHOICE_TYPES.SINGLE_CHOICE}
            checked={question.type === CHOICE_TYPES.SINGLE_CHOICE}
          />
          <div className="single-span">단일 선택</div>
        </div>
        <div className="typeMulti">
          <input
            className="radioBtn multi"
            type="radio"
            name="type"
            onClick={handleTypeChange}
            value={CHOICE_TYPES.MULTIPLE_CHOICE}
            checked={question.type === CHOICE_TYPES.MULTIPLE_CHOICE}
          />
          <div className="multi-span">복수 선택</div>
        </div>
      </div>
      {question.type === CHOICE_TYPES.SINGLE_CHOICE ||
      question.type === CHOICE_TYPES.MULTIPLE_CHOICE ? (
        <div className="options">
          <div className="optionList">
            <div className="optionItem">
              {question.options.map((option, index) => (
                <div className="optionInputWrap" key={index}>
                  <input
                    className="optionInput"
                    type="text"
                    value={option}
                    onChange={(e) => handleOptionChange(index, e.target.value)}
                    placeholder={`항목 ${index + 1}`}
                  />
                  <button className="optionDeletebtn" onClick={() => removeOption(index)}>
                    X
                  </button>
                </div>
              ))}
            </div>
            <button className="addOptionBtn" onClick={addOption}>
              +
            </button>
          </div>
        </div>
      ) : null}
    </div>
  );
}
