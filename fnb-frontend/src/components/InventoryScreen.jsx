/* eslint-disable react-hooks/immutability */
/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const InventoryScreen = () => {
  const [ingredients, setIngredients] = useState([]);
  const [importAmounts, setImportAmounts] = useState({}); // Lưu trữ số lượng nhập riêng cho từng dòng

  // 1. Tự động tải dữ liệu kho khi vừa vào trang
  useEffect(() => {
    fetchIngredients();
  }, []);

  const fetchIngredients = () => {
    axios.get('http://localhost:8080/api/ingredients')
      .then(response => {
        setIngredients(response.data);
      })
      .catch(error => {
        console.error("Lỗi khi lấy dữ liệu kho:", error);
      });
  };

  // 2. Xử lý cập nhật state khi user gõ số lượng nhập kho
  const handleInputChange = (id, value) => {
    setImportAmounts({
      ...importAmounts,
      [id]: value
    });
  };

  // 3. Gọi API cộng kho khi bấm nút "Nhập"
  const handleImportStock = (id, name) => {
    const amount = importAmounts[id];
    if (!amount || parseFloat(amount) <= 0) {
      alert("Vui lòng nhập số lượng lớn hơn 0!");
      return;
    }

    axios.put(`http://localhost:8080/api/ingredients/${id}/import?amount=${amount}`)
      .then(response => {
        alert(`📦 Nhập kho thành công cho: ${name}!`);
        // Xóa trắng ô input của nguyên liệu đó sau khi nhập thành công
        setImportAmounts({ ...importAmounts, [id]: '' });
        // Tải lại danh sách mới
        fetchIngredients();
      })
      .catch(error => {
        alert("❌ Lỗi nhập kho: " + (error.response?.data || error.message));
      });
  };

  return (
    <div className="p-6 max-w-6xl mx-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-800 flex items-center gap-2">
          📦 Quản Lý Kho & Nguyên Liệu
        </h2>
        <span className="text-sm text-gray-500">Cập nhật thời gian thực</span>
      </div>

      {/* Bảng danh sách tồn kho */}
      <div className="bg-white shadow-md rounded-lg overflow-hidden border border-gray-200">
        <table className="min-w-full leading-normal">
          <thead>
            <tr className="bg-gray-100 text-gray-700 text-left text-xs font-bold uppercase tracking-wider border-b border-gray-200">
              <th className="px-6 py-4">ID</th>
              <th className="px-6 py-4">Tên Nguyên Liệu</th>
              <th className="px-6 py-4 text-right">Tồn Kho Hiện Tại</th>
              <th className="px-6 py-4">Đơn Vị</th>
              <th className="px-6 py-4 text-center">Trạng Thái</th>
              <th className="px-6 py-4 text-center">Hành Động (Nhập Kho)</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {ingredients.length === 0 ? (
              <tr>
                <td colSpan="6" className="text-center py-8 text-gray-500 italic">
                  Không có dữ liệu nguyên liệu. Vui lòng kiểm tra lại Database!
                </td>
              </tr>
            ) : (
              ingredients.map((ing) => {
                // Quy định: Tồn kho dưới 15 là mức cảnh báo (Sắp hết)
                const isLowStock = ing.stockQuantity < 15;

                return (
                  <tr key={ing.id} className="hover:bg-gray-50 transition-colors">
                    <td className="px-6 py-4 text-sm text-gray-600 font-mono">#{ing.id}</td>
                    <td className="px-6 py-4 text-sm font-semibold text-gray-900">{ing.name}</td>
                    <td className={`px-6 py-4 text-sm font-bold text-right ${isLowStock ? 'text-red-600 text-base animate-pulse' : 'text-green-600'}`}>
                      {ing.stockQuantity}
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-500">{ing.unit}</td>
                    <td className="px-6 py-4 text-center">
                      {isLowStock ? (
                        <span className="bg-red-100 text-red-800 text-xs px-2.5 py-1 rounded-full font-bold">
                          🚨 Sắp Hết Hàng
                        </span>
                      ) : (
                        <span className="bg-green-100 text-green-800 text-xs px-2.5 py-1 rounded-full font-bold">
                          ✅ An Toàn
                        </span>
                      )}
                    </td>
                    <td className="px-6 py-4 text-center">
                      <div className="flex justify-center items-center gap-2">
                        <input
                          type="number"
                          placeholder="SL thêm..."
                          value={importAmounts[ing.id] || ''}
                          onChange={(e) => handleInputChange(ing.id, e.target.value)}
                          className="w-24 px-3 py-1.5 text-sm border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                        <button
                          onClick={() => handleImportStock(ing.id, ing.name)}
                          className="bg-blue-600 hover:bg-blue-700 text-white font-medium text-sm px-4 py-1.5 rounded shadow transition-colors"
                        >
                          Nhập
                        </button>
                      </div>
                    </td>
                  </tr>
                );
              })
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default InventoryScreen;