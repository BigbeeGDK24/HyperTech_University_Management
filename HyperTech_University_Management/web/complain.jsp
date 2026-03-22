<%-- 
    Document   : complain
    Created on : Mar 21, 2026, 4:39:08 PM
    Author     : hasot
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div style="display:flex; justify-content:space-between; margin-bottom:15px;">

            <form action="MainController" method="get">

                <input type="hidden" name="action" value="searchComplain"/>

                <input type="text" name="keywords"
                       value="${param.keywords}"
                       placeholder="Tìm đánh giá...">

                <select name="category">
                    <option value="">Tất cả</option>
                    <option value="1" ${param.category eq '1' ? 'selected' : ''}>Laptop</option>
                    <option value="2" ${param.category eq '2' ? 'selected' : ''}>Màn Hình</option>
                    <option value="3" ${param.category eq '3' ? 'selected' : ''}>Bàn Phím</option>
                    <option value="4" ${param.category eq '4' ? 'selected' : ''}>Mouse</option>
                </select>

                <button type="submit">Tìm</button>
            </form>
        </div>
    <c:if test="${not empty list}">

        <table border="1" width="100%" cellpadding="10">
            <tr>
                <th>ID</th>
                <th>Email</th>
                <th>Tên sản phẩm</th>
                <th>Tiêu đề</th>
                <th>Nội dung</th>
            </tr>

            <c:forEach var="c" items="${list}">
                <tr>
                    <td>${c.id}</td>
                    <td>${c.email}</td>
                    <td>${c.productId}</td>            
                    <td>${c.title}</td>
                    <td>${c.content}</td>

                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
