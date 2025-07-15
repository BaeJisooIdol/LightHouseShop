<%-- 
    Document   : admin-control
    Created on : Mar 1, 2025, 10:30:03 PM
    Author     : Phạm Hải Đăng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section class="admin-main-control col-lg-2 col-md-3">
    <ul class="admin-main-control-list">
        <li><a href="./dashboard"><i class="fas fa-tachometer-alt"></i>Main dashboard</a></li>
        <li><a href="./chart"><i class="fas fa-chart-line"></i> Revenue by month</a></li>
        <li><a href="./chart?type=year"><i class="fas fa-chart-bar"></i> Revenue by year</a></li>
        <li><a href="./order"><i class="fas fa-file-invoice-dollar"></i> Order</a></li>
        <li><a href="./product"><i class="fa-solid fa-cart-shopping"></i> Product</a></li>
        <li><a href="./account"><i class="fas fa-user-circle"></i> Account</a></li>
        <li><a href="./supplier"><i class="fas fa-parachute-box"></i>Supplier</a></li>
        <li><a href="./top10product"><i class="fas fa-shoe-prints"></i> Top 10 product</a></li>
        <li><a href="./top5customer"><i class="fa-solid fa-users"></i> Top 5 customer</a></li>
    </ul>
</section>