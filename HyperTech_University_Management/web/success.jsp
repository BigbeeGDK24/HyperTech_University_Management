<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Đặt hàng thành công</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>

            body{
                background: linear-gradient(135deg,#eef2ff,#fdfbfb);
                font-family:Segoe UI;
            }

            .wrapper{
                width:750px;
                margin:auto;
                margin-top:60px;
                background:white;
                padding:40px;
                border-radius:16px;
                box-shadow:0 10px 30px rgba(0,0,0,0.1);
                text-align:center;
                animation:fadeIn 0.6s ease;
                position:relative;
            }

            /* 🐱 mèo meme */
            .cat{
                position:absolute;
                width:80px;
                top:-30px;
                right:-30px;
            }

            .steps{
                display:flex;
                justify-content:space-between;
                background:#fdeaea;
                padding:18px;
                border-radius:10px;
                margin-bottom:30px;
            }

            .step{
                flex:1;
                text-align:center;
                color:#666;
            }

            .step.active{
                color:#28a745;
                font-weight:bold;
            }

            .success-icon{
                font-size:80px;
                color:#28a745;
                animation:pop 0.5s ease;
            }

            .info-box{
                background:#f8f9fa;
                padding:15px;
                border-radius:10px;
                margin-top:20px;
                text-align:left;
            }

            .btn{
                border-radius:10px;
                padding:10px 20px;
            }

            @keyframes fadeIn{
                from{
                    opacity:0;
                    transform:translateY(20px);
                }
                to{
                    opacity:1;
                    transform:translateY(0);
                }
            }

            @keyframes pop{
                0%{
                    transform:scale(0.5);
                }
                100%{
                    transform:scale(1);
                }
            }

        </style>

    </head>

    <body>

        <div class="wrapper">

            <!-- 🐱 -->
            <img class="cat" src="https://cdn-icons-png.flaticon.com/512/616/616430.png">

            <div class="steps">
                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin</div>
                <div class="step">💳<br>Thanh toán</div>
                <div class="step active">✔<br>Hoàn tất</div>
            </div>

            <div class="success-icon">✔</div>

            <h2 class="mt-3">Đặt hàng thành công 🎉</h2>

            <p>Cảm ơn bạn đã mua hàng tại <b>HyperTech Store</b></p>
            <p>Đơn hàng của bạn đang được xử lý.</p>

            <!-- INFO -->
            <div class="info-box">
                <h5>📦 Thông tin đơn hàng</h5>
                <p><b>Mã đơn:</b> <%= session.getAttribute("ORDER_ID")%></p>
                <p><b>Khách hàng:</b> <%= session.getAttribute("FULLNAME")%></p>
                <p><b>SĐT:</b> <%= session.getAttribute("PHONE")%></p>
                <p><b>Địa chỉ:</b> <%= session.getAttribute("ADDRESS")%></p>
            </div>

            <div class="mt-4">

                <a href="index.jsp" class="btn btn-primary">
                    🛍 Tiếp tục mua sắm
                </a>

                <a href="MainController?action=searchOrder" class="btn btn-success">
                    📄 Xem đơn hàng
                </a>

            </div>

        </div>

    </body>
</html>