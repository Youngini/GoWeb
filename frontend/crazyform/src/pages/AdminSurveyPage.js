import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList'
import Logo from "../components/logo";
const AdminSurveyPage = () => {
    return(
        <div>
            <Logo />
            <AdminTopNavbar />
            <CategoryList></CategoryList>
        </div>
    )
}

export default AdminSurveyPage;