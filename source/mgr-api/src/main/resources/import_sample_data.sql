-- ==============================================================================
-- KỊCH BẢN THÊM DỮ LIỆU MẪU ĐỂ TEST HỆ THỐNG (BADMINTON SHOP)
-- Chạy script này vào Schema DB của bạn sau khi file Liquibase đã chạy xong.
-- Mật khẩu mặc định cho TẤT CẢ các tài khoản dưới đây là: 123456
-- ==============================================================================

-- 1. NATION (5 Quốc gia / Khu vực)
INSERT INTO db_mgr_nation (id, kind, name, post_code, parent_id, created_by, created_date, modified_by, modified_date, status) VALUES 
(1, 1, 'Vietnam', '70000', NULL, 'admin', NOW(), 'admin', NOW(), 1),
(2, 1, 'USA', '10001', NULL, 'admin', NOW(), 'admin', NOW(), 1),
(3, 1, 'Japan', '100-0001', NULL, 'admin', NOW(), 'admin', NOW(), 1),
(4, 1, 'China', '100000', NULL, 'admin', NOW(), 'admin', NOW(), 1),
(5, 1, 'Korea', '03000', NULL, 'admin', NOW(), 'admin', NOW(), 1);

-- 2. ACCOUNTS CHO ADMIM (Group ID: 1) - Tạo 5 Admins
-- (Password: 123456 -> đã mã hoá {bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS)
INSERT INTO db_mgr_account (id, kind, username, email, phone, password, full_name, is_super_admin, group_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(101, 1, 'admin1', 'admin1@gmail.com', '0123000101', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Sub Admin 1', 1, 1, 1, 'system', NOW(), 'system', NOW()),
(102, 1, 'admin2', 'admin2@gmail.com', '0123000102', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Sub Admin 2', 0, 1, 1, 'system', NOW(), 'system', NOW()),
(103, 1, 'admin3', 'admin3@gmail.com', '0123000103', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Sub Admin 3', 0, 1, 1, 'system', NOW(), 'system', NOW()),
(104, 1, 'admin4', 'admin4@gmail.com', '0123000104', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Sub Admin 4', 0, 1, 1, 'system', NOW(), 'system', NOW()),
(105, 1, 'admin5', 'admin5@gmail.com', '0123000105', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Sub Admin 5', 0, 1, 1, 'system', NOW(), 'system', NOW());

-- 3. ACCOUNTS CHO SELLER (Group ID: 6 theo schema mới) - Tạo 5 Sellers
INSERT INTO db_mgr_account (id, kind, username, email, phone, password, full_name, is_super_admin, group_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(201, 1, 'seller1', 'seller1@shop.com', '0912000201', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'NMS Seller 1', 0, 6, 1, 'system', NOW(), 'system', NOW()),
(202, 1, 'seller2', 'seller2@shop.com', '0912000202', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'NMS Seller 2', 0, 6, 1, 'system', NOW(), 'system', NOW()),
(203, 1, 'seller3', 'seller3@shop.com', '0912000203', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'NMS Seller 3', 0, 6, 1, 'system', NOW(), 'system', NOW()),
(204, 1, 'seller4', 'seller4@shop.com', '0912000204', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'NMS Seller 4', 0, 6, 1, 'system', NOW(), 'system', NOW()),
(205, 1, 'seller5', 'seller5@shop.com', '0912000205', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'NMS Seller 5', 0, 6, 1, 'system', NOW(), 'system', NOW());

-- 4. INSERT BẢNG db_mgr_seller mapping 1-1 với Account_ID
INSERT INTO db_mgr_seller (id, shop_name, shop_description, address_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(201, 'Tiến Đạt Sport', 'Chuyên Vợt Yonex Chính Hãng', NULL, 1, 'system', NOW(), 'system', NOW()),
(202, 'VNB Shop HCM', 'Phụ kiện cầu lông giá sỉ', NULL, 1, 'system', NOW(), 'system', NOW()),
(203, 'Linh Tâm Badminton', 'Giày và Quần áo Lining', NULL, 1, 'system', NOW(), 'system', NOW()),
(204, 'Mạnh Cường Pro', 'Căng cược chuyên nghiệp', NULL, 1, 'system', NOW(), 'system', NOW()),
(205, 'Bảo Anh Store', 'Cầu lông phong trào', NULL, 1, 'system', NOW(), 'system', NOW());

-- 5. ACCOUNTS CHO USER (Group ID: 5 theo schema mới) - Tạo 5 Users 
INSERT INTO db_mgr_account (id, kind, username, email, phone, password, full_name, is_super_admin, group_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(301, 1, 'customer1', 'customer1@gmail.com', '0988000301', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Customer VIP 1', 0, 5, 1, 'system', NOW(), 'system', NOW()),
(302, 1, 'customer2', 'customer2@gmail.com', '0988000302', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Customer 2', 0, 5, 1, 'system', NOW(), 'system', NOW()),
(303, 1, 'customer3', 'customer3@gmail.com', '0988000303', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Customer 3', 0, 5, 1, 'system', NOW(), 'system', NOW()),
(304, 1, 'customer4', 'customer4@gmail.com', '0988000304', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Customer 4', 0, 5, 1, 'system', NOW(), 'system', NOW()),
(305, 1, 'customer5', 'customer5@gmail.com', '0988000305', '{bcrypt}$2a$10$YMZFAA1FLQQzz29qLCtHG.8oBvq0g/6tpX0ODh269rckAU9lsgrrS', 'Customer 5', 0, 5, 1, 'system', NOW(), 'system', NOW());

INSERT INTO db_mgr_user (id, gender, date_of_birth, created_by, created_date, modified_by, modified_date, status) VALUES 
(301, 1, '1995-01-01', 'system', NOW(), 'system', NOW(), 1),
(302, 2, '1996-05-12', 'system', NOW(), 'system', NOW(), 1),
(303, 1, '2000-10-15', 'system', NOW(), 'system', NOW(), 1),
(304, 2, '2002-09-02', 'system', NOW(), 'system', NOW(), 1),
(305, 1, '1988-11-20', 'system', NOW(), 'system', NOW(), 1);


-- 6. CATEGORIES (5 Danh Mục)
INSERT INTO db_mgr_category (id, name, description, parent_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(1, 'Vợt Cầu Lông', 'Danh mục vợt chính hãng', NULL, 1, 'admin', NOW(), 'admin', NOW()),
(2, 'Giày Cầu Lông', 'Giày thể thao êm ái chống lật cổ chân', NULL, 1, 'admin', NOW(), 'admin', NOW()),
(3, 'Quần áo', 'Trang phục thi đấu thoáng mát', NULL, 1, 'admin', NOW(), 'admin', NOW()),
(4, 'Phụ kiện', 'Balo, quấn cán, phụ kiện lưới', NULL, 1, 'admin', NOW(), 'admin', NOW()),
(5, 'Quả Cầu Lông', 'Cầu lông các hãng Yonex, Victor', NULL, 1, 'admin', NOW(), 'admin', NOW());


-- 7. PRODUCTS (Sinh 30 sản phẩm chia đều cho 5 category và 5 seller)
-- Lặp lại 30 records
INSERT INTO db_mgr_product (id, name, brand, color, description, image_path, price, size, stock, category_id, seller_id, status, created_by, created_date, modified_by, modified_date) VALUES 
(1, 'Vợt Yonex Astrox 99 Pro', 'Yonex', 'Trắng Đen', 'Vợt tấn công uy lực dành cho người tay phải', 'https://via.placeholder.com/400x400', 3500000, '3U/4U', 50, 1, 201, 1, 'seller1', NOW(), 'seller1', NOW()),
(2, 'Vợt Victor Thruster K 9900', 'Victor', 'Xanh Biển', 'Sợi carbon đan chéo tăng cường độ cứng', 'https://via.placeholder.com/400x400', 2800000, '4U', 120, 1, 202, 1, 'seller2', NOW(), 'seller2', NOW()),
(3, 'Vợt Lining Aeronaut 9000C', 'Lining', 'Đỏ Đen', 'Vợt công thủ toàn diện', 'https://via.placeholder.com/400x400', 3200000, '3U', 30, 1, 203, 1, 'seller3', NOW(), 'seller3', NOW()),
(4, 'Vợt Mizuno Fortius 10 Quick', 'Mizuno', 'Vàng Đen', 'Vợt phản tạt nhanh nhẹn', 'https://via.placeholder.com/400x400', 2500000, '4U', 40, 1, 204, 1, 'seller4', NOW(), 'seller4', NOW()),
(5, 'Vợt Flypower Tornado 800', 'Flypower', 'Bạc', 'Dành cho người mới chơi', 'https://via.placeholder.com/400x400', 1200000, '4U', 200, 1, 205, 1, 'seller5', NOW(), 'seller5', NOW()),
(6, 'Vợt Yonex Nanoflare 1000Z', 'Yonex', 'Neon', 'Thế hệ Nano siêu nhẹ', 'https://via.placeholder.com/400x400', 4100000, '4U', 150, 1, 201, 1, 'seller1', NOW(), 'seller1', NOW()),

(7, 'Giày Yonex SHB 65Z3', 'Yonex', 'Trắng Xanh', 'Giày siêu êm bám sân tốt', 'https://via.placeholder.com/400x400', 2200000, '41,42,43', 50, 2, 202, 1, 'seller2', NOW(), 'seller2', NOW()),
(8, 'Giày Victor P9200II', 'Victor', 'Trắng', 'Bảo vệ gót chân hoàn hảo', 'https://via.placeholder.com/400x400', 1800000, '40,41,42', 20, 2, 203, 1, 'seller3', NOW(), 'seller3', NOW()),
(9, 'Giày Lining AYAR 025', 'Lining', 'Hồng', 'Màu sắc cực cháy cho nữ giới', 'https://via.placeholder.com/400x400', 1500000, '36,37,38', 12, 2, 204, 1, 'seller4', NOW(), 'seller4', NOW()),
(10, 'Giày Kawasaki K1B', 'Kawasaki', 'Cam', 'Bền bỉ trên mặt sân xi măng', 'https://via.placeholder.com/400x400', 800000, '39,40,41', 50, 2, 205, 1, 'seller5', NOW(), 'seller5', NOW()),
(11, 'Giày Mizuno Wave Claw 2', 'Mizuno', 'Xanh Neon', 'Công nghệ Wave đẩy chân nhanh', 'https://via.placeholder.com/400x400', 2300000, '42,43,44', 30, 2, 201, 1, 'seller1', NOW(), 'seller1', NOW()),
(12, 'Giày Kumpoo KH 318', 'Kumpoo', 'Đen', 'Giá rẻ dành cho hs-sv', 'https://via.placeholder.com/400x400', 550000, '40,41', 120, 2, 202, 1, 'seller2', NOW(), 'seller2', NOW()),

(13, 'Áo Yonex YNX-8080 Cộc Tay', 'Yonex', 'Đỏ Xanh', 'Mát mẻ thấm hút mồ hôi 100%', 'https://via.placeholder.com/400x400', 350000, 'M,L,XL', 500, 3, 203, 1, 'seller3', NOW(), 'seller3', NOW()),
(14, 'Quần Victor V-512', 'Victor', 'Đen', 'Kiểu dáng thể thao co giãn 4 chiều', 'https://via.placeholder.com/400x400', 250000, 'M,L,XL', 300, 3, 204, 1, 'seller4', NOW(), 'seller4', NOW()),
(15, 'Áo Lining A-Line Team', 'Lining', 'Vàng', 'Dùng làm áo đấu nhóm rất tốt', 'https://via.placeholder.com/400x400', 320000, 'S,M,L,XL', 150, 3, 205, 1, 'seller5', NOW(), 'seller5', NOW()),
(16, 'Váy Tennis/Cầu lông Victor', 'Victor', 'Trắng', 'Thanh lịch và tiện lợi', 'https://via.placeholder.com/400x400', 300000, 'S,M,L', 50, 3, 201, 1, 'seller1', NOW(), 'seller1', NOW()),
(17, 'Bô Quần áo thi đấu đội tuyển QG', 'Yonex', 'Xanh Bích', 'Set đồ thi đấu chuyên nghiệp', 'https://via.placeholder.com/400x400', 650000, 'L,XL,XXL', 80, 3, 202, 1, 'seller2', NOW(), 'seller2', NOW()),
(18, 'Áo Lining Training X1', 'Lining', 'Xám', 'Tập luyện cường độ cao', 'https://via.placeholder.com/400x400', 250000, 'M,L', 90, 3, 203, 1, 'seller3', NOW(), 'seller3', NOW()),

(19, 'Balo Yonex BA92212', 'Yonex', 'Đen Trắng', 'Đựng được 3 hộp cầu và 5 cây vợt', 'https://via.placeholder.com/400x400', 1250000, 'Free', 40, 4, 204, 1, 'seller4', NOW(), 'seller4', NOW()),
(20, 'Túi chéo Victor V-Bag', 'Victor', 'Ocean Blue', 'Tiện lợi nhỏ gọn đi giao lưu', 'https://via.placeholder.com/400x400', 650000, 'Free', 80, 4, 205, 1, 'seller5', NOW(), 'seller5', NOW()),
(21, 'Cuốn cán vợt Yonex AC102', 'Yonex', 'Mix Colors', 'Dính tay thấm hút cực thích', 'https://via.placeholder.com/400x400', 35000, 'Tiêu chuẩn', 5000, 4, 201, 1, 'seller1', NOW(), 'seller1', NOW()),
(22, 'Cước căng vợt Yonex BG65 Ti', 'Yonex', 'Trắng', 'Sợi cước titan có âm nổ to', 'https://via.placeholder.com/400x400', 160000, '0.68mm', 200, 4, 202, 1, 'seller2', NOW(), 'seller2', NOW()),
(23, 'Cước căng Lining No.1', 'Lining', 'Trắng Sữa', 'Nảy cầu tốt, trợ lực cao', 'https://via.placeholder.com/400x400', 150000, '0.65mm', 350, 4, 203, 1, 'seller3', NOW(), 'seller3', NOW()),
(24, 'Chặn mồ hôi tay Lining', 'Lining', 'Xanh đen', 'Chặn mồ hôi cực kỳ hiệu quả', 'https://via.placeholder.com/400x400', 45000, 'Free', 400, 4, 204, 1, 'seller4', NOW(), 'seller4', NOW()),

(25, 'Ống cầu lông VinaStar', 'VinaStar', 'Trắng', 'Độ bền tiêu chuẩn phòng tập', 'https://via.placeholder.com/400x400', 210000, '12 quả', 1000, 5, 205, 1, 'seller5', NOW(), 'seller5', NOW()),
(26, 'Ống cầu lông Victor Lark 5', 'Victor', 'Trắng', 'Loại cầu bay đầm êm', 'https://via.placeholder.com/400x400', 280000, '12 quả', 800, 5, 201, 1, 'seller1', NOW(), 'seller1', NOW()),
(27, 'Ống cầu lông Yonex AS 40', 'Yonex', 'Trắng', 'Cầu lông sử dụng vô địch thế giới', 'https://via.placeholder.com/400x400', 750000, '12 quả', 100, 5, 202, 1, 'seller2', NOW(), 'seller2', NOW()),
(28, 'Ống cầu Thành Công', 'Thành Công', 'Trắng', 'Hàng VN chất lượng cao', 'https://via.placeholder.com/400x400', 195000, '12 quả', 3000, 5, 203, 1, 'seller3', NOW(), 'seller3', NOW()),
(29, 'Ống cầu Hải Yến', 'Hải Yến', 'Trắng Xanh', 'Chịu nhiệt và độ bền cao', 'https://via.placeholder.com/400x400', 170000, '12 quả', 1500, 5, 204, 1, 'seller4', NOW(), 'seller4', NOW()),
(30, 'Quả cầu nhựa Yonex Mavis 350', 'Yonex', 'Vàng chanh', 'Sử dụng chơi ngoài sân xi măng, gió', 'https://via.placeholder.com/400x400', 350000, '6 quả', 120, 5, 205, 1, 'seller5', NOW(), 'seller5', NOW());
