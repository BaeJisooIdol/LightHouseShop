<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                        <h1>Create New Account</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container admin-main-content-body-form">
                                    <form class="admin-main-content-body-form create-account" action="account?action=create-account" method="post">

                                        <div class="admin-main-content-body-group admin-main-group-account">
                                            <label for="username">User Name</label>
                                            <input type="text" name="username" id="username" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>

                                        <div class="admin-main-content-body-group admin-main-group-account">
                                            <label for="password">Password</label>
                                            <input type="text" name="password" id="password" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>

                                        <div class="admin-main-content-body-group admin-main-group-account">
                                            <label for="fullname">Full Name</label>
                                            <input type="text" name="fullname" id="fullname" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>

                                        <div class="admin-main-content-body-group admin-main-group-account">
                                            <label for="email">Email</label>
                                            <input type="email" name="email" id="email" required>
                                            <span class="message admin-main-content-body-group-error"></span>
                                        </div>

                                        <div class="admin-account-group">
                                            <div class="admin-main-content-body-group admin-main-group-account">
                                                <label for="password">Phone</label>
                                                <input type="number" name="phone" id="phone" min="0" max="9" step="1" required>
                                                <span class="message admin-main-content-body-group-error"></span>
                                            </div>

                                            <div class="admin-main-content-body-group admin-main-group-account">
                                                <label for="image">Image</label>
                                                <input type="text" name="image" id="image" required>
                                                <span class="message admin-main-content-body-group-error"></span>
                                            </div>
                                        </div>

                                        <div class="admin-account-group">
                                            <div class="admin-main-content-body-group admin-main-group-account">
                                                <label for="role">Role</label>
                                                <select name="role" id="role" required>
                                                    <option value="Customer">Customer</option>
                                                    <option value="Admin">Admin</option>
                                                </select>
                                                <span class="message admin-main-content-body-group-error"></span>
                                            </div>

                                            <div class="admin-main-content-body-group admin-main-group-account">
                                                <label for="status">Status</label>
                                                <select name="status" id="status" required>
                                                    <option value="ON">ON</option>
                                                    <option value="OFF">OFF</option>
                                                </select>
                                                <span class="message admin-main-content-body-group-error"></span>
                                            </div>
                                        </div>

                                        <div class="admin-main-content-body-form-control">
                                            <button class="bttn admin-main-content-body-form-control-back" type="button">
                                                <a href="account">
                                                    <span>Back</span>
                                                </a>
                                            </button>
                                            <button class="bttn" type="submit">
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
        <div class="modall box-modal create-account-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Create a new account successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('create-account')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <!-- box modal end -->

        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="../assets/js/main.js?version=<%= System.currentTimeMillis() %>"></script>
        <script src="../assets/js/validation.js?version=<%= System.currentTimeMillis() %>"></script>
        <c:if test="${not empty requestScope.createAccountSuccess}">
            <c:remove var="createAccountSuccess" scope="request" />
            <script>
                    document.querySelector('.create-account-success').style.display = 'flex';
            </script>
        </c:if>

    </body>
</html>
