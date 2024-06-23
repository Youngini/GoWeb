import React, {useEffect} from "react";
import AdminTopNavbar from "../components/AdminTopNavBar";
import VoteList from "../components/VoteComponents/VoteList";
import "../components/style/AdminVotePage.css";
import PathCategory from "../components/pathCategory";
import { useNavigate } from "react-router-dom";

const AdminVotePage = () => {
  
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      navigate(`/AdminVote/${token}`); // 토큰이 있으면 /UserList/${refreshToken} 경로로 이동
    } else {
      navigate("/"); // 토큰이 없으면 홈(로그인 페이지)으로 이동
    }
  }, [navigate]);

  return (
    <div>
      {/* <Logo /> */}
      <AdminTopNavbar />
      <div className="content-Wrapper">
        <div className="voteList">
          <VoteList />
        </div>
        <div className="pathCategory">
          <PathCategory />
        </div>
      </div>
    </div>
  );
};

export default AdminVotePage;
