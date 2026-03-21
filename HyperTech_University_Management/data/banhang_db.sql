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
    cpu NVARCHAR(100),
    gpu NVARCHAR(100),
    ram NVARCHAR(50),
    ssd NVARCHAR(50),
    screen NVARCHAR(100),
    refresh_rate NVARCHAR(50),
    old_price DECIMAL(12,0) NOT NULL,
    new_price DECIMAL(12,0) NOT NULL,
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
(N'Màn hình',N'Màn hình máy tính'),
(N'Bàn phím',N'Bàn phím cơ'),
(N'Chuột',N'Chuột gaming');

-- ================= INSERT PRODUCTS =================
INSERT INTO products
(category_id,name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,stock,description,image)
VALUES
(1,N'Laptop gaming Gigabyte A16 i7 RTX4050','i7-13620H','RTX 4050','16 GB','1 TB','16 inch WUXGA','165 Hz',29930000,28490000,10,N'Gigabyte A16 i7 RTX4050 16GB RAM 1TB SSD','lab1.png'),
(1,N'Laptop gaming Gigabyte A16 i5 RTX3050','i5-12500H','RTX 3050','16 GB','512 GB','15.6 inch','144 Hz',29490000,27990000,10,N'Gigabyte A16 i5 RTX3050 16GB RAM 512GB SSD','lab2.png'),
(1,N'Acer Nitro V ProPanel R5 RTX3050','R5-7535HS','RTX 3050','16 GB','512 GB','15.6 inch FHD','180 Hz',27990000,25990000,10,N'Acer Nitro V Ryzen5 RTX3050','lab3.jpg'),
(1,N'Lenovo LOQ 15IAX9E i5 RTX3050','i5-12450HX','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',24490000,25490000,10,N'Lenovo LOQ i5 RTX3050','lab4.png'),
(1,N'Lenovo LOQ 15ARP10E R7 RTX4050','R7-7735HS','RTX 4050','16 GB','512 GB','15.6 inch FHD','144 Hz',28990000,26990000,10,N'Lenovo LOQ Ryzen7 RTX4050','lab5.png'),
(1,N'MSI Cyborg 15 A13UC i7 RTX3050','i7-13620H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',32990000,29990000,10,N'MSI Cyborg RTX3050','lab6.png'),
(1,N'Acer Nitro Lite 16 NL16 71','i5-13420H','RTX 3050','16 GB','512 GB','16 inch FHD+','165 Hz',23990000,25190000,10,N'Acer Nitro Lite RTX3050','lab7.png'),
(1,N'Gigabyte A16 i7 RTX4050 512GB','i7-13620H','RTX 4050','16 GB','512 GB','16 inch FHD+ IPS','165 Hz',29990000,27990000,10,N'Gigabyte A16 RTX4050','lab8.png'),
(1,N'Acer Nitro V i5 RTX4050 32GB','i5-13420H','RTX 4050','32 GB','512 GB','15.6 inch FHD','180 Hz',31990000,29490000,10,N'Acer Nitro V RTX4050 32GB','lab9.png'),
(1,N'HP Victus 15 i5 RTX3050','i5-13420H','RTX 3050','16 GB','512 GB','15.6 inch FHD','144 Hz',22990000,20990000,10,N'HP Victus RTX3050','lab12.png'),
(1,N'Laptop Gigabyte G5 MD 51S1123SH','i5-11400H','RTX 3050Ti','16 GB','512 GB','15.6 FHD','144 Hz',24490000,21990000,10,N'Gigabyte G5 RTX3050Ti','l2.png'),
(1,N'Laptop gaming MSI Cyborg 15 A13VEK','i7-13620H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz',26390000,24990000,10,N'MSI Cyborg RTX4050','l3.png'),
(1,N'Laptop gaming ASUS V16 K3607VJ RP106W','Core 7 240H','RTX 3050','16 GB','512 GB','16 WUXGA','144 Hz',24490000,22490000,10,N'ASUS V16 RTX3050','l4.png'),
(1,N'Laptop gaming HP Victus 16 s0142AX','R5-7640HS','RTX 4060','32 GB','512 GB','16.1 FHD','144 Hz',34490000,24990000,10,N'HP Victus RTX4060','l5.png'),
(1,N'Laptop gaming Gigabyte G5 MF5','i7-13620H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz',25490000,21990000,10,N'Gigabyte G5 RTX4050','l6.png'),
(1,N'Laptop gaming MSI Thin 15 B13VE 2824VN','i5-13420H','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz',22990000,20990000,10,N'MSI Thin RTX4050','l7.png'),
(1,N'Laptop gaming ASUS V16 V3607VU RP290W','Core 5 210H','RTX 4050','16 GB','512 GB','16 WUXGA','144 Hz',25490000,23990000,10,N'ASUS V16 RTX4050','l8.png'),
(1,N'Laptop gaming Gigabyte G6 MF','i7-13700H','RTX 4050','16 GB','1 TB','16 FHD+','165 Hz',25890000,23990000,10,N'Gigabyte G6 RTX4050','l9.png'),
(1,N'Laptop gaming HP VICTUS 15 fb3116AX','R7-7445HS','RTX 3050','16 GB','512 GB','15.6 FHD','144 Hz',25990000,20990000,10,N'HP Victus Ryzen7 RTX3050','l10.png'),
(1,N'Laptop gaming MSI Katana A15 AI B8VE','R7-8845HS','RTX 4050','16 GB','512 GB','15.6 FHD','144 Hz',28990000,23990000,10,N'MSI Katana RTX4050','l11.png'),
(1,N'Laptop gaming Lenovo LOQ 15ARP9','R5-7235HS','RTX 3050','16 GB','1 TB','15.6 FHD','144 Hz',24490000,22290000,10,N'Lenovo LOQ RTX3050','l12.png'),
(1,N'Laptop gaming Acer Predator Helios PHN',N'Ultra 7 255HX', N'RTX 5060', N'32 GB', N'1 TB',N'16 inch 2K+', N'240 Hz',56990000, 56990000,10,'Acer Predator Helios PHN', 'l16.png'),
(1,N'Laptop gaming Acer Nitro ProPanel ANV15',
N'R7-7735HS', N'RTX 4050', N'16 GB', N'512 GB',
N'15.6 inch FHD', N'180 Hz',
31990000,30490000,10,
N'Laptop gaming Acer Nitro ProPanel ANV15','l17.png'),

