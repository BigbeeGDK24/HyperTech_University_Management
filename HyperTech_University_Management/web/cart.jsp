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
                background:#f6f7fb;
                font-family:Segoe UI;
            }

            .cart-wrapper{
                width:1000px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:35px;
                border-radius:14px;
                box-shadow:0 6px 18px rgba(0,0,0,0.08);
            }

            .cart-header{
                display:flex;
                align-items:center;
                margin-bottom:25px;
            }

            .cart-header h3{
                font-weight:600;
                margin:0;
            }

            .cart-steps{
                display:flex;
                justify-content:space-between;
                background:#fdecec;
                padding:18px;
                border-radius:10px;
                margin-bottom:30px;
            }

            .step{
                flex:1;
                text-align:center;
                font-weight:500;
                color:#666;
            }

            .step.active{
                color:#e53935;
                font-weight:700;
            }

            .table thead{
                background:#2c2c2c;
                color:white;
            }

            .product-name{
                font-weight:600;
            }

            .price{
                font-weight:700;
            }

            .item-total{
                font-weight:600;
            }

            .quantity{
                text-align:center;
            }

            .remove-btn{
                background:#ff4d4f;
                border:none;
            }

            .remove-btn:hover{
                background:#d9363e;
            }

            .total-box{
                margin-top:25px;
                text-align:right;
                font-size:22px;
                font-weight:700;
            }

            .cart-actions{
                margin-top:25px;
                display:flex;
                justify-content:flex-end;
                gap:10px;
            }

            .btn-continue{
                background:#6c757d;
                color:white;
            }

            .btn-clear{
                background:#ffc107;
            }

            .btn-checkout{
                background:#28a745;
                color:white;
            }

            /* ===== EMPTY CART ANIMATION ===== */
            .discount-badge{
                background:#ff424e;
                color:white;
                font-size:12px;
                padding:3px 8px;
                border-radius:6px;
                margin-left:6px;
            }

            .old-price{
                text-decoration:line-through;
                color:#999;
                font-size:13px;
            }

            .new-price{
                color:#e53935;
                font-weight:700;
            }
            .empty-cart-box{
                text-align:center;
                padding:60px;
                animation:fadeIn 0.6s ease;
            }

            .empty-cart-box img{
                width:120px;
                margin-bottom:15px;
                animation:bounce 1.5s infinite;
            }

            @keyframes bounce{
                0%{
                    transform:translateY(0);
                }
                50%{
                    transform:translateY(-12px);
                }
                100%{
                    transform:translateY(0);
                }
            }

            @keyframes fadeIn{
                from{
                    opacity:0;
                }
                to{
                    opacity:1;
                }
            }

        </style>

    </head>

    <body>

        <div class="cart-wrapper">

            <%
                CartDTO cart = (CartDTO) session.getAttribute("CART");

                double total = 0;

                boolean isEmpty = (cart == null || cart.getCart() == null || cart.getCart().isEmpty());
            %>

            <div class="cart-header">
                <h3>🛒 Giỏ hàng của bạn</h3>
            </div>

            <div class="cart-steps">

                <div class="step active">🛒<br>Giỏ hàng</div>
                <div class="step">📄<br>Thông tin</div>
                <div class="step">💳<br>Thanh toán</div>
                <div class="step">✔<br>Hoàn tất</div>

            </div>

            <table class="table table-bordered">

                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Tổng</th>
                    <th></th>
                </tr>

                <%
                    if (!isEmpty) {

                        for (ProductDTO p : cart.getCart().values()) {

                            double itemTotal = p.getFinalPrice() * p.getQuantity();

                            total += itemTotal;

                %>

                <tr>

                    <td class="product-name">
                        <%=p.getName()%>

                        <% if (p.getDiscountPercent() > 0) {%>
                        <span class="discount-badge">
                            -<%=p.getDiscountPercent()%>%
                        </span>
                        <% }%>
                    </td>

                    <td class="price">

                        <% if (p.getDiscountPercent() > 0) {%>

                        <div class="old-price">
                            <%= String.format("%,.0f đ", p.getOld_price())%>
                        </div>

                        <div class="new-price">
                            <%= String.format("%,.0f đ", p.getNew_price())%>
                        </div>

                        <span class="discount-badge">
                            -<%=p.getDiscountPercent()%>%
                        </span>

                        <% } else {%>

                        <%= String.format("%,.0f đ", p.getNew_price())%>

                        <% }%>

                    </td>
                    <td>

                        <input type="number"
                               value="<%=p.getQuantity()%>"
                               min="1"
                               class="form-control quantity"
                               style="width:90px"
                               data-id="<%=p.getId()%>"
                               data-price="<%= p.getFinalPrice()%>">

                    </td>

                    <td class="item-total">
                        <%= String.format("%,.0f ₫", itemTotal)%>
                    </td>

                    <td>

                        <button class="btn btn-sm remove-btn text-white"
                                data-id="<%=p.getId()%>">
                            Remove
                        </button>

                    </td>

                </tr>

                <%
                        }
                    }
                %>

            </table>

            <div class="total-box">
                Tổng tiền: <span id="cartTotal"><%=String.format("%,.0f", total)%></span> ₫
            </div>

            <% if (!isEmpty) { %>

            <div class="cart-actions">

                <a href="MainController" class="btn btn-continue">
                    Tiếp tục mua
                </a>

                <a href="MainController?action=clearCart" id="clearCartBtn" class="btn btn-clear">
                    🗑 Xóa toàn bộ giỏ hàng
                </a>

                <a href="MainController?action=checkout" class="btn btn-checkout">
                    Thanh toán
                </a>

            </div>

            <% } %>

            <!-- EMPTY CART -->

            <% if (isEmpty) { %>

            <div class="empty-cart-box">

                <img src="https://cdn-icons-png.flaticon.com/512/2038/2038854.png">

                <h4>Giỏ hàng của bạn đang trống</h4>

                <p>Hãy thêm sản phẩm để tiếp tục mua sắm</p>

            </div>

            <% }%>

        </div>

        <script>

            function updateCartTotal() {

                let total = 0;

                document.querySelectorAll(".item-total").forEach(cell => {

                    let value = cell.innerText
                            .replace("₫", "")
                            .replace(/\./g, "")
                            .replace(/,/g, "")
                            .trim();

                    total += parseFloat(value);

                });

                document.getElementById("cartTotal").innerText = total.toLocaleString("vi-VN");

            }

            document.querySelectorAll(".quantity").forEach(input => {

                input.addEventListener("change", function () {

                    let quantity = this.value;
                    let productID = this.dataset.id;
                    let price = this.dataset.price;

                    let row = this.closest("tr");

                    let totalCell = row.querySelector(".item-total");

                    let newTotal = quantity * price;

                    totalCell.innerHTML = newTotal.toLocaleString("vi-VN") + " ₫";

                    updateCartTotal();

                    fetch("MainController", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        body: "action=UpdateCart&productID=" + productID + "&quantity=" + quantity
                    });

                });

            });

            document.querySelectorAll(".remove-btn").forEach(btn => {

                btn.addEventListener("click", function () {

                    let productID = this.dataset.id;

                    let row = this.closest("tr");

                    row.remove();

                    updateCartTotal();

                    fetch("MainController?action=RemoveCart&productID=" + productID);

                });

            });

        </script>

    </body>
</html>