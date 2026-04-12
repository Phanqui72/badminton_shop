import { useEffect } from 'react';
import { Menu, ShoppingBag, Receipt, Tag, MapPin, CreditCard, HelpCircle, LogOut, ChevronRight } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

const Profile = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      navigate('/login');
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50 flex flex-col">
      {/* Header */}
      <div className="bg-white px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center shadow-sm relative z-10">
        <Menu className="w-6 h-6 text-gray-500 lg:hidden" />
        <div className="text-xl font-black italic text-primary tracking-tighter">Wi Shop</div>
        <div className="relative">
          <ShoppingBag className="w-6 h-6 text-primary" />
          <span className="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] w-4 h-4 flex items-center justify-center rounded-full">3</span>
        </div>
      </div>

      <div className="p-3 sm:p-4 lg:p-6 flex-grow max-w-4xl mx-auto w-full">
        {/* Top section: User Card + Action Cards in a grid on desktop */}
        <div className="flex flex-col lg:flex-row lg:gap-6">
          {/* Left: User Card */}
          <div className="lg:flex-1">
            <div className="bg-white rounded-[24px] sm:rounded-[32px] p-5 sm:p-6 shadow-sm border border-gray-100 flex flex-col items-center pt-8 relative mt-10 sm:mt-12">
              <div className="absolute -top-10 bg-white p-1 rounded-full shadow-lg">
                <div className="w-18 h-18 sm:w-20 sm:h-20 bg-gray-200 rounded-full overflow-hidden relative" style={{ width: '5rem', height: '5rem' }}>
                  <img src="https://via.placeholder.com/150" alt="Avatar" className="w-full h-full object-cover" />
                </div>
                <div className="absolute bottom-0 right-0 bg-primary w-6 h-6 rounded-full flex items-center justify-center border-2 border-white shadow-sm">
                  <svg className="w-3 h-3 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 3v4M3 5h4M6 17v4m-2-2h4m5-16l2.286 6.857L21 12l-5.714 2.143L13 21l-2.286-6.857L5 12l5.714-2.143L13 3z" /></svg>
                </div>
              </div>
              
              <h2 className="text-xl sm:text-2xl font-black text-dark tracking-tight mt-2 pb-1">Phan Qui</h2>
              <div className="flex items-center gap-2 mb-4 sm:mb-6 flex-wrap justify-center">
                <span className="bg-[#007066] text-white text-[10px] font-bold px-3 py-1 rounded-full uppercase tracking-wider">Gold Member</span>
                <span className="text-xs text-gray-500">Joined June 2023</span>
              </div>

              <div className="flex justify-center gap-8 sm:gap-12 w-full px-4 sm:px-6">
                <div className="flex flex-col items-center">
                  <span className="text-[10px] font-bold text-gray-400 uppercase tracking-widest mb-1">Points</span>
                  <span className="text-xl sm:text-2xl font-black text-[#007066]">1,240</span>
                </div>
                <div className="w-px h-full bg-gray-100"></div>
                <div className="flex flex-col items-center">
                  <span className="text-[10px] font-bold text-gray-400 uppercase tracking-widest mb-1">Orders</span>
                  <span className="text-xl sm:text-2xl font-black text-dark">24</span>
                </div>
              </div>
            </div>
          </div>

          {/* Right: Action Cards */}
          <div className="lg:flex-1 flex flex-col gap-3 sm:gap-4 mt-3 sm:mt-4 lg:mt-12">
            <div className="bg-white rounded-[20px] sm:rounded-[24px] p-4 sm:p-5 shadow-sm border border-gray-100">
              <div className="flex justify-between items-start mb-3">
                <div className="w-10 h-10 sm:w-12 sm:h-12 rounded-xl bg-secondary/50 flex items-center justify-center text-[#007066]">
                  <Receipt className="w-5 h-5 sm:w-6 sm:h-6" />
                </div>
                <Link to="/orders" className="text-[10px] font-extrabold text-[#007066] uppercase hover:underline">View All</Link>
              </div>
              <h3 className="font-bold text-base sm:text-lg text-dark mb-1">Đơn hàng của tôi</h3>
              <p className="text-xs text-gray-500 mb-3 sm:mb-4 line-clamp-2">Theo dõi, trả hàng, hoặc mua lại các sản phẩm bạn đã đặt.</p>
              <div className="flex items-center">
                <div className="flex -space-x-3">
                  <img src="https://via.placeholder.com/40" className="w-8 h-8 sm:w-10 sm:h-10 rounded-full border-2 border-white bg-gray-100" />
                  <img src="https://via.placeholder.com/40" className="w-8 h-8 sm:w-10 sm:h-10 rounded-full border-2 border-white bg-gray-100" />
                  <div className="w-8 h-8 sm:w-10 sm:h-10 rounded-full border-2 border-white bg-gray-200 flex items-center justify-center text-xs font-bold text-gray-600">+2</div>
                </div>
              </div>
            </div>

            <div className="bg-white rounded-[20px] sm:rounded-[24px] p-4 sm:p-5 shadow-sm border border-gray-100">
              <div className="w-10 h-10 sm:w-12 sm:h-12 rounded-xl bg-gray-100 flex items-center justify-center text-gray-600 mb-3">
                <Tag className="w-5 h-5 sm:w-6 sm:h-6" />
              </div>
              <h3 className="font-bold text-base sm:text-lg text-dark mb-1">Khuyến mãi</h3>
              <p className="text-xs text-gray-500">Bạn đang có 3 mã giảm giá chờ sử dụng.</p>
            </div>
          </div>
        </div>

        {/* Menu List */}
        <div className="mt-3 sm:mt-4 flex flex-col gap-3 sm:gap-4">
          <div className="bg-white rounded-[20px] sm:rounded-[24px] overflow-hidden shadow-sm border border-gray-100">
            <MenuListItem icon={<MapPin className="w-5 h-5" />} title="Địa chỉ nhận hàng" subtitle="2 địa chỉ đã lưu" />
            <div className="h-px w-full bg-gray-50 ml-16"></div>
            <MenuListItem icon={<CreditCard className="w-5 h-5" />} title="Phương thức thanh toán" subtitle="Visa ending in **** 4242" />
            <div className="h-px w-full bg-gray-50 ml-16"></div>
            <MenuListItem icon={<HelpCircle className="w-5 h-5" />} title="Trợ giúp" subtitle="FAQs và Liên hệ hỗ trợ" />
          </div>

          <div className="bg-white rounded-[20px] sm:rounded-[24px] overflow-hidden shadow-sm border border-gray-100">
            <button onClick={handleLogout} className="w-full flex items-center gap-3 sm:gap-4 p-3 sm:p-4 hover:bg-gray-50 transition">
              <div className="w-10 h-10 rounded-xl bg-red-50 flex items-center justify-center text-red-500">
                <LogOut className="w-5 h-5" />
              </div>
              <div className="flex-grow text-left">
                <div className="font-bold text-red-600 text-sm sm:text-base">Đăng xuất</div>
                <div className="text-[10px] text-red-400">Xóa phiên đăng nhập</div>
              </div>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

const MenuListItem = ({ icon, title, subtitle }: { icon: React.ReactNode, title: string, subtitle: string }) => (
  <button className="w-full flex items-center gap-3 sm:gap-4 p-3 sm:p-4 hover:bg-gray-50 transition">
    <div className="w-10 h-10 rounded-xl bg-gray-50 flex items-center justify-center text-gray-600">
      {icon}
    </div>
    <div className="flex-grow text-left min-w-0">
      <div className="font-bold text-dark text-sm">{title}</div>
      <div className="text-[10px] sm:text-xs text-gray-500 truncate">{subtitle}</div>
    </div>
    <ChevronRight className="w-4 h-4 text-gray-400 flex-shrink-0" />
  </button>
)

export default Profile;
