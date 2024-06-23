import React from "react";
import AdminTopNavbar from "../components/AdminTopNavBar";
import VoteList from "../components/VoteComponents/VoteList";
import "../components/style/AdminVotePage.css";
import PathCategory from "../components/pathCategory";

const AdminVotePage = () => {
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
