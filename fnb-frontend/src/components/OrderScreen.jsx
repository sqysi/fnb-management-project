// eslint-disable-next-line no-unused-vars
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const OrderScreen = () => {
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);

  // 1. Lấy danh sách sản phẩm từ Spring Boot khi load trang
  useEffect(() => {
    axios.get('http://localhost:8080/api/products')
      .then(response => {
        setProducts(response.data);
      })
      .catch(error => {
        console.error("Lỗi khi tải sản phẩm:", error);
      });
  }, []);

  // 2. Hàm thêm món vào giỏ hàng
  const addToCart = (product) => {
    const existingItem = cart.find(item => item.product.id === product.id);
    if (existingItem) {
      // Nếu đã có trong giỏ, tăng số lượng lên 1
      setCart(cart.map(item => 
        item.product.id === product.id ? { ...item, quantity: item.quantity + 1 } : item
      ));
    } else {
      // Nếu chưa có, thêm mới với số lượng 1
      setCart([...cart, { product, quantity: 1 }]);
    }
  };

  // 3. Tính tổng tiền giỏ hàng
  const totalAmount = cart.reduce((sum, item) => sum + (item.product.price * item.quantity), 0);

  // 4. Gọi API chốt đơn về Backend
// 4. Gọi API chốt đơn về Backend
  const handlePlaceOrder = () => {
    if (cart.length === 0) {
      alert("Giỏ hàng đang trống!");
      return;
    }

    const orderRequest = {
      items: cart.map(item => ({
        productId: item.product.id,
        quantity: item.quantity
      }))
    };

    axios.post('http://localhost:8080/api/orders', orderRequest)
      .then(response => {
        const orderId = response.data.id;
        alert('🎉 Chốt đơn thành công! Mã hóa đơn: #' + orderId);
        setCart([]); // Xóa trắng giỏ hàng sau khi thành công
        
        // TÍCH HỢP IN PDF: Mở tab mới gọi thẳng vào API xuất PDF của Spring Boot
        window.open(`http://localhost:8080/api/orders/${orderId}/invoice`, '_blank');
      })
      .catch(error => {
        alert('❌ Lỗi tạo đơn: ' + (error.response?.data || error.message));
      });
  };
  return (
    <div className="flex h-screen bg-gray-100 font-sans">
      
      {/* CỘT TRÁI: DANH SÁCH MÓN ĂN */}
      <div className="w-2/3 p-6 overflow-y-auto">
        <h2 className="text-2xl font-bold mb-6 text-gray-800">Menu Sản Phẩm</h2>
        <div className="grid grid-cols-3 gap-6">
          {products.map(product => (
            <div 
              key={product.id} 
              onClick={() => addToCart(product)}
              className="bg-white p-4 rounded-xl shadow-sm cursor-pointer hover:shadow-md transition-all border border-gray-200"
            >
              <div className="h-32 bg-gray-200 rounded-lg mb-4 flex items-center justify-center overflow-hidden">
                {/* Nếu có ảnh thì hiển thị, không thì để ô xám */}
                {product.imageUrl ? (
                  <img src={product.imageUrl} alt={product.name} className="object-cover w-full h-full" />
                ) : (
                  <span className="text-gray-400">No Image</span>
                )}
              </div>
              <h3 className="font-semibold text-lg text-gray-800">{product.name}</h3>
              <p className="text-blue-600 font-bold mt-1">{product.price.toLocaleString()} đ</p>
            </div>
          ))}
        </div>
      </div>

      {/* CỘT PHẢI: GIỎ HÀNG (CART) */}
      <div className="w-1/3 bg-white shadow-xl p-6 flex flex-col border-l border-gray-200">
        <h2 className="text-2xl font-bold mb-6 text-gray-800 border-b pb-4">Hóa Đơn</h2>
        
        <div className="flex-1 overflow-y-auto">
          {cart.length === 0 ? (
            <p className="text-gray-500 text-center mt-10">Chưa có món nào được chọn</p>
          ) : (
            <ul className="space-y-4">
              {cart.map((item, index) => (
                <li key={index} className="flex justify-between items-center bg-gray-50 p-3 rounded-lg">
                  <div>
                    <h4 className="font-semibold text-gray-800">{item.product.name}</h4>
                    <p className="text-sm text-gray-500">{item.product.price.toLocaleString()} đ x {item.quantity}</p>
                  </div>
                  <div className="font-bold text-gray-800">
                    {(item.product.price * item.quantity).toLocaleString()} đ
                  </div>
                </li>
              ))}
            </ul>
          )}
        </div>

        {/* Tổng kết và Nút Thanh toán */}
        <div className="border-t pt-4 mt-4">
          <div className="flex justify-between items-center mb-6">
            <span className="text-lg text-gray-600 font-semibold">Tổng cộng:</span>
            <span className="text-2xl font-bold text-blue-600">{totalAmount.toLocaleString()} đ</span>
          </div>
          <button 
            onClick={handlePlaceOrder}
            className="w-full bg-blue-600 text-white py-4 rounded-xl text-lg font-bold hover:bg-blue-700 transition-colors shadow-lg"
          >
            Thanh Toán & Chốt Đơn
          </button>
        </div>
      </div>

    </div>
  );
};

export default OrderScreen;