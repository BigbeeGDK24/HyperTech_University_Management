<%-- 
    Document   : BestSeller.jsp
    Created on : Mar 8, 2026, 9:42:38 AM
    Author     : truon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/BestSeller.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    </head>
    <body>
        <div class="bestseller-banner">
            <img src="images/BestSeller1.png" alt="banner">
        </div>

        <div class="promo-section">

            <div class="promo-container">

                <div class="promo-item">
                    <img src="images/voucher1.png" alt="Voucher">
                </div>


                <div class="promo-item">
                    <img src="images/BestSeller2.png" alt="Best Seller">
                </div>

                <div class="promo-item">
                    <img src="images/BestSeller3.png" alt="Duoi 25 Trieu">
                </div>

                <div class="promo-item">
                    <img src="images/BestSeller4.png" alt="Duoi 30 Trieu">
                </div>

                <div class="promo-item">
                    <img src="images/BestSeller5.png" alt="Tren 30 Trieu">
                </div>

            </div>

        </div>
        <div class="best-title"> 
            <img src="images/BestSeller.png" alt="Best Seller"> 
        </div>
        <div class="background-section">

            <div class="product-row">
                <!-- 6 card đầu -->
                <div class="product-card">
                    <img src="images/lab1.png" alt="Laptop">

                    <h3>Laptop gaming Gigabyte A16</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13620H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i>1 TB</span>
                        <span><i class="fa-solid fa-tv"></i>16 inch WUXGA</span>
                        <span><i class="fa-solid fa-gauge-high"></i>165 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">29.930.000₫</div>
                        <div class="new-price">
                            28.490.000₫
                            <span class="discount">-5%</span>
                        </div>
                    </div>
                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab2.png" alt="Laptop ASUS TUF">

                    <h3>Laptop gaming Gigabyte A16</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-12500H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">29.490.000₫</div>
                        <div class="new-price">
                            27.990.000₫
                            <span class="discount">-5%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab3.jpg">
                    <h3>Laptop gaming Acer Nitro V ProPanel</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R5-7535HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 180 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">27.990.000₫</div>
                        <div class="new-price">
                            25.990.000₫
                            <span class="discount">-7%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab4.png">
                    <h3>Laptop gaming Lenovo LOQ 15IAX9E</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-12450HX</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">24.490.000₫</div>
                        <div class="new-price">
                            23.490.000₫
                            <span class="discount">-4%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab5.png">
                    <h3>Laptop gaming Lenovo LOQ 15ARP10E</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R7-7735HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">28.990.000₫</div>
                        <div class="new-price">
                            24.790.000₫
                            <span class="discount">-14%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab6.png">
                    <h3>Laptop gaming MSI Cyborg 15 A13UC</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13620H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">32.990.000₫</div>
                        <div class="new-price">
                            29.990.000₫
                            <span class="discount">-9%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab7.png" alt="Acer Nitro Lite 16">

                    <h3>Laptop gaming Acer Nitro Lite 16 NL16 71</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-13420H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch FHD+</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 165 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">23.990.000₫</div>
                        <div class="new-price">
                            21.990.000₫
                            <span class="discount">-8%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab8.png" alt="Gigabyte A16">

                    <h3>Laptop gaming Gigabyte A16</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13620H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch FHD+ IPS</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 165 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">29.990.000₫</div>
                        <div class="new-price">
                            27.990.000₫
                            <span class="discount">-6%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab9.png" alt="Acer Nitro V">

                    <h3>Laptop gaming Acer Nitro V ProPanel</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-13420H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 32 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 180 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">31.990.000₫</div>
                        <div class="new-price">
                            29.490.000₫
                            <span class="discount">-7%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab10.png" alt="Lenovo Legion">

                    <h3>Laptop gaming Lenovo Legion 5 15IRX10</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-14700HX</span>
                        <span><i class="fa-solid fa-server"></i> RTX 5070</span>
                        <span><i class="fa-solid fa-memory"></i> 24 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 1 TB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.1 inch WQXGA OLED</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 165 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">49.990.000₫</div>
                        <div class="new-price">
                            45.990.000₫
                            <span class="discount">-8%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab11.png" alt="Acer Nitro V 16S">

                    <h3>Laptop gaming Acer Nitro V 16S ProPanel</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R7 260</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 1 TB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch WUXGA</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 180 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">34.990.000₫</div>
                        <div class="new-price">
                            31.990.000₫
                            <span class="discount">-9%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/lab12.png" alt="HP Victus">

                    <h3>Laptop gaming HP VICTUS 15-fa2731TX</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-13420H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">22.990.000₫</div>
                        <div class="new-price">
                            20.990.000₫
                            <span class="discount">-9%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>
            </div>
        </div>

        <div class="under25-section">

            <div class="price-section-title">
                <img src="images/duoi25trieu.png" alt="Laptop dưới 25 triệu">
            </div>

            <div class="product-row">

                <div class="product-card">
                    <img src="images/l1.jpg" alt="Gigabyte G5 GD">

                    <h3>Laptop Gigabyte G5 GD 51S1123SH</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-11400H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">26.990.000₫</div>
                        <div class="new-price">
                            23.190.000₫
                            <span class="discount">-14%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l2.png" alt="Gigabyte G5 MD">

                    <h3>Laptop Gigabyte G5 MD 51S1123SH</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-11400H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050Ti</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">24.490.000₫</div>
                        <div class="new-price">
                            21.990.000₫
                            <span class="discount">-10%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l3.png" alt="MSI Cyborg 15">

                    <h3>Laptop gaming MSI Cyborg 15 A13VEK</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13620H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">26.390.000₫</div>
                        <div class="new-price">
                            24.990.000₫
                            <span class="discount">-5%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l4.png" alt="ASUS V16">

                    <h3>Laptop gaming ASUS V16 K3607VJ RP106W</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> Core 7 240H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch WUXGA</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">24.490.000₫</div>
                        <div class="new-price">
                            22.490.000₫
                            <span class="discount">-8%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l5.png" alt="HP Victus">

                    <h3>Laptop gaming HP Victus 16 s0142AX</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R5-7640HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4060</span>
                        <span><i class="fa-solid fa-memory"></i> 32 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 16.1 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">34.490.000₫</div>
                        <div class="new-price">
                            24.990.000₫
                            <span class="discount">-28%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l6.png" alt="Gigabyte G5 MF5">

                    <h3>Laptop gaming Gigabyte G5 MF5</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13620H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">25.490.000₫</div>
                        <div class="new-price">
                            21.990.000₫
                            <span class="discount">-14%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l7.png" alt="MSI Thin 15">

                    <h3>Laptop gaming MSI Thin 15 B13VE 2824VN</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i5-13420H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">22.990.000₫</div>
                        <div class="new-price">
                            20.990.000₫
                            <span class="discount">-9%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>


                <div class="product-card">
                    <img src="images/l8.png" alt="ASUS V16">

                    <h3>Laptop gaming ASUS V16 V3607VU RP290W</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> Core 5 210H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch WUXGA</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">25.490.000₫</div>
                        <div class="new-price">
                            23.990.000₫
                            <span class="discount">-6%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>


                <div class="product-card">
                    <img src="images/l9.png" alt="Gigabyte G6">

                    <h3>Laptop gaming Gigabyte G6 MF</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> i7-13700H</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 1 TB</span>
                        <span><i class="fa-solid fa-tv"></i> 16 inch FHD+</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 165 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">25.890.000₫</div>
                        <div class="new-price">
                            23.990.000₫
                            <span class="discount">-7%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>


                <div class="product-card">
                    <img src="images/l10.png" alt="HP Victus">

                    <h3>Laptop gaming HP VICTUS 15 fb3116AX</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R7-7445HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">25.990.000₫</div>
                        <div class="new-price">
                            20.990.000₫
                            <span class="discount">-19%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l11.png" alt="MSI Katana">

                    <h3>Laptop gaming MSI Katana A15 AI B8VE</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R7-8845HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 4050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 512 GB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">28.990.000₫</div>
                        <div class="new-price">
                            23.990.000₫
                            <span class="discount">-17%</span>
                        </div>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>

                <div class="product-card">
                    <img src="images/l12.png" alt="Lenovo LOQ 15ARP9">

                    <h3>Laptop gaming Lenovo LOQ 15ARP9</h3>

                    <div class="spec-box">
                        <span><i class="fa-solid fa-microchip"></i> R5-7235HS</span>
                        <span><i class="fa-solid fa-server"></i> RTX 3050</span>
                        <span><i class="fa-solid fa-memory"></i> 16 GB</span>
                        <span><i class="fa-solid fa-hard-drive"></i> 1 TB</span>
                        <span><i class="fa-solid fa-tv"></i> 15.6 inch FHD</span>
                        <span><i class="fa-solid fa-gauge-high"></i> 144 Hz</span>
                    </div>

                    <div class="price-box">
                        <div class="old-price">24.490.000₫</div>

                        <div class="new-price">
                            22.290.000₫
                            <span class="discount">-9%</span>
                        </div>
                    </div>

                    <div class="rating">
                        <span class="star">0.0 ★</span>
                        <span class="review">(0 đánh giá)</span>
                    </div>

                    <button class="buy-btn">Mua ngay</button>
                </div>
            </div>
        </div>

    </body>
</html>
