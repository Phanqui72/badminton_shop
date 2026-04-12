import { useState } from 'react';
import { Eye, EyeOff, ArrowLeft } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api/axios';

const Register = () => {
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    password: '',
    confirmPassword: ''
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({...formData, [e.target.name]: e.target.value});
  };

  const handleRegister = async (e: React.FormEvent) => {
    e.preventDefault();
    if(formData.password !== formData.confirmPassword) {
      alert("Mật khẩu xác nhận không khớp!");
      return;
    }
    
    setLoading(true);
    try {
      // Assuming a generic registration endpoint like /v1/user/register or /v1/account/register
      const response = await api.post('/account/create', { // Update with exact backend register API
        username: formData.email,
        password: formData.password,
        fullName: formData.fullName,
        email: formData.email
      });
      if(response.data.result) {
        alert("Đăng ký thành công!");
        navigate('/login');
      } else {
        alert(response.data.message || "Đăng ký thất bại");
      }
    } catch (error) {
      alert("Đã xảy ra lỗi khi kết nối với server");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#a1f1e1]/30 to-gray-50 p-4 sm:p-6 flex flex-col pt-10 sm:pt-12 pb-20 sm:pb-24">
      <div className="max-w-md mx-auto w-full">
        <div className="mb-6 sm:mb-8 flex items-center">
          <Link to="/login" className="w-10 h-10 bg-white rounded-full flex items-center justify-center shadow-sm text-dark">
              <ArrowLeft className="w-5 h-5" />
          </Link>
        </div>

        <div className="mb-6 sm:mb-8">
          <h1 className="text-3xl sm:text-4xl font-black text-dark tracking-tighter">Đăng ký</h1>
          <p className="text-sm text-gray-500 mt-2">Tạo tài khoản để trải nghiệm Wi Shop ngay</p>
        </div>

        <div className="bg-white/70 backdrop-blur-md rounded-[24px] sm:rounded-[32px] p-5 sm:p-6 shadow-xl border border-white">
          <form className="space-y-4" onSubmit={handleRegister}>
            <div>
              <input 
                type="text" 
                name="fullName"
                placeholder="Họ và tên" 
                onChange={handleChange}
                required
                className="w-full bg-white border border-gray-100 rounded-xl px-4 py-3.5 sm:py-4 text-sm focus:ring-2 focus:ring-secondary outline-none shadow-sm" 
              />
            </div>
            <div>
              <input 
                type="email" 
                name="email"
                placeholder="Email" 
                onChange={handleChange}
                required
                className="w-full bg-white border border-gray-100 rounded-xl px-4 py-3.5 sm:py-4 text-sm focus:ring-2 focus:ring-secondary outline-none shadow-sm" 
              />
            </div>
            
            <div>
              <div className="relative">
                <input 
                  type={showPassword ? "text" : "password"} 
                  name="password"
                  placeholder="Mật khẩu" 
                  onChange={handleChange}
                  required
                  className="w-full bg-white border border-gray-100 rounded-xl px-4 py-3.5 sm:py-4 pr-12 text-sm focus:ring-2 focus:ring-secondary outline-none shadow-sm" 
                />
                <button 
                  type="button"
                  className="absolute right-4 top-1/2 -translate-y-1/2 text-dark/60"
                  onClick={() => setShowPassword(!showPassword)}
                >
                  {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                </button>
              </div>
            </div>
            
            <div>
              <input 
                type="password" 
                name="confirmPassword"
                placeholder="Xác nhận mật khẩu" 
                onChange={handleChange}
                required
                className="w-full bg-white border border-gray-100 rounded-xl px-4 py-3.5 sm:py-4 text-sm focus:ring-2 focus:ring-secondary outline-none shadow-sm" 
              />
            </div>

            <button type="submit" disabled={loading} className="w-full bg-secondary hover:bg-[#8ae0d0] text-dark font-bold py-3.5 sm:py-4 rounded-xl shadow-lg mt-4 transition">
              {loading ? "Đang xử lý..." : "Đăng ký tài khoản"}
            </button>
          </form>

          <div className="mt-6 text-center">
            <p className="text-sm text-gray-500">Đã có tài khoản? <Link to="/login" className="font-bold text-primary hover:underline">Đăng nhập</Link></p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
