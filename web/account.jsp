<%-- 
    Document   : account
    Created on : Jan 3, 2025, 5:38:12 PM
    Author     : Phạm Hải Đăng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Account</title>
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
        <main>
            <section class="handel-account">
                <div class="container-fluid">
                    <div class="row">
                        <div class="account-info col-lg-6 col-md-12 col-sm-12">

                            <div class="account-info-container">
                                <div class="account-header">
                                    <a href="home"><img src="./assets/imgs/logo.png" alt="logo"></a>
                                    <div class="account-nav">
                                        <span onclick="handelAccount('login')">Login</span>
                                        <span onclick="handelAccount('register')">Register</span>
                                    </div>
                                </div>

                                <div class="account-body">
                                    <h1 class="title">Login</h1>
                                    <p>Log in to continue in our website</p>
                                    <form class="form-group" action="login" method="post">
                                        <div class="form-input">
                                            <input type="text" class="username" name="username" value="${cookie.username.value}" required>
                                            <span>User name</span>
                                            <span class="message"></span>
                                        </div>
                                        <div class="form-input">
                                            <input type="password" class="password" name="password" required>
                                            <span>Password</span>
                                            <span class="eye" onclick="displayPass(this)"><i class="eye-icon fa-solid fa-eye-slash"></i></span>
                                            <span class="message"></span>
                                        </div>
                                        <div class="refoget">
                                            <div class="remember">
                                                <input type="checkbox" id="accountRemember" name="remember" class="accountRemember" ${cookie.isRemember.value}>
                                                <label for="accountRemember">Remember me</label>
                                            </div>
                                            <div class="forgot-pass" onclick="handelAccount('forgot-password')">
                                                Forgot password? 
                                            </div>
                                        </div>
                                        <button type="submit" class="account-btn bttn">
                                            <span>Login</span>
                                        </button>
                                        <div class="error">${requestScope.errorLogin}</div>
                                        <p class="option-login">or login with</p>
                                        <div class="method-login">
                                            <a href="login?method=googleLogin">
                                                <i class="fa-brands fa-google"></i>
                                            </a>
                                            <a href="login?method=facebookLogin">
                                                <i class="fa-brands fa-facebook-f"></i>
                                            </a>
                                        </div>
                                    </form>
                                </div>
                                <div class="render-error"></div>
                            </div>

                        </div>

                        <div class="right-img col-lg-6 col-md-0 col-sm-0">
                            <img src="./assets/imgs/background-account.png"
                                 alt="Login image" class="w-100 vh-100" style="object-fit: cover;
                                 object-position: left;">
                        </div>
                    </div>
                </div>
            </section>
        </main>

        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="./assets/js/main.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script src="./assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script>
                                               
                                                if (${not empty method}) {
                                                    const url = window.location.href;
                                                    // Create object URLSearchParams to take the params from URL
                                                    let params = new URLSearchParams(url.split("?")[1]);
                                                    // Take value of `data`, `email` and decode
                                                    let method = params.get("method");

                                                    let encodedEmail = params.get("email");
                                                    let decodedEmail = (encodedEmail !== null) ? decodeURIComponent(encodedEmail) : '';

                                                    let encodedData = params.get("data");
                                                    let decodedData = (encodedData !== null) ? decodeURIComponent(encodedData) : '';
                                                    handelAccount(method, decodedEmail, decodedData);
                                                }
        </script>
    </body>
</html>
