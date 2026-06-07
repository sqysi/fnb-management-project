/* eslint-disable no-unused-vars */
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import OrderScreen from './components/OrderScreen';
import InventoryScreen from './components/InventoryScreen'; // IMPORT COMPONENT THẬT Ở ĐÂY

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100 flex flex-col">
        <Navbar />
        
        <div className="flex-1">
          <Routes>
            <Route path="/" element={<OrderScreen />} />
            <Route path="/inventory" element={<InventoryScreen />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;