CREATE DATABASE banhang_db;
GO
USE banhang_db;
GO

-- ================= USERS =================
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(100) UNIQUE NOT NULL,
    username NVARCHAR(50) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    phone VARCHAR(10),
    address NVARCHAR(255),
    status BIT DEFAULT 1
);

-- ================= ADMIN =================
CREATE TABLE admin (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL,
    password NVARCHAR(255) NOT NULL
);

-- ================= CATEGORIES =================
CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(255),
    status BIT DEFAULT 1
);

-- ================= PRODUCTS =================
CREATE TABLE products (
    id INT IDENTITY(1,1) PRIMARY KEY,
    category_id INT NOT NULL,
    name NVARCHAR(150) NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    stock INT DEFAULT 0,
    description NVARCHAR(MAX),
    image VARCHAR(255),
    status BIT DEFAULT 1,

    FOREIGN KEY (category_id)
    REFERENCES categories(id)
    ON DELETE CASCADE
);

-- ================= CART =================
CREATE TABLE cart (
    user_id INT,
    product_id INT,
    quantity INT DEFAULT 1,

    PRIMARY KEY (user_id, product_id),

    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE,

    FOREIGN KEY (product_id)
    REFERENCES products(id)
    ON DELETE CASCADE
);

-- ================= ORDERS =================
CREATE TABLE orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    total_price DECIMAL(12,2) NOT NULL,
    status VARCHAR(20)
        CHECK (status IN ('confirmed','shipping','completed','cancelled','outstock')),
    created_at DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);

-- ================= ORDER ITEMS =================
CREATE TABLE order_items (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    product_id INT,
    price DECIMAL(12,2),
    quantity INT,

    FOREIGN KEY (order_id)
    REFERENCES orders(id)
    ON DELETE CASCADE,

    FOREIGN KEY (product_id)
    REFERENCES products(id)
);

-- ================= DISCOUNTS =================
CREATE TABLE discounts (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100),
    discount_percent INT CHECK (discount_percent BETWEEN 1 AND 100),
    start_date DATETIME,
    end_date DATETIME
);

-- ================= PRODUCT DISCOUNTS =================
CREATE TABLE product_discounts (
    product_id INT,
    discount_id INT,

    PRIMARY KEY (product_id, discount_id),

    FOREIGN KEY (product_id)
    REFERENCES products(id)
    ON DELETE CASCADE,

    FOREIGN KEY (discount_id)
    REFERENCES discounts(id)
    ON DELETE CASCADE
);

-- ================= COMPLAINTS =================
CREATE TABLE complaints (
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    order_id INT,
    product_id INT,
    title NVARCHAR(200),
    content NVARCHAR(MAX),
    status VARCHAR(20)
        CHECK (status IN ('pending','processing','resolved','rejected'))
        DEFAULT 'pending',

    FOREIGN KEY (user_id)
    REFERENCES users(id),

    FOREIGN KEY (order_id)
    REFERENCES orders(id),

    FOREIGN KEY (product_id)
    REFERENCES products(id)
);

-- ================= PAYMENTS =================
CREATE TABLE payments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    user_id INT,
    payment_method VARCHAR(30)
        CHECK (payment_method IN ('COD','bank_transfer','momo','vnpay','paypal')),
    amount DECIMAL(12,2),
    status VARCHAR(20)
        CHECK (status IN ('pending','paid','failed','refunded'))
        DEFAULT 'pending',
    transaction_code VARCHAR(100),
    paid_at DATETIME,

    FOREIGN KEY (order_id)
    REFERENCES orders(id)
    ON DELETE CASCADE,

    FOREIGN KEY (user_id)
    REFERENCES users(id)
);

