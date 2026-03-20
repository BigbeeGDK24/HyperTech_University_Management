<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("ORDER_ID") == null) {
        response.sendRedirect("cart.jsp");
        return;
    }
%>
<%@page import="model.CartDTO"%>
<%@page import="model.ProductDTO"%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Thanh toán</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body{
                background: linear-gradient(135deg, #eef2ff, #fdfbfb);
                font-family: Segoe UI;
            }

            .wrapper{
                width:900px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:30px;
                border-radius:16px;
                box-shadow:0 10px 30px rgba(0,0,0,0.1);
                animation:fadeIn 0.5s ease;
                position:relative;
            }

            /* 🐱 mèo meme */
            .cat-left, .cat-right{
                position:absolute;
                width:80px;
            }

            .cat-left{
                top:-30px;
                left:-40px;
            }

            .cat-right{
                bottom:-30px;
                right:-40px;
            }

            .steps{
                display:flex;
                justify-content:space-between;
                background:#fdeaea;
                padding:18px;
                border-radius:10px;
                margin-bottom:25px;
            }

            .step{
                flex:1;
                text-align:center;
                color:#666;
            }

            .step.active{
                color:#e53935;
                font-weight:bold;
            }

            .table thead{
                background:#212529;
                color:white;
            }

            .payment-box{
                background:#fafafa;
                padding:20px;
                border-radius:10px;
                border:1px solid #eee;
                margin-bottom:20px;
            }

            .payment-box:hover{
                box-shadow:0 5px 15px rgba(0,0,0,0.05);
            }

            .info-box{
                background:#f8f9fa;
                padding:15px;
                border-radius:10px;
                margin-bottom:20px;
            }

            .total-box{
                text-align:right;
                font-size:22px;
                font-weight:bold;
                color:#e53935;
            }

            .btn-success{
                padding:10px 20px;
                border-radius:10px;
            }

            @keyframes fadeIn{
                from{
                    opacity:0;
                    transform:translateY(10px);
                }
                to{
                    opacity:1;
                    transform:translateY(0);
                }
            }
        </style>

    </head>

    <body>

        <div class="wrapper">

            <!-- 🐱 mèo meme -->
            <img class="cat-left" src="https://cdn-icons-png.flaticon.com/512/616/616408.png">
            <img class="cat-right" src="https://cdn-icons-png.flaticon.com/512/616/616430.png">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                double total = 0;
            %>
            <%
                if (cart == null) {
            %>
            <script>
                alert("Giỏ hàng trống!");
                window.location = "cart.jsp";
            </script>
            <%
                    return;
                }
            %>
            <!-- STEP -->
            <div class="steps">
                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin</div>
                <div class="step active">💳<br>Thanh toán</div>
                <div class="step">✔<br>Hoàn tất</div>
            </div>

            <!-- INFO USER -->
            <div class="info-box">
                <h5>👤 Thông tin giao hàng</h5>
                <p><b>Mã đơn:</b> <%= session.getAttribute("ORDER_ID")%></p>
                <p><b>Tên:</b> <%= session.getAttribute("FULLNAME")%></p>
                <p><b>Điện thoại:</b> <%= session.getAttribute("PHONE")%></p>
                <p><b>Địa chỉ:</b> <%= session.getAttribute("ADDRESS")%></p>
            </div>

            <h4>💳 Phương thức thanh toán</h4>

            <form action="MainController" method="post" onsubmit="return loading()">

                <input type="hidden" name="action" value="complete">

                <div class="payment-box">

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment_method" value="COD" checked>
                        <label class="form-check-label">Thanh toán khi nhận hàng (COD)</label>
                    </div>

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment_method" value="Banking">
                        <label class="form-check-label">Chuyển khoản ngân hàng</label>
                    </div>

                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="payment_method" value="Momo">
                        <label class="form-check-label">Ví MoMo</label>
                    </div>

                </div>

                <h4>📦 Sản phẩm</h4>

                <table class="table table-bordered">

                    <tr>
                        <th>Tên</th>
                        <th>Giá</th>
                        <th>SL</th>
                        <th>Tổng</th>
                    </tr>

                    <%
                        if (cart != null && cart.getCart() != null) {
                            for (ProductDTO p : cart.getCart().values()) {

                                double itemTotal = p.getNew_price() * p.getQuantity();
                                total += itemTotal;
                    %>

                    <tr>
                        <td><%=p.getName()%></td>
                        <td><%= String.format("%,.0f", p.getNew_price())%> ₫</td>
                        <td><%=p.getQuantity()%></td>
                        <td><%= String.format("%,.0f", itemTotal)%> ₫</td>
                    </tr>

                    <%
                            }
                        }
                    %>

                </table>

                <div class="total-box">
                    Tổng: <%= String.format("%,.0f", total)%> ₫
                </div>

                <div style="text-align:right;margin-top:20px">

                    <a href="cart.jsp" class="btn btn-secondary">
                        ← Quay lại
                    </a>

                    <button class="btn btn-success">
                        Xác nhận thanh toán
                    </button>

                </div>

            </form>

        </div>

        <script>
            function loading() {
                let btn = document.querySelector(".btn-success");
                btn.innerText = "Đang xử lý...";
                btn.disabled = true;
                return true;
            }
        </script>

    </body>
</html>