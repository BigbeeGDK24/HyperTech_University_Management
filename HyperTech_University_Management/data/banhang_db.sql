-- ================= RESET DATABASE =================
IF DB_ID('banhang_db') IS NOT NULL
DROP DATABASE banhang_db;
GO

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
    phone VARCHAR(15),
    address NVARCHAR(255),
    status BIT DEFAULT 1
);

-- ================= ADMINS =================
CREATE TABLE admins (
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
    price DECIMAL(12,0) NOT NULL,
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
    id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT,
    product_id INT,
    quantity INT DEFAULT 1,

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
    total_price DECIMAL(12,0) NOT NULL,
    status VARCHAR(20)
        CHECK (status IN ('pending','confirmed','shipping','completed','cancelled','outstock')),
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
    price DECIMAL(12,0),
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

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- ================= PAYMENTS =================
CREATE TABLE payments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    user_id INT,
    payment_method VARCHAR(30)
        CHECK (payment_method IN ('COD','bank_transfer','momo','vnpay','paypal')),
    amount DECIMAL(12,0),
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
('a@gmail.com',N'Nguyễn Văn A','123456','0900000001',N'Hà Nội'),
('b@gmail.com',N'Trần Thị B','123456','0900000002',N'Hồ Chí Minh'),
('c@gmail.com',N'Lê Văn C','123456','0900000003',N'Đà Nẵng'),
('d@gmail.com',N'Phạm Thị D','123456','0900000004',N'Cần Thơ');

-- ================= INSERT ADMINS =================
INSERT INTO admins (username,password) VALUES
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
INSERT INTO cart (user_id,product_id,quantity) VALUES
(1,1,1),
(1,2,1),
(2,3,2),
(4,4,3);

-- ================= INSERT ORDERS =================
INSERT INTO orders (user_id,total_price,status) VALUES
(1,22500000,'confirmed'),
(2,3000000,'shipping'),
(3,3000000,'completed'),
(4,5400000,'pending');

-- ================= INSERT ORDER ITEMS =================
INSERT INTO order_items (order_id,product_id,price,quantity) VALUES
(1,1,18000000,1),
(1,2,4500000,1),
(2,3,1500000,2),
(3,3,1500000,2),
(4,4,1800000,3);

-- ================= LAPTOPS =================
CREATE TABLE laptops (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255),
    cpu NVARCHAR(100),
    gpu NVARCHAR(100),
    ram NVARCHAR(50),
    ssd NVARCHAR(50),
    screen NVARCHAR(100),
    refresh_rate NVARCHAR(50),
    old_price DECIMAL(12,0),
    new_price DECIMAL(12,0),
    image_url NVARCHAR(255)
);

INSERT INTO laptops
(name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,image_url)
VALUES
(N'Laptop gaming Gigabyte A16','i7-13620H','RTX 4050','16 GB','1 TB','16 inch WUXGA','165 Hz',29930000,28490000,'a16_1.jpg'),
(N'Laptop gaming Gigabyte A16','i5-12500H','RTX 3050','16 GB','512 GB','15.6 inch','144 Hz',29490000,27990000,'a16_2.jpg'),
(N'Laptop gaming Acer Nitro V ProPanel','R5-7535HS','RTX 3050','16 GB','512 GB','15.6 inch FHD','180 Hz',27990000,25990000,'nitro_v1.jpg'),
(N'Laptop gaming Lenovo LOQ 15IAX9E','i5-12450HX','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',24490000,23490000,'loq1.jpg'),
(N'Laptop gaming Lenovo LOQ 15ARP10E','R7-7735HS','RTX 4050','16 GB','512 GB','15.6 inch FHD','144 Hz',28990000,24790000,'loq2.jpg'),
(N'Laptop gaming MSI Cyborg 15 A13UC','i7-13620H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',32990000,29990000,'msi_cyborg.jpg'),
(N'Laptop gaming Acer Nitro Lite 16 NL16 71','i5-13420H','RTX 3050','16 GB','512 GB','16 inch FHD+','165 Hz',23990000,21990000,'nitro_lite.jpg'),
(N'Laptop gaming Gigabyte A16','i7-13620H','RTX 4050','16 GB','512 GB','16 inch FHD+ IPS','165 Hz',29990000,27990000,'a16_3.jpg'),
(N'Laptop gaming Acer Nitro V ProPanel','i5-13420H','RTX 4050','32 GB','512 GB','15.6 inch FHD','180 Hz',31990000,29490000,'nitro_v2.jpg'),
(N'Laptop gaming Lenovo Legion 5 15IRX10','i7-14700HX','RTX 5070','24 GB','1 TB','15.1 inch WQXGA OLED','165 Hz',49990000,45990000,'legion5.jpg'),
(N'Laptop gaming Acer Nitro V 16S ProPanel','R7 260','RTX 4050','16 GB','1 TB','16 inch WUXGA','180 Hz',34990000,31990000,'nitro_v3.jpg'),
(N'Laptop gaming HP VICTUS 15-fa2731TX','i5-13420H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',22990000,20990000,'victus.jpg');