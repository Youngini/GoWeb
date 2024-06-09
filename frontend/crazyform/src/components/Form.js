import React, { useState } from "react";
import "./style/Form.css";

export default function Form() {

    return (
    <div className="formWrapper">
      <div className="formTitle">2024 MT 수요조사</div>
      <button className="showResponse">응답 보기</button>
      <button className="activeForm">폼 활성화</button>
    </div>
  );
}
