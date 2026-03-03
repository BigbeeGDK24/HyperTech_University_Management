
USE banhang_db;

-- ==============================
-- BẢNG USERS
-- ==============================
CREATE TABLE users (
    Username NVARCHAR(70) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL,
    email NVARCHAR(70) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    phone VARCHAR(10),
    address NVARCHAR(MAX),
    status bit not null,
);

-- ==============================
-- BẢNG CATEGORIES
-- ==============================
CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(70) NOT NULL,
    description NVARCHAR(MAX),
    status bit not null,
);

-- ==============================
-- BẢNG PRODUCTS
-- ==============================
CREATE TABLE products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    name NVARCHAR(150) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    description NVARCHAR(MAX),
    image VARCHAR(255),
    status bit not null,

    CONSTRAINT FK_products_categories
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
);


-- ==============================
-- BẢNG CART_ITEMS
-- ==============================
CREATE TABLE cart (
    user_id NVARCHAR(70) NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,

    PRIMARY KEY (user_id, product_id),

    FOREIGN KEY (user_id)
        REFERENCES users(Username)
        ON DELETE CASCADE,

    FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
);

-- ==============================
-- BẢNG ORDERS
-- ==============================
CREATE TABLE orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id NVARCHAR(70) NOT NULL,
    total_price DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL
        CHECK (status IN ('outstock','confirmed','shipping','completed','cancelled')),

    CONSTRAINT FK_orders_users
        FOREIGN KEY (user_id)
        REFERENCES users(Username)
        ON DELETE CASCADE
);

-- ==============================
-- BẢNG ORDER_ITEMS
-- ==============================
CREATE TABLE order_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,

    CONSTRAINT FK_orderitems_orders
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_orderitems_products
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);

-- ==============================
-- BẢNG ADMIN
-- ==============================
CREATE TABLE admin (
    admin NVARCHAR(255) PRIMARY KEY,
    adPass NVARCHAR(255) NOT NULL
);

-- ==============================
-- BẢNG DISCOUNTS
-- ==============================
CREATE TABLE discounts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    discount_percent INT NOT NULL
        CHECK (discount_percent BETWEEN 1 AND 100),
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,

);


-- ==============================
-- BẢNG PRODUCT_DISCOUNTS
-- ==============================
CREATE TABLE product_discounts (
    product_id INT NOT NULL,
    discount_id INT NOT NULL,

    CONSTRAINT PK_product_discounts
        PRIMARY KEY (product_id, discount_id),

    CONSTRAINT FK_pd_products
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_pd_discounts
        FOREIGN KEY (discount_id)
        REFERENCES discounts(id)
        ON DELETE CASCADE
);

-- ==============================
-- BẢNG COMPLAINTS
-- ==============================
CREATE TABLE complaints (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id NVARCHAR(70) NOT NULL,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    title NVARCHAR(200) NOT NULL,
    content NVARCHAR(MAX) NOT NULL,
    status VARCHAR(20) NOT NULL
        CHECK (status IN ('pending','processing','resolved','rejected'))
        DEFAULT 'pending',

    CONSTRAINT FK_complaints_users
        FOREIGN KEY (user_id)
        REFERENCES users(Username)
        ON DELETE NO ACTION,

    CONSTRAINT FK_complaints_orders
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE NO ACTION,

    CONSTRAINT FK_complaints_products
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE NO ACTION
);

-- ==============================
-- BẢNG PAYMENTS
-- ==============================
CREATE TABLE payments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    user_id NVARCHAR(70) NOT NULL,

    payment_method VARCHAR(30) NOT NULL
        CHECK (payment_method IN ('COD', 'bank_transfer', 'momo', 'vnpay', 'paypal')),

    amount DECIMAL(12,2) NOT NULL,

    status VARCHAR(20) NOT NULL
        CHECK (status IN ('pending', 'paid', 'failed', 'refunded'))
        DEFAULT 'pending',

    transaction_code VARCHAR(100),
    paid_at DATETIME NULL,

    CONSTRAINT FK_payments_orders
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT FK_payments_users
        FOREIGN KEY (user_id)
        REFERENCES users(Username)
        ON DELETE NO ACTION
);-- ==============================
-- INSERT USERS
-- ==============================
INSERT INTO users VALUES
('user01', N'Nguyễn Văn A', 'a@gmail.com', '123456', '0900000001', N'Hà Nội'),
('user02', N'Trần Thị B', 'b@gmail.com', '123456', '0900000002', N'Hồ Chí Minh'),
('user03', N'Lê Văn C', 'c@gmail.com', '123456', '0900000003', N'Đà Nẵng'),
('user04', N'Phạm Thị D', 'd@gmail.com', '123456', '0900000004', N'Cần Thơ');

