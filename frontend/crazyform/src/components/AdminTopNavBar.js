import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import './style/AdminTopNavBar.css'
const AdminTopNavbar = () => {

    const location = useLocation();
    const navigate = useNavigate();
    const surveyButtonBackground = location.pathname === '/AdminSurvey' ? '#EAA0A0' : 'white';
    const voteButtonBackground = location.pathname === '/AdminVote' ? '#EAA0A0' : 'white';
    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [isSignUpModal, setIsSignUpModal] = useState(false)

    const goToAdminSurvey = () => {
        navigate('/AdminSurvey')
    }

    const openSignUpModal = () => {
        setIsSignUpModal(true)
    }

    const closeSignUpModal = () => {
        setId('')
        setPassword('')
        setIsSignUpModal(false);
    };

    const handleSubmit = () => {
        if (id === ''&& password === ''){
            alert('모든 정보를 입력해주세요')
            return
        }
        else{
            const isConfirmed = window.confirm('관리자를 추가하시겠습니까?');
            if (isConfirmed) {
            console.log(id, password);
            setId('')
            setPassword('')
        } else{

            setIsSignUpModal(false); 

        }
    }
        setIsSignUpModal(false); 
    };

    const goToHome = () => {
        const isConfirmed = window.confirm('로그아웃하시겠습니까?');
        if (isConfirmed) {
            navigate('/Home')
        }
    }

    return(
        <div>
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                borderBottom : '1px solid #EAA0A0'
            }}>
                <div style={{
                    margin : '5vh'
                }}>
                    <button style={{
                        width : '20vw',
                        height : '8vh',
                        borderRadius : '2vh',
                        backgroundColor : surveyButtonBackground,
                        border: '3px solid #EAA0A0',
                        fontSize : '3vh',
                    }}
                        onClick={goToAdminSurvey}>
                        설문 페이지
                    </button>
                </div>
                <div style={{
                    margin : '5vh',
                    display : 'flex',
                    flexDirection : 'row'
                }}>
                    <button style={{
                        width : '20vw',
                        height : '8vh',
                        borderRadius : '2vh',
                        backgroundColor : voteButtonBackground,
                        border: '3px solid #EAA0A0',
                        fontSize : '3vh'
                    }}>
                        투표 페이지
                    </button>
                </div>
                <div style={{
                    display : 'flex',
                    justifyContent : 'flex-end',
                    
                }}>
                    <div style={{
                        display : 'flex',
                        flexDirection : 'column',
                    }}>
                        <button className='addAdmin'
                            style={{
                                marginBottom : '2vh'
                            }}
                            onClick={openSignUpModal}>관리자 추가</button>
                        {isSignUpModal && (
                            <div style={{ 
                                position: 'fixed', 
                                top: '50%', 
                                left: '50%', 
                                transform: 'translate(-50%, -50%)', 
                                backgroundColor: 'white', 
                                padding: '5vh', 
                                zIndex: 1000 
                            }}>
                                <div
                                    style={{
                                        marginBottom : '1vh'
                                    }}>
                                    <label htmlFor="id"></label>
                                    <input
                                        placeholder='아이디를 입력하세요'
                                        type="text"
                                        id="id"
                                        value={id}
                                        onChange={(e) => setId(e.target.value)}
                                    />
                                </div>
                                <div>
                                    <label htmlFor="password"></label>
                                    <input
                                        placeholder='비밀번호를 입력하세요'
                                        type="password"
                                        id="password"
                                        value={password}
                                        onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <div style={{
                                    display : 'flex',
                                    flexDirection : 'row',
                                    alignContent : 'center',
                                    justifyContent : 'center',
                                    marginTop : '2vh'
                                }}>
                                <button 
                                    style={{
                                        marginRight : '1vw'
                                    }}
                                    onClick={closeSignUpModal}>취소</button>
                                <button onClick={handleSubmit}>확인</button>
                                </div>
                            </div>
                        )}
                        {/* 모달 뒤 배경을 어둡게 처리 */}
                        {isSignUpModal && (
                            <div style={{
                                position: 'fixed',
                                top: 0,
                                left: 0,
                                right: 0,
                                bottom: 0,
                                backgroundColor: 'rgba(0,0,0,0.5)',
                                zIndex: 999
                            }} onClick={closeSignUpModal}></div>
                        )}
                        <button className='logout'
                            onClick={goToHome}>
                            로그아웃
                        </button>
                    </div>
                    </div>
            </div>
        </div>
    )
}

export default AdminTopNavbar;
