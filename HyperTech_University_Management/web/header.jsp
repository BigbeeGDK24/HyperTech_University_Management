<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<!-- Banner -->
<div class="top-banner"></div>

<!-- Header -->
<header class="main-header">

    <a href="home.jsp" class="logo">TKT</a>

    <button class="menu-btn">
        <i class="fa-solid fa-bars"></i> Danh mục
    </button>

    <div class="search-box">
        <input type="text" placeholder="Bạn cần tìm gì?">
        <button><i class="fa-solid fa-magnifying-glass"></i></button>
    </div>

    <div class="header-right">

        <a href="#" class="item">
            <i class="fa-solid fa-headset"></i>
            <div>
                Hotline<br><b>1900.5301</b>
            </div>
        </a>

        <a href="#" class="item">
            <i class="fa-solid fa-location-dot"></i>
            <div>Hệ Thống<br>Showroom</div>
        </a>

        <a href="cart.jsp" class="item">
            <i class="fa-solid fa-cart-shopping"></i>
            <div>Giỏ hàng</div>
        </a>

        <a href="order-lookup.jsp" class="item">
            <i class="fa-solid fa-magnifying-glass"></i>
            <div>Tra cứu<br>đơn hàng</div>
        </a>

        <a href="login.jsp" class="item">
            <i class="fa-solid fa-user"></i>
            <div>Đăng nhập</div>
        </a>

    </div>

</header>

<div class="sub-menu">
    <a href="#">Mua PC tặng màn 240Hz</a>
    <a href="#">Hot Deal</a>
    <a href="#">Tin tức</a>
    <a href="#">Dịch vụ kỹ thuật</a>
    <a href="#">Thu cũ đổi mới</a>
    <a href="#">Tra cứu bảo hành</a>
</div>

<div class="main-layout">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <ul class="category-list">

            <li>
                <a href="#">
                    <span>Laptop</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Main, CPU, VGA</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Case, Nguồn, Tản</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Ổ cứng, RAM, Thẻ nhớ</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Màn hình</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Bàn phím</span>
                    <span class="arrow">›</span>
                </a>
            </li>

            <li>
                <a href="#">
                    <span>Chuột + Lót chuột</span>
                    <span class="arrow">›</span>
                </a>
            </li>

        </ul>
    </div>

    <!-- BANNER -->
   <div class="home-wrapper">

    <!-- CỘT TRÁI -->
    <div class="home-banner">

        <a href="khuyenmai.jsp">
            <img class="banner-main" src="images/madaothanhcong.png">
        </a>

        <div class="banner-row">
            <a href="BestSeller.jsp">
                <img class="banner-small" src="images/laptopgaming.jpg">
            </a>

            <a href="BestSeller2.jsp">
                <img class="banner-small" src="images/chuotgaming.jpg">
            </a>
        </div>

    </div>

    <!-- CỘT PHẢI -->
    <div class="home-vertical">
        <a href="BestSeller3.jsp">
            <img class="banner-vertical" src="images/keyboard.jpg">
        </a>
    </div>

</div>

</div>