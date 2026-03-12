<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Quản Lý Sản Phẩm</title>
        <link rel="stylesheet" href="css/product.css">
    </head>

    <body>

        <div class="product-container">

            <h1>Quản Lý Sản Phẩm</h1>

            <div class="category-grid">

                <a href="${pageContext.request.contextPath}/laptop.jsp">
                    <div class="category-card">Laptop</div>
                </a>

                <a href="${pageContext.request.contextPath}/vga.jsp">
                    <div class="category-card">VGA</div>
                </a>

                <a href="${pageContext.request.contextPath}/case.jsp">
                    <div class="category-card">Case</div>
                </a>

                <a href="${pageContext.request.contextPath}/ram.jsp">
                    <div class="category-card">RAM</div>
                </a>

                <a href="${pageContext.request.contextPath}/monitor.jsp">
                    <div class="category-card">Màn hình</div>
                </a>

                <a href="${pageContext.request.contextPath}/keyboard.jsp">
                    <div class="category-card">Bàn phím</div>
                </a>

                <a href="${pageContext.request.contextPath}/mouse.jsp">
                    <div class="category-card">Chuột</div>
                </a>

            </div>

        </div>

    </body>
</html>