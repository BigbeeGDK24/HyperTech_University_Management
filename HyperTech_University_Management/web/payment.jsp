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
                font-family:Segoe UI;
            }

            .wrapper{
                width:900px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:30px;
                border-radius:12px;
                box-shadow:0 5px 15px rgba(0,0,0,0.1);
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
                font-weight:500;
            }

            .step.active{
                color:#e53935;
                font-weight:bold;
            }

            .table thead{
                background:#343a40;
                color:white;
            }

            .payment-box{
                background:#fafafa;
                padding:15px;
                border-radius:8px;
                border:1px solid #eee;
                margin-bottom:20px;
            }

            .total-box{
                text-align:right;
                font-size:20px;
                font-weight:bold;
                margin-top:20px;
            }

        </style>

    </head>

    <body>

        <div class="wrapper">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                double total = 0;
            %>

            <div class="steps">

                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin đặt hàng</div>
                <div class="step active">💳<br>Thanh toán</div>
                <div class="step">✔<br>Hoàn tất</div>

            </div>

            <h4>Phương thức thanh toán</h4>

            <form action="MainController" method="post">

                <input type="hidden" name="action" value="addPayment">

                <div class="payment-box">

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

                </div>

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

                <div class="total-box">
                    Total: $ <%=total%>
                </div>

                <div style="text-align:right;margin-top:20px">

                    <a href="cart.jsp" class="btn btn-secondary">
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