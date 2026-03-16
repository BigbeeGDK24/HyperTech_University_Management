<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

            <%
                model.CartDTO cart = (model.CartDTO) session.getAttribute("CART");

                int count = 0;

                if (cart != null && cart.getCart() != null) {
                    count = cart.getCart().size();
                }
            %>

            <div>Giỏ hàng (<%=count%>)</div>

        </a>

        <a href="order-lookup.jsp" class="item">
            <i class="fa-solid fa-magnifying-glass"></i>
            <div>Tra cứu<br>đơn hàng</div>
        </a>

        <div class="item account">
            <i class="fa-solid fa-user"></i>

            <%
                Object user = session.getAttribute("user");
                if (user != null) {
            %>

            <div>
                <%= ((model.UserDTO) user).getUsername()%>
            </div>

            <%
            } else {
            %>

            <div id="openLoginBtn" style="cursor:pointer;">
                Đăng nhập
            </div>

            <%
                }
            %>

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
            <li><a href="ProductController?action=list"><span>Laptop</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>VGA</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Case</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>RAM</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Màn hình</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Bàn phím</span><span class="arrow">›</span></a></li>
            <li><a href="#"><span>Chuột</span><span class="arrow">›</span></a></li>
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
                    <a href="ProductController?action=list">
                        <img class="banner-small" src="images/laptopgaming.jpg">
                    </a>
                    <a href="ProductController?action=searchProduct&category=2">
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
            <span class="close-btn" id="closeModal" style="cursor:pointer;">&times;</span>
        </div>

        <!-- FORM -->
        <form action="MainController" method="post">

            <input type="hidden" name="action" value="login">
            <div class="login-body">
                <input type="text" placeholder="Email" name="txtUsername">
                <input type="password" placeholder="Mật khẩu" name="txtPassword">

                <div class="forgot">
                    <a href="#">Quên mật khẩu?</a>
                </div>
                <c:if test="${not empty sessionScope.message}">
                    <span style="color:red">${sessionScope.message}</span>
                </c:if>
                <%
                    session.removeAttribute("message");
                    session.removeAttribute("showLoginModal");
                %>
                <button class="login-submit">ĐĂNG NHẬP</button>
            </div>
        </form>
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
            <a href="#" id="openRegister">Đăng ký ngay!</a>
        </div>
    </div> <!-- login-box -->
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
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="addUser">

            <div class="login-body">

                <input type="text" name="username" placeholder="Họ và Tên" required>

                <input type="email" name="email" placeholder="Email" required>

                <input type="password" name="password" placeholder="Mật khẩu" required>

                <input type="password" name="confirm_password" placeholder="Nhập lại mật khẩu" required>

                <button type="submit" class="login-submit">ĐĂNG KÝ</button>

            </div>
        </form>

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

        const loginModal = document.getElementById("loginModal");
        const registerModal = document.getElementById("registerModal");

        const openLoginBtn = document.getElementById("openLoginBtn");
        const closeModal = document.getElementById("closeModal");

        const openRegister = document.getElementById("openRegister");
        const switchToLogin = document.getElementById("switchToLogin");
        const closeRegister = document.getElementById("closeRegister");

        // mở bảng đăng nhập
        if (openLoginBtn) {
            openLoginBtn.addEventListener("click", function (e) {
                e.preventDefault();
                loginModal.classList.add("show");
            });
        }

        // đóng bảng đăng nhập
        if (closeModal) {
            closeModal.addEventListener("click", function () {
                loginModal.classList.remove("show");
            });
        }

        // đăng nhập -> đăng ký
        if (openRegister) {
            openRegister.addEventListener("click", function (e) {
                e.preventDefault();
                loginModal.classList.remove("show");
                registerModal.classList.add("show");
            });
        }

        // đăng ký -> đăng nhập
        if (switchToLogin) {
            switchToLogin.addEventListener("click", function (e) {
                e.preventDefault();
                registerModal.classList.remove("show");
                loginModal.classList.add("show");
            });
        }

        // đóng đăng ký
        if (closeRegister) {
            closeRegister.addEventListener("click", function () {
                registerModal.classList.remove("show");
            });
        }

    });
</script>

<c:if test="${sessionScope.showLoginModal}">
    <script>

        document.addEventListener("DOMContentLoaded", function () {

            let toastEl = document.getElementById("loginToast");
            let toast = new bootstrap.Toast(toastEl);

            toast.show();

        });

    </script>
</c:if>
