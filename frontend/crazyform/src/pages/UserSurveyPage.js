import React, { useState } from 'react';
import TopNavBar from "../components/TopNavBar";
import UserSurveyComponent from '../components/UserSurveyComponent';
import '../components/style/UserSurvey.css';

const UserSurvey = () => {
    const [isSurvey, setIsSurvey] = useState(true);

    return(
        <div>
            <TopNavBar isSurvey={isSurvey} setIsSurvey={setIsSurvey} />
            <div className='pagewrap'>
                {isSurvey ? <UserSurveyComponent /> : <div>투표 페이지</div>}
            </div>
        </div>
    )
};

export default UserSurvey;
