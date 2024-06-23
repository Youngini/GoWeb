import { useEffect, useState } from 'react';
import './style/Response.css';
import { ApiAddress } from '../constants';
import { useParams } from 'react-router-dom';

const Response = () => {
    const [countnumber, setCountnumber] = useState(0);
    const [responses, setResponses] = useState([]);
    const { id: surveyId } = useParams(); 

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`${ApiAddress}/responses/${surveyId}`, {
                    headers: {
                      'Authorization': `Bearer ${localStorage.getItem("token")}`,
                  },
                  });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                else {
                    const data = await response.json();

                    const extractedResponses = data.questions.map(question => {
                        return {
                            content: question.content,
                            userResponses: question.selectedOptions.map(userResponse => ({
                                studentName: userResponse.studentName,
                                responses: Array.isArray(userResponse.response) ? userResponse.response : [userResponse.response]
                            }))
                        };
                    });
                    
                    setResponses(extractedResponses);
                    const totalUserResponses = extractedResponses.reduce((acc, question) => acc + question.userResponses.length, 0);
                    setCountnumber(totalUserResponses);
                    
                }
            } catch (error) {
                console.error('Error fetching data', error);
            }
        };

        fetchData();
    }, [surveyId]);

    const handledownload = async () => {
        try {
            const response = await fetch(`${ApiAddress}/excels/surveyDownload/${surveyId}`, {
                headers: {
                  'Authorization': `Bearer ${localStorage.getItem("token")}`,
                },
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            else{
                const blob = await response.blob();
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = `survey_${surveyId}.xlsx`; // 적절한 파일명을 설정하세요
                document.body.appendChild(a);
                a.click();
                document.body.removeChild(a);
                window.URL.revokeObjectURL(url);
            }
        } catch (error) {
            console.error('Error downloading file', error);
        }
    };

    return (
        <div className="wrap">
            <div className="countnumberWrap">
                <p className='countnumber'>응답 {countnumber}개</p>
                <div className='downloadbuttonWrap'>
                    <button className='downloadbutton'
                        onClick={handledownload}>
                        응답 결과 다운로드
                    </button>
                </div>
            </div>
            <div className="responses">
                {responses.map((question, index) => (
                    <div key={index} className="question">
                        <h3>{question.content}</h3>
                        {question.userResponses.map((userResponse, idx) => (
                            <div key={idx} className="userResponse">
                                <p><strong>{userResponse.studentName}</strong></p>
                                <p>{userResponse.responses.join(', ')}</p>
                            </div>
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Response;
