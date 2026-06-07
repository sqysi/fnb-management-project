/* eslint-disable no-unused-vars */
import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <nav className="bg-blue-800 text-white shadow-md">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center gap-4">
            <span className="font-bold text-xl tracking-wider">☕ F&B POS</span>
            <div className="flex space-x-4 ml-6">
              <Link to="/" className="hover:bg-blue-700 px-3 py-2 rounded-md font-medium transition-colors">
                Bán Hàng
              </Link>
              <Link to="/inventory" className="hover:bg-blue-700 px-3 py-2 rounded-md font-medium transition-colors">
                Quản Lý Kho
              </Link>
            </div>
          </div>
          <div>
            <span className="text-sm bg-blue-900 py-1 px-3 rounded-full">Admin</span>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;