-- ==============================
-- INSERT CATEGORIES
-- ==============================
INSERT INTO categories (name, description) VALUES
(N'Laptop', N'Các dòng laptop văn phòng, gaming, đồ họa'),
(N'Main,CPU,VGA', N'Linh kiện xử lý và bo mạch: Mainboard, CPU, Card đồ họa'),
(N'Case,Nguồn,Tản', N'Thùng máy, bộ nguồn và hệ thống tản nhiệt'),
(N'Ổ cứng, RAM, Thẻ Nhớ', N'Thiết bị lưu trữ và bộ nhớ trong'),
(N'Màn Hình', N'Màn hình máy tính văn phòng, gaming, thiết kế'),
(N'Bàn Phím', N'Bàn phím cơ, bàn phím văn phòng, gaming'),
(N'Chuột + Lót chuột', N'Chuột máy tính và lót chuột gaming');

-- ==============================
-- INSERT PRODUCTS
-- ==============================
INSERT INTO products (category_id, name, price, stock, description, image) VALUES
(1, N'Dell Inspiron 15', 18000000, 10, 
 N'Laptop văn phòng 15.6 inch, CPU Intel Core i5, RAM 8GB, SSD 512GB', 
 'dell_inspiron15.jpg'),

(2, N'CPU Intel Core i5 12400F', 4500000, 15, 
 N'Bộ vi xử lý Intel thế hệ 12, 6 nhân 12 luồng, socket LGA1700', 
 'intel_i5_12400f.jpg'),

(3, N'Nguồn Corsair CV650 650W', 1500000, 20, 
 N'Bộ nguồn công suất 650W, chuẩn 80 Plus Bronze', 
 'corsair_cv650.jpg'),

(4, N'SSD Kingston NV2 1TB', 1800000, 25, 
 N'Ổ cứng SSD NVMe PCIe Gen4 dung lượng 1TB tốc độ cao', 
 'kingston_nv2_1tb.jpg');


-- ==============================
-- INSERT CART_ITEMS
-- ==============================
INSERT INTO cart ( user_id, product_id, quantity) VALUES
('user01', 1, 1),
('user01', 2, 1),
('user02', 3, 2),
('user04', 4, 3);

-- ==============================
-- INSERT ORDERS
-- ==============================
INSERT INTO orders (user_id, total_price, status) VALUES
('user01', 25000000, 'confirmed'),
('user02', 28000000, 'shipping'),
('user03', 3000000, 'completed'),
('user04', 900000, 'outstock');

-- ==============================
-- INSERT ORDER_ITEMS
-- ==============================
INSERT INTO order_items (order_id, product_id, price, quantity) VALUES
(1, 1, 25000000, 1),
(2, 2, 28000000, 1),
(3, 3, 1500000, 2),
(4, 4, 300000, 3);

-- ==============================
-- INSERT ADMIN
-- ==============================
INSERT INTO admin (admin,adPass) VALUES
('Truong','admin123'),
('Kiet','admin456'),
('Trieu','admin789');

-- ==============================
-- INSERT DISCOUNTS
-- ==============================
INSERT INTO discounts (name, discount_percent, start_date, end_date) VALUES
(N'Giảm 10%', 10, '2026-01-01', '2026-12-31'),
(N'Giảm 20%', 20, '2026-01-01', '2026-06-30'),
(N'Giảm 15%', 15, '2026-03-01', '2026-09-30'),
(N'Giảm 5%', 5, '2026-01-01', '2026-04-30');

-- ==============================
-- INSERT PRODUCT_DISCOUNTS
-- ==============================
INSERT INTO product_discounts VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4);

-- ==============================
-- INSERT COMPLAINTS
-- ==============================
INSERT INTO complaints (user_id, order_id, product_id, title, content, status) VALUES
('user01', 1, 1, N'Lỗi màn hình', N'Màn hình bị sọc', 'pending'),
('user02', 2, 2, N'Giao hàng chậm', N'Chậm 3 ngày', 'processing'),
('user03', 3, 3, N'Sản phẩm lỗi', N'Tai nghe không kết nối được', 'resolved'),
('user04', 4, 4, N'Sai kích cỡ', N'Áo không đúng size', 'rejected');

-- ==============================
-- INSERT PAYMENTS
-- ==============================
INSERT INTO payments 
(order_id, user_id, payment_method, amount, status, transaction_code, paid_at)
VALUES
(1, 'user01', 'vnpay', 25000000, 'paid', 'VNP001', GETDATE()),
(2, 'user02', 'momo', 28000000, 'paid', 'MOMO002', GETDATE()),
(3, 'user03', 'COD', 3000000, 'pending', NULL, NULL),
(4, 'user04', 'paypal', 900000, 'failed', 'PAY004', NULL);
