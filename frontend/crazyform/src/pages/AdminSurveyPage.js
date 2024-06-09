import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList'
import FormList from "../components/FormList"
import '../components/style/AdminSurveyPage.css'

const AdminSurveyPage = () => {
    return(
        <div>
            {/* <Logo /> */}
            <AdminTopNavbar />
            <div className='surveypageContent'>
                <CategoryList></CategoryList>
                <FormList></FormList>
            </div>

        </div>
    )
}

export default AdminSurveyPage;