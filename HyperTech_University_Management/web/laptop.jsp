<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/laptop.css">

    <form action="LaptopController" method="post">

        <input type="hidden" name="id">

        <div class="form-row">
            <label>Tên sản phẩm</label>
            <input type="text" name="name" required>
        </div>

        <div class="form-row">
            <label>CPU</label>
            <input type="text" name="cpu">
        </div>

        <div class="form-row">
            <label>GPU</label>
            <input type="text" name="gpu">
        </div>

        <div class="form-row">
            <label>RAM</label>
            <input type="text" name="ram">
        </div>

        <div class="form-row">
            <label>SSD</label>
            <input type="text" name="ssd">
        </div>

        <div class="form-row">
            <label>Màn hình</label>
            <input type="text" name="screen">
        </div>

        <div class="form-row">
            <label>Refresh Rate</label>
            <input type="text" name="refresh_rate">
        </div>

        <div class="form-row">
            <label>Giá cũ</label>
            <input type="number" name="old_price" step="any">
        </div>

        <div class="form-row">
            <label>Giá mới</label>
            <input type="number" name="new_price" step="any">
        </div>

        <div class="form-row">
            <label>Link ảnh</label>
            <input type="text" name="image_url" placeholder="lab1.png">
        </div>

        <div class="form-buttons">

            <button type="submit" name="action" value="addLaptop" class="add-btn">
                Add
            </button>

            <button type="submit" name="action" value="updateLaptop" class="update-btn">
                Update
            </button>

        </div>

    </form>