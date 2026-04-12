import { Menu, ShoppingCart as CartIcon, Trash2, Plus, Minus, ArrowRight } from 'lucide-react';
import { Link } from 'react-router-dom';

const cartItems = [
  { id: 1, name: 'Yonex Astrox 88D Pro', color: 'Camel Gold', size: '4U/G5', price: '4.250.000₫', qty: 1, img: 'https://via.placeholder.com/100', isPromo: false },
  { id: 2, name: 'Victor P9200II AC', color: 'White/Blue', size: '42', price: '2.850.000₫', qty: 1, img: 'https://via.placeholder.com/100', isPromo: false },
  { id: 3, name: 'Yonex Aerosensa 50', type: 'Lông vũ', qtyDesc: '12 quả', price: '950.000₫', qty: 2, img: 'https://via.placeholder.com/100', isPromo: true },
];

const Cart = () => {
  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50 flex flex-col">
      {/* Header */}
      <div className="bg-gray-50 px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center z-10 sticky top-0">
        <Menu className="w-6 h-6 text-gray-500 lg:hidden" />
        <div className="text-xl font-black italic text-primary tracking-tighter">Wi Shop</div>
        <div className="relative">
          <CartIcon className="w-6 h-6 text-primary" />
          <span className="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] w-4 h-4 flex items-center justify-center rounded-full">3</span>
        </div>
      </div>

      <div className="px-4 sm:px-6 lg:px-8 py-2 max-w-6xl mx-auto w-full">
        <div className="flex justify-between items-baseline mb-6 mt-4">
          <h1 className="text-2xl sm:text-3xl font-black text-dark tracking-tight">Giỏ hàng</h1>
          <span className="text-sm font-semibold text-gray-500">3 sản phẩm</span>
        </div>

        {/* Main content: items + summary side by side on desktop */}
        <div className="flex flex-col lg:flex-row lg:gap-8">
          {/* Cart Items */}
          <div className="space-y-3 sm:space-y-4 lg:flex-1">
            {cartItems.map((item) => (
              <div key={item.id} className="bg-white rounded-2xl sm:rounded-3xl p-3 sm:p-4 flex gap-3 sm:gap-4 shadow-sm border border-gray-100 relative overflow-hidden group">
                <div className="absolute top-0 right-0 w-32 h-full bg-gradient-to-l from-red-50 to-transparent opacity-0 group-hover:opacity-100 transition duration-500"></div>
                
                <div className="w-20 h-20 sm:w-24 sm:h-24 bg-gray-100 rounded-xl sm:rounded-2xl flex-shrink-0 overflow-hidden">
                  <img src={item.img} alt={item.name} className="w-full h-full object-cover mix-blend-multiply" />
                </div>
                
                <div className="flex flex-col flex-grow py-0.5 sm:py-1 relative z-10 min-w-0">
                  <div className="flex justify-between items-start mb-1 gap-2">
                    <h3 className="font-bold text-sm text-dark leading-tight truncate">{item.name}</h3>
                    <span className="font-bold text-primary whitespace-nowrap text-sm">{item.price}</span>
                  </div>
                  
                  <div className="text-[10px] sm:text-xs text-gray-500 mb-auto">
                    {item.color && <span>Màu: {item.color} | </span>}
                    {item.size && <span>Size: {item.size}</span>}
                    {item.type && <span>Loại: {item.type} | </span>}
                    {item.qtyDesc && <span>Số lượng: {item.qtyDesc}</span>}
                  </div>
                  
                  <div className="flex justify-between items-center mt-2">
                    <div className="bg-gray-100 rounded-full flex items-center px-1 py-1">
                      <button className="w-6 h-6 flex items-center justify-center text-gray-500 hover:text-dark hover:bg-white rounded-full transition">
                        <Minus className="w-3 h-3" />
                      </button>
                      <span className="w-8 text-center text-xs font-bold">{item.qty}</span>
                      <button className="w-6 h-6 flex items-center justify-center text-gray-500 hover:text-dark hover:bg-white rounded-full transition">
                        <Plus className="w-3 h-3" />
                      </button>
                    </div>
                    
                    <button className="text-gray-400 hover:text-red-500 transition p-2">
                      <Trash2 className="w-4 h-4" />
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Order Summary */}
          <div className="bg-white rounded-[24px] sm:rounded-[32px] p-5 sm:p-6 shadow-xl border border-gray-100 mt-6 lg:mt-0 mb-6 lg:w-96 lg:sticky lg:top-24 lg:self-start">
            <h2 className="text-lg sm:text-xl font-bold text-dark mb-6">Chi tiết đơn hàng</h2>
            
            <div className="space-y-3 sm:space-y-4 mb-6">
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Tạm tính</span>
                <span className="font-semibold text-dark">9.000.000₫</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Phí vận chuyển</span>
                <span className="font-semibold text-dark">35.000₫</span>
              </div>
              <div className="flex justify-between text-sm text-[#007066]">
                <span className="font-medium">Giảm giá</span>
                <span className="font-semibold">-150.000₫</span>
              </div>
            </div>

            <div className="mb-6">
              <label className="text-[10px] font-bold text-gray-400 uppercase tracking-widest block mb-2">Mã giảm giá</label>
              <div className="flex gap-2">
                <input type="text" placeholder="Nhập mã..." className="flex-grow bg-gray-50 rounded-xl px-4 py-2.5 sm:py-3 text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition" />
                <button className="bg-gray-200 text-dark font-bold text-xs uppercase px-4 sm:px-5 rounded-xl hover:bg-gray-300 transition whitespace-nowrap">Áp dụng</button>
              </div>
            </div>
            
            <div className="h-px bg-gray-100 w-full mb-6 relative">
              <div className="absolute top-1/2 left-0 -translate-y-1/2 -ml-7 sm:-ml-8 w-4 h-4 rounded-full bg-gray-50"></div>
              <div className="absolute top-1/2 right-0 -translate-y-1/2 -mr-7 sm:-mr-8 w-4 h-4 rounded-full bg-gray-50"></div>
            </div>

            <div className="flex justify-between items-end mb-6">
              <span className="text-sm font-bold text-dark">Tổng cộng</span>
              <div className="text-right">
                <span className="text-xl sm:text-2xl font-black text-primary block leading-none">8.885.000₫</span>
                <span className="text-[10px] text-gray-400">Đã bao gồm VAT</span>
              </div>
            </div>

            <Link to="/checkout" className="w-full bg-primary hover:bg-primary-hover text-white font-bold py-3.5 sm:py-4 rounded-xl shadow-lg shadow-primary/30 flex items-center justify-center gap-2 transition group">
              <span>Tiến hành thanh toán</span>
              <ArrowRight className="w-4 h-4 group-hover:translate-x-1 transition" />
            </Link>
            
            <p className="text-center text-[10px] text-gray-400 mt-4 px-4">
              Bằng cách đặt hàng, bạn đồng ý với <a href="#" className="underline">Điều khoản dịch vụ</a> của Wi Shop.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Cart;
