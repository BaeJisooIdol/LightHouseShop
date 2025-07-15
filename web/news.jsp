<%-- 
    Document   : news
    Created on : Jan 3, 2025, 3:19:13 PM
    Author     : Kim Soan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="store">
                <div class="container">
                    <div class="row">
                        <h1>Our Stores</h1>
                        <!-- news start -->
                        <section id="store">
                            <div class="store-body">
                                <div class="container">
                                    <div class="row store-container">
                                        <div class="store-items-container col-lg-6 col-md-6">
                                            <div class="store-item">
                                                <div class="store-img">
                                                    <img src="./assets/imgs/store1.png" alt="store image"/>
                                                </div>
                                                <div class="store-content">
                                                    <h2 class="store-header">HCM SIGNATURE by The Light House Coffee</h2>
                                                    <p class="store-open">07:00 - 22:00</p>
                                                    <desc class="store-info">TTTM Crescent Mall, 101 Tôn Dật Tiên, Phường Tân Phú, Quận 7, Thành phố Hồ Chí Minh</desc>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </main>
        <!-- main end -->
        <!-- footer start -->
        <jsp:include page="footer.jsp" />
        <!-- footer start -->

        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="./assets/js/main.js?version=<%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
    </body>
</html>
