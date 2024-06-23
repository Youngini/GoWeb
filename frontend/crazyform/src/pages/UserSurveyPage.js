import React, { useState } from 'react';
import TopNavBar from "../components/TopNavBar";

const UserSurvey = () => {
    const [isSurvey, setIsSurvey] = useState(true);

    return(
        <div>
            <TopNavBar isSurvey={isSurvey} setIsSurvey={setIsSurvey} />
            {isSurvey ? <div>설문조사 페이지</div> : <div>투표 페이지</div>}
        </div>
    )
};

export default UserSurvey;
