<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                background:#f5f6fa;
            }

            .wrapper{
                width:900px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:30px;
                border-radius:10px;
                box-shadow:0 3px 10px rgba(0,0,0,0.1);
            }

            .steps{
                display:flex;
                justify-content:space-between;
                background:#f8dede;
                padding:20px;
                border-radius:8px;
                margin-bottom:30px;
            }

            .step{
                text-align:center;
                flex:1;
            }

            .active{
                color:red;
                font-weight:bold;
            }

        </style>

    </head>

    <body>

        <div class="wrapper">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");

                double total = 0;
            %>

            <!-- STEP BAR -->

            <div class="steps">

                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin đặt hàng</div>
                <div class="step active">💳<br>Thanh toán</div>
                <div class="step">✔<br>Hoàn tất</div>

            </div>

            <h4>Phương thức thanh toán</h4>

            <form action="MainController" method="post">

                <input type="hidden" name="action" value="addPayment">

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payment_method" value="COD" checked>
                    <label class="form-check-label">
                        Thanh toán khi nhận hàng (COD)
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payment_method" value="Banking">
                    <label class="form-check-label">
                        Chuyển khoản ngân hàng
                    </label>
                </div>

                <div class="form-check">
                    <input class="form-check-input" type="radio" name="payment_method" value="Momo">
                    <label class="form-check-label">
                        Ví MoMo
                    </label>
                </div>

                <hr>

                <h4>Sản phẩm trong đơn</h4>

                <table class="table table-bordered">

                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>

                    <%
                        if (cart != null && cart.getCart() != null) {

                            for (ProductDTO p : cart.getCart().values()) {

                                double itemTotal = p.getPrice() * p.getQuantity();
                                total += itemTotal;

                    %>

                    <tr>

                        <td><%=p.getProductName()%></td>

                        <td>$ <%=p.getPrice()%></td>

                        <td><%=p.getQuantity()%></td>

                        <td>$ <%=itemTotal%></td>

                    </tr>

                    <%
                            }
                        }
                    %>

                </table>

                <div style="text-align:right">

                    <h4>Total: $ <%=total%></h4>

                </div>

                <div style="text-align:right;margin-top:20px">

                    <a href="order.jsp" class="btn btn-secondary">
                        Quay lại
                    </a>

                    <button class="btn btn-success">
                        Xác nhận thanh toán
                    </button>

                </div>

            </form>

        </div>

    </body>
</html>