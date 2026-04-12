import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Shield, Eye, EyeOff } from 'lucide-react';
import api from '../../api/axios';

const AdminLogin = () => {
  const navigate = useNavigate();
  const [roleMode, setRoleMode] = useState<'admin' | 'seller'>('admin');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
        const payload = {
            username: email,
            password: password,
            // Sử dụng cơ chế mapping grant_type tương ứng CustomTokenGranter của Backend
            grant_type: roleMode === 'admin' ? 'custom' : 'seller'
        };
        
        const response = await api.post('/api/token', payload, {
            headers: {
                'Content-Type': 'application/json',
                // Basic Auth bắt buộc cho client_id giống như user web
                'Authorization': 'Basic YWJjX2NsaWVudDphYmMxMjM=' 
            }
        });
        
        const token = response.data.access_token || response.data.token;
        if(token) {
           localStorage.setItem('admin_token', token);
           localStorage.setItem('admin_role', roleMode === 'admin' ? 'ADMIN' : 'SELLER');
           navigate('/admin');
        } else {
           alert("Lỗi: Token không thể khởi tạo!");
        }
    } catch (error: any) {
        alert(error.response?.data?.message || "Đăng nhập quản trị thất bại! Sai quyền hoặc tài khoản.");
    } finally {
        setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-dark flex items-center justify-center p-4">
      <div className="w-full max-w-md bg-white rounded-3xl shadow-2xl overflow-hidden">
        <div className="bg-gradient-to-r from-gray-900 to-gray-800 p-8 text-center relative overflow-hidden">
          <div className="absolute top-0 right-0 w-32 h-32 bg-primary blur-3xl opacity-20 -mr-10 -mt-10 rounded-full"></div>
          <Shield className="w-12 h-12 text-primary mx-auto mb-4 relative z-10" />
          <h1 className="text-3xl font-black text-white tracking-tighter relative z-10">Wi Shop <span className="text-primary italic">Portal</span></h1>
          <p className="text-gray-400 text-sm mt-2 relative z-10">Restricted access area</p>
        </div>

        <div className="p-8">
          <div className="flex bg-gray-100 rounded-xl p-1 mb-8">
            <button 
                className={`flex-1 py-2 text-sm font-bold rounded-lg transition ${roleMode === 'admin' ? 'bg-white text-dark shadow-sm' : 'text-gray-500 hover:text-gray-700'}`}
                onClick={() => setRoleMode('admin')}
            >
                Administrator
            </button>
            <button 
                className={`flex-1 py-2 text-sm font-bold rounded-lg transition ${roleMode === 'seller' ? 'bg-white text-dark shadow-sm' : 'text-gray-500 hover:text-gray-700'}`}
                onClick={() => setRoleMode('seller')}
            >
                NMS Seller
            </button>
          </div>

          <form onSubmit={handleLogin} className="space-y-4">
            <div>
              <label className="text-[10px] uppercase font-bold text-gray-500 tracking-wider">Tài khoản</label>
              <input 
                  type="text" 
                  autoFocus
                  required
                  value={email}
                  onChange={e => setEmail(e.target.value)}
                  className="w-full mt-1 border-b-2 border-gray-200 py-2 focus:outline-none focus:border-primary transition text-dark font-medium" 
                  placeholder={roleMode === 'admin' ? "admin@manager.com" : "seller@shop.com"}
              />
            </div>
            
            <div className="relative pt-2">
              <label className="text-[10px] uppercase font-bold text-gray-500 tracking-wider">Mật khẩu</label>
              <input 
                  type={showPassword ? "text" : "password"} 
                  required
                  value={password}
                  onChange={e => setPassword(e.target.value)}
                  className="w-full mt-1 border-b-2 border-gray-200 py-2 focus:outline-none focus:border-primary transition text-dark font-medium pr-10" 
                  placeholder="••••••••"
              />
              <button 
                  type="button" 
                  className="absolute right-0 bottom-2 text-gray-400 hover:text-dark"
                  onClick={() => setShowPassword(!showPassword)}
              >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
              </button>
            </div>

            <button disabled={loading} className="w-full bg-dark hover:bg-black text-white font-bold py-4 rounded-xl mt-8 shadow-xl shadow-dark/20 transition">
              {loading ? 'Authenticating...' : `Login as ${roleMode === 'admin' ? 'Admin' : 'Seller'}`}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AdminLogin;