(1,N'Laptop gaming Acer Predator Helios Neo 16S AI',
N'Ultra 9 275HX', N'RTX 5070Ti', N'64 GB', N'2 TB',
N'16 inch WQXGA OLED', N'240 Hz',
101990000,95990000,10,
N'Laptop gaming Acer Predator Helios Neo 16S AI','l18.png'),

(1,N'Laptop gaming MSI Stealth 18 HX AI',
N'Ultra 9 275HX', N'RTX 5080', N'32 GB', N'2 TB',
N'18 inch UHD+ MiniLED', N'120 Hz',
107990000,102990000,10,
N'Laptop gaming MSI Stealth 18 HX AI','l19.png'),

(1,N'Laptop gaming Acer Gaming Nitro 16S AI',
N'R7 AI 350', N'RTX 5060', N'16 GB', N'512 GB',
N'16 inch FHD+', N'180 Hz',
50990000,48790000,10,
N'Laptop gaming Acer Gaming Nitro 16S AI','l20.png'),

(1,N'Laptop gaming Acer Predator Triton 14 AI',
N'Ultra 9 288V', N'RTX 5070', N'32 GB', N'2 TB',
N'14.5 inch 2.8K OLED', N'120 Hz',
93580000,90490000,10,
N'Laptop gaming Acer Predator Triton 14 AI','l21.png'),

(1,N'Laptop gaming Acer Predator Triton Neo 16',
N'Ultra 7 155H', N'RTX 4060', N'32 GB', N'1 TB',
N'16 inch 2.5K', N'240 Hz',
52490000,49400000,10,
N'Laptop gaming Acer Predator Triton Neo 16','l22.png'),

(1,N'Laptop gaming MSI Sword 16 HX B14VEKG 856VN',
N'Intel Core i7-14700HX', N'RTX 4050 6GB', N'16 GB', N'1 TB',
N'16 inch', N'165 Hz',
35990000,31190000,10,
N'Laptop gaming MSI Sword 16 HX B14VEKG 856VN','l23.png'),

(1,N'Laptop gaming HP OMEN 16-am0178TX BX8Y4PA',
N'Intel Core Ultra 7 255H', N'RTX 5060 8GB', N'16 GB', N'512 GB',
N'16 inch', N'165 Hz',
40890000,39990000,10,
N'Laptop gaming HP OMEN 16-am0178TX BX8Y4PA','l24.png'),

(1,N'Laptop gaming ASUS ROG Strix SCAR 18 G835LW SA193W',
N'Intel Core Ultra 9 275HX', N'RTX 5080 16GB', N'32 GB', N'1 TB',
N'18 inch', N'240 Hz',
88390000,84990000,10,
N'Laptop gaming ASUS ROG Strix SCAR 18 G835LW SA193W','l25.png'),

(1,N'Laptop gaming Lenovo Legion 5 15AHP10 83M0002XVN',
N'AMD Ryzen 7 260', N'RTX 5050', N'24 GB', N'512 GB',
N'15 inch', N'180 Hz',
39490000,36990000,10,
N'Laptop gaming Lenovo Legion 5 15AHP10 83M0002XVN','l26.png'),

(1,N'Laptop gaming ASUS ROG Zephyrus G14 GA403WM QS058WS',
N'AMD Ryzen 9 8945HS', N'RTX 4070', N'32 GB', N'1 TB',
N'14 inch', N'180 Hz',
58990000,55990000,10,
N'Laptop gaming ASUS ROG Zephyrus G14 GA403WM QS058WS','l27.png')

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





