<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<link rel="stylesheet" href="css/header.css">
<link rel="stylesheet" href="css/home.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"><!-- comment -->


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
            <div>Hotline<br><b>1900.5301</b></div>
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

        <div class="item account">
            <i class="fa-solid fa-user"></i>
            <div>Đăng nhập</div>

            <div class="account-dropdown">

                <div class="account-top">
                    <i class="fa-regular fa-hand"></i>
                    <span>Xin chào, vui lòng đăng nhập</span>
                </div>

                <div class="account-buttons">
                    <a href="#" class="btn-login" id="openLoginBtn">
                        ĐĂNG NHẬP
                    </a>
                    <a href="#" class="btn-register" id="openRegisterBtn">ĐĂNG KÝ</a>
                </div>

                <div class="account-divider"></div>

                <div class="account-help">
                    <i class="fa-regular fa-circle-question"></i>
                    <span>Trợ giúp</span>
                </div>

            </div>
        </div>
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

<!-- ================= MAIN LAYOUT ================= -->

<div class="main-layout">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <ul class="category-list">
            <li><a href="#"><span>Laptop</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Main, CPU, VGA</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Case, Nguồn, Tản</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Ổ cứng, RAM, Thẻ nhớ</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Màn hình</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Bàn phím</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Chuột + Lót chuột</span><span class="arrow">›</span></a></li>
        </ul>
    </div>

    <!-- CONTENT -->
    <div class="content">

        <!-- Banner khu vực -->
        <div class="home-wrapper">

            <div class="home-left">
                <a href="li-xi-tet-2026.jsp">
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

            <div class="home-middle">
                <a href="BestSeller3.jsp">
                    <img class="banner-keyboard" src="images/keyboard_new.jpg">
                </a>
            </div>

            <!-- Fixed RTX -->
            <div class="rtx-side">
                <a href="LaptopRTX.jsp">
                    <img src="images/laptoprtx.jpg" alt="Laptop RTX">
                </a>
            </div>

        </div>

        <!-- ================= SẢN PHẨM ĐÃ XEM ================= -->
        <section class="section-viewed">
            <div id="section_viewed">

                <div class="section-heading">
                    <h2>Sản phẩm đã xem</h2>
                </div>

                <div id="viewed-products">
                </div>

            </div>
        </section>

    </div>
</div>



<script>
    window.addEventListener("load", function () {

        const banner = document.querySelector(".fixed-rtx");
        const topBanner = document.querySelector(".top-banner");
        const mainHeader = document.querySelector(".main-header");
        const subMenu = document.querySelector(".sub-menu");

        const spacing = 60;

        // Tính vị trí ban đầu
        const baseTop =
                topBanner.offsetHeight +
                mainHeader.offsetHeight +
                subMenu.offsetHeight +
                spacing;

        // Set vị trí ban đầu
        banner.style.top = baseTop + "px";

        // Scroll effect
        window.addEventListener("scroll", function () {
            const offset = window.scrollY * 0.2;  // tốc độ trôi
            banner.style.top = (baseTop + offset) + "px";
        });

    });
</script>

<div class="login-modal" id="loginModal">
    <div class="login-box">

        <!-- HEADER -->
        <div class="login-header">
            <h2>ĐĂNG NHẬP HOẶC TẠO TÀI KHOẢN</h2>
            <span class="close-btn" id="closeModal">&times;</span>
        </div>

        <!-- FORM -->
        <div class="login-body">
            <input type="text" placeholder="Email">
            <input type="password" placeholder="Mật khẩu">

            <div class="forgot">
                <a href="#">Quên mật khẩu?</a>
            </div>

            <button class="login-submit">ĐĂNG NHẬP</button>
        </div>

        <!-- DIVIDER -->
        <div class="divider">
            <span>Hoặc đăng nhập bằng</span>
        </div>

        <!-- SOCIAL LOGIN -->
        <div class="social-login">
            <button class="google-btn">
                <i class="fa-brands fa-google"></i>
                <span>Google</span>
            </button>

            <button class="facebook-btn">
                <i class="fa-brands fa-facebook-f"></i>
                <span>Facebook</span>
            </button>
        </div>

        <!-- FOOTER -->
        <div class="login-footer">
            Bạn chưa có tài khoản?
            <a href="register.jsp">Đăng ký ngay!</a>
        </div>

    </div>
</div>

<div class="login-modal" id="registerModal">
    <div class="login-box">

        <!-- HEADER -->
        <div class="login-header">
            <h2>ĐĂNG KÝ TÀI KHOẢN</h2>
            <span class="close-btn" id="closeRegister">&times;</span>
        </div>

        <!-- LINK SĐT -->
        <div style="text-align:right; margin-bottom:15px;">
            <a href="#" style="font-size:14px; color:#666; text-decoration:underline;">
                Đăng ký bằng số điện thoại
            </a>
        </div>

        <!-- FORM -->
        <div class="login-body">
            <input type="text" placeholder="Email">
            <input type="text" placeholder="Họ">
            <input type="text" placeholder="Tên">
            <input type="password" placeholder="Mật khẩu">

            <button class="login-submit">TẠO TÀI KHOẢN</button>
        </div>

        <!-- DIVIDER -->
        <div class="divider">
            <span>hoặc đăng ký bằng</span>
        </div>

        <!-- SOCIAL LOGIN -->
        <div class="social-login">
            <button class="google-btn">
                <i class="fa-brands fa-google"></i>
                <span>Google</span>
            </button>

            <button class="facebook-btn">
                <i class="fa-brands fa-facebook-f"></i>
                <span>Facebook</span>
            </button>
        </div>

        <!-- FOOTER -->
        <div style="text-align:center; margin-top:20px; font-size:14px;">
            Bạn đã có tài khoản?
            <a href="#" id="switchToLogin" style="color:#0d6efd; text-decoration:none;">
                Đăng nhập!
            </a>
        </div>

    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {

        const modal = document.getElementById("loginModal");
        const openBtn = document.getElementById("openLoginBtn");
        const closeBtn = document.getElementById("closeModal");

        openBtn.addEventListener("click", function (e) {
            e.preventDefault();
            modal.classList.add("show");
            document.body.style.overflow = "hidden";
        });

        closeBtn.addEventListener("click", function () {
            modal.classList.remove("show");
            document.body.style.overflow = "auto";
        });

        window.addEventListener("click", function (e) {
            if (e.target === modal) {
                modal.classList.remove("show");
                document.body.style.overflow = "auto";
            }
        });

    });
</script>


<script>
    const registerModal = document.getElementById("registerModal");
    const openRegisterBtn = document.getElementById("openRegisterBtn");
    const closeRegister = document.getElementById("closeRegister");

    openRegisterBtn.addEventListener("click", function (e) {
        e.preventDefault();
        registerModal.classList.add("show");
        document.body.style.overflow = "hidden";
    });

    closeRegister.addEventListener("click", function () {
        registerModal.classList.remove("show");
        document.body.style.overflow = "auto";
    });
</script>

<script>
const switchToLogin = document.getElementById("switchToLogin");
const loginModal = document.getElementById("loginModal");

switchToLogin.addEventListener("click", function(e){
    e.preventDefault();
    registerModal.classList.remove("show");
    loginModal.classList.add("show");
});
</script>