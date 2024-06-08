// path 는 URL, component는 해당 URL에 나타낼 페이지 라고 인식하면 됨

import HomePage from "./pages/HomePage";
import AdminSurvey from "./pages/AdminSurvey";

const routes = [
  {
    path: "/",
    component: HomePage
  },
  {
    path : "/AdminSurvey",
    component : AdminSurvey
  }
];

export default routes;