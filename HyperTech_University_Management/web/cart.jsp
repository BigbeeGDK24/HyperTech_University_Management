<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.CartDTO"%>
<%@page import="model.ProductDTO"%>

<!DOCTYPE html>
<html>
    <head>

        <meta charset="UTF-8">
        <title>Giỏ hàng</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>

            body{
                background:#f5f6fa;
            }

            .cart-wrapper{
                width:900px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:30px;
                border-radius:10px;
                box-shadow:0 3px 10px rgba(0,0,0,0.1);
            }

            /* STEP BAR */

            .cart-steps{
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
                font-weight:500;
                color:#555;
            }

            .step.active{
                color:#e53935;
                font-weight:bold;
            }

            /* TABLE */

            .table thead{
                background:#343a40;
                color:white;
            }

            .product-name{
                font-weight:600;
            }

            .price{
                color:#e53935;
                font-weight:bold;
            }

            /* EMPTY CART */

            .empty-cart{
                text-align:center;
                padding:40px;
                font-size:18px;
                color:#666;
            }

            /* BUTTON */

            .btn-continue{
                background:#1976d2;
                color:white;
                padding:10px 25px;
                font-weight:600;
            }

        </style>

    </head>

    <body>

        <div class="cart-wrapper">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                double total = 0;
            %>

            <!-- STEP PROCESS -->

            <div class="cart-steps">

                <div class="step active">
                    🛒<br>
                    Giỏ hàng
                </div>

                <div class="step">
                    📄<br>
                    Thông tin đặt hàng
                </div>

                <div class="step">
                    💳<br>
                    Thanh toán
                </div>

                <div class="step">
                    ✔<br>
                    Hoàn tất
                </div>

            </div>


            <h4>Giỏ hàng của bạn</h4>

            <table class="table table-bordered mt-3">

                <tr>
                    <th>Product</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                    <th>Action</th>
                </tr>

                <%
                    if (cart != null && cart.getCart() != null && !cart.getCart().isEmpty()) {

                        for (ProductDTO p : cart.getCart().values()) {

                            double itemTotal = p.getPrice() * p.getQuantity();
                            total += itemTotal;

                %>

                <tr>

                    <td class="product-name">
                        <%=p.getProductName()%>
                    </td>

                    <td class="price">
                        $ <%=String.format("%.0f", p.getPrice())%>
                    </td>

                    <td>

                        <form action="MainController" method="post" style="display:flex;gap:5px">

                            <input type="hidden" name="action" value="UpdateCart">
                            <input type="hidden" name="productID" value="<%=p.getId()%>">

                            <input type="number"
                                   name="quantity"
                                   value="<%=p.getQuantity()%>"
                                   min="1"
                                   style="width:70px"
                                   class="form-control">

                            <button class="btn btn-primary btn-sm">
                                Update
                            </button>

                        </form>

                    </td>

                    <td>
                        $ <%=String.format("%.0f", itemTotal)%>
                    </td>

                    <td>

                        <a href="MainController?action=RemoveCart&productID=<%=p.getId()%>"
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
                    <td colspan="5" class="empty-cart">

                        Giỏ hàng của bạn đang trống <br><br>

                        <a href="MainController" class="btn btn-continue">
                            TIẾP TỤC MUA HÀNG
                        </a>

                    </td>
                </tr>

                <%
                    }
                %>

            </table>

            <div style="text-align:right;margin-top:20px">

                <h4>Total: $ <%=String.format("%.0f", total)%></h4>

            </div>

            <div style="text-align:right;margin-top:20px">

                <a href="MainController" class="btn btn-secondary">
                    Tiếp tục mua
                </a>

                <a href="MainController?action=clearCart" class="btn btn-warning">
                    Clear Cart
                </a>

                <a href="PaymentController" class="btn btn-success">
                    Thanh toán
                </a>

            </div>

        </div>

    </body>
</html>