import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';

const Layout = () => {
  return (
    <div className="flex flex-col min-h-screen font-sans bg-gray-50">
      <main className="flex-grow w-full max-w-7xl mx-auto bg-white shadow-xl min-h-screen relative overflow-hidden">
        <Outlet />
      </main>
      <Navbar />
    </div>
  );
};

export default Layout;
