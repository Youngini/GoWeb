import React, { useState } from 'react';
import AdminTopNavbar from "../components/AdminTopNavBar";
import CategoryList from '../components/CategoryList';
import FormList from "../components/FormList";
import VoteList from '../components/VoteComponents/VoteList';
import '../components/style/AdminVotePage.css';

const AdminVotePage = () => {

  return (
    <div>
      {/* <Logo /> */}
      <AdminTopNavbar />
      <VoteList />
    </div>
  );
};

export default AdminVotePage;