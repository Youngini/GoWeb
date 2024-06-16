// path 는 URL, component는 해당 URL에 나타낼 페이지 라고 인식하면 됨

import AdminSurveyDetail from "./pages/AdminSurveyDetailPage";
import AdminSurveyPage from "./pages/AdminSurveyPage";
import HomePage from "./pages/HomePage";

const routes = [
  {
    path: "/",
    component: HomePage
  },
  {
    path : "AdminSurvey",
    component : AdminSurveyPage
  },
  {
    path : "AdminSurveyDetail/:id",
    component : AdminSurveyDetail
  }
];

export default routes;