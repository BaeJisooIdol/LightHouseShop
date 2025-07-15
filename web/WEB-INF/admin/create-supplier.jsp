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
                                        <h1>Create New Supplier</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container admin-main-content-body-form">
                                    <form class="admin-main-content-body-form create-supplier" action="supplier?action=create-supplier" method="post">
                                        <div class="admin-main-content-body-group">
                                            <label>Supplier name</label>
                                            <input class="suppliername" type="text" name="suppliername" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>
                                        <div class="admin-main-content-body-group">
                                            <label>Contact name</label>
                                            <input type="text" name="contactname" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>
                                        <div class="admin-main-content-body-group">
                                            <label>Phone</label>
                                            <input type="number" name="phone" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>
                                        <div class="admin-main-content-body-group">
                                            <label>Email</label>
                                            <input type="email" name="email" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>
                                        <div class="admin-main-content-body-group">
                                            <label>Address</label>
                                            <input type="address" name="address" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>
                                        <div class="admin-main-content-body-form-control">
                                            <button class="bttn admin-main-content-body-form-control-back" type="button">
                                                <span>Back</span>
                                                <a href="supplier"></a>
                                            </button>
                                            <button onclick="handelSupplier(event, 'create-supplier')" class="bttn" type="submit">
                                                <span>Create</span>
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

        <!-- box modal start -->
        <div class="modall box-modal create-supplier-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Create a new supplier successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('create-supplier')">
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
