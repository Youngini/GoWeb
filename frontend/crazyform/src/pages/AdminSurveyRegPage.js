import { useState } from "react"
import AdminTopNavbar from "../components/AdminTopNavBar"
import '../components/style/AdminSurveyRegPage.css'
import SurveyForm from "../components/SurveyForm"

const AdminSurveyReg = () => {
    const [isform, setIsform] = useState(true)
    return(
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
                    <div></div>
                )}
            </div>
        </div>
    )
}

export default AdminSurveyReg
