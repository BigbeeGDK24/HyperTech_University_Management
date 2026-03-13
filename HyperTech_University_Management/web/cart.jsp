<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

    </head>

    <body>

        <jsp:include page="header.jsp"/>

        <div class="container mt-4">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                double total = 0;
            %>

            <h3>Giỏ hàng của bạn</h3>

            <table class="table table-bordered">

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

                    <td>
                        <b><%=p.getProductName()%></b>
                    </td>

                    <td>
                        $ <%=String.format("%.0f", p.getPrice())%>
                    </td>

                    <td>

                        <form action="MainController" method="post">

                            <input type="hidden" name="action" value="UpdateCart">
                            <input type="hidden" name="productID" value="<%=p.getId()%>">

                            <input type="number"
                                   name="quantity"
                                   value="<%=p.getQuantity()%>"
                                   min="1"
                                   class="form-control"
                                   style="width:80px;display:inline-block">

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
                    <td colspan="5" style="text-align:center">
                        Giỏ hàng trống
                    </td>
                </tr>

                <%
                    }
                %>

            </table>

            <div class="text-end mt-3">

                <h4>Total: $ <%=String.format("%.0f", total)%></h4>

            </div>

            <div class="text-end mt-4">

                <a href="MainController" class="btn btn-secondary">
                    Tiếp tục mua hàng
                </a>

                <a href="MainController?action=clearCart" class="btn btn-warning">
                    Clear Cart
                </a>

                <a href="#" class="btn btn-danger">
                    Thanh toán
                </a>

            </div>

        </div>

    </body>
</html>