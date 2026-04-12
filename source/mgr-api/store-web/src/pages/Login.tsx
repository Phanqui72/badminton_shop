import { useState } from 'react';
import { Eye, EyeOff, ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const Login = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
        // Spring Boot của bạn có JsonToUrlEncodedAuthenticationFilter nên ta có thể gửi thẳng JSON
        const payload = {
            username: email,
            password: password,
            grant_type: 'user'
        };
        
        const response = await api.post('/api/token', payload, {
            headers: {
                'Content-Type': 'application/json',
                // Basic Auth header for abc_client:abc123
                'Authorization': 'Basic YWJjX2NsaWVudDphYmMxMjM=' 
            }
        });
        
        // Tùy theo response structure. Có thể là response.data.access_token HOẶC response.data.data.token
        const token = response.data.access_token || response.data.token;
        if(token) {
           localStorage.setItem('token', token);
           navigate('/profile');
        } else {
           alert("Token không được trả về hợp lệ!");
        }
    } catch (error: any) {
        alert(error.response?.data?.message || "Đăng nhập thất bại. Sai thông tin hoặc lỗi máy chủ!");
    } finally {
        setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#a1f1e1]/30 to-gray-50 p-4 sm:p-6 flex flex-col justify-center pb-20 sm:pb-32 relative">
      <div className="absolute top-4 sm:top-6 left-4 sm:left-6">
        <Link to="/" className="w-10 h-10 bg-white rounded-full flex items-center justify-center shadow-sm text-dark">
            <ArrowLeft className="w-5 h-5" />
        </Link>
      </div>

      <div className="max-w-md mx-auto w-full">
        <div className="text-center mb-8 sm:mb-10">
          <h1 className="text-4xl sm:text-5xl font-black italic text-primary tracking-tighter">Wi Shop</h1>
          <p className="text-xs sm:text-sm font-semibold text-gray-500 uppercase tracking-widest mt-2">VÀO SÂN CÙNG ĐAM MÊ</p>
        </div>

        <div className="bg-white/70 backdrop-blur-md rounded-[24px] sm:rounded-[32px] p-5 sm:p-6 shadow-xl border border-white">
          <h2 className="text-xl sm:text-2xl font-bold text-dark mb-1">Đăng nhập</h2>
          <p className="text-sm text-gray-500 mb-6">Chào mừng bạn quay trở lại với Wi Shop</p>

          <form className="space-y-4" onSubmit={handleLogin}>
            <div>
              <div className="relative">
                <input 
                  type="email" 
                  placeholder="Email" 
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="w-full bg-gray-50 border-none rounded-xl px-4 py-3.5 sm:py-4 text-sm focus:ring-2 focus:ring-primary/20 outline-none" 
                />
              </div>
            </div>
            
            <div>
              <div className="relative">
                <input 
                  type={showPassword ? "text" : "password"} 
                  placeholder="Mật khẩu" 
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                  className="w-full bg-gray-50 border-none rounded-xl px-4 py-3.5 sm:py-4 pr-12 text-sm focus:ring-2 focus:ring-primary/20 outline-none" 
                />
                <button 
                  type="button"
                  className="absolute right-4 top-1/2 -translate-y-1/2 text-dark/60 hover:text-dark"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                </button>
              </div>
              <div className="flex justify-end mt-2">
                <a href="#" className="text-sm font-semibold text-primary hover:underline">Quên mật khẩu?</a>
              </div>
            </div>

            <button type="submit" disabled={loading} className="w-full bg-primary hover:bg-[#a63a00] text-white font-bold py-3.5 sm:py-4 rounded-xl shadow-lg shadow-primary/30 mt-2 transition">
              {loading ? 'Đang xử lý...' : 'Đăng nhập'}
            </button>
          </form>

          <div className="relative flex items-center justify-center mt-6 sm:mt-8 mb-6">
            <div className="h-px w-full bg-gray-200"></div>
            <span className="absolute bg-transparent backdrop-blur-xl px-4 text-xs font-bold text-gray-500 uppercase tracking-wider">Hoặc tiếp tục với</span>
          </div>

          <div className="grid grid-cols-3 gap-3">
            <button className="flex items-center justify-center p-2.5 sm:p-3 rounded-xl bg-white border border-gray-100 shadow-sm hover:bg-gray-50 transition">
              <img src="https://upload.wikimedia.org/wikipedia/commons/5/53/Google_%22G%22_Logo.svg" alt="Google" className="w-5 h-5 sm:w-6 sm:h-6" />
            </button>
            <button className="flex items-center justify-center p-2.5 sm:p-3 rounded-xl bg-white border border-gray-100 shadow-sm hover:bg-gray-50 transition">
              <svg className="w-5 h-5 sm:w-6 sm:h-6 text-[#1877F2]" fill="currentColor" viewBox="0 0 24 24"><path d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.469h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.469h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z"/></svg>
            </button>
            <button className="flex items-center justify-center p-2.5 sm:p-3 rounded-xl bg-white border border-gray-100 shadow-sm hover:bg-gray-50 transition">
               <img src="https://upload.wikimedia.org/wikipedia/commons/f/fa/Apple_logo_black.svg" alt="Apple" className="w-4 h-4 sm:w-5 sm:h-5" />
            </button>
          </div>

          <div className="mt-6 sm:mt-8 text-center">
            <p className="text-sm text-gray-500 mb-3">Chưa có tài khoản?</p>
            <Link to="/register" className="block w-full text-center bg-secondary hover:bg-[#8ae0d0] text-dark font-bold py-3 sm:py-3.5 rounded-xl transition shadow-sm">
              Tạo tài khoản mới
            </Link>
          </div>
        </div>
        
        <div className="flex justify-center gap-4 sm:gap-6 mt-6 sm:mt-8 text-xs text-gray-500 font-medium flex-wrap">
          <Link to="#">Điều khoản sử dụng</Link>
          <Link to="#">Chính sách bảo mật</Link>
          <Link to="#">Trợ giúp</Link>
        </div>
      </div>
    </div>
  );
};

export default Login;
