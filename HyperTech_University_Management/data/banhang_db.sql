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
    email NVARCHAR(100) PRIMARY KEY,
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
    email NVARCHAR(100),
    product_id INT,
    quantity INT DEFAULT 1,

    PRIMARY KEY (email, product_id),

    FOREIGN KEY (email)
    REFERENCES users(email)
    ON DELETE CASCADE,

    FOREIGN KEY (product_id)
    REFERENCES products(id)
    ON DELETE CASCADE
);

-- ================= ORDERS =================
CREATE TABLE orders (
    id INT IDENTITY(1,1) PRIMARY KEY,
    email NVARCHAR(100)  NOT NULL,
    total_price DECIMAL(12,0) NOT NULL,
    status VARCHAR(20)
        CHECK (status IN ('pending','confirmed','shipping','completed','cancelled','outstock')),
    created_at DATETIME DEFAULT GETDATE(),

    FOREIGN KEY (email)
    REFERENCES users(email)
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
    email NVARCHAR(100),
    order_id INT,
    product_id INT,
    title NVARCHAR(200),
    content NVARCHAR(MAX),
    status VARCHAR(20)
        CHECK (status IN ('pending','processing','resolved','rejected'))
        DEFAULT 'pending',

    FOREIGN KEY (email) REFERENCES users(email),
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- ================= PAYMENTS =================
CREATE TABLE payments (
    id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT,
    email NVARCHAR(100),
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

    FOREIGN KEY (email)
    REFERENCES users(email)
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
(1,N'Laptop gaming Gigabyte A16 i7 RTX4050',28490000,10,N'Gigabyte A16 i7-13620H RTX4050 16GB RAM 1TB SSD','lab1.png'),
(1,N'Laptop gaming Gigabyte A16 i5 RTX3050',27990000,10,N'Gigabyte A16 i5-12500H RTX3050 16GB RAM 512GB SSD','lab2.png'),
(1,N'Acer Nitro V ProPanel R5 RTX3050',25990000,10,N'Acer Nitro V Ryzen5 7535HS RTX3050 16GB RAM','lab3.png'),
(1,N'Lenovo LOQ 15IAX9E i5 RTX3050',23490000,10,N'Lenovo LOQ i5-12450HX RTX3050 16GB RAM','lab4.png'),
(1,N'Lenovo LOQ 15ARP10E R7 RTX4050',24790000,10,N'Lenovo LOQ Ryzen7 7735HS RTX4050','lab5.png'),
(1,N'MSI Cyborg 15 A13UC i7 RTX3050',29990000,10,N'MSI Cyborg i7-13620H RTX3050','lab6.png'),
(1,N'Acer Nitro Lite 16 NL16 i5 RTX3050',21990000,10,N'Acer Nitro Lite i5-13420H RTX3050','lab7.png'),
(1,N'Gigabyte A16 i7 RTX4050 512GB',27990000,10,N'Gigabyte A16 i7 RTX4050 512GB SSD','lab8.png'),
(1,N'Acer Nitro V i5 RTX4050 32GB',29490000,10,N'Acer Nitro V i5 RTX4050 32GB RAM','lab9.png'),
(1,N'Lenovo Legion 5 i7 RTX5070',45990000,10,N'Lenovo Legion 5 i7-14700HX RTX5070','lab10.png'),
(1,N'Acer Nitro V 16S RTX4050',31990000,10,N'Acer Nitro V Ryzen7 RTX4050','lab11.png'),
(1,N'HP Victus 15 i5 RTX3050',20990000,10,N'HP Victus 15 i5 RTX3050','lab12.png');
-- ================= INSERT CART =================
INSERT INTO cart (email,product_id,quantity) VALUES
('a@gmail.com',1,1),
('a@gmail.com',2,1),
('a@gmail.com',3,2),
('a@gmail.com',4,3);

-- ================= INSERT ORDERS =================
INSERT INTO orders (email,total_price,status) VALUES
('a@gmail.com',22500000,'confirmed'),
('a@gmail.com',3000000,'shipping'),
('a@gmail.com',3000000,'completed'),
('a@gmail.com',5400000,'pending');

-- ================= INSERT ORDER ITEMS =================
INSERT INTO order_items (order_id,product_id,price,quantity) VALUES
(1,1,18000000,1),
(1,2,4500000,1),
(2,3,1500000,2),
(3,3,1500000,2),
(4,4,1800000,3);

-- ================= LAPTOPS =================
CREATE TABLE laptops (
    product_id INT PRIMARY KEY,
    cpu NVARCHAR(100),
    gpu NVARCHAR(100),
    ram NVARCHAR(50),
    ssd NVARCHAR(50),
    screen NVARCHAR(100),
    refresh_rate NVARCHAR(50)
   FOREIGN KEY (product_id)
    REFERENCES products(id)
    ON DELETE CASCADE
);

INSERT INTO laptops
(product_id,cpu,gpu,ram,ssd,screen,refresh_rate)
VALUES
(1,'i7-13620H','RTX 4050','16 GB','1 TB','16 inch WUXGA','165 Hz'),
(2,'i5-12500H','RTX 3050','16 GB','512 GB','15.6 inch','144 Hz'),
(3,'R5-7535HS','RTX 3050','16 GB','512 GB','15.6 inch FHD','180 Hz'),
(4,'i5-12450HX','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz'),
(5,'R7-7735HS','RTX 4050','16 GB','512 GB','15.6 inch FHD','144 Hz'),
(6,'i7-13620H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz'),
(7,'i5-13420H','RTX 3050','16 GB','512 GB','16 inch FHD+','165 Hz'),
(8,'i7-13620H','RTX 4050','16 GB','512 GB','16 inch FHD+ IPS','165 Hz'),
(9,'i5-13420H','RTX 4050','32 GB','512 GB','15.6 inch FHD','180 Hz'),
(10,'i7-14700HX','RTX 5070','24 GB','1 TB','15.1 inch WQXGA OLED','165 Hz'),
(11,'R7 260','RTX 4050','16 GB','1 TB','16 inch WUXGA','180 Hz'),
(12,'i5-13420H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz');
