import { useState } from "react";
import './style/SurveyForm.css';
import { useLocation, useNavigate } from 'react-router-dom';
import { DndProvider, useDrag, useDrop } from 'react-dnd';
import { HTML5Backend } from 'react-dnd-html5-backend';
import { ApiAddress } from "../constants";
import { useEffect } from "react";

const QUESTION_TYPES = {
    SHORT_ANSWER: "ShortAnswer",
    LONG_ANSWER: "Paragraph",
    MULTIPLE_CHOICE: "MultipleChoice",
    CHECKBOXES: "Checkboxes",
    DROP_DOWN: "Dropdown"
};

const ItemType = {
    QUESTION: 'question'
};


const Question = ({ question, onUpdate, onDelete, index, moveQuestion }) => {
    const handleTypeChange = (e) => {
        const updatedQuestion = { ...question, type: e.target.value, options: [] };
        onUpdate(updatedQuestion);
    };

    const handleContentChange = (e) => {
        const updatedQuestion = { ...question, content: e.target.value };
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

    const [, ref] = useDrag({
        type: ItemType.QUESTION,
        item: { index },
    });

    const [, drop] = useDrop({
        accept: ItemType.QUESTION,
        hover: (draggedItem) => {
            if (draggedItem.index !== index) {
                moveQuestion(draggedItem.index, index);
                draggedItem.index = index;
            }
        },
    });

    return (
        <div ref={(node) => ref(drop(node))} className="question">
            <div>
                <div className="questionTitleWrap">
                    <input 
                        className="questionTitle" 
                        placeholder="질문을 입력하세요"
                        value={question.content}
                        onChange={handleContentChange}
                    />
                </div>
            </div>
            <div className="selectoptionwrap">
                <select className='selectoption' value={question.type} onChange={handleTypeChange}>
                    <option value="" disabled selected>옵션 선택</option>
                    <option value={QUESTION_TYPES.SHORT_ANSWER}>단답형</option>
                    <option value={QUESTION_TYPES.LONG_ANSWER}>장문형</option>
                    <option value={QUESTION_TYPES.MULTIPLE_CHOICE}>객관식 질문</option>
                    <option value={QUESTION_TYPES.CHECKBOXES}>체크박스</option>
                    <option value={QUESTION_TYPES.DROP_DOWN}>드롭다운</option>
                </select>
            </div>
            {question.type === QUESTION_TYPES.SHORT_ANSWER || question.type === QUESTION_TYPES.LONG_ANSWER ? (
                <textarea
                    className="firstinput"
                    type="text"
                    placeholder="텍스트"
                    value={question.text}
                    disabled
                />
            ) : null}
            {question.type === QUESTION_TYPES.MULTIPLE_CHOICE ||
            question.type === QUESTION_TYPES.CHECKBOXES ||
            question.type === QUESTION_TYPES.DROP_DOWN ? (
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
                                <div className="optiondeletebuttonwrap">
                                    <button className='optiondeletebutton' onClick={() => removeOption(index)}>삭제</button>
                                </div>
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

const SurveyForm = (id) => {
    const [questions, setQuestions] = useState([{}]);
    const [title, setTitle] = useState('')
    const [category, setCategory] = useState('')
    const [description, setDescription] = useState('')
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [activation, setActivation] = useState(false); 

    const navigate = useNavigate();


    const addQuestion = () => {
        setQuestions([
            ...questions,
            { content: "", text: "", type: '', options: [] }
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

    const moveQuestion = (fromIndex, toIndex) => {
        const updatedQuestions = [...questions];
        const [movedQuestion] = updatedQuestions.splice(fromIndex, 1);
        updatedQuestions.splice(toIndex, 0, movedQuestion);
        setQuestions(updatedQuestions);
    };

    const handleTitle = (e) => {
        setTitle(e.target.value);
    }

    const handleCategory = (e) => {
        setCategory(e.target.value)
    }

    const handleDescription = (e) => {
        setDescription(e.target.value)
    }

    const handleStartDateChange = (e) => {
        setStartDate(e.target.value);
    }

    const handleEndDateChange = (e) => {
        setEndDate(e.target.value);
    }

    const location = useLocation();
    const isAdminSurveyDetail = location.pathname.includes('/AdminSurveyDetail');

    useEffect(() => {
        if (isAdminSurveyDetail && id) {
            const fetchSurvey = async () => {
                try {
                    const response = await fetch(`${ApiAddress}/surveys/${id}`);
                    if (response.ok) {
                        const data = await response.json();
                        setTitle(data.title);
                        setDescription(data.description);
                        setStartDate(data.startDate);
                        setEndDate(data.dueDate);
                        setActivation(data.activation);
                        setCategory(data.hashtag);
                        setQuestions(data.questions.map(question => ({
                            content: question.content,
                            type: question.questionType,
                            options: question.options.map(option => option.name),
                        })));
                    } else {
                        console.error('Failed to fetch survey');
                    }
                } catch (error) {
                    console.error('Error fetching survey:', error);
                }
            };

            fetchSurvey();
        }
    }, [isAdminSurveyDetail, id]);


    const handleSubmit = async () => {
        const data = {
            title,
            description,
            startDate,
            dueDate: endDate,
            activation,
            hashtag : category,
            questions: questions.map((question, index) => ({
                order: index,
                content: question.content,
                questionType: question.type,
                imageUrl: '',
                options: question.options.map((option, optIndex) => ({
                    name: option,
                    order: optIndex,
                    imageUrl: '',
                })),
            })),
        };

        try {
            const response = await fetch(`${ApiAddress}/surveys`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
            console.log(data)

            if (response.ok) {
                console.log('Survey submitted successfully');
                alert('설문조사가 등록되었습니다.')
                navigate('/AdminSurvey')
            } else {
                console.error('Failed to submit survey');
            }
        } catch (error) {
            console.error('Error submitting survey:', error);
        }
    };

    return (
        <DndProvider backend={HTML5Backend}>
            <div className="survey-form">
                <div className="titlewrap">
                    <input 
                        className="title" 
                        placeholder="설문 조사 제목을 입력하세요"
                        value={title}
                        onChange={handleTitle}
                        ></input>
                    <button className="submitbutton" onClick={handleSubmit}>확인</button>
                    {isAdminSurveyDetail && <button className="deletebutton">삭제</button>}
                </div>
                <div>
                    <input 
                        className="addCategory" 
                        placeholder="카테고리를 입력하세요" 
                        value={category}
                        onChange={handleCategory}
                    />
                </div>
                <div>
                    <textarea
                        className="description"
                        placeholder="설문 설명을 입력하세요"
                        value={description}
                        onChange={handleDescription}
                    />
                </div>
                <div className="dateWrap">
                <div>
                    시작 날짜 
                    <input
                        className="dateinput" 
                        type="date" 
                        value={startDate} 
                        onChange={handleStartDateChange} 
                    />
                </div>
                <div className="enddate">
                    마감 날짜
                    <input 
                        className="dateinput" 
                        type="date" 
                        value={endDate} 
                        onChange={handleEndDateChange} 
                    />
                </div>
                </div>
                <button className='questionadd' onClick={addQuestion}>질문 추가</button>
                    {questions.map((question, index) => (
                        <Question
                            key={index}
                            index={index}
                            question={question}
                            onUpdate={(updatedQuestion) => updateQuestion(index, updatedQuestion)}
                            onDelete={() => deleteQuestion(index)}
                            moveQuestion={moveQuestion}
                        />
                    ))}
            </div>
        </DndProvider>
    );
};

export default SurveyForm;