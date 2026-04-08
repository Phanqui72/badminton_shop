# 🏸 Badminton Shop - Hướng Dẫn Test API Hoàn Chỉnh

> Base URL: `http://localhost:4120`
> Client: `abc_client` / `abc_secret_key`

---

## 🔄 Luồng Hoàn Chỉnh: Từ Đăng Ký → Mua Hàng

```mermaid
graph LR
    A[1. Register] --> B[2. Login]
    B --> C[3. Create Profile]
    C --> D[4. Add Address]
    D --> E[5. Add to Cart]
    E --> F[6. Checkout]
    F --> G[7. View Orders]
```

---

## 📋 BƯỚC 1: Đăng Ký Tài Khoản User

```
POST /v1/user/register
Content-Type: application/json
```
> 🔓 **Không cần token** (Public API)

```json
{
    "email": "customer1@gmail.com",
    "password": "123456",
    "fullName": "Nguyen Van A",
    "phone": "0901234567",
    "gender": 1
}
```

> [!NOTE]
> - `gender`: 1 = Nam, 2 = Nữ, 3 = Khác
> - `password`: tối thiểu 6 ký tự
> - Tài khoản sẽ tự động gán vào nhóm **USER** (group_id = 5)

---

## 📋 BƯỚC 2: Đăng Nhập (Lấy Token)

```
POST /api/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic YWJjX2NsaWVudDphYmNfc2VjcmV0X2tleQ==
```
> ⚠️ **Basic Auth** = base64("abc_client:abc_secret_key")
> ⚠️ **Content-Type phải là `x-www-form-urlencoded`**, KHÔNG phải JSON

### Đăng nhập User:

| Key | Value |
|-----|-------|
| `grant_type` | `user` |
| `username` | `customer1@gmail.com` |
| `password` | `123456` |

### Đăng nhập Seller:

| Key | Value |
|-----|-------|
| `grant_type` | `seller` |
| `username` | `seller1@gmail.com` |
| `password` | `123456` |

### Đăng nhập Admin:

| Key | Value |
|-----|-------|
| `grant_type` | `password` |
| `username` | `admin` |
| `password` | `admin123456` |

### Response mẫu:
```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIs...",
    "token_type": "bearer",
    "expires_in": 86399,
    "scope": "read write",
    "user_id": 9388625878646784,
    "user_kind": 2,
    "grant_type": "user"
}
```

> [!IMPORTANT]
> Sau khi có `access_token`, tất cả các API bên dưới đều cần header:
> ```
> Authorization: Bearer <access_token>
> ```

---

## 📋 BƯỚC 3: Tạo Hồ Sơ User (Create Profile)

```
POST /v1/user/create
Content-Type: application/json
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `USR_C`

```json
{
    "gender": 1,
    "dateOfBirth": "1995-06-15",
    "addresses": [
        {
            "receiverName": "Nguyen Van A",
            "nationId": 1,
            "detail": "123 Nguyen Hue, Q1, TP.HCM",
            "isDefault": true,
            "userId": 1
        }
    ]
}
```

---

## 📋 BƯỚC 3b: Xem Hồ Sơ

```
GET /v1/user/profile
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `USR_V`

---

## 📋 BƯỚC 4: Thêm Địa Chỉ Giao Hàng

```
POST /v1/address/create
Content-Type: application/json
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `ADDR_C`

```json
{
    "receiverName": "Nguyen Van A",
    "nationId": 1,
    "detail": "456 Le Loi, Q3, TP.HCM",
    "isDefault": false,
    "userId": 1
}
```

---

## 📋 BƯỚC 5: Thêm Sản Phẩm Vào Giỏ Hàng

```
POST /v1/cart/add
Content-Type: application/json
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `CRT_C`

```json
{
    "productId": 1,
    "quantity": 2
}
```

---

## 📋 BƯỚC 5b: Xem Giỏ Hàng

```
GET /v1/cart/my-cart
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `CRT_V`

---

## 📋 BƯỚC 5c: Cập Nhật Số Lượng

```
PUT /v1/cart/update
Content-Type: application/json
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `CRT_U`

```json
{
    "cartItemId": 1,
    "quantity": 3
}
```

---

## 📋 BƯỚC 5d: Xóa Sản Phẩm Khỏi Giỏ

```
DELETE /v1/cart/remove/{cartItemId}
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `CRT_D`

---

## 📋 BƯỚC 6: Đặt Hàng (Checkout)

```
POST /v1/order/checkout
Content-Type: application/json
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `ORD_C`

```json
{
    "addressId": 1,
    "paymentMethod": 1
}
```

> [!NOTE]
> - `paymentMethod`: 1 = COD (thanh toán khi nhận hàng), 2 = Online
> - Checkout sẽ tự động tạo đơn từ toàn bộ giỏ hàng, trừ stock & xóa cart

---

## 📋 BƯỚC 7: Xem Đơn Hàng Của Tôi

```
GET /v1/order/my-orders?page=0&size=10
Authorization: Bearer <access_token>
```
> 🔒 Quyền: `ORD_L`

---

## 📋 BƯỚC 7b: Xem Chi Tiết Đơn Hàng

```
GET /v1/order/get/{orderId}
Authorization: Bearer <access_token>
```

---

# 🏪 LUỒNG SELLER

## Đăng Ký Seller (Admin tạo)

```
POST /v1/seller/create
Content-Type: application/json
Authorization: Bearer <admin_token>
```
> 🔒 Quyền: `SEL_C`

```json
{
    "shopName": "Pro Badminton Shop",
    "shopDescription": "Chuyên cung cấp vợt cầu lông chính hãng",
    "addressId": 1,
    "accountId": 2
}
```

---

