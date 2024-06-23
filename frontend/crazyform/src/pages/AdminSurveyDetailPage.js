import { useState } from "react";
import AdminTopNavbar from "../components/AdminTopNavBar";
import '../components/style/AdminSurveyDetailPage.css';
import SurveyForm from "../components/SurveyForm";
import Response from "../components/Response";

const AdminSurveyDetail = () => {
    const [isform, setIsform] = useState(true);

    return (
        <div>
            <AdminTopNavbar />
            <div className="surveydetailpageContent">
                <div className="buttonContent">
                    <div className={`${isform ? 'buttonWrap-active' : 'buttonWrap'}`}
                        onClick={() => setIsform(true)}>
                        질문
                    </div>
                    <div className={`${isform ? 'buttonWrap' : 'buttonWrap-active'}`}
                        onClick={() => setIsform(false)}>
                        응답
                    </div>
                </div>
                {isform ? (
                    <SurveyForm />  
                ) : (
                    <Response />
                )}
            </div>
        </div>
    );
}

export default AdminSurveyDetail;
