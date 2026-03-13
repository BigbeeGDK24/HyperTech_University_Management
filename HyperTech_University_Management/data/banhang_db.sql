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

dro
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
(N'Laptop gaming Acer Nitro V ProPanel','R5-7535HS','RTX 3050','16 GB','512 GB','15.6 inch FHD','180 Hz',27990000,25990000,'nitro_v1.png'),
(N'Laptop gaming Lenovo LOQ 15IAX9E','i5-12450HX','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',24490000,23490000,'loq1.jpg'),
(N'Laptop gaming Lenovo LOQ 15ARP10E','R7-7735HS','RTX 4050','16 GB','512 GB','15.6 inch FHD','144 Hz',28990000,24790000,'loq2.jpg'),
(N'Laptop gaming MSI Cyborg 15 A13UC','i7-13620H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',32990000,29990000,'msi_cyborg.jpg'),
(N'Laptop gaming Acer Nitro Lite 16 NL16 71','i5-13420H','RTX 3050','16 GB','512 GB','16 inch FHD+','165 Hz',23990000,21990000,'nitro_lite.jpg'),
(N'Laptop gaming Gigabyte A16','i7-13620H','RTX 4050','16 GB','512 GB','16 inch FHD+ IPS','165 Hz',29990000,27990000,'a16_3.jpg'),
(N'Laptop gaming Acer Nitro V ProPanel','i5-13420H','RTX 4050','32 GB','512 GB','15.6 inch FHD','180 Hz',31990000,29490000,'nitro_v2.jpg'),
(N'Laptop gaming Lenovo Legion 5 15IRX10','i7-14700HX','RTX 5070','24 GB','1 TB','15.1 inch WQXGA OLED','165 Hz',49990000,45990000,'legion5.jpg'),
(N'Laptop gaming Acer Nitro V 16S ProPanel','R7 260','RTX 4050','16 GB','1 TB','16 inch WUXGA','180 Hz',34990000,31990000,'nitro_v3.jpg'),
(N'Laptop gaming HP VICTUS 15-fa2731TX','i5-13420H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',22990000,20990000,'victus.jpg');

UPDATE laptops SET image_url = 'lab1.png' WHERE id = 1;
UPDATE laptops SET image_url = 'lab2.png' WHERE id = 2;
UPDATE laptops SET image_url = 'lab3.jpg' WHERE id = 3;
UPDATE laptops SET image_url = 'lab4.png' WHERE id = 4;
UPDATE laptops SET image_url = 'lab5.png' WHERE id = 5;
UPDATE laptops SET image_url = 'lab6.png' WHERE id = 6;
UPDATE laptops SET image_url = 'lab7.png' WHERE id = 7;
UPDATE laptops SET image_url = 'lab8.png' WHERE id = 8;
UPDATE laptops SET image_url = 'lab9.png' WHERE id = 9;
UPDATE laptops SET image_url = 'lab10.png' WHERE id = 10;
UPDATE laptops SET image_url = 'lab11.png' WHERE id = 11;
UPDATE laptops SET image_url = 'lab12.png' WHERE id = 12;

UPDATE laptops
SET new_price = 25490000
WHERE name = 'Laptop gaming Lenovo LOQ 15IAX9E';

UPDATE laptops
SET new_price = 26990000
WHERE name = 'Laptop gaming Lenovo LOQ 15ARP10E';

UPDATE laptops
SET new_price = 25190000
WHERE name = 'Laptop gaming Acer Nitro Lite 16 NL16 71';




INSERT INTO laptops 
( name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES

('Laptop Gigabyte G5 MD 51S1123SH','i5-11400H','RTX 3050Ti','16 GB','512 GB','15.6 FHD','144 Hz','24490000','21990000','l2.png'),

('Laptop gaming MSI Cyborg 15 A13VEK','i7-13620H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz','26390000','24990000','l3.png'),

('Laptop gaming ASUS V16 K3607VJ RP106W','Core 7 240H','RTX 3050','16 GB','512 GB','16 WUXGA','144 Hz','24490000','22490000','l4.png'),

('Laptop gaming HP Victus 16 s0142AX','R5-7640HS','RTX 4060','32 GB','512 GB','16.1 FHD','144 Hz','34490000','24990000','l5.png'),

('Laptop gaming Gigabyte G5 MF5','i7-13620H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz','25490000','21990000','l6.png'),

('Laptop gaming MSI Thin 15 B13VE 2824VN','i5-13420H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz','22990000','20990000','l7.png'),

('Laptop gaming ASUS V16 V3607VU RP290W','Core 5 210H','RTX 4050','16 GB','512 GB','16 WUXGA','144 Hz','25490000','23990000','l8.png'),

('Laptop gaming Gigabyte G6 MF','i7-13700H','RTX 4050','16 GB','1 TB','16 FHD+','165 Hz','25890000','23990000','l9.png'),

('Laptop gaming HP VICTUS 15 fb3116AX','R7-7445HS','RTX 3050','16 GB','512 GB','15.6 FHD','144 Hz','25990000','20990000','l10.png'),

('Laptop gaming MSI Katana A15 AI B8VE','R7-8845HS','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz','28990000','23990000','l11.png'),

('Laptop gaming Lenovo LOQ 15ARP9','R5-7235HS','RTX 3050','16 GB','1 TB','15.6 FHD','144 Hz','24490000','22290000','l12.png');

INSERT INTO laptops
(name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,image_url)
VALUES
(N'Laptop gaming Gigabyte A16','i7-13620H','RTX 4050','16 GB','1 TB','16 inch WUXGA','165 Hz',29930000,28490000,'a16_1.jpg'),
(N'Laptop gaming Gigabyte A16','i5-12500H','RTX 3050','16 GB','512 GB','15.6 inch','144 Hz',29490000,27990000,'a16_2.jpg'),
(N'Laptop gaming Acer Nitro V ProPanel','R5-7535HS','RTX 3050','16 GB','512 GB','15.6 inch FHD','180 Hz',27990000,25990000,'nitro_v1.png');


