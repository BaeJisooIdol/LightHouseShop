<%-- 
    Document   : profile
    Created on : Jan 3, 2025, 3:19:13 PM
    Author     : Kim Soan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${sessionScope.account}" var="user" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Light House Shop</title>
        <link rel="icon" href="./assets/imgs/logo.png"/>
        <!-- Link icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Link bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" 
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <!-- Link css files -->
        <link rel="stylesheet" href="./assets/css/style.css?version=<%= System.currentTimeMillis() %>"/>
        <link rel="stylesheet" href="./assets/css/main.css?version=<%= System.currentTimeMillis() %>"/>
        <link rel="stylesheet" href="./assets/css/responsive.css?version=<%= System.currentTimeMillis() %>"/>
    </head>
    <body>
        <!-- header start -->
        <jsp:include page="header.jsp" />
        <!-- header start -->
        <!-- main start -->
        <main id="main">
            <div id="profile">
                <div class="profile-background"></div>
                <div class="container-xxl">
                    <h1>My Profile</h1>
                    <div class="row profile-container">
                        <section class="profile-left col-lg-3">
                            <div class="profile-left-content">
                                <div class="profile-left-header">
                                    Profile Picture
                                </div>
                                <div class="profile-left-body">
                                    <div class="profile-left-body-avatar">
                                        <img class="profile-left-body-avatar-img" src="${user.picture}" referrerpolicy="no-referrer" alt="avatar"/>
                                    </div>
                                    <div class="profile-left-body-changeAvatar" onclick="changeAvatar(this)">
                                        <input onchange="doChangeAvatar(this, event)" type="file" id="fileInput" accept="image/*" style="display: none;">
                                        JPG or PNG no larger than 5 MB <i style="margin-left: 0.5rem" class="fa-solid fa-pen-to-square"></i>
                                    </div>
                                </div>
                            </div>

                            <div class="profile-left-content profile-left-balance">
                                <div class="profile-left-header">
                                    Your Wallet
                                </div>
                                <div class="profile-left-body">
                                    <div class="profile-left-body-balance">
                                        <div class="profile-left-balance-money">
                                            ${user.formatPrice(user.balance)}<span>đ</span>
                                        </div>
                                        <button class="profile-left-balance-add" onclick="reCharge()">
                                            <i class="fa-solid fa-plus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="profile-left-input">
                                    <input type="text" name="balance" placeholder="VND" oninput="formatCurrencyVND(this)">
                                    <button class="bttn" onclick="doRecharge(this)">
                                        <span>Pay</span>
                                    </button>
                                    <p class="err-price err-balance">
                                        <span></span>
                                    </p>
                                </div>
                            </div>
                        </section>
                        <section class="profile-right col-lg-9">
                            <div class="profile-right-content">
                                <div class="profile-right-header">
                                    Your Profile
                                </div>
                                <form class="profile-right-body">
                                    <div class="profile-form-group">
                                        <label>User name</label>
                                        <input class="active" type="text" name="username" value="${user.name}" required>
                                        <span class="message"></span>
                                    </div>
                                    <div class="profile-form-group">
                                        <label>Password</label>
                                        <input class="active" type="password" name="password" value="${user.password}" required>
                                        <span class="message"></span>
                                        <span class="eye profile-form-eye" onclick="displayPass(this)"><i class="eye-icon fa-solid fa-eye-slash"></i></span>
                                    </div>
                                    <div class="profile-form-group">
                                        <label>Full name</label>
                                        <input class="active" type="text" name="fullname" value="${user.fullName}" required>
                                        <span class="message"></span>
                                    </div>
                                    <div class="profile-phone-email">
                                        <div class="profile-form-group">
                                            <label>Phone</label>
                                            <input class="active" type="number" name="phone" value="${user.phone}" required>
                                            <span class="message"></span>
                                        </div>
                                        <div class="profile-form-group">
                                            <label>Email</label>
                                            <input class="active" type="email" name="email" value="${user.email}" required>
                                            <span class="message"></span>
                                        </div>
                                    </div>
                                    <div class="profile-form-group">
                                        <label>Address</label>
                                        <input class="active" type="address" name="address" value="${user.address}" required>
                                    </div>
                                    <div class="profile-form-control">
                                        <button onclick="editProfile(this, event)" class="bttn profile-right-body-submit" type="submit">
                                            <span>Edit</span>
                                        </button>
                                        <span class="render-error profile-error"></span>
                                    </div>
                                </form>
                            </div>

                        </section>
                    </div>
                </div>
            </div>
        </main>
        <!-- main end -->
        <!-- box modal start -->
        <div class="modall box-modal">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Your personal information has been updated.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('update-profile')">
                    <span>OK</span>
                </button>
            </div>
        </div>

        <div class="modall box-modal recharge">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Your balance increased by ${sessionScope.recharge}đ.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('close-box', 'recharge')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <!-- box modal end -->
        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="./assets/js/main.js?version=<%= System.currentTimeMillis() %>"></script>
        <script src="./assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <c:if test="${not empty sessionScope.recharge}">
            <!-- Remove attribute after render -->
            <c:remove var="recharge" scope="session"/>
            <script>
                    document.querySelector('.recharge').style.display = 'flex';
            </script>
        </c:if>

    </body>
</html>
