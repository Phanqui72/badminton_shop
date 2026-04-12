import { ShoppingCart } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import api from '../api/axios';

const categories = [
  { name: 'Vợt Cầu Lông', img: 'https://cdn.shopvnb.com/uploads/images/vot-cau-long-the-3rd-game-duora-100z-trang.webp' },
  { name: 'Giày Thi Đấu', img: 'https://lh3.googleusercontent.com/aida-public/AB6AXuD88yNxkdGnMVtKeN3LJzDbFUcfJL8DY2L7kJel_Ld1tHqk5ruvwBNbUphBUa0YvlOAi5UYf1wz2LxosYjpZvq5PI8XYeKJid6-geQpYuE7aL4u6c_k9QrZiQPojUbeOs2PumlmVYXW5pAqAGUAm6Vf38MCnyOt1G_zFfmtFsF0GbpUfuxXFGCmta5w8ff2LYHGTBdwyIly4yVQU2ajZAHOIaqcExlrRzAHuHnkV5WTTqLrfW_2yWaLYIVtpuCSC5T-AkD3RYWB8Dw' },
  { name: 'Quả Cầu Lông', img: 'https://tse2.mm.bing.net/th/id/OIP.P23xeMbIzoRRzNimvdQwEgAAAA?pid=Api&P=0&h=180' },
  { name: 'Trang Phục', img: 'https://votcaulongshop.vn/wp-content/uploads/2023/10/ao-cau-long-yonex-2316-trang-xanh.jpg' },
];

const formatMoney = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

