import { Tag, Search, ArrowLeft, Clock, Copy, CheckCircle } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useState } from 'react';

const Promotions = () => {
  const [copied, setCopied] = useState<number | null>(null);

  const promos = [
    { id: 1, code: 'WISHOP50', desc: 'Giảm 50K cho đơn từ 500K', exp: '31/12/2026', type: 'Giảm giá trực tiếp' },
    { id: 2, code: 'FREESHIP', desc: 'Miễn phí vận chuyển đơn từ 1TR', exp: '30/11/2026', type: 'Vận chuyển' },
    { id: 3, code: 'VOTXIN10', desc: 'Giảm 10% khi mua vợt Yonex', exp: '15/10/2026', type: 'Khuyến mãi ngành hàng' },
  ];

  const handleCopy = (id: number, code: string) => {
    navigator.clipboard.writeText(code);
    setCopied(id);
    setTimeout(() => setCopied(null), 2000);
  };

  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50 flex flex-col">
      <div className="bg-white px-4 sm:px-6 lg:px-8 py-4 flex items-center shadow-sm z-10 sticky top-0 gap-3 sm:gap-4">
        <Link to="/" className="text-dark">
            <ArrowLeft className="w-6 h-6" />
        </Link>
        <span className="text-lg sm:text-xl font-bold text-dark tracking-tight">Khuyến Mãi & Ưu Đãi</span>
      </div>

      {/* Search Promo */}
      <div className="p-3 sm:p-4 bg-white border-b border-gray-100 mb-2">
         <div className="relative max-w-4xl mx-auto">
             <input type="text" placeholder="Nhập mã khuyến mãi của bạn..." className="w-full bg-gray-50 rounded-xl px-4 py-2.5 sm:py-3 text-sm focus:outline-none focus:ring-2 focus:ring-primary/20 transition pr-24" />
             <button className="absolute right-1 top-1 bottom-1 bg-dark text-white px-3 sm:px-4 rounded-lg text-xs font-bold hover:bg-black transition">
                Áp dụng
             </button>
         </div>
      </div>

      <div className="p-3 sm:p-4 lg:p-6 max-w-4xl mx-auto w-full flex flex-col gap-3 sm:gap-4">
        {promos.map((promo) => (
           <div key={promo.id} className="bg-white rounded-[20px] sm:rounded-[24px] p-4 sm:p-5 shadow-sm border border-gray-100 flex relative overflow-hidden">
             
             {/* Left color bar */}
             <div className="absolute left-0 top-0 bottom-0 w-1.5 sm:w-2 bg-primary"></div>
             
             <div className="w-12 h-12 sm:w-16 sm:h-16 bg-primary/10 rounded-xl flex items-center justify-center text-primary flex-shrink-0 mr-3 sm:mr-4">
                <Tag className="w-6 h-6 sm:w-8 sm:h-8" />
             </div>
             
             <div className="flex-grow min-w-0">
               <h4 className="font-bold text-dark text-sm leading-tight flex items-center gap-2">
                 {promo.desc}
               </h4>
               <div className="text-[10px] sm:text-xs text-gray-500 mt-1 mb-2 sm:mb-3 flex items-center gap-1">
                 <Clock className="w-3 h-3 flex-shrink-0" /> HSD: {promo.exp}
               </div>

               <div className="flex items-center justify-between gap-2 flex-wrap sm:flex-nowrap">
                  <div className="border border-dashed border-primary bg-primary/5 text-primary font-black px-3 py-1 rounded w-fit text-xs sm:text-sm">
                    {promo.code}
                  </div>
                  <button 
                     onClick={() => handleCopy(promo.id, promo.code)}
                     className={`flex items-center gap-1 px-2.5 sm:px-3 py-1.5 rounded-lg text-xs font-bold transition flex-shrink-0 ${copied === promo.id ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-dark hover:bg-gray-200'}`}
                  >
                     {copied === promo.id ? <><CheckCircle className="w-3 h-3"/> Đã chép</> : <><Copy className="w-3 h-3"/> Copy mã</>}
                  </button>
               </div>
             </div>
           </div>
        ))}
      </div>
    </div>
  );
};

export default Promotions;
