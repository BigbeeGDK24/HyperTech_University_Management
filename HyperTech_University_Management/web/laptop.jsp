<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/laptop.css">

<h2>QUẢN LÝ LAPTOP</h2>

<!-- ===== SEARCH + ADD ===== -->
<div style="display:flex; justify-content:space-between; margin-bottom:20px;">

<form action="ProductController" method="get">
    
    <input type="hidden" name="action" value="searchByAd"/>

    <input type="text" name="keywords" placeholder="Tìm sản phẩm...">

    <select name="category">
        <option value="">Tất cả</option>
        <option value="1">Laptop</option>
        <option value="7">Mouse</option>
    </select>

    <button type="submit">Tìm</button>

</form>

    <!-- ADD BUTTON -->
    <button onclick="openForm()">+ Thêm sản phẩm</button>

</div>

<!-- ===== FORM ADD / UPDATE ===== -->
<div id="productForm" style="display:none; border:1px solid #ccc; padding:15px; margin-bottom:20px;">
    <form action="MainController" method="post">

        <input type="hidden" name="id" id="id">

        <input type="text" name="name" id="name" placeholder="Tên sản phẩm" required>
        <input type="text" name="cpu" id="cpu" placeholder="CPU">
        <input type="text" name="gpu" id="gpu" placeholder="GPU">
        <input type="text" name="ram" id="ram" placeholder="RAM">
        <input type="text" name="ssd" id="ssd" placeholder="SSD">

        <input type="number" name="old_price" id="old_price" placeholder="Giá cũ">
        <input type="number" name="new_price" id="new_price" placeholder="Giá mới">

        <input type="text" name="image_url" id="image_url" placeholder="Ảnh">

        <br><br>

        <button type="submit" name="action" value="addLaptop">Thêm</button>
        <button type="submit" name="action" value="updateLaptop">Cập nhật</button>

        <button type="button" onclick="closeForm()">Đóng</button>
    </form>
</div>

<!-- ===== TABLE ===== -->
<table border="1" width="100%" cellpadding="10">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>CPU</th>
        <th>RAM</th>
        <th>Giá</th>
        <th>Hành động</th>
    </tr>

    <c:forEach var="p" items="${list}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.cpu}</td>
            <td>${p.ram}</td>
            <td>${p.new_price}</td>

            <td>
                <!-- UPDATE -->
                <button onclick="editProduct('${p.id}','${p.name}','${p.cpu}','${p.gpu}','${p.ram}','${p.ssd}','${p.old_price}','${p.new_price}','${p.image}')">
                    Update
                </button>

                <!-- DELETE -->
                <form action="LaptopController" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${p.id}">
                    <button type="submit" name="action" value="deleteLaptop">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<script>
function openForm() {
    console.log("click add"); // debug
    document.getElementById("productForm").style.display = "block";
}

function closeForm() {
    document.getElementById("productForm").style.display = "none";
}
</script>