import React, { useEffect, useState } from 'react';
import { ApiAddress } from '../constants';
import { useParams } from 'react-router-dom';
import './style/UserSurveyComponent.css';

const UserSurveyComponent = () => {
    const [surveyData, setSurveyData] = useState(null);
    const [responses, setResponses] = useState({});
    const { id: userId } = useParams(); 
    const [surveyid, setSurveyId] = useState('')


    useEffect(() => {
        fetch(`${ApiAddress}/responses`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("usertoken")}`
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data)
                const surveyId = data[0].id
                setSurveyId(data[0].id)
                setSurveyData(data);
                if (data.length > 0) {
                    return fetch(`${ApiAddress}/surveys/${surveyId}`, {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${localStorage.getItem("usertoken")}`
                        }
                    });
                } else {
                    throw new Error('No survey data found');
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data)
                setSurveyData(data);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []);

    const handleResponseChange = (questionId, answer, responseOptions = []) => {
        setResponses(prevResponses => ({
            ...prevResponses,
            [questionId]: { answer, responseOptions }
        }));
    };

    const submitSurvey = () => {
        const responseArray = Object.entries(responses).map(([questionId, response]) => ({
            questionId: parseInt(questionId),
            answer: response.answer,
            responseOptions: response.responseOptions
        }));
        console.log(responseArray)

        fetch(`${ApiAddress}/responses/${surveyid}/${userId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${localStorage.getItem("usertoken")}`
            },
            body: JSON.stringify(responseArray)
        })
            .then(response => {
                if (!response.ok) {
                    alert('설문조사를 이미 제출하였습니다.')
                    throw new Error('Network response was not ok');
                }
                else{
                    alert('설문조사 등록 완료');
                }
            })
            .then(data => {
                console.log('Successfully submitted survey:', data);
            })
            .catch(error => {
                console.error('Error submitting survey:', error);
            });
    };

    const renderQuestion = (question) => {
        switch (question.questionType) {
            case 'ShortAnswer':
            case 'Paragraph':
                return (
                    <div className='optionWrap' key={question.questionId}>
                        <label className='optionName'>{question.content}</label>
                        <input
                            className="firstinput"
                            type="text"
                            onChange={(e) => handleResponseChange(question.questionId, e.target.value)}
                        />
                    </div>
                );
            case 'MultipleChoice':
            case 'Checkboxes':
                return (
                    <div key={question.questionId}>
                        <label className='optionName'>{question.content}</label>
                        {question.options.map(option => (
                            <div key={option.optionId}>
                                <input
                                    type={question.questionType === 'MultipleChoice' ? 'radio' : 'checkbox'}
                                    name={`question-${question.questionId}`}
                                    value={option.optionId}
                                    onChange={(e) => {
                                        if (question.questionType === 'MultipleChoice') {
                                            handleResponseChange(question.questionId, null, [parseInt(e.target.value)]);
                                        } else {
                                            const selectedOptions = responses[question.questionId]?.responseOptions || [];
                                            if (e.target.checked) {
                                                handleResponseChange(question.questionId, null, [...selectedOptions, parseInt(e.target.value)]);
                                            } else {
                                                handleResponseChange(question.questionId, null, selectedOptions.filter(id => id !== parseInt(e.target.value)));
                                            }
                                        }
                                    }}
                                />
                                <label>{option.name}</label>
                            </div>
                        ))}
                    </div>
                );
            case 'Dropdown':
                return (
                    <div key={question.questionId}>
                        <label className='optionName'>{question.content}</label>
                        <select onChange={(e) => handleResponseChange(question.questionId, null, [parseInt(e.target.value)])}>
                            {question.options.map(option => (
                                <option key={option.optionId} value={option.optionId}>
                                    {option.name}
                                </option>
                            ))}
                        </select>
                    </div>
                );
            default:
                return null;
        }
    };

    return (
        <div className='content-wrap'>
            <div className='survey-wrap'>
                <h1>설문조사 하기</h1>
                {surveyData ? (
                    <div>
                        <h1>{surveyData.title}</h1>
                        <p>{surveyData.description}</p>
                        <p># {surveyData.hashtag}</p>
                        <p>{surveyData.startDate} ~ {surveyData.dueDate}</p>
                        {surveyData.questions && surveyData.questions.map(renderQuestion)}
                        <button style={{
                            marginTop : '1vh'
                        }}onClick={submitSurvey}>설문조사 제출</button>
                    </div>
                ) : (
                    <p>설문조사 데이터를 불러오는 중...</p>
                )}
            </div>
        </div>
    );

};

export default UserSurveyComponent;
