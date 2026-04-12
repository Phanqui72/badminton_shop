import { Link, useLocation } from 'react-router-dom';
import { Home, Search, ShoppingCart, User, Tag } from 'lucide-react';
import clsx from 'clsx';

const Navbar = () => {
  const location = useLocation();
  const path = location.pathname;

  const isActive = (p: string) => path === p;

  return (
    <nav className="fixed bottom-0 left-0 right-0 z-50 bg-white/90 backdrop-blur-lg border-t border-gray-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-2 sm:py-3 flex justify-around sm:justify-center sm:gap-16 items-center text-xs font-medium text-gray-500">
        <Link to="/" className={clsx("flex flex-col items-center gap-0.5 sm:gap-1 transition-colors", isActive('/') ? "text-primary" : "hover:text-gray-700")}>
          <Home className="w-5 h-5 sm:w-6 sm:h-6" />
          <span className="text-[10px] sm:text-xs">HOME</span>
        </Link>
        <Link to="/products" className={clsx("flex flex-col items-center gap-0.5 sm:gap-1 transition-colors", isActive('/products') ? "text-primary" : "hover:text-gray-700")}>
          <Search className="w-5 h-5 sm:w-6 sm:h-6" />
          <span className="text-[10px] sm:text-xs">PRODUCTS</span>
        </Link>
        <Link to="/promotions" className={clsx("flex flex-col items-center gap-0.5 sm:gap-1 transition-colors", isActive('/promotions') ? "text-primary" : "hover:text-gray-700")}>
          <Tag className="w-5 h-5 sm:w-6 sm:h-6" />
          <span className="text-[10px] sm:text-xs">PROMOS</span>
        </Link>
        <Link to="/cart" className={clsx("flex flex-col items-center gap-0.5 sm:gap-1 relative transition-colors", isActive('/cart') ? "text-primary" : "hover:text-gray-700")}>
          <ShoppingCart className="w-5 h-5 sm:w-6 sm:h-6" />
          <span className="absolute -top-1 right-0 sm:-right-1 bg-red-500 text-white text-[10px] rounded-full w-4 h-4 flex items-center justify-center">3</span>
          <span className="text-[10px] sm:text-xs">CART</span>
        </Link>
        <Link to="/profile" className={clsx("flex flex-col items-center gap-0.5 sm:gap-1 transition-colors", isActive('/profile') ? "text-primary" : "hover:text-gray-700")}>
          <User className="w-5 h-5 sm:w-6 sm:h-6" />
          <span className="text-[10px] sm:text-xs">PROFILE</span>
        </Link>
      </div>
    </nav>
  );
};

export default Navbar;
