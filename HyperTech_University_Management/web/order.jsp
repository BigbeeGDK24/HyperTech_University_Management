<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.CartDTO"%>
<%@page import="model.ProductDTO"%>
<%@page import="model.UserDTO"%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Thông tin đặt hàng</title>

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
                UserDTO user = (UserDTO) session.getAttribute("user");

                double total = 0;
            %>

            <!-- STEP BAR -->

            <div class="steps">

                <div class="step">🛒<br>Giỏ hàng</div>
                <div class="step active">📄<br>Thông tin đặt hàng</div>
                <div class="step">💳<br>Thanh toán</div>
                <div class="step">✔<br>Hoàn tất</div>

            </div>

            <h4>Thông tin người nhận</h4>

            <form action="MainController" method="post">

                <input type="hidden" name="action" value="createOrder">

                <div class="row mb-3">

                    <div class="col-md-6">
                        <label>Email</label>
                        <input type="text" class="form-control" name="email"
                               value="<%=user != null ? user.getEmail() : ""%>" readonly>
                    </div>

                    <div class="col-md-6">
                        <label>Số điện thoại</label>
                        <input type="text" class="form-control" name="phone" required>
                    </div>

                </div>

                <div class="mb-3">
                    <label>Địa chỉ giao hàng</label>
                    <input type="text" class="form-control" name="address" required>
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

                    <a href="cart.jsp" class="btn btn-secondary">Quay lại</a>

                    <button class="btn btn-success">
                        Tiếp tục thanh toán
                    </button>

                </div>

            </form>

        </div>

    </body>
</html>