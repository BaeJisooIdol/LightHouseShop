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
            <!-- carousel image start -->
            <div id="slideshow" class="carousel slide" data-bs-ride="true">
                <div class="carousel-indicators">
                    <button type="button" data-bs-target="#slideshow" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#slideshow" data-bs-slide-to="1" aria-label="Slide 2"></button>
                    <button type="button" data-bs-target="#slideshow" data-bs-slide-to="2" aria-label="Slide 3"></button>
                </div>
                <div class="carousel-inner">
                    <div class="carousel-item active" data-bs-interval="3000">
                        <img src="./assets/imgs/banner1.png" class="d-block w-100" alt="banner1">
                    </div>
                    <div class="carousel-item" data-bs-interval="3000">
                        <img src="./assets/imgs/banner2.png" class="d-block w-100" alt="banner2">
                    </div>
                    <div class="carousel-item" data-bs-interval="3000">
                        <img src="./assets/imgs/banner3.png" class="d-block w-100" alt="banner3">
                    </div>
                </div>
                <div class="slideshow-controls">
                    <button class="carousel-control-prev" type="button" data-bs-target="#slideshow" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Previous</span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#slideshow" data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="visually-hidden">Next</span>
                    </button>
                </div>
            </div>
            <!-- carousel image end -->
            <!-- new products start -->
            <section id="new-products">
                <h2 class="new-products-heading">New Products</h2>
                <div class="new-products-body">
                    <c:set var="limitIndicator" value="4" />
                    <c:set var="listNewProductSize" value="${requestScope.newProducts.size()}" />
                    <div class="container new-products-container">
                        <div product-size="${listNewProductSize}" class="row new-products-block">
                            <c:forEach items="${requestScope.newProducts}" var="n" >
                                <div class="products-items col-lg-3 col-md-6 col-sm-6 col-6">
                                    <a class="products-img ribbon-new" href="#" onclick="productDetail(${n.id})">
                                        <img src="${n.image}" alt="new-product-img"/>
                                    </a>
                                    <h3>
                                        <a href="#">${n.name}</a>
                                    </h3>
                                    <div class="products-items-info">
                                        <div>
                                            <c:if test="${n.discount == 0.0}">
                                                <span>${n.formatPrice(n.price)}đ</span>
                                            </c:if>
                                            <c:if test="${n.discount != 0.0}">
                                                <s>${n.formatPrice(n.price)}đ</s>
                                                <span>${n.formatPrice(n.newPrice)}đ</span>
                                            </c:if>

                                        </div>
                                        <div class="products-items-stars">
                                            <c:set var="numberStars" value="${n.rating}" />
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:if test="${i <= numberStars}">
                                                    <i class="start-icon fa fa-star"></i>
                                                </c:if>
                                                <c:if test="${i > numberStars}">
                                                    <i class="fa fa-star"></i>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="new-products-body-controls">
                            <c:if test="${listNewProductSize > limitIndicator}">
                                <div class="new-products-body-left active" onclick="carouselProduct('pre')">
                                    <i class="fa-solid fa-arrow-left"></i>
                                </div>
                                <div class="new-products-body-right" onclick="carouselProduct('next')">
                                    <i class="fa-solid fa-arrow-right"></i>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="new-product-indicators">
                        <%--<c:if test="${listNewProductSize <= limitIndicator}">
                            <div class="active"></div>
                        </c:if> --%>
                        <c:if test="${listNewProductSize > limitIndicator}">
                            <c:forEach begin="4" end="${listNewProductSize}" var="i">
                                <c:if test="${i == 4}">
                                    <div onclick="updateIndex(`${i-4}`)" class="active"></div>
                                </c:if>
                                <c:if test="${i > 4}">
                                    <div onclick="updateIndex(`${i-4}`)"></div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </section>
            <!-- new products end -->
            <!-- best seller start -->
            <section id="best-seller">
                <h2 class="best-seller-heading">Best Seller</h2>
                <div class="best-seller-body">
                    <div class="container">
                        <div class="row">
                            <div class="products-items col-lg-6 col-md-12 col-sm-12 col-12">
                                <a class="products-img" href="#">
                                    <img src="https://file.hstatic.net/1000075078/file/banner_app_8a179a636b7349efacfc6b6837c7c67c.jpg" alt="alt"/>
                                </a>
                            </div>
                            <c:forEach items="${requestScope.bestProducts}" var="b" >
                                <div class="products-items col-lg-3 col-md-6 col-sm-6 col-6">
                                    <a class="products-img ribbon-best-seller" href="#" onclick="productDetail(${b.id})">
                                        <img src="${b.image}" alt="new-product-img"/>
                                    </a>
                                    <h3>
                                        <a href="#">${b.name}</a>
                                    </h3>
                                    <div class="products-items-info">
                                        <div>
                                            <c:if test="${b.discount == 0.0}">
                                                <span>${b.formatPrice(b.price)}đ</span>
                                            </c:if>
                                            <c:if test="${b.discount != 0.0}">
                                                <s>${b.formatPrice(b.price)}đ</s>
                                                <span>${b.formatPrice(b.newPrice)}đ</span>
                                            </c:if>

                                        </div>
                                        <div class="products-items-stars">
                                            <c:set var="numberStars" value="${b.rating}" />
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:if test="${i <= numberStars}">
                                                    <i class="start-icon fa fa-star"></i>
                                                </c:if>
                                                <c:if test="${i > numberStars}">
                                                    <i class="fa fa-star"></i>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </section>
            <!-- best seller end -->
            <!-- hot deal start -->
            <section id="hot-deal">
                <h2 class="hot-deal-heading">Hot Deal
                    <span><i class="fa-solid fa-fire"></i></span>
                </h2>
                <div class="hot-deal-body">
                    <div class="container">
                        <div class="row">
                            <div class="products-items col-lg-6 col-md-12 col-sm-12 col-12">
                                <a class="products-img" href="#">
                                    <img src="https://file.hstatic.net/1000075078/file/banner_app_8a179a636b7349efacfc6b6837c7c67c.jpg" alt="alt"/>
                                </a>
                            </div>
                            <c:forEach items="${requestScope.hotProducts}" var="h" >
                                <div class="products-items col-lg-3 col-md-6 col-sm-6 col-6">
                                    <a class="products-img ribbon-hot-deal" href="#" data-content="${h.discount}%" onclick="productDetail(${h.id})">
                                        <img src="${h.image}" alt="new-product-img"/>
                                    </a>
                                    <h3>
                                        <a href="#">${h.name}</a>
                                    </h3>
                                    <div class="products-items-info">
                                        <div>
                                            <s>${h.formatPrice(h.price)}đ</s>
                                            <span>${h.formatPrice(h.newPrice)}đ</span>
                                        </div>
                                        <div class="products-items-stars">
                                            <c:set var="numberStars" value="${h.rating}" />
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:if test="${i <= numberStars}">
                                                    <i class="start-icon fa fa-star"></i>
                                                </c:if>
                                                <c:if test="${i > numberStars}">
                                                    <i class="fa fa-star"></i>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <button class="see-more bttn" onclick="collection(1)">
                    <span>See more</span>
                </button>
            </section>
            <!-- hot deal end -->
            <!-- news start -->
            <section id="news">
                <h2 class="news-heading">News
                    <span><i class="fa-solid fa-newspaper"></i></span>
                </h2>
                <div class="news-body">
                    <div class="container">
                        <div class="row">
                            <c:forEach begin="0" end="2" var="i">
                                <div class="news-items-container col-lg-4 col-md-6 col-sm-6 col-6">
                                    <div class="news-items">
                                        <div class="news-items-img" onclick="getNews(`${requestScope.listNews.get(i).nId}`)">
                                            <img src="${requestScope.listNews.get(i).img}" alt="alt"/>
                                        </div>
                                        <p>${requestScope.listNews.get(i).startDate}</p>
                                        <h3>
                                            <span onclick="getNews(`${requestScope.listNews.get(i).nId}`)">${requestScope.listNews.get(i).header}</span>
                                        </h3>
                                        <p class="news-items-content">${requestScope.listNews.get(i).content}</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </section>
            <!-- news end -->
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
