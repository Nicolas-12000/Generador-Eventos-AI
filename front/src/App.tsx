import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Me from "./pages/Me";
import EventGenerator from "./pages/EventGenerator";
import EventPage from "./pages/EventPage";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/me" element={<Me />} />
        <Route path="/generador" element={<EventGenerator />} />
        <Route path="/evento/:id" element={<EventPage />} />
      </Routes>
    </Router>
  );
}

export default App;
