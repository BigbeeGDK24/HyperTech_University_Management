<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="model.CartDTO"%>
<%@page import="model.ProductDTO"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Giỏ hàng</title>

        <link rel="stylesheet" href="css/header.css">
        <link rel="stylesheet" href="css/home.css">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>

            .checkout-steps{
                display:flex;
                justify-content:center;
                gap:40px;
                margin-bottom:30px;
                background:#f7dede;
                padding:20px;
                border-radius:10px;
            }

            .step{
                text-align:center;
            }

            .circle{
                width:35px;
                height:35px;
                border-radius:50%;
                background:#ddd;
                color:white;
                display:flex;
                align-items:center;
                justify-content:center;
                margin:auto;
            }

            .step.active .circle{
                background:red;
            }

            .cart-table img{
                width:80px;
            }

            .total-box{
                font-size:20px;
                font-weight:bold;
                text-align:right;
                margin-top:20px;
            }

        </style>

    </head>

    <body>

        <!-- HEADER -->
        <jsp:include page="header.jsp"/>

        <div class="container mt-4">

            <!-- STEP BAR -->
            <div class="checkout-steps">

                <div class="step active">
                    <div class="circle">1</div>
                    <span>Giỏ hàng</span>
                </div>

                <div class="step">
                    <div class="circle">2</div>
                    <span>Thông tin đặt hàng</span>
                </div>

                <div class="step">
                    <div class="circle">3</div>
                    <span>Thanh toán</span>
                </div>

                <div class="step">
                    <div class="circle">4</div>
                    <span>Hoàn tất</span>
                </div>

            </div>


            <%

                CartDTO cart = (CartDTO) session.getAttribute("CART");

                double total = 0;

            %>

            <h3>Giỏ hàng của bạn</h3>

            <table class="table cart-table">

                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>

                <%    if (cart != null && cart.getCart() != null) {

                        for (ProductDTO p : cart.getCart().values()) {

                            double itemTotal = p.getPrice() * p.getQuantity();
                            total += itemTotal;

                %>

                <tr>

                    <td>
                        <b><%=p.getProductName()%></b>
                    </td>

                    <td>
                        $ <%=p.getPrice()%>
                    </td>

                    <td>

                        <form action="MainController">

                            <input type="hidden" name="action" value="UpdateCart">

                            <input type="hidden" name="productID" value="<%=p.getProductID()%>">

                            <input type="number" name="quantity" value="<%=p.getQuantity()%>" min="1">

                            <button class="btn btn-sm btn-primary">Update</button>

                        </form>

                    </td>

                    <td>

                        $ <%=itemTotal%>

                    </td>

                    <td>

                        <a href="MainController?action=RemoveCart&productID=<%=p.getProductID()%>" 
                           class="btn btn-danger btn-sm">

                            Remove

                        </a>

                    </td>

                </tr>

                <%

                    }

                } else {

                %>

                <tr>
                    <td colspan="5" style="text-align:center">
                        Giỏ hàng trống
                    </td>
                </tr>

                <%    }

                %>

            </table>


            <div class="total-box">

                Total: $ <%=total%>

            </div>


            <div style="text-align:right;margin-top:20px">

                <a href="index.jsp" class="btn btn-secondary">

                    Tiếp tục mua hàng

                </a>

                <a href="checkout.jsp" class="btn btn-danger">

                    Thanh toán

                </a>

            </div>

        </div>

    </body>
</html>