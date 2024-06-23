// path 는 URL, component는 해당 URL에 나타낼 페이지 라고 인식하면 됨

import { computeHeadingLevel } from "@testing-library/react";
import AdminSurveyDetail from "./pages/AdminSurveyDetailPage";
import AdminSurveyPage from "./pages/AdminSurveyPage";
import AdminSurveyReg from "./pages/AdminSurveyRegPage";
import AdminVotePage from "./pages/AdminVotePage";
import AdminVoteRegPage from "./pages/AdminVoteRegPage";
import HomePage from "./pages/HomePage";
import UserReg from "./pages/UserRegPage";
import UserSurvey from "./pages/UserSurveyPage";

const token = localStorage.getItem('token');
const usertoken = localStorage.getItem('usertoken')

const routes = [
  {
    path: "/",
    component: HomePage
  },
  {
    path:"/UserReg",
    component : UserReg
  },
  {
    path : "AdminSurvey/:num",
    component : AdminSurveyPage
  },
  {
    path : `AdminSurvey/${token}`,
    component : AdminSurveyPage
  },
  {
    path : "AdminSurveyDetail/:number/:id",
    component : AdminSurveyDetail
  },
  {
    path : `AdminSurveyDetail/${token}/:id`,
    component : AdminSurveyDetail
  },
  {
    path : "AdminSurveyReg/:num",
    component : AdminSurveyReg
  },
  {
    path : `AdminSurveyReg/${token}`,
    component : AdminSurveyReg
  },
  {
    path : "AdminVote/:num",
    component : AdminVotePage
  },
  {
    path : `AdminVote/${token}`,
    component : AdminVotePage
  },
  {
    path : "AdminVoteReg/:num",
    component : AdminVoteRegPage
  },
  {
    path : `AdminVoteReg/${token}`,
    component : AdminVoteRegPage
  },
  {
    path: `UserSurvey/:number/:id`,
    component : UserSurvey
  },
  {
    path: `UserSurvey/${usertoken}/:id`,
    component : UserSurvey
  },
];

export default routes;