-- ================= INSERT USERS =================
INSERT INTO users (email, username, password, phone, address) VALUES
('a@gmail.com', N'Nguyễn Văn A', '123456', '0900000001', N'Hà Nội'),
('b@gmail.com', N'Trần Thị B', '123456', '0900000002', N'Hồ Chí Minh'),
('c@gmail.com', N'Lê Văn C', '123456', '0900000003', N'Đà Nẵng'),
('d@gmail.com', N'Phạm Thị D', '123456', '0900000004', N'Cần Thơ');

-- ================= INSERT ADMIN =================
INSERT INTO admin (username,password) VALUES
('truong','admin123'),
('kiet','admin456'),
('trieu','admin789');

-- ================= INSERT CATEGORIES =================
INSERT INTO categories (name,description) VALUES
(N'Laptop',N'Laptop văn phòng và gaming'),
(N'CPU',N'Bộ vi xử lý máy tính'),
(N'Nguồn',N'Bộ nguồn máy tính'),
(N'SSD',N'Ổ cứng SSD'),
(N'Màn hình',N'Màn hình máy tính'),
(N'Bàn phím',N'Bàn phím cơ'),
(N'Chuột',N'Chuột gaming');

-- ================= INSERT PRODUCTS =================
INSERT INTO products (category_id,name,price,stock,description,image) VALUES
(1,N'Dell Inspiron 15',18000000,10,N'Laptop văn phòng 15.6 inch i5 RAM 8GB SSD 512GB','dell.jpg'),
(2,N'Intel Core i5 12400F',4500000,15,N'CPU Intel thế hệ 12 socket LGA1700','i5.jpg'),
(3,N'Corsair CV650',1500000,20,N'Nguồn 650W chuẩn 80 Plus Bronze','corsair.jpg'),
(4,N'SSD Kingston NV2 1TB',1800000,25,N'SSD NVMe PCIe Gen4','ssd.jpg');

-- ================= INSERT CART =================
INSERT INTO cart VALUES
(1,1,1),
(1,2,1),
(2,3,2),
(4,4,3);

-- ================= INSERT ORDERS =================
INSERT INTO orders (user_id,total_price,status) VALUES
(1,25000000,'confirmed'),
(2,28000000,'shipping'),
(3,3000000,'completed'),
(4,900000,'outstock');

-- ================= INSERT ORDER ITEMS =================
INSERT INTO order_items (order_id,product_id,price,quantity) VALUES
(1,1,25000000,1),
(2,2,28000000,1),
(3,3,1500000,2),
(4,4,300000,3);

-- ================= INSERT DISCOUNTS =================
INSERT INTO discounts (name,discount_percent,start_date,end_date) VALUES
(N'Giảm 10%',10,'2026-01-01','2026-12-31'),
(N'Giảm 20%',20,'2026-01-01','2026-06-30'),
(N'Giảm 15%',15,'2026-03-01','2026-09-30'),
(N'Giảm 5%',5,'2026-01-01','2026-04-30');

-- ================= INSERT PRODUCT DISCOUNTS =================
INSERT INTO product_discounts VALUES
(1,1),
(2,2),
(3,3),
(4,4);

-- ================= INSERT COMPLAINTS =================
INSERT INTO complaints (user_id,order_id,product_id,title,content,status) VALUES
(1,1,1,N'Lỗi màn hình',N'Màn hình bị sọc','pending'),
(2,2,2,N'Giao hàng chậm',N'Chậm 3 ngày','processing'),
(3,3,3,N'Sản phẩm lỗi',N'Không hoạt động','resolved'),
(4,4,4,N'Sai sản phẩm',N'Nhận sai hàng','rejected');

-- ================= INSERT PAYMENTS =================
INSERT INTO payments
(order_id,user_id,payment_method,amount,status,transaction_code,paid_at)
VALUES
(1,1,'vnpay',25000000,'paid','VNP001',GETDATE()),
(2,2,'momo',28000000,'paid','MOMO002',GETDATE()),
(3,3,'COD',3000000,'pending',NULL,NULL),
(4,4,'paypal',900000,'failed','PAY004',NULL);