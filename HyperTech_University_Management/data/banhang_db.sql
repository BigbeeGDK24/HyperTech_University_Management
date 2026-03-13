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

TRUNCATE TABLE laptops;

INSERT INTO laptops
(name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,image_url)
VALUES
(N'Laptop gaming MSI Cyborg 15 B2RWFKG 047VN','Intel Core i5-12450H','RTX 4050','16 GB','512GB','15 inch','165 Hz',33290000,27990000,'l13.png'),

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming Gigabyte A16','i7-13620H','RTX 4050','16 GB','512 GB','16 inch FHD+ IPS','165 Hz',29990000,27990000,'lab8.png');

INSERT INTO laptops
(name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,image_url)
VALUES
(N'
Laptop gaming ASUS V16 V3607VH RP024W','i5- Processor 210H 2.2 GHz','RTX 5050 ','16 GB','512 GB','16  inch','144 Hz',28490000,27100000,'l15.png');

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming Acer Predator Helios PHN',
N'Ultra 7 255HX', N'RTX 5060', N'32 GB', N'1 TB',
N'16 inch 2K+', N'240 Hz',
56990000, 56990000, 'l16.png'),

(N'Laptop gaming Acer Nitro ProPanel ANV15',
N'R7-7735HS', N'RTX 4050', N'16 GB', N'512 GB',
N'15.6 inch FHD', N'180 Hz',
31990000, 30490000, 'l17.jpg'),

(N'Laptop gaming Acer Predator Helios Neo 16S AI',
N'Ultra 9 275HX', N'RTX 5070Ti', N'64 GB', N'2 TB',
N'16 inch WQXGA OLED', N'240 Hz',
101990000, 95990000, 'l18.png'),

(N'Laptop gaming MSI Stealth 18 HX AI',
N'Ultra 9 275HX', N'RTX 5080', N'32 GB', N'2 TB',
N'18 inch UHD+ MiniLED', N'120 Hz',
107990000, 102990000, 'l19.png'),

(N'Laptop gaming Acer Gaming Nitro 16S AI',
N'R7 AI 350', N'RTX 5060', N'16 GB', N'512 GB',
N'16 inch FHD+', N'180 Hz',
50990000, 48790000, 'l20.png'),

(N'Laptop gaming Acer Predator Triton 14 AI',
N'Ultra 9 288V', N'RTX 5070', N'32 GB', N'2 TB',
N'14.5 inch 2.8K OLED', N'120 Hz',
93580000, 90490000, 'l21.jpg');

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming Acer Predator Triton Neo 16',
N'Ultra 7 155H',
N'RTX 4060',
N'32 GB',
N'1 TB',
N'16 inch 2.5K',
N'240 Hz',
52490000,
49400000,
'l22.png');

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming MSI Sword 16 HX B14VEKG 856VN',
N'Intel Core i7-14700HX',
N'RTX 4050 6GB',
N'16 GB',
N'1 TB',
N'16 inch',
N'165 Hz',
35990000,
31190000,
'l23.png');

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming HP OMEN 16-am0178TX BX8Y4PA',
N'Intel Core Ultra 7 255H',
N'RTX 5060 8GB',
N'16 GB',
N'512 GB',
N'16 inch',
N'165 Hz',
40890000,
39990000,
'l24.png');

INSERT INTO laptops
(name, cpu, gpu, ram, ssd, screen, refresh_rate, old_price, new_price, image_url)
VALUES
(N'Laptop gaming ASUS ROG Strix SCAR 18 G835LW SA193W',
N'Intel Core Ultra 9 275HX',
N'RTX 5080 16GB',
N'32 GB',
N'1 TB',
N'18 inch',
N'240 Hz',
88390000,
84990000,
'l25.png');