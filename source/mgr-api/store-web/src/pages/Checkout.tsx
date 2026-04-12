import { useState } from 'react';
import { Menu, Search, MapPin, CreditCard, Wallet, Landmark, CheckCircle, Shield, ShoppingCart, Truck } from 'lucide-react';
import { Link } from 'react-router-dom';

const Checkout = () => {
  const [paymentMethod, setPaymentMethod] = useState('cod');

  return (
    <div className="pb-20 sm:pb-24 min-h-screen bg-gray-50 flex flex-col">
      {/* Header */}
      <div className="bg-gray-50 px-4 sm:px-6 lg:px-8 py-4 flex justify-between items-center z-10 sticky top-0">
        <Menu className="w-6 h-6 text-gray-500 lg:hidden" />
        <div className="text-xl font-black italic text-primary tracking-tighter">Wi Shop</div>
        <div className="relative">
          <Search className="w-6 h-6 text-dark" />
        </div>
      </div>

      {/* Progress */}
      <div className="px-6 sm:px-8 mt-2 mb-6 flex justify-between relative max-w-md mx-auto w-full">
        <div className="absolute top-1/2 left-10 right-10 h-[2px] bg-gray-200 -z-10 -translate-y-1/2"></div>
        
        <div className="flex flex-col items-center gap-2">
          <div className="w-6 h-6 rounded-full bg-[#007066] text-white flex items-center justify-center text-[10px] font-bold z-10">1</div>
          <span className="text-[10px] font-bold text-[#007066]">Shipping</span>
        </div>
        
        <div className="flex flex-col items-center gap-2">
          <div className="w-6 h-6 rounded-full bg-primary text-white flex items-center justify-center text-[10px] font-bold z-10 shadow-md shadow-primary/30">2</div>
          <span className="text-[10px] font-bold text-primary">Payment</span>
        </div>
        
        <div className="flex flex-col items-center gap-2">
          <div className="w-6 h-6 rounded-full bg-gray-200 text-gray-500 flex items-center justify-center text-[10px] font-bold z-10">3</div>
          <span className="text-[10px] font-medium text-gray-500">Review</span>
        </div>
      </div>

      {/* Main Content: 2 columns on desktop */}
      <div className="px-4 sm:px-6 lg:px-8 max-w-6xl mx-auto w-full flex flex-col lg:flex-row lg:gap-8">
        {/* Left Column: Shipping + Payment Method */}
        <div className="lg:flex-1">
          {/* Shipping Address */}
          <div className="bg-white p-4 sm:p-5 rounded-2xl sm:rounded-3xl shadow-sm border border-gray-100 mb-6">
            <div className="flex justify-between items-center mb-4">
              <h2 className="font-bold text-base sm:text-lg text-dark">Shipping Address</h2>
              <MapPin className="w-5 h-5 text-[#007066]" />
            </div>
            
            <div className="space-y-3">
              <div>
                <label className="text-[10px] font-bold text-gray-400 uppercase tracking-widest block mb-1">Full Name</label>
                <input type="text" value="Nguyen Van A" readOnly className="w-full bg-gray-50 rounded-xl px-4 py-2.5 sm:py-3 text-sm text-dark outline-none" />
              </div>
              <div>
                <label className="text-[10px] font-bold text-gray-400 uppercase tracking-widest block mb-1">Phone Number</label>
                <input type="text" value="090 123 4567" readOnly className="w-full bg-gray-50 rounded-xl px-4 py-2.5 sm:py-3 text-sm text-dark outline-none" />
              </div>
              <div>
                <label className="text-[10px] font-bold text-gray-400 uppercase tracking-widest block mb-1">Address Detail</label>
                <input type="text" value="123 Le Loi, District 1, HCMC" readOnly className="w-full bg-gray-50 rounded-xl px-4 py-2.5 sm:py-3 text-sm text-dark outline-none" />
              </div>
            </div>
          </div>

          {/* Payment Method */}
          <h2 className="font-bold text-lg sm:text-xl text-dark mb-4">Payment Method</h2>
          
          <div className="space-y-3 mb-6">
            <label className={`flex items-center p-3 sm:p-4 rounded-xl sm:rounded-2xl cursor-pointer transition shadow-sm relative ${paymentMethod === 'cod' ? 'border-2 border-primary bg-white' : 'border border-gray-100 bg-gray-50 hover:bg-gray-100'}`} onClick={() => setPaymentMethod('cod')}>
              <Truck className={`w-5 h-5 sm:w-6 sm:h-6 mr-3 sm:mr-4 flex-shrink-0 ${paymentMethod === 'cod' ? 'text-primary' : 'text-gray-600'}`} />
              <span className={`font-bold text-sm ${paymentMethod === 'cod' ? 'text-dark' : 'text-gray-600'}`}>Thanh toán khi nhận hàng (COD)</span>
              {paymentMethod === 'cod' && (
                <div className="ml-auto w-5 h-5 rounded-full bg-primary flex items-center justify-center flex-shrink-0">
                  <CheckCircle className="w-3 h-3 text-white" />
                </div>
              )}
            </label>

            <label className={`flex items-center p-3 sm:p-4 rounded-xl sm:rounded-2xl cursor-pointer transition shadow-sm relative ${paymentMethod === 'credit' ? 'border-2 border-primary bg-white' : 'border border-gray-100 bg-gray-50 hover:bg-gray-100'}`} onClick={() => setPaymentMethod('credit')}>
              <CreditCard className={`w-5 h-5 sm:w-6 sm:h-6 mr-3 sm:mr-4 flex-shrink-0 ${paymentMethod === 'credit' ? 'text-primary' : 'text-gray-600'}`} />
              <span className={`font-bold text-sm ${paymentMethod === 'credit' ? 'text-dark' : 'text-gray-600'}`}>Thẻ tín dụng / Ghi nợ</span>
              {paymentMethod === 'credit' && (
                <div className="ml-auto w-5 h-5 rounded-full bg-primary flex items-center justify-center flex-shrink-0">
                  <CheckCircle className="w-3 h-3 text-white" />
                </div>
              )}
            </label>
            
            <label className={`flex items-center p-3 sm:p-4 rounded-xl sm:rounded-2xl cursor-pointer transition shadow-sm relative ${paymentMethod === 'vnpay' ? 'border-2 border-primary bg-white' : 'border border-gray-100 bg-gray-50 hover:bg-gray-100'}`} onClick={() => setPaymentMethod('vnpay')}>
              <Wallet className={`w-5 h-5 sm:w-6 sm:h-6 mr-3 sm:mr-4 flex-shrink-0 ${paymentMethod === 'vnpay' ? 'text-primary' : 'text-gray-600'}`} />
              <span className={`font-bold text-sm ${paymentMethod === 'vnpay' ? 'text-dark' : 'text-gray-600'}`}>Ví VNPay</span>
              {paymentMethod === 'vnpay' && (
                <div className="ml-auto w-5 h-5 rounded-full bg-primary flex items-center justify-center flex-shrink-0">
                  <CheckCircle className="w-3 h-3 text-white" />
                </div>
              )}
            </label>
          </div>

          {/* Credit Card Details */}
          {paymentMethod === 'credit' && (
            <div className="bg-gradient-to-br from-gray-50 to-red-50 p-4 sm:p-5 rounded-2xl sm:rounded-3xl border border-gray-100 mb-8 relative overflow-hidden">
              <div className="absolute -right-8 -top-8 w-32 h-32 bg-primary/5 rounded-full blur-2xl"></div>
              
              <div className="flex justify-between items-start mb-6">
                <label className="text-[10px] font-bold text-primary uppercase tracking-widest block">Card Number</label>
                <div className="flex gap-1">
                  <div className="w-8 h-5 bg-gray-200 rounded"></div>
                  <div className="w-8 h-5 bg-gray-200 rounded"></div>
                </div>
              </div>
              
              <div className="text-lg sm:text-xl font-bold tracking-widest text-dark mb-6">
                <span className="mr-2 sm:mr-3">****</span><span className="mr-2 sm:mr-3">****</span><span className="mr-2 sm:mr-3">****</span><span>4242</span>
              </div>
              
              <div className="flex justify-between">
                <div>
                  <label className="text-[8px] font-bold text-primary uppercase tracking-widest block mb-1">Card Holder</label>
                  <div className="text-xs font-bold text-gray-700">NGUYEN VAN A</div>
                </div>
                <div>
                  <label className="text-[8px] font-bold text-primary uppercase tracking-widest block mb-1">Expiry</label>
                  <div className="text-xs font-bold text-gray-700">MM/YY</div>
                </div>
                <div>
                  <label className="text-[8px] font-bold text-primary uppercase tracking-widest block mb-1">CVV</label>
                  <div className="text-xs font-bold text-gray-700">***</div>
                </div>
              </div>
            </div>
          )}
        </div>

        {/* Right Column: Order Summary */}
        <div className="lg:w-96 lg:sticky lg:top-24 lg:self-start">
          <div className="bg-white rounded-[24px] sm:rounded-[32px] p-5 sm:p-6 shadow-xl border border-gray-100 mb-6">
            <h2 className="text-lg font-bold text-dark mb-4">Order Summary</h2>
            
            <div className="space-y-3 mb-6 border-b border-gray-100 pb-6 mt-4">
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Subtotal (3 items)</span>
                <span className="font-semibold text-dark">2,450,000₫</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Shipping Fee</span>
                <span className="font-semibold text-[#007066]">Free</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Discount</span>
                <span className="font-semibold text-primary">-150,000₫</span>
              </div>
            </div>

            <div className="flex justify-between items-end mb-6">
              <span className="text-base font-bold text-dark">Total</span>
              <div className="text-right">
                <span className="text-xl sm:text-2xl font-black text-primary block leading-none mb-1">2,300,000₫</span>
                <span className="text-[9px] text-gray-400 tracking-wider">INCLUDING ALL TAXES</span>
              </div>
            </div>

            <button className="w-full bg-primary hover:bg-primary-hover text-white font-bold py-3.5 sm:py-4 rounded-xl shadow-lg shadow-primary/30 transition mb-6">
              Complete Order
            </button>
            
            <div className="flex justify-around px-2">
              <div className="flex flex-col items-center gap-1">
                <div className="w-8 h-8 rounded-full bg-gray-50 flex items-center justify-center text-[#007066]">
                  <Shield className="w-4 h-4" />
                </div>
                <span className="text-[8px] font-bold text-gray-400 capitalize text-center">Secure Payment</span>
              </div>
              <div className="flex flex-col items-center gap-1">
                <div className="w-8 h-8 rounded-full bg-gray-50 flex items-center justify-center text-[#007066]">
                  <CheckCircle className="w-4 h-4" />
                </div>
                <span className="text-[8px] font-bold text-gray-400 capitalize text-center">Money back guarantee</span>
              </div>
            </div>
          </div>
          
          {/* Support Card */}
          <div className="bg-gray-50 rounded-2xl p-3 sm:p-4 flex gap-3 items-start border border-gray-100 mb-6 font-sans">
            <ShoppingCart className="w-5 h-5 text-gray-400 flex-shrink-0 mt-0.5" />
            <div>
              <h4 className="text-xs font-bold text-dark mb-1">Need assistance?</h4>
              <p className="text-[10px] text-gray-500 leading-relaxed">Our concierge is available 24/7 to help with your purchase.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Checkout;
