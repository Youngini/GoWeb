import { useState } from 'react';
import { useEffect } from 'react';
import { ApiAddress } from '../constants';
import { useNavigate } from 'react-router-dom';

const UserReg = () => {
    const navigate = useNavigate();
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [message, setMessage] = useState('');
    const [studentNum, setStudentNum] = useState('')
    const [name, setName] = useState('')

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    }

    const handlePasswordConfirmChange = (e) => {
        setPasswordConfirm(e.target.value);
    }

    const handleStudenNumChange = (e) => {
        setStudentNum(e.target.value)
    }

    const handleNameChange = (e) => {
        setName(e.target.value)
    }

    useEffect(() => {
        if (passwordConfirm) {
            if (password === passwordConfirm) {
                setMessage('비밀번호가 같습니다.');
            } else {
                setMessage('비밀번호가 다릅니다.');
            }
        } else {
            setMessage('');
        }
    }, [password, passwordConfirm]);

    const handleReg = async () => {
        if (!studentNum || !name || !password || !passwordConfirm) {
            alert('모든 정보를 입력해주세요');
            return;
        }

        if (password !== passwordConfirm) {
            alert('비밀번호가 다릅니다');
            return;
        }

        const data = {
            studentNumber: studentNum,
            name: name,
            password: password,
        };

        try {
            const response = await fetch(`${ApiAddress}/auths/join`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });
            console.log(data);

            if (response.ok) {
                console.log('회원가입 완료');
                alert('회원가입 완료했습니다.');
                navigate('/');
            } else {
                console.error('Failed to submit survey');
            }
        } catch (error) {
            console.error('Error submitting survey:', error);
        }
    }

    const styles = {
        container: {
            margin: '1vw',
            backgroundImage: 'url("/images/CSE_Logo.png")',
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            backgroundRepeat: 'no-repeat',
            filter: 'opacity(10%)',
            height: '100vh',
            width: '100vw',
            position: 'fixed',
            top: 0,
            left: 0,
            zIndex: -1,
        },
        formContainer: {
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh',
            zIndex: 1, // 컨텐츠가 배경 위에 오도록 설정
        },
        input: {
            margin: '10px 0',
            padding: '10px',
            width: '80%',
            maxWidth: '400px',
        },
        message: {
            color: password === passwordConfirm ? 'green' : 'red',
            marginTop: '10px',
        },
    };
    
    return (
        <div style={{ position: 'relative', height: '100vh', overflow: 'hidden' }}>
            <div style={styles.container}></div>
            <div style={styles.formContainer}>
                <h1>회원가입</h1>
                <input
                    className="studentNumInput"
                    style={styles.input}
                    placeholder="학번을 입력하세요"
                    value={studentNum}
                    onChange={handleStudenNumChange}
                />
                <input
                    className="nameInput"
                    style={styles.input}
                    placeholder="이름을 입력하세요"
                    value={name}
                    onChange={handleNameChange}
                />
                <input
                    type="password"
                    className="password"
                    style={styles.input}
                    placeholder="비밀번호를 입력하세요"
                    value={password}
                    onChange={handlePasswordChange}
                />
                <input
                    type="password"
                    className="passwordconfirm"
                    style={styles.input}
                    placeholder="비밀번호를 한 번 더 입력하세요"
                    value={passwordConfirm}
                    onChange={handlePasswordConfirmChange}
                />
                <div style={styles.message}>
                    {message}
                </div>
                <div style={{
                    display : 'flex',
                    flexDirection : 'row'
                }}>
                    <button style={{
                        margin : '3vh',
                        width : '8vh',
                        height : '3vw',
                        borderRadius : '1vh',
                        background : 'white'
                    }}
                    onClick={() => navigate(-1)}>취소</button>
                    <button style={{
                        margin : '3vh',
                        width : '8vh',
                        height : '3vw',
                        borderRadius : '1vh',
                        background : 'white'
                    }}
                    onClick={handleReg}
                    >확인</button>
                </div>
            </div>
        </div>
    );
};

export default UserReg;
