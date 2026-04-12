import { useState, useEffect } from 'react';
import { Search, ShoppingCart, Filter } from 'lucide-react';
import { Link } from 'react-router-dom';
import api from '../api/axios';

const formatMoney = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

const Products = () => {
  const [products, setProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Fetch products list on mount
    const fetchProducts = async () => {
      try {
        const response = await api.get('/v1/product/list?page=0&size=20');
        if (response.data.result) {
          setProducts(response.data.data.content);
        }
      } catch (error) {
        console.error("Failed to fetch products:", error);
      } finally {
        setLoading(false);
      }
    };
    fetchProducts();
  }, []);

  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50">
      {/* Header */}
      <div className="bg-white px-4 sm:px-6 lg:px-8 py-4 flex flex-col shadow-sm gap-3 sm:gap-4 z-10 sticky top-0">
        <div className="max-w-6xl mx-auto w-full flex flex-col gap-3 sm:gap-4">
          <div className="flex justify-between items-center">
            <div className="text-xl font-black italic text-primary tracking-tighter">Wi Shop</div>
            <div className="relative">
              <Link to="/cart">
                <ShoppingCart className="w-6 h-6 text-dark" />
                <span className="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] px-1 rounded-full">0</span>
              </Link>
            </div>
          </div>
          
          {/* Search Bar */}
          <div className="flex gap-2">
             <div className="relative flex-grow">
                <Search className="w-5 h-5 text-gray-400 absolute left-3 top-1/2 -translate-y-1/2" />
                <input 
                   type="text" 
                   placeholder="Tìm kiếm dụng cụ cầu lông..." 
                   className="w-full bg-gray-100 rounded-xl pl-10 pr-4 py-2.5 sm:py-3 text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition"
                />
             </div>
             <button className="w-10 h-10 sm:w-12 sm:h-12 bg-gray-100 rounded-xl flex items-center justify-center text-dark hover:bg-gray-200 transition flex-shrink-0">
                <Filter className="w-5 h-5" />
             </button>
          </div>
        </div>
      </div>

      {/* Grid */}
      <div className="p-3 sm:p-4 lg:p-6 mt-2 max-w-6xl mx-auto">
         {loading ? (
             <div className="flex justify-center py-10 text-gray-500">Đang tải dữ liệu...</div>
         ) : (
            <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-3 sm:gap-4 lg:gap-5">
              {products.map((p) => (
                <Link to={`/product/${p.id}`} key={p.id} className="bg-white rounded-2xl p-2.5 sm:p-3 shadow-sm border border-gray-100 relative group flex flex-col hover:shadow-lg transition-shadow">
                  {p.tag && (
                    <div className="absolute top-3 left-3 z-10 bg-secondary text-dark shadow-sm text-[9px] font-bold px-2 py-0.5 rounded-full uppercase">
                      {p.tag}
                    </div>
                  )}
                  <div className="w-full aspect-square bg-gray-100 rounded-xl mb-2 sm:mb-3 overflow-hidden mt-1">
                    <img src={p.imagePath || 'https://via.placeholder.com/200x200?text=' + encodeURIComponent(p.name || 'Product')} alt={p.name} className="w-full h-full object-cover group-hover:scale-105 transition duration-300" />
                  </div>
                  <h3 className="font-bold text-xs sm:text-sm text-dark leading-snug mb-1 line-clamp-2">{p.name}</h3>
                  <div className="mt-auto pt-2">
                     <span className="font-extrabold text-primary text-sm sm:text-base">{formatMoney(p.price || 0)}</span>
                  </div>
                </Link>
              ))}
            </div>
         )}
      </div>
    </div>
  );
};

export default Products;
