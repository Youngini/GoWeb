import { useState } from "react";
import './style/SurveyForm.css';

const QUESTION_TYPES = {
    SHORT_ANSWER: "short_answer",
    LONG_ANSWER: "long_answer",
    MULTIPLE_CHOICE: "multiple_choice",
    CHECKBOXES: "checkboxes",
    RADIO_BUTTONS: "radio_buttons"
};

const Question = ({ question, onUpdate, onDelete }) => {
    const handleTypeChange = (e) => {
        const updatedQuestion = { ...question, type: e.target.value, options: [] };
        onUpdate(updatedQuestion);
    };

    const handleTextChange = (e) => {
        const updatedQuestion = { ...question, text: e.target.value };
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
        <div className="question">
            <div className="selectoptionwrap">
                <select className='selectoption' value={question.type} onChange={handleTypeChange}>
                    <option value="" disabled selected>옵션 선택</option>
                    <option value={QUESTION_TYPES.SHORT_ANSWER}>단답형</option>
                    <option value={QUESTION_TYPES.LONG_ANSWER}>장문형</option>
                    <option value={QUESTION_TYPES.MULTIPLE_CHOICE}>객관식 질문</option>
                    <option value={QUESTION_TYPES.CHECKBOXES}>체크박스</option>
                    <option value={QUESTION_TYPES.RADIO_BUTTONS}>라디오 버튼</option>
                </select>
            </div>
            {question.type === QUESTION_TYPES.SHORT_ANSWER || question.type === QUESTION_TYPES.LONG_ANSWER ? (
                <textarea
                    className="firstinput"
                    type="text"
                    placeholder="텍스트"
                    value={question.text}
                    onChange={handleTextChange}
                />
            ) : null}
            {question.type === QUESTION_TYPES.MULTIPLE_CHOICE ||
            question.type === QUESTION_TYPES.CHECKBOXES ||
            question.type === QUESTION_TYPES.RADIO_BUTTONS ? (
                    <div className="options">
                        <div className="makerow">
                            <div>
                        {question.options.map((option, index) => (
                            <div className='secondinputwrap' key={index}>
                                <input
                                    className="secondinput"
                                    type="text"
                                    value={option}
                                    onChange={(e) => handleOptionChange(index, e.target.value)}
                                    placeholder={`옵션 ${index+1}`}
                                />
                                <button onClick={() => removeOption(index)}>삭제</button>
                            </div>
                        ))}
                        </div>
                        <div className="addoptionwrap">
                            <button className='addoption' onClick={addOption}>옵션 추가</button>
                        </div>
                    </div>
                </div>
            ) : null}
            <div className="deleteqwrap">
                <button className='deleteq' onClick={onDelete}>질문 삭제</button>
            </div>
        </div>
    );
};

const SurveyForm = () => {
    const [questions, setQuestions] = useState([{}]);

    const addQuestion = () => {
        setQuestions([
            ...questions,
            { text: "", type: '', options: [] }
        ]);
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

    return (
        <div className="survey-form">
            <button className='questionadd' onClick={addQuestion}>질문 추가</button>
                {questions.map((question, index) => (
                    <Question
                        key={index}
                        question={question}
                        onUpdate={(updatedQuestion) => updateQuestion(index, updatedQuestion)}
                        onDelete={() => deleteQuestion(index)}
                    />
                ))}
        </div>
    );
};

export default SurveyForm;
