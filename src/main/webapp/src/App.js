import './App.css';
import {approveContent, declineContent, DocumentContent} from "./flow/ContentFlow";
import ReportView from "./ReportView";

function App() {
    return (
        <section className="main">
            <section className="site-header">
                <h1>Review Dashboard</h1>
            </section>
            <DocumentContent/>
            <ReportView/>
        </section>
    );
}

export default App;
