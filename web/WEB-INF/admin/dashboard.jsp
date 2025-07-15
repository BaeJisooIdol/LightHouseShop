<%-- 
    Document   : dashboard
    Created on : Mar 1, 2025, 2:58:02 PM
    Author     : Phạm Hải Đăng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Admin</title>
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
                                        <h1 class="">Main Dashboard</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container container-lg">
                                    <div class="row admin-dashboard-container">
                                        <section class="admin-main-content-body-left col-lg-6 col-md-6">
                                            <!-- Total product -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: orange" class="fa-solid fa-spray-can-sparkles"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Total Products</h3>
                                                    <p>${requestScope.totalProduct}</p>
                                                </div>
                                            </div>

                                            <!-- Total sales -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: #E94666" class="fa-solid fa-sack-dollar"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Total Sales</h3>
                                                    <p>${requestScope.getTotalSale}đ</p>
                                                </div>
                                            </div>

                                            <!-- Total categories -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: #111" class="fa-solid fa-layer-group"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Total Categories</h3>
                                                    <p>${requestScope.getTotalCategory}</p>
                                                </div>
                                            </div>
                                        </section>
                                        <section class="admin-main-content-body-right col-lg-6 col-md-6">
                                            <!-- Total product sold -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: blueviolet" class="fa-solid fa-truck"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Products Sold</h3>
                                                    <p class="admin-dashboard-title-content">
                                                        <span>
                                                            <c:set var="i" value="1" />
                                                            <c:forEach items="${requestScope.listProductSold}" var="s">
                                                                ${s.categoryName}: ${s.totalOrder} <c:if test="${i < 3}">-</c:if> 
                                                                <c:set var="i" value="${i + 1}" />
                                                            </c:forEach>
                                                        </span>
                                                        <b>Total: ${requestScope.listProductSold[0].getTotal(listProductSold)}</b>
                                                    </p>
                                                </div>
                                            </div>

                                            <!-- Total users -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: hotpink" class="fa-solid fa-users"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Total Users</h3>
                                                    <p>${requestScope.getTotalUser}</p>
                                                </div>
                                            </div>

                                            <!-- Total suppliers -->
                                            <div class="admin-dashboard">
                                                <div class="admin-dashboard-icon">
                                                    <i style="color: green" class="fa-solid fa-parachute-box"></i>
                                                </div>
                                                <div class="admin-dashboard-title">
                                                    <h3>Total Suppliers</h3>
                                                    <p>${requestScope.getTotalSupplier}</p>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                </div>
                            </div>
                        </div>   
                    </section>
                </div>
            </div>
        </main> 

        <!-- box modal start -->
        <%-- Box wrong id --%>
        <div class="modall box-modal supplier-wrong-id">
            <div class="box-modal-container">
                <i style="color: grey" class="box-icon fa-regular fa-circle-xmark"></i>
                <h3>Fail</h3>
                <p>${requestScope.wrongID}</p>
                <button style="background-color: grey" class="bttn box-modal-btn" onclick="closeBox('create-supplier')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <%-- Box delete --%>
        <div class="modall box-modal supplier-delete-id">
            <div class="box-modal-container">
                <i style="color: orangered" class="box-icon fa-solid fa-trash"></i>
                <h3>Delete</h3>
                <p>Do you want to delete supplier width id <b></b>?</p>
                <div class="box-modal-control">
                    <button class="bttn box-modal-btn btn-back" onclick="closeBox('delete-supplier', 'supplier-delete-id')">
                        <span>Back</span>
                    </button>
                    <button class="bttn box-modal-btn" onclick="closeBox('delete-supplier')">
                        <span>OK</span>
                    </button>
                </div>
            </div>
        </div>
        <%-- Box delete success --%>
        <div class="modall box-modal delete-supplier-sccess">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Detele successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('delete-supplier-sccess')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <!-- box modal end -->
        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="../assets/js/main.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script src="../assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
    </body>
</html>