INSERT INTO products
(category_id,name,cpu,gpu,ram,ssd,screen,refresh_rate,old_price,new_price,stock,description,image)
VALUES
(1,N'Laptop gaming Acer Predator Helios PHN',N'Ultra 7 255HX', N'RTX 5060', N'32 GB', N'1 TB',N'16 inch 2K+', N'240 Hz',56990000, 56990000,10,'Acer Predator Helios PHN', 'l16.png'),
(1,N'Laptop gaming Lenovo Legion 5 15AHP10 83M0002XVN',
N'AMD Ryzen 7 260', N'RTX 5050', N'24 GB', N'512 GB',
N'15 inch', N'180 Hz',
39490000,36990000,10,
N'Laptop gaming Lenovo Legion 5 15AHP10 83M0002XVN','l26.png'),

(1,N'Laptop gaming ASUS ROG Zephyrus G14 GA403WM QS058WS',
N'AMD Ryzen 9 8945HS', N'RTX 4070', N'32 GB', N'1 TB',
N'14 inch', N'180 Hz',
58990000,55990000,10,
N'Laptop gaming ASUS ROG Zephyrus G14 GA403WM QS058WS','l27.png');





INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột Logitech G502 Hero Gaming', 1090000, 890000, 'm1.png');

DELETE FROM products
WHERE name = N'Chuột Logitech G502 Hero Gaming';

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột gaming có dây Rapoo V260 Pro', 499000, 329000, 'm13.jpg');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột DareU Không dây EM911T RGB Đen', 690000, 400000, 'm14.jpg'),
(7, N'Chuột ATK A9 SE Tri-mode Nearlink Wireless Black', 590000, 490000, 'm15.jpg');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột ASUS ROG Strix Impact III', 1090000, 990000, 'm16.jpg'),
(7, N'Chuột DareU Không dây EM911T RGB Trắng', 690000, 400000, 'm17.jpg'),
(7, N'Chuột Razer DeathAdder Essential White', 790000, 410000, 'm18.png'),
(7, N'Chuột Razer Cobra', 1049000, 990000, 'm19.jpg');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột Razer Basilisk V3 Pro 35K Black', 4490000, 4190000, 'm20.png'),
(7, N'Chuột Razer Basilisk V3 Pro White', 4090000, 3390000, 'm21.gif'),
(7, N'Chuột Logitech G502 X Plus LightSpeed Black', 3590000, 3100000, 'm22.png'),
(7, N'Chuột không dây Corsair Nightsabre RGB', 3990000, 3590000, 'm23.png'),
(7, N'Chuột không dây Corsair Darkstar RGB', 3990000, 3590000, 'm24.png');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES
(7, N'Chuột văn phòng Logitech M331 Silent Black', 349000, 340000, 'm25.png'),
(7, N'Chuột văn phòng Logitech M331 Silent Blue', 400000, 340000, 'm26.png'),
(7, N'Chuột văn phòng Logitech M331 Silent Red', 490000, 340000, 'm27.jpg'),
(7, N'Chuột văn phòng Logitech Pebble Mouse 2 M350S Rose', 699000, 450000, 'm28.jpg'),
(7, N'Chuột văn phòng Logitech Pebble Mouse 2 M350S White', 699000, 450000, 'm29.png'),
(7, N'Chuột văn phòng Logitech MX Anywhere 3S Rose', 1750000, 1550000, 'm30.png'),
(7, N'Chuột văn phòng Logitech MX Anywhere 3S Pale Grey', 1750000, 1550000, 'm31.png'),
(7, N'Chuột văn phòng Rapoo M20 Wireless', 150000, 90000, 'm32.png'),
(7, N'Chuột văn phòng Logitech M650 Signature Graphite', 849000, 645000, 'm33.png'),
(7, N'Chuột văn phòng Logitech M650 Off White', 800000, 645000, 'm34.png');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES 
(7, N'Chuột văn phòng Rapoo M20 Wireless', 150000, 90000, 'm32.jpg');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES
(7, N'Chuột văn phòng MonsGeek D1 Pink', 299000, 120000, 'm35.png');

INSERT INTO products (category_id, name, old_price, new_price, image)
VALUES
(7, N'Chuột văn phòng MonsGeek D1 Black', 299000, 150000, 'm36.png');
-- ================= ADD DISCOUNT (FIX CHUẨN) =================

-- 1. FIX GIÁ TRƯỚC
UPDATE products
SET new_price = old_price * 0.95
WHERE new_price >= old_price;

-- 2. XÓA DISCOUNT CŨ (đúng thứ tự FK)
DELETE FROM product_discounts;
DELETE FROM discounts;

-- 3. INSERT DISCOUNT
INSERT INTO discounts (name, discount_percent, start_date, end_date)
VALUES (N'Sale toàn shop 5%', 5, GETDATE()-1, GETDATE()+30);

-- 4. LẤY ID AN TOÀN
DECLARE @discountId INT;
SELECT @discountId = MAX(id) FROM discounts;

-- 5. GÁN CHO TOÀN BỘ PRODUCT
INSERT INTO product_discounts (product_id, discount_id)
SELECT id, @discountId FROM products;