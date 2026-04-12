import { useEffect, useState } from 'react';
import { DollarSign, ShoppingCart, Users, Package, TrendingUp } from 'lucide-react';
import api from '../../api/axios';

const formatMoney = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    products: 0,
    orders: 0,
    revenue: 0
  });

  const role = localStorage.getItem('admin_role');

  useEffect(() => {
    // Demo metrics. They will be connected to true analytical API controllers later.
    // Replace with real backend dashboard API fetch
    const fetchMetrics = async () => {
      try {
        const prod = await api.get('/product/list?size=1');
        const orders = await api.get('/order/list?size=1');
        
        setStats({
          products: prod.data?.data?.totalElements || 124,
          orders: orders.data?.data?.totalElements || 45,
          revenue: 24500000 // Fake revenue
        });
      } catch(e) {
          // keep defaults
          setStats({ products: 124, orders: 45, revenue: 24500000 });
      }
    };
    fetchMetrics();
  }, []);

  return (
    <div>
      <div className="mb-6 sm:mb-8">
        <h2 className="text-xl sm:text-2xl font-bold text-dark">Tổng quan hệ thống</h2>
        <p className="text-xs sm:text-sm text-gray-500 mt-1">Xin chào! Dưới đây là tình hình hoạt động của cửa hàng hôm nay.</p>
      </div>

      {/* Metrics */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-6 mb-6 sm:mb-8">
        <div className="bg-white rounded-xl sm:rounded-2xl p-4 sm:p-6 shadow-sm border border-gray-100 flex items-center justify-between">
          <div className="min-w-0">
            <p className="text-xs sm:text-sm font-bold text-gray-400 uppercase tracking-widest mb-1">Doanh thu</p>
            <h3 className="text-lg sm:text-2xl font-black text-dark truncate">{formatMoney(stats.revenue)}</h3>
            <span className="text-[10px] sm:text-xs font-bold text-green-500 flex items-center mt-2">
              <TrendingUp className="w-3 h-3 mr-1" /> +12.5% so với hôm qua
            </span>
          </div>
          <div className="w-10 h-10 sm:w-14 sm:h-14 bg-green-50 rounded-full flex items-center justify-center text-green-600 flex-shrink-0 ml-3">
            <DollarSign className="w-5 h-5 sm:w-6 sm:h-6" />
          </div>
        </div>

        <div className="bg-white rounded-xl sm:rounded-2xl p-4 sm:p-6 shadow-sm border border-gray-100 flex items-center justify-between">
          <div>
            <p className="text-xs sm:text-sm font-bold text-gray-400 uppercase tracking-widest mb-1">Đơn hàng mới</p>
            <h3 className="text-lg sm:text-2xl font-black text-dark">{stats.orders}</h3>
            <span className="text-[10px] sm:text-xs font-bold text-orange-500 flex items-center mt-2">
              Chờ xử lý (12)
            </span>
          </div>
          <div className="w-10 h-10 sm:w-14 sm:h-14 bg-orange-50 rounded-full flex items-center justify-center text-orange-600 flex-shrink-0 ml-3">
            <ShoppingCart className="w-5 h-5 sm:w-6 sm:h-6" />
          </div>
        </div>

        <div className="bg-white rounded-xl sm:rounded-2xl p-4 sm:p-6 shadow-sm border border-gray-100 flex items-center justify-between sm:col-span-2 lg:col-span-1">
          <div>
            <p className="text-xs sm:text-sm font-bold text-gray-400 uppercase tracking-widest mb-1">Tổng sản phẩm</p>
            <h3 className="text-lg sm:text-2xl font-black text-dark">{stats.products}</h3>
            <span className="text-[10px] sm:text-xs font-bold text-gray-500 flex items-center mt-2">
              Đang kinh doanh
            </span>
          </div>
          <div className="w-10 h-10 sm:w-14 sm:h-14 bg-primary/10 rounded-full flex items-center justify-center text-primary flex-shrink-0 ml-3">
            <Package className="w-5 h-5 sm:w-6 sm:h-6" />
          </div>
        </div>
      </div>

      {/* Quick Actions / Tables */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 sm:gap-6">
        <div className="bg-white rounded-xl sm:rounded-2xl shadow-sm border border-gray-100 p-4 sm:p-6">
           <div className="flex justify-between items-center mb-4 sm:mb-6">
              <h3 className="font-bold text-dark text-base sm:text-lg">Đơn hàng gần đây</h3>
              <button className="text-primary text-xs sm:text-sm font-bold hover:underline">Xem tất cả</button>
           </div>
           
           <div className="space-y-3 sm:space-y-4">
              {[1,2,3].map(i => (
                <div key={i} className="flex justify-between items-center border-b border-gray-100 pb-3 sm:pb-4 last:border-0 last:pb-0 gap-3">
                  <div className="flex items-center gap-2 sm:gap-3 min-w-0">
                    <div className="w-8 h-8 sm:w-10 sm:h-10 bg-gray-50 rounded-lg flex items-center justify-center flex-shrink-0">
                       <ShoppingCart className="w-3 h-3 sm:w-4 sm:h-4 text-gray-400" />
                    </div>
                    <div className="min-w-0">
                      <p className="text-xs sm:text-sm font-bold text-dark truncate">Order #WS00{i}</p>
                      <p className="text-[10px] sm:text-xs text-gray-500 truncate">Nguyễn Văn Khách Hàng</p>
                    </div>
                  </div>
                  <div className="text-right flex-shrink-0">
                    <p className="text-xs sm:text-sm font-bold text-primary">{formatMoney(2500000)}</p>
                    <span className="text-[9px] sm:text-[10px] font-bold text-orange-600 bg-orange-50 px-2 py-0.5 rounded">Chờ xử lý</span>
                  </div>
                </div>
              ))}
           </div>
        </div>

        {role === 'ADMIN' && (
          <div className="bg-white rounded-xl sm:rounded-2xl shadow-sm border border-gray-100 p-4 sm:p-6">
             <div className="flex justify-between items-center mb-4 sm:mb-6">
                <h3 className="font-bold text-dark text-base sm:text-lg">Sellers Hàng Đầu</h3>
                <button className="text-primary text-xs sm:text-sm font-bold hover:underline">Quản lý User</button>
             </div>
             
             <div className="space-y-3 sm:space-y-4">
                {[1,2].map(i => (
                  <div key={i} className="flex justify-between items-center bg-gray-50 p-2.5 sm:p-3 rounded-xl border border-gray-100 gap-3">
                    <div className="flex items-center gap-2 sm:gap-3 min-w-0">
                      <div className="w-8 h-8 sm:w-10 sm:h-10 bg-white rounded-full shadow-sm flex items-center justify-center font-bold text-gray-400 flex-shrink-0 text-sm">
                         S{i}
                      </div>
                      <div className="min-w-0">
                        <p className="text-xs sm:text-sm font-bold text-dark truncate">Cửa hàng Tiến Đạt {i}</p>
                        <p className="text-[10px] sm:text-xs text-gray-500">Người bán uy tín</p>
                      </div>
                    </div>
                    <div className="flex bg-white px-2 py-1 rounded shadow-sm gap-1 sm:gap-2 flex-shrink-0 items-center">
                       <Users className="w-3 h-3 sm:w-4 sm:h-4 text-gray-400" />
                       <span className="text-[10px] sm:text-xs font-bold">1.2k</span>
                    </div>
                  </div>
                ))}
             </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AdminDashboard;
