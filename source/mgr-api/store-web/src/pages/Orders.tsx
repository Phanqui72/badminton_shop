import { useState, useEffect } from 'react';
import { ArrowLeft, Box, CheckCircle, Clock } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../api/axios';

const formatMoney = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

const Orders = () => {
  const [orders, setOrders] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await api.get('/order/list'); // Map to OrderController
        if (response.data.result) {
          setOrders(response.data.data.content || []);
        }
      } catch (error) {
        console.error("Failed to fetch orders:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchOrders();
  }, []);

  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50 flex flex-col">
      <div className="bg-white px-4 sm:px-6 lg:px-8 py-4 flex items-center shadow-sm z-10 sticky top-0 gap-3 sm:gap-4">
        <Link to="/profile" className="text-dark">
            <ArrowLeft className="w-6 h-6" />
        </Link>
        <span className="text-lg sm:text-xl font-bold text-dark tracking-tight">Đơn hàng của tôi</span>
      </div>

      <div className="p-3 sm:p-4 lg:p-6 max-w-4xl mx-auto w-full">
        {loading ? (
             <div className="text-center py-10 text-gray-500">Đang tải lịch sử...</div>
        ) : orders.length > 0 ? (
          <div className="flex flex-col gap-3 sm:gap-4">
            {orders.map((order) => (
               <div key={order.id} className="bg-white rounded-2xl sm:rounded-3xl p-4 sm:p-5 shadow-sm border border-gray-100">
                 <div className="flex justify-between items-center border-b border-gray-100 pb-3 mb-3 gap-2">
                   <span className="text-xs sm:text-sm font-bold text-dark uppercase tracking-wide truncate">Mã ĐH: {order.id || '#WISHOP001'}</span>
                   {order.status === 1 ? (
                       <span className="text-[10px] sm:text-xs font-bold text-green-600 bg-green-50 px-2 sm:px-3 py-1 rounded-full flex items-center gap-1 flex-shrink-0">
                         <CheckCircle className="w-3 h-3" /> Hoàn thành
                       </span>
                   ) : (
                       <span className="text-[10px] sm:text-xs font-bold text-orange-600 bg-orange-50 px-2 sm:px-3 py-1 rounded-full flex items-center gap-1 flex-shrink-0">
                         <Clock className="w-3 h-3" /> Đang vận chuyển
                       </span>
                   )}
                 </div>

                 <div className="flex items-center gap-3 sm:gap-4 mb-3 sm:mb-4">
                    <div className="w-14 h-14 sm:w-16 sm:h-16 bg-gray-100 rounded-xl flex items-center justify-center text-gray-400 flex-shrink-0">
                        <Box className="w-7 h-7 sm:w-8 sm:h-8" />
                    </div>
                    <div className="min-w-0">
                      <h4 className="text-sm font-bold text-dark line-clamp-1">{order.productName || 'Đơn hàng mua sắm Wi Shop'}</h4>
                      <p className="text-xs text-gray-500 mt-1">Số lượng: {order.quantity || 1}</p>
                    </div>
                 </div>

                 <div className="flex justify-between items-end pt-2">
                    <span className="text-xs text-gray-400">Tổng tiền {order.totalQuantity || ''} sản phẩm:</span>
                    <span className="text-base sm:text-lg font-extrabold text-primary">{formatMoney(order.totalPrice || 2450000)}</span>
                 </div>
               </div>
            ))}
          </div>
        ) : (
          <div className="bg-white rounded-2xl sm:rounded-3xl p-8 sm:p-10 flex flex-col items-center justify-center border border-gray-100 mt-6 sm:mt-10">
              <div className="w-16 h-16 sm:w-20 sm:h-20 bg-gray-50 rounded-full flex items-center justify-center mb-4">
                 <Box className="w-8 h-8 sm:w-10 sm:h-10 text-gray-300" />
              </div>
              <h3 className="font-bold text-dark text-base sm:text-lg mb-2">Chưa có đơn hàng</h3>
              <p className="text-xs text-gray-500 text-center mb-6">Bạn chưa đặt bất kỳ đơn hàng nào từ cửa hàng của chúng tôi.</p>
              <Link to="/products" className="bg-primary text-white font-bold py-3 px-6 rounded-xl shadow-lg shadow-primary/30">Khám phá ngay</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Orders;