## Seller Tạo Sản Phẩm

```
POST /v1/product/create
Content-Type: application/json
Authorization: Bearer <seller_token>
```
> 🔒 Quyền: `PRD_C`

```json
{
    "name": "Vợt Yonex Astrox 99 Pro",
    "description": "Vợt cầu lông cao cấp Yonex Astrox 99 Pro, tấn công mạnh mẽ",
    "price": 4500000,
    "stock": 50,
    "imagePath": "/images/yonex-astrox-99.jpg",
    "brand": "Yonex",
    "size": "4U",
    "color": "Cherry Sunburst",
    "categoryId": 1
}
```

---

## Seller Xem Đơn Hàng Shop

```
GET /v1/order/list?page=0&size=10
Authorization: Bearer <seller_token>
```
> 🔒 Quyền: `ORD_M`

---

# 🔧 LUỒNG ADMIN

## Tạo Quốc Gia/Tỉnh Thành

```
POST /v1/nation/create
Content-Type: application/json
Authorization: Bearer <admin_token>
```
> 🔒 Quyền: `NAT_C`

```json
{
    "name": "Việt Nam",
    "kind": 1,
    "postCode": "VN",
    "parentId": null
}
```

### Tạo Tỉnh/Thành (con của Việt Nam):
```json
{
    "name": "TP. Hồ Chí Minh",
    "kind": 2,
    "postCode": "HCM",
    "parentId": 1
}
```

> [!NOTE]
> `kind`: 1 = Quốc gia, 2 = Tỉnh/Thành phố, 3 = Quận/Huyện

---

## Tạo Danh Mục Sản Phẩm

```
POST /v1/category/create
Content-Type: application/json
Authorization: Bearer <admin_token>
```
> 🔒 Quyền: `CAT_C`

```json
{
    "name": "Vợt cầu lông",
    "description": "Các loại vợt cầu lông",
    "parentId": null
}
```

### Tạo danh mục con:
```json
{
    "name": "Vợt Yonex",
    "description": "Vợt cầu lông thương hiệu Yonex",
    "parentId": 1
}
```

---

# 📊 Bảng Tổng Hợp Quyền Theo Role

| API | Method | Path | USER | SELLER | ADMIN |
|-----|--------|------|:----:|:------:|:-----:|
| Đăng ký | POST | `/v1/user/register` | 🔓 | 🔓 | 🔓 |
| Đăng nhập | POST | `/api/token` | 🔓 | 🔓 | 🔓 |
| Xem profile | GET | `/v1/user/profile` | ✅ | ✅ | ✅ |
| Cập nhật profile | PUT | `/v1/user/update` | ✅ | ✅ | ✅ |
| Thêm địa chỉ | POST | `/v1/address/create` | ✅ | ✅ | ❌ |
| Thêm giỏ hàng | POST | `/v1/cart/add` | ✅ | ✅ | ❌ |
| Xem giỏ hàng | GET | `/v1/cart/my-cart` | ✅ | ✅ | ❌ |
| Đặt hàng | POST | `/v1/order/checkout` | ✅ | ❌ | ❌ |
| Xem đơn tôi | GET | `/v1/order/my-orders` | ✅ | ✅ | ❌ |
| Tạo sản phẩm | POST | `/v1/product/create` | ❌ | ✅ | ✅ |
| Quản lý đơn | GET | `/v1/order/list` | ❌ | ✅ | ✅ |
| Tạo danh mục | POST | `/v1/category/create` | ❌ | ❌ | ✅ |
| Tạo quốc gia | POST | `/v1/nation/create` | ❌ | ❌ | ✅ |

---

# 🚀 Kịch Bản Test End-to-End Hoàn Chỉnh

> Thứ tự gọi API từ đầu khi database sạch:

### Phase 1: Admin Setup (login với `grant_type=password`)
1. `POST /api/token` → Đăng nhập Admin
2. `POST /v1/nation/create` → Tạo "Việt Nam" (kind=1)
3. `POST /v1/nation/create` → Tạo "TP.HCM" (kind=2, parentId=1)
4. `POST /v1/category/create` → Tạo "Vợt cầu lông"
5. `POST /v1/category/create` → Tạo "Giày cầu lông"

### Phase 2: Seller Setup (đăng ký seller rồi login với `grant_type=seller`)
6. `POST /v1/user/register` → Đăng ký tài khoản seller1@gmail.com
7. Admin: `POST /v1/seller/create` → Gán seller role cho account
8. `POST /api/token` → Đăng nhập seller (grant_type=seller)
9. `POST /v1/product/create` → Tạo "Vợt Yonex Astrox 99 Pro"
10. `POST /v1/product/create` → Tạo "Giày Yonex Eclipsion Z3"

### Phase 3: User Flow (đăng ký rồi login với `grant_type=user`)
11. `POST /v1/user/register` → Đăng ký customer1@gmail.com
12. `POST /api/token` → Đăng nhập user (grant_type=user)
13. `POST /v1/user/create` → Tạo hồ sơ + địa chỉ
14. `GET /v1/user/profile` → Xem hồ sơ
15. `POST /v1/cart/add` → Thêm vợt vào giỏ (productId=1, quantity=2)
16. `POST /v1/cart/add` → Thêm giày vào giỏ (productId=2, quantity=1)
17. `GET /v1/cart/my-cart` → Xem giỏ hàng
18. `PUT /v1/cart/update` → Sửa số lượng vợt thành 1
19. `POST /v1/order/checkout` → Đặt hàng (addressId=1, paymentMethod=1)
20. `GET /v1/order/my-orders` → Xem đơn hàng đã đặt

### Phase 4: Seller xử lý đơn
21. `GET /v1/order/list` → Seller xem đơn hàng của shop
