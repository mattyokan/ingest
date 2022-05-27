import React from 'react';
import ReactDOM from 'react-dom/client';
import {
    BrowserRouter as Router,
    Routes,
    Route,
} from "react-router-dom";
import './index.css';
import App from './App';
import ReportView from "./ReportView";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Router>
        <Routes>
            <Route exact path="/" element={<App/>}/>
            <Route path="/reported.html" element={<ReportView/>}/>
        </Routes>
    </Router>
);