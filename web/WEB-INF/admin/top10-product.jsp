<%-- 
    Document   : top10-product
    Created on : Mar 1, 2025, 2:58:02 PM
    Author     : Tu
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
                                        <h1 class="col-lg-12 col-md-12">Top 10 Products</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container container-lg admin-main-table-container admin-main-table-container-top10">
                                    <table class="admin-main-content-body-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Product Name</th>
                                                <th>Image</th>
                                                <th>Rating</th>
                                                <th>Price</th>
                                                <th>Sold</th>
                                            </tr>
                                        </thead>
                                        <tbody class="admin-main-content-body-tbody">
                                            <c:choose>
                                                <c:when test="${empty requestScope.listTop10Products}">
                                                    <tr>
                                                        <td colspan="6" style="text-align: center; font-size: 2rem;">Nothing in top 10 products found!</td>			
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${requestScope.listTop10Products}" var="p">
                                                        <tr>
                                                            <td>${p.id}</td>			
                                                            <td>${p.name}</td>
                                                            <td>
                                                                <div class="admin-main-content-body-tbody-img">
                                                                    <img src=".${p.image}" alt="product img"/>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div class="products-items-stars">
                                                                    <c:forEach begin="1" end="5" var="i">
                                                                        <c:if test="${i <= p.rating}">
                                                                            <i style="color: orange" class="start-icon fa fa-star"></i>
                                                                        </c:if>
                                                                        <c:if test="${i > p.rating}">
                                                                            <i style="color: #000" class="start-icon fa fa-star"></i>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </div>
                                                            </td>
                                                            <td>${p.formatPrice(p.price)}đ</td>
                                                            <td>${p.quantity}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr class="admin-main-content-body-table-notFound">
                                                <td colspan="6" style="text-align: center; font-size: 2rem;">Nothing in top 10 products found!</td>			
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>   
                    </section>
                </div>
            </div>
        </main> 

        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="../assets/js/main.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script src="../assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
    </body>
</html>
