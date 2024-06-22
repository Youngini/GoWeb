import { useState } from 'react';
import './style/Response.css'

const Response = () => {

    const [countnumber, setCountnubmer] = useState(0)

    return(
        <div className="wrap">
            <div className="countnumberWrap">
                <p className='countnumber'>응답 {countnumber}개</p>
                <div className='downloadbuttonWrap'>
                    <button className='downloadbutton'>
                        응답 결과 다운로드
                    </button>
                </div>
            </div>
        </div>
    )
}

export default Response;