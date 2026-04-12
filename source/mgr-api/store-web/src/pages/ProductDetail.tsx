import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { ArrowLeft, ShoppingCart, Star, Shield, Truck } from 'lucide-react';
import api from '../api/axios';

const formatMoney = (amount: number) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
};

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await api.get(`/v1/product/get/${id}`);
        if (response.data.result) {
          setProduct(response.data.data);
        }
      } catch (error) {
        console.error("Failed to fetch product:", error);
      } finally {
        setLoading(false);
      }
    };
    if (id) fetchProduct();
  }, [id]);

  if (loading) {
    return <div className="min-h-screen flex items-center justify-center">Đang tải...</div>;
  }

  if (!product) {
    return <div className="min-h-screen flex flex-col items-center justify-center">
      <h2 className="text-xl font-bold">Không tìm thấy sản phẩm</h2>
      <Link to="/" className="text-primary mt-4 underline">Về trang chủ</Link>
    </div>;
  }

  return (
    <div className="pb-24 min-h-screen bg-gray-50 flex flex-col">
      {/* Header - floating */}
      <div className="absolute top-0 left-0 right-0 p-4 flex justify-between z-10 w-full max-w-5xl mx-auto">
        <Link to="/" className="w-10 h-10 bg-white/80 backdrop-blur rounded-full flex items-center justify-center shadow-sm">
          <ArrowLeft className="w-5 h-5 text-dark" />
        </Link>
        <Link to="/cart" className="w-10 h-10 bg-white/80 backdrop-blur rounded-full flex items-center justify-center shadow-sm relative">
          <ShoppingCart className="w-5 h-5 text-dark" />
          <span className="absolute -top-1 -right-1 bg-red-500 text-white text-[10px] w-4 h-4 flex items-center justify-center rounded-full">3</span>
        </Link>
      </div>

      {/* Content: stacked on mobile, side-by-side on desktop */}
      <div className="flex flex-col lg:flex-row lg:max-w-6xl lg:mx-auto lg:mt-6 lg:gap-10 lg:p-8">
        {/* Product Image */}
        <div className="w-full lg:w-1/2 lg:sticky lg:top-6 lg:self-start">
          <div className="w-full aspect-square bg-gray-100 rounded-b-[40px] lg:rounded-2xl overflow-hidden relative">
            <img 
              src={product.imagePath || 'https://via.placeholder.com/400x400?text=' + encodeURIComponent(product.name || 'Product')} 
              alt={product.name} 
              className="w-full h-full object-cover" 
            />
          </div>
        </div>

        {/* Product Info */}
        <div className="p-4 sm:p-6 lg:flex-1 lg:p-0">
          <div className="flex justify-between items-start mb-2">
            <h1 className="text-xl sm:text-2xl lg:text-3xl font-black text-dark leading-tight pr-4">{product.name}</h1>
          </div>
          
          <div className="flex items-center gap-4 mb-4">
            <span className="text-xl sm:text-2xl lg:text-3xl font-black text-primary">{formatMoney(product.price || 0)}</span>
            {product.oldPrice && <span className="text-sm text-gray-400 line-through">{formatMoney(product.oldPrice)}</span>}
          </div>

          <div className="flex items-center gap-2 mb-4 flex-wrap">
            {product.brand && <span className="bg-gray-100 text-gray-600 text-xs font-bold px-3 py-1 rounded-full">{product.brand}</span>}
            {product.color && <span className="bg-gray-100 text-gray-600 text-xs font-bold px-3 py-1 rounded-full">{product.color}</span>}
            {product.size && <span className="bg-gray-100 text-gray-600 text-xs font-bold px-3 py-1 rounded-full">Size: {product.size}</span>}
            {product.category?.name && <span className="bg-primary/10 text-primary text-xs font-bold px-3 py-1 rounded-full">{product.category.name}</span>}
          </div>

          <div className="flex items-center gap-2 mb-6 text-sm text-gray-600 flex-wrap">
            <div className="flex text-yellow-400">
              <Star className="w-4 h-4 fill-current" />
              <Star className="w-4 h-4 fill-current" />
              <Star className="w-4 h-4 fill-current" />
              <Star className="w-4 h-4 fill-current" />
              <Star className="w-4 h-4 fill-current opacity-50" />
            </div>
            <span>4.0 (120 đánh giá)</span>
            {product.stock != null && <span className="sm:ml-auto text-xs font-bold text-green-600">Còn {product.stock} hàng</span>}
          </div>

          <div className="grid grid-cols-2 gap-3 mb-6 border-y border-gray-200 py-4">
            <div className="flex items-center gap-2">
              <Shield className="w-5 h-5 text-[#007066]" />
              <span className="text-xs sm:text-sm font-medium">Bảo hành 90 ngày</span>
            </div>
            <div className="flex items-center gap-2">
              <Truck className="w-5 h-5 text-[#007066]" />
              <span className="text-xs sm:text-sm font-medium">Giao hàng 2h</span>
            </div>
          </div>

          <div className="mb-6">
            <h3 className="font-bold text-lg mb-2">Mô tả sản phẩm</h3>
            <p className="text-sm sm:text-base text-gray-600 leading-relaxed">
              {product.description || "Chưa có mô tả chi tiết cho sản phẩm này. Đây là các sản phẩm trang thiết bị cầu lông chuyên nghiệp cung cấp bởi Wi Shop với chất lượng đỉnh cao."}
            </p>
          </div>

          {/* Desktop CTA button (inline) */}
          <div className="hidden lg:block">
            <button className="w-full bg-primary hover:bg-primary-hover text-white font-bold py-4 rounded-xl shadow-lg transition text-lg">
              Thêm vào giỏ hàng
            </button>
          </div>
        </div>
      </div>

      {/* Mobile Bottom Bar */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 p-3 sm:p-4 flex gap-3 z-50 lg:hidden">
        <button className="flex-grow bg-primary hover:bg-primary-hover text-white font-bold py-3.5 sm:py-4 rounded-xl shadow-lg transition">
          Thêm vào giỏ hàng
        </button>
      </div>
    </div>
  );
};

export default ProductDetail;