const Home = () => {
  const [products, setProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await api.get('/v1/product/list?page=0&size=10');
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
    <div className="pb-20 sm:pb-24 overflow-y-auto min-h-screen">
      {/* Header Banner */}
      <div className="bg-secondary p-4 sm:p-6 lg:p-10 rounded-br-[40px]">
        <div className="max-w-6xl mx-auto">
          <div className="flex justify-between items-center mb-6 sm:mb-8">
            <div className="text-xl sm:text-2xl font-bold italic text-primary">Wi Shop</div>
            <div className="relative cursor-pointer">
              <Link to="/cart">
                <ShoppingCart className="w-6 h-6 text-dark" />
                <span className="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] px-1 rounded-full">0</span>
              </Link>
            </div>
          </div>
          
          <div className="flex flex-col lg:flex-row lg:items-center lg:gap-12">
            <div className="lg:flex-1">
              <h1 className="text-3xl sm:text-4xl lg:text-5xl xl:text-6xl font-extrabold text-dark leading-tight mb-4 tracking-tight">
                Mua sắm thông<br className="sm:hidden" /> minh <span className="text-primary">— Giá tốt</span> mỗi<br className="sm:hidden" /> ngày
              </h1>
              <p className="text-sm sm:text-base text-gray-700 mb-6 max-w-xl">
                Hàng chất lượng, giao nhanh, ưu đãi mỗi ngày. Nâng tầm cuộc chơi với trang thiết bị cầu lông chuyên nghiệp từ Wi Shop.
              </p>
              
              <div className="flex gap-3">
                <button className="bg-primary text-white px-5 sm:px-8 py-2.5 sm:py-3 rounded-md font-bold text-sm sm:text-base shadow-lg hover:bg-[#a63a00] transition">
                  Mua ngay →
                </button>
                <button className="bg-white text-dark px-5 sm:px-8 py-2.5 sm:py-3 rounded-md font-medium text-sm sm:text-base shadow hover:bg-gray-50 transition">
                  Xem sản phẩm
                </button>
              </div>
            </div>
            
            <div className="mt-8 lg:mt-0 flex justify-center lg:flex-1">
              <img src="https://phuquisport.com/uploads/source/all/banner/z6592640473638-d2209ef8bdb171071b4bc8849c9f66c0.jpg" alt="Banner" className="w-full sm:w-[80%] lg:w-full max-w-lg rounded-xl shadow-xl border-4 border-white" />
            </div>
          </div>
        </div>
      </div>

      {/* Categories */}
      <div className="px-4 sm:px-6 lg:px-8 mt-8 mb-6 max-w-6xl mx-auto">
        <div className="flex justify-between items-end mb-4">
          <div>
            <h2 className="text-lg sm:text-xl font-bold text-dark">Danh mục sản phẩm</h2>
            <p className="text-xs sm:text-sm text-gray-500">Khám phá bộ sưu tập chuyên dụng cho bộ môn cầu lông</p>
          </div>
          <Link to="/categories" className="text-primary text-sm font-medium hover:underline hidden sm:inline">Tất cả danh mục →</Link>
        </div>
        
        <div className="grid grid-cols-4 sm:grid-cols-4 md:grid-cols-4 lg:grid-cols-4 gap-3 sm:gap-6">
          {categories.map((cat, i) => (
            <div key={i} className="flex flex-col items-center cursor-pointer group">
              <div className="w-14 h-14 sm:w-20 sm:h-20 lg:w-24 lg:h-24 rounded-2xl bg-white shadow-sm flex items-center justify-center p-2 sm:p-3 mb-2 group-hover:shadow-md transition">
                <img src={cat.img} alt={cat.name} className="w-full h-full object-contain" />
              </div>
              <span className="text-[10px] sm:text-xs lg:text-sm font-semibold text-center leading-tight truncate w-full">{cat.name}</span>
            </div>
          ))}
        </div>
      </div>

      {/* Featured Products */}
      <div className="px-4 sm:px-6 lg:px-8 mb-8 mt-10 max-w-6xl mx-auto">
        <h2 className="text-xl sm:text-2xl font-black text-center text-dark mb-6">Sản phẩm nổi bật</h2>
        
        {loading ? (
           <div className="text-center text-gray-500 py-10">Đang tải sản phẩm...</div>
        ) : (
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-3 sm:gap-4 lg:gap-6">
            {products.length > 0 ? products.map((p) => (
              <Link to={`/product/${p.id}`} key={p.id} className="bg-white rounded-2xl p-3 shadow-sm border border-gray-100 relative group block hover:shadow-lg transition-shadow">
                {p.tag && (
                  <div className="absolute top-3 left-3 z-10 bg-primary text-white text-[10px] font-bold px-2 py-0.5 rounded-full uppercase">
                    {p.tag}
                  </div>
                )}
                <div className="w-full aspect-square bg-gray-100 rounded-xl mb-3 overflow-hidden relative">
                  <img src={p.imagePath || 'https://via.placeholder.com/200x200?text=' + encodeURIComponent(p.name || 'Product')} alt={p.name} className="w-full h-full object-cover group-hover:scale-105 transition duration-300" />
                  <button className="absolute bottom-2 right-2 bg-white p-1.5 rounded-full shadow-md hover:bg-primary hover:text-white transition group-hover:scale-110" onClick={(e) => { e.preventDefault(); /* Add to cart */ }}>
                    <ShoppingCart className="w-4 h-4" />
                  </button>
                </div>
                <p className="text-[10px] sm:text-xs text-gray-400 uppercase tracking-wider">{p.category?.name || p.brand || 'Dụng cụ'}</p>
                <h3 className="font-bold text-xs sm:text-sm text-dark leading-tight mt-1 mb-2 line-clamp-2">{p.name}</h3>
                <div className="flex items-end gap-2">
                  <span className="font-extrabold text-sm sm:text-base text-primary">{formatMoney(p.price || 0)}</span>
                  {p.oldPrice && <span className="text-[10px] text-gray-400 line-through mb-0.5">{formatMoney(p.oldPrice)}</span>}
                </div>
              </Link>
            )) : (
               <div className="text-center col-span-full text-gray-500 py-6">Chưa có sản phẩm nào.</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
