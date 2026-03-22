<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.OrderDTO"%>

<%
    ArrayList<OrderDTO> list = (ArrayList<OrderDTO>) request.getAttribute("list");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Tra cứu đơn hàng</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body{
                background:#f4f6fb;
                font-family:Segoe UI;
            }

            .wrapper{
                width:1100px;
                margin:auto;
                margin-top:40px;
                background:white;
                padding:30px;
                border-radius:12px;
                box-shadow:0 6px 20px rgba(0,0,0,0.08);
            }

            .badge{
                padding:6px 10px;
                border-radius:8px;
            }

            .pending{
                background:orange;
                color:white;
            }
            .shipping{
                background:#0d6efd;
                color:white;
            }
            .completed{
                background:#28a745;
                color:white;
            }

            /* timeline */
            .mini-timeline{
                display:flex;
                gap:25px;
            }

            .mini-step{
                text-align:center;
            }

            .mini-circle{
                width:14px;
                height:14px;
                border-radius:50%;
                background:#ccc;
                margin:auto;
            }

            .mini-circle.active{
                background:#28a745;
            }

            /* button */
            .btn-detail{
                background:#0d6efd;
                color:white;
                border:none;
            }

            .btn-complain{
                background:#dc3545;
                color:white;
                border:none;
            }

            /* modal */
            .modal-box{
                display:none;
                position:fixed;
                top:0;
                left:0;
                width:100%;
                height:100%;
                background:rgba(0,0,0,0.5);
            }

            .modal-content{
                background:white;
                width:400px;
                margin:100px auto;
                padding:20px;
                border-radius:10px;
            }
        </style>
    </head>

    <body>

        <div class="wrapper">

            <h3>📦 Tra cứu đơn hàng</h3>

            <!-- FILTER -->
            <div class="mb-3">
                <button class="btn btn-warning" onclick="filterStatus('all')">Tất cả</button>
                <button class="btn btn-warning" onclick="filterStatus('pending')">Đang xử lý</button>
                <button class="btn btn-primary" onclick="filterStatus('shipping')">Đang giao</button>
                <button class="btn btn-success" onclick="filterStatus('completed')">Hoàn tất</button>
            </div>

            <table class="table table-hover">

                <tr>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Tiến trình</th>
                    <th>Ngày</th>
                    <th>Hành động</th>
                </tr>

                <tbody id="tbl">

                    <%
                        if (list != null && !list.isEmpty()) {
                            for (OrderDTO o : list) {
                    %>

                    <tr data-status="<%=o.getStatus()%>">

                        <td>#<%=o.getId()%></td>
                        <td><%=o.getEmail()%></td>
                        <td><%=String.format("%,.0f", o.getTotalPrice())%> ₫</td>

                        <td>
                            <span class="badge <%=o.getStatus()%>"><%=o.getStatus()%></span>
                        </td>

                        <td>
                            <div class="mini-timeline">

                                <div class="mini-step">
                                    <div class="mini-circle <%="pending".equals(o.getStatus())
                                            || "shipping".equals(o.getStatus())
                                            || "completed".equals(o.getStatus()) ? "active" : ""%>"></div>
                                    <small>Đang xử lý</small>
                                </div>

                                <div class="mini-step">
                                     <div class="mini-circle <%="shipping".equals(o.getStatus())
                                             || "completed".equals(o.getStatus()) ? "active" : ""%>"></div>
                                    <small>Đang giao</small>
                                </div>

                                <div class="mini-step">
                                    <div class="mini-circle <%="completed".equals(o.getStatus()) ? "active" : ""%>"></div>
                                    <small>Hoàn tất</small>
                                </div>

                            </div>
                        </td>

                        <td><%=o.getCreatedAt()%></td>

                        <td>
                            <!-- DETAIL -->
                            <button class="btn btn-sm btn-detail" onclick="viewDetail(<%=o.getId()%>)">
                                Chi tiết
                            </button>

                            <!-- COMPLAIN -->
                            <button class="btn btn-sm btn-complain" onclick="openComplain(<%=o.getId()%>)">
                                Khiếu nại
                            </button>
                            <!-- 🔥 THÊM NGAY DƯỚI ĐÂY (dòng ~186) -->
                            <% if ("pending".equals(o.getStatus())) {%>
                            <form action="MainController" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="cancelOrder">
                                <input type="hidden" name="id" value="<%=o.getId()%>">

                                <button class="btn btn-sm btn-danger">
                                    Hủy đơn
                                </button>
                            </form>
                            <% } %>
                        </td>

                    </tr>

                    <%
                        }
                    } else {
                    %>

                    <tr>
                        <td colspan="7" style="text-align:center; color:red;">
                            Không có đơn hàng
                        </td>
                    </tr>

                    <%
                        }
                    %>

                </tbody>
            </table>

            <a href="index.jsp" class="btn btn-secondary">🛍 Tiếp tục mua</a>

        </div>

        <!-- MODAL DETAIL -->
        <div id="detailModal" class="modal-box">
            <div class="modal-content">
                <h5>Chi tiết đơn hàng</h5>
                <p id="detailContent">Loading...</p>
                <button onclick="closeModal()">Đóng</button>
            </div>
        </div>

        <!-- MODAL COMPLAIN -->
        <div id="complainModal" class="modal-box">
            <div class="modal-content">
                <h5>Gửi khiếu nại</h5>

                <form action="MainController" method="POST">
                    <input type="hidden" name="action" value="complain">
                    <input type="hidden" name="orderId" id="complainOrderId">

                    <textarea name="content" class="form-control" placeholder="Nhập khiếu nại"></textarea>

                    <button class="btn btn-danger mt-2">Gửi</button>
                </form>

                <button onclick="closeModal()">Đóng</button>
            </div>
        </div>

        <script>
            function filterStatus(status) {
                let rows = document.querySelectorAll("#tbl tr");

                rows.forEach(row => {
                    let s = row.getAttribute("data-status");
                    row.style.display = (status === "all" || s === status) ? "" : "none";
                });
            }

            // DETAIL
            function viewDetail(id) {
                document.getElementById("detailModal").style.display = "block";
                document.getElementById("detailContent").innerHTML = "Đang tải đơn #" + id;
            }

            // COMPLAIN
            function openComplain(id) {
                document.getElementById("complainModal").style.display = "block";
                document.getElementById("complainOrderId").value = id;
            }

            function closeModal() {
                document.getElementById("detailModal").style.display = "none";
                document.getElementById("complainModal").style.display = "none";
            }
        </script>

    </body>
</html>