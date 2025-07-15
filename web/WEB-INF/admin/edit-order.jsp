<%-- 
    Document   : edit-order
    Created on : Mar 14, 2025, 11:26:35 PM
    Author     : Pham Quoc Tu - CE181513
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Admin - Edit Order Product Created</title>
        <link rel="icon" href="../assets/imgs/logo.png"/>
        <!-- Link icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Link bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Link css files -->
        <link rel="stylesheet" href="../assets/css/style.css?version=<%= System.currentTimeMillis() %>"/>
        <link rel="stylesheet" href="../assets/css/main.css?version=<%= System.currentTimeMillis() %>"/>
        <link rel="stylesheet" href="../assets/css/responsive.css?version=<%= System.currentTimeMillis() %>"/>

    </head>
    <body>
        <!-- admin header start -->
        <jsp:include page="admin-header.jsp" />
        <!-- admin header end -->
        <main id="admin-main">

        <main id="admin-main">
            <div class="container-fluid">
                <div class="row admin-main-container">
                    <!-- admin control start -->
                    <jsp:include page="admin-control.jsp" />
                    <!-- admin control end -->
                    <section class="admin-main-content col-lg-10 col-md-9">
                        <div class="admin-main-content-container">
                            <div class="admin-main-content-header">
                                <div class="container">
                                    <div class="row">
                                        <h1>Edit Order</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container admin-main-content-body-form">
                                    <form method="POST" action="order?method=edit" class="admin-main-content-body-form">


                                        <input type="hidden" value="${requestScope.productid}" name="productID" required>

                                        <div class="admin-main-content-body-group">
                                            <label for="order_id">Order ID</label>
                                            <input type="text" name="order_id" id="order_id" value="${requestScope.id}" readonly>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="user_name">User Name</label>
                                            <input type="text" name="user_name" id="user_name" value="${requestScope.order.userName}" readonly>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="productName">Product Name</label>
                                            <input type="text" name="productName" id="productName" value="${requestScope.order.productName}" readonly>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="quantity">Quantity</label>
                                            <input type="number" min="1" max="1000" step="1" name="quantity" id="quantity" value="${requestScope.order.quantity}" required>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="payment_method">Payment Method</label>
                                            <select id="payment_method" name="payment_method" required>
                                                <option>${requestScope.order.paymentMethod}</option>
                                            </select>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="status_order">Status</label>
                                            <select id="status_order" name="status_order" required>
                                                <option ${requestScope.order.status.equals("Completed") ? "selected":""}  value="Completed">Completed</option>
                                                <option ${requestScope.order.status.equals("Processing") ? "selected":""}  value="Processing">Processing</option>
                                                <option ${requestScope.order.status.equals("Cancelled") ? "selected":""}  value="Cancelled">Cancelled</option>
                                            </select>
                                        </div>

                                        <div class="admin-main-content-body-form-control">
                                            <a class="bttn" href="order"><span>Back</span></a>
                                            <button class="bttn">
                                                <span>Edit</span>
                                            </button>
                                        </div>

                                    </form>
                                </div>
                            </div>
                        </div>   
                    </section>
                </div>
            </div>
        </main>
        <script src="../assets/js/main.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script src="../assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script> 
    </body>
</html>
