<%-- 
    Document   : product
    Created on : Mar 3, 2025, 11:10:30 PM
    Author     : Pham Quoc Tu - CE181513
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Admin - Manage Product</title>
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
                                        <h1 class="col-lg-6 col-md-6">Manage Product</h1>

                                        <div class="admin-main-content-search col-lg-6 col-md-6">
                                            <input oninput="searchProduct(this)" type="text" name="searchProduct" placeholder="Search for product name" >
                                            <button><i class="fa fa-search"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container container-lg admin-main-table-container">
                                    <table class="admin-main-content-body-table">
                                        <thead class="sticky-top">
                                            <tr>
                                                <th><i class="fa-solid fa-id-badge"></i></th>
                                                <th>Category</th>
                                                <th>Product Name</th>
                                                <th><i class="fa-solid fa-image"></i></th>
                                                <th><i class="fa-solid fa-money-bill"></i></th>
                                                <th><i class="fa-solid fa-power-off"></i></th>
                                                <th class="admin-main-content-body-table-control"><button> <a href="product?action=create"><i class="fa-solid fa-plus"></i></a></button></th>
                                            </tr>
                                        </thead>
                                        <tbody class="admin-main-content-body-tbody">
                                            <c:forEach items="${listProduct}" var="s">
                                                <tr>
                                                    <td>${s.id}</td>			
                                                    <td>${s.categoryName}</td>
                                                    <td>${s.name}</td>
                                                    <td><img src=".${s.image}" class="rounded" alt=""/></td>
                                                    <td>${String.format("%.0f",s.price)}</td>
                                                    <td><i class="fa-solid ${s.status.equals("Available")? "fa-toggle-on":"fa-toggle-off"}"> </i></td>
                                                    <td class="admin-main-content-body-table-control">
                                                        <button><a href="product?action=edit&id=${s.id}"><i class="fa-solid fa-pen"></i></a></button>
                                                        <button><a href="product?action=delete&id=${s.id}" onclick="return confirm('Are you sure you want to delete?');"><i class="fa-solid fa-trash"></i></a></button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <div class="admin-main-content-body-paging">
                                        <ul>
                                            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                                <c:if test="${i == 1}"><li class="active" onclick="pagingProduct(this)">${i}</li></c:if>
                                                <c:if test="${i != 1}"><li onclick="pagingProduct(this)">${i}</li></c:if>
                                                </c:forEach>
                                        </ul>
                                    </div>

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
    </body>
</html>
