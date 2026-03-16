<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Đặt hàng thành công</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>

            body{
                background:#f5f6fa;
                font-family:Segoe UI;
            }

            .wrapper{
                width:700px;
                margin:auto;
                margin-top:60px;
                background:white;
                padding:40px;
                border-radius:12px;
                box-shadow:0 5px 15px rgba(0,0,0,0.1);
                text-align:center;
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
                color:green;
                font-weight:bold;
            }

            .success-icon{
                font-size:70px;
                color:green;
            }

        </style>

    </head>

    <body>

        <div class="wrapper">

            <div class="steps">

                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin đặt hàng</div>
                <div class="step">💳<br>Thanh toán</div>
                <div class="step active">✔<br>Hoàn tất</div>

            </div>

            <div class="success-icon">
                ✔
            </div>

            <h2 class="mt-3">Đặt hàng thành công!</h2>

            <p>Cảm ơn bạn đã mua hàng tại <b>HyperTech Store</b></p>

            <p>Đơn hàng của bạn đang được xử lý.</p>

            <hr>

            <div class="mt-4">

                <a href="index.jsp" class="btn btn-primary">
                    Tiếp tục mua sắm
                </a>

                <a href="MainController?action=searchOrder" class="btn btn-success">
                    Xem đơn hàng
                </a>

            </div>

        </div>

    </body>
</html>