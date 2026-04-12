import { useState } from 'react';
import { Outlet, Navigate } from 'react-router-dom';
import { LayoutDashboard, Users, UserCog, Package, ShoppingCart, LogOut, Menu, X } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';

const AdminLayout = () => {
  const navigate = useNavigate();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  // Basic guard concept for admin
  const token = localStorage.getItem('admin_token');
  
  if (!token) {
    return <Navigate to="/admin/login" replace />;
  }

  const handleLogout = () => {
    localStorage.removeItem('admin_token');
    localStorage.removeItem('admin_role');
    navigate('/admin/login');
  };

  const role = localStorage.getItem('admin_role') || 'ADMIN'; // 'ADMIN' or 'SELLER'

  const closeSidebar = () => setSidebarOpen(false);

  return (
    <div className="min-h-screen bg-gray-100 flex relative">
      {/* Mobile Overlay */}
      {sidebarOpen && (
        <div className="fixed inset-0 bg-black/50 z-40 lg:hidden" onClick={closeSidebar}></div>
      )}

      {/* Sidebar */}
      <aside className={`
        fixed lg:static inset-y-0 left-0 z-50 
        w-64 bg-dark text-white flex flex-col
        transform transition-transform duration-300 ease-in-out
        ${sidebarOpen ? 'translate-x-0' : '-translate-x-full'}
        lg:translate-x-0
      `}>
        <div className="p-4 sm:p-6 border-b border-gray-800 flex items-center justify-between">
          <h1 className="text-xl sm:text-2xl font-black italic text-primary">
            Wi Shop <span className="text-xs sm:text-sm font-normal not-italic bg-gray-800 px-2 py-0.5 rounded text-gray-300 ml-1">{role}</span>
          </h1>
          <button onClick={closeSidebar} className="lg:hidden text-gray-400 hover:text-white">
            <X className="w-5 h-5" />
          </button>
        </div>
        
        <nav className="flex-1 p-3 sm:p-4 flex flex-col gap-1 sm:gap-2">
          <Link to="/admin" onClick={closeSidebar} className="flex items-center gap-3 p-3 bg-primary/20 text-primary rounded-xl font-bold text-sm">
            <LayoutDashboard className="w-5 h-5" />
            Dashboard
          </Link>
          
          {role === 'ADMIN' && (
            <>
              <Link to="#" onClick={closeSidebar} className="flex items-center gap-3 p-3 text-gray-400 hover:text-white hover:bg-gray-800 rounded-xl transition text-sm">
                <Users className="w-5 h-5" />
                Tài khoản User
              </Link>
              <Link to="#" onClick={closeSidebar} className="flex items-center gap-3 p-3 text-gray-400 hover:text-white hover:bg-gray-800 rounded-xl transition text-sm">
                <UserCog className="w-5 h-5" />
                Quản lý Phân quyền
              </Link>
            </>
          )}

          <Link to="#" onClick={closeSidebar} className="flex items-center gap-3 p-3 text-gray-400 hover:text-white hover:bg-gray-800 rounded-xl transition text-sm">
            <Package className="w-5 h-5" />
            Giỏ Hàng / Sản phẩm
          </Link>
          <Link to="#" onClick={closeSidebar} className="flex items-center gap-3 p-3 text-gray-400 hover:text-white hover:bg-gray-800 rounded-xl transition text-sm">
            <ShoppingCart className="w-5 h-5" />
            Đơn hàng
          </Link>
        </nav>
        
        <div className="p-3 sm:p-4 border-t border-gray-800">
          <button onClick={handleLogout} className="flex items-center gap-3 p-3 w-full text-red-400 hover:bg-red-500/10 rounded-xl transition text-sm">
            <LogOut className="w-5 h-5" />
            Đăng xuất
          </button>
        </div>
      </aside>

      {/* Main Content Area */}
      <main className="flex-1 flex flex-col min-h-screen w-full lg:w-auto">
        {/* Top Header */}
        <header className="h-14 sm:h-16 bg-white shadow-sm flex items-center justify-between px-4 sm:px-6 lg:px-8 z-10 sticky top-0">
          <div className="flex items-center gap-3">
            <button onClick={() => setSidebarOpen(true)} className="lg:hidden text-gray-600 hover:text-dark">
              <Menu className="w-6 h-6" />
            </button>
            <div className="font-semibold text-gray-600 text-sm sm:text-base">Hệ thống quản trị Backend</div>
          </div>
          <div className="flex items-center gap-3 sm:gap-4">
            <div className="w-8 h-8 bg-gray-200 rounded-full flex items-center justify-center font-bold text-gray-500 text-sm">
              {role.charAt(0)}
            </div>
          </div>
        </header>

        {/* Content scrolling area */}
        <div className="flex-1 overflow-auto p-4 sm:p-6 lg:p-8">
          <Outlet />
        </div>
      </main>
    </div>
  );
};

export default AdminLayout;
