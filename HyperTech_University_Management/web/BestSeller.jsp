<%-- 
    Document   : BestSeller.jsp
    Created on : Mar 8, 2026, 9:42:38 AM
    Author     : truon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <c:forEach items="${list}" var="p">
                    <div class="product-card">
                        <img src="images/${p.image_url}" alt="${p.name}">
                        <h3>${p.name}</h3>

                        <div class="spec-box">
                            <span><i class="fa-solid fa-microchip"></i> ${p.cpu}</span>
                            <span><i class="fa-solid fa-server"></i> ${p.gpu}</span>
                            <span><i class="fa-solid fa-memory"></i> ${p.ram}</span>
                            <span><i class="fa-solid fa-hard-drive"></i> ${p.ssd}</span>
                            <span><i class="fa-solid fa-tv"></i> ${p.screen}</span>
                            <span><i class="fa-solid fa-gauge-high"></i> ${p.refresh_rate}</span>
                        </div>

                        <div class="price-box">
                            <div class="old-price">${p.old_price}₫</div>
                            <div class="new-price">
                                ${p.new_price}
                                <span class="discount">-5%</span>
                            </div>
                        </div>
                        <button class="buy-btn">Mua ngay</button>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
