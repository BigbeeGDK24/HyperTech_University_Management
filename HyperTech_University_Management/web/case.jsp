<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>

        <meta charset="UTF-8">
        <title>Case Management</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/case.css">

    </head>

    <body>

        <div class="product-container">

            <h1>Quản Lý Case</h1>

            <!-- FORM -->

            <div class="product-form">

                <form action="CaseController" method="post" enctype="multipart/form-data">

                    <input type="hidden" name="id">

                    <div class="form-row">
                        <label>Description</label>
                        <input type="text" name="description">
                    </div>

                    <div class="form-row">
                        <label>Price</label>
                        <input type="number" name="price">
                    </div>

                    <div class="form-row">
                        <label>Image</label>
                        <input type="file" name="image" accept="image/png,image/jpeg">
                    </div>

                    <div class="form-buttons">

                        <button type="submit" name="action" value="add" class="add-btn">
                            Add
                        </button>

                        <button type="submit" name="action" value="update" class="update-btn">
                            Update
                        </button>

                    </div>

                </form>

            </div>

            <!-- SEARCH -->

            <div class="search-box">

                <form action="CaseController" method="get">

                    <input type="hidden" name="action" value="search">

                    <input type="text" name="keyword" placeholder="Search Case...">

                    <button type="submit">Search</button>

                </form>

            </div>

            <!-- TABLE -->

            <div class="product-table-box">

                <table>

                    <thead>

                        <tr>
                            <th>ID</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Image</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        <tr>

                            <td>1</td>
                            <td>Gaming Case RGB</td>
                            <td>1500000</td>

                            <td>
                                <img src="images/case.png" width="80">
                            </td>

                            <td>

                                <button class="edit-btn">Edit</button>

                                <button class="delete-btn">Delete</button>

                            </td>

                        </tr>

                    </tbody>

                </table>

            </div>

        </div>

    </body>
</html>