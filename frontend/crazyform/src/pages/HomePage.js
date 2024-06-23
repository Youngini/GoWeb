import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Modal from 'react-modal';
import { ApiAddress } from '../constants';
import '../components/style/HomePage.css';

const HomePage = () => {
    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [adminId, setAdminId] = useState('');
    const [adminPassword, setAdminPassword] = useState('');

    const handleButtonClick = () => {
        setModalIsOpen(true);
    };

    const handleButtonUserReg = () => {
        navigate('/UserReg')
    };

    const closeModal = () => {
        setAdminId('')
        setAdminPassword('')
        setModalIsOpen(false);
    };

    const handleConfirmClick = async () => {
        if (!adminId || !adminPassword) {
            alert('모든 정보를 입력하세요');
        } else {
            try {
                const response = await fetch(`${ApiAddress}/auths/login`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        studentNumber: adminId,
                        password: adminPassword
                    })
                });
    
                if (response.ok) {
                    const data = await response.json();
                    const token = data.accessToken;
    
                    // 토큰을 저장소에 저장 (예: 로컬 스토리지)
                    localStorage.setItem('token', token);
                    console.log(token)
                    alert('로그인에 성공했습니다.')
                    navigate(`/AdminSurvey/${token}`);
                    closeModal();
                } else {
                    alert('로그인에 실패했습니다. 다시 시도해주세요.');
                }
            } catch (error) {
                console.error('로그인 중 오류 발생:', error);
            }
        }
    };
    

    const styles = {
        container: {
            margin: '1vw',
            backgroundImage: 'url("/images/CSE_Logo.png")', // 배경 이미지 설정
            backgroundSize: 'cover', // 이미지가 컨테이너를 가득 채우도록 설정
            backgroundPosition: 'center', // 이미지가 중앙에 위치하도록 설정
            backgroundRepeat: 'no-repeat', // 이미지가 반복되지 않도록 설정
            filter: 'opacity(10%)',
            height: '100vh', // 높이를 화면 전체로 설정
            width: '100vw', // 너비를 화면 전체로 설정
            position: 'fixed', // 스크롤을 해도 배경이 고정되도록 설정
            top: 0,
            left: 0,
            zIndex: -1, // 내용물이 배경 위에 오도록 설정
        },
        header: {
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
            zIndex: 1, // 배경보다 앞에 오도록 설정,
            margin : '2vh'
        },
        adminButton: {
            padding: '1vh',
            fontSize: '16px',
            backgroundColor: '#4CAF50',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
            transition: 'background-color 0.3s ease',
        },
        adminButtonHover: {
            backgroundColor: '#45a049',
        },
        adminButtonActive: {
            backgroundColor: '#3e8e41',
        },
        mainContent: {
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '80vh',
        },
        title: {
            fontSize: '8vh'
        },
        buttonGroup: {
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'center',
            marginTop : '10vh'
        },
        primaryButton: {
            padding: '1vh 2vh',
            fontSize: '16px',
            marginRight: '5vw',
            backgroundColor: '#008CBA',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
            transition: 'background-color 0.3s ease',
        },
        primaryButtonLastChild: {
            marginRight: '0',
        },
        primaryButtonHover: {
            backgroundColor: '#007bb5',
        },
        primaryButtonActive: {
            backgroundColor: '#006fa1',
        },
    };

    return (
        <div style={{position: 'relative', height: '100vh', overflow: 'hidden'}}> {/* 배경 이미지가 전체 페이지를 덮도록 조정 */}
            <div style={styles.container}></div> {/* 배경 이미지를 위한 별도의 div */}
            <div style={{position: 'relative', zIndex: 2}}> {/* 내용물이 배경 위에 올라오도록 설정 */}
                {/* 페이지 내용 */}
                <div style={styles.header}>
                    <button 
                        style={styles.adminButton}
                        onMouseOver={(e) => e.currentTarget.style.backgroundColor = styles.adminButtonHover.backgroundColor}
                        onMouseOut={(e) => e.currentTarget.style.backgroundColor = styles.adminButton.backgroundColor}
                        onMouseDown={(e) => e.currentTarget.style.backgroundColor = styles.adminButtonActive.backgroundColor}
                        onMouseUp={(e) => e.currentTarget.style.backgroundColor = styles.adminButtonHover.backgroundColor}
                        onClick={handleButtonClick}
                    >
                        관리자
                    </button>
                </div>
                <div style={styles.mainContent}>
                    <div style={styles.title}>컴퓨터학부 학생회 설문조사 페이지</div>
                    <div style={styles.buttonGroup}>
                        <button style={styles.primaryButton}>설문하러가기</button>
                        <button style={styles.primaryButton}>투표하러가기</button>
                        <button 
                            style={{ ...styles.primaryButton, ...styles.primaryButtonLastChild }}
                            onClick={handleButtonUserReg}
                            >회원가입</button>
                    </div>
                </div>
            
            </div>

            <Modal
                isOpen={modalIsOpen}
                onRequestClose={closeModal}
                contentLabel="Admin Login"
                style={{
                    content: {
                        top: '50%',
                        left: '50%',
                        right: 'auto',
                        bottom: 'auto',
                        marginRight: '-50%',
                        transform: 'translate(-50%, -50%)',
                        zIndex: 1000,
                    },
                    overlay: {
                        backgroundColor: 'rgba(0, 0, 0, 0.75)',
                        zIndex: 999, // Overlay의 zIndex 설정
                    },
                }}
            >
                <h2>관리자 로그인</h2>
                <form>
                    <label>
                        <input
                            type="text"
                            value={adminId}
                            onChange={(e) => setAdminId(e.target.value)}
                            placeholder='학번'
                            style={{
                                marginBottom : '1vh'
                            }}
                        />
                    </label>
                    <br />
                    <label>
                        <input
                            type="password"
                            value={adminPassword}
                            onChange={(e) => setAdminPassword(e.target.value)}
                            placeholder='비밀번호'
                            style={{
                                marginBottom : '1vh'
                            }}
                        />
                    </label>
                    <br />
                    <button type="button" onClick={handleConfirmClick}
                    style={{
                        marginRight : '1vw'
                    }}>확인</button>
                    <button type="button" onClick={closeModal}>취소</button>
                </form>
            </Modal>
        </div>
    );
};

export default HomePage;