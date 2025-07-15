<%-- 
    Document   : header
    Created on : Feb 28, 2025, 12:30:06 AM
    Author     : Phạm Hải Đăng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="header">
    <div class="container">
        <div class="row">
            <div class="side-bar col-md-4 col-sm-4 col-3">
                <label onclick="handleNavBar(`block`)" for="x" class="bar-icon fa-solid fa-bars"></label>
            </div>
            <div class="header-logo col-md-4 col-lg-3 col-sm-4 col-4">
                <a href="home">
                    <img src="./assets/imgs/logo.png" alt="logo">
                </a>
            </div>

            <nav class="header-navbar col-lg-6">
                <div class="header-navbar-title">
                    <h2 >Categories</h2>
                    <label onclick="handleNavBar(`none`)" for="x"  class="exit-icon fa-solid fa-x"></label>
                </div>
                <ul>
                    <li>
                        <span class="menu-icons">
                            <i class="fa-solid fa-mug-hot"></i>
                        </span>
                        <h3 class="header-bar-title" onclick="collection(1)">Coffee</h3>
                    </li>
                    <li >
                        <span class="menu-icons">
                            <i class="fa-solid fa-cake-candles"></i>
                        </span>
                        <h3 class="header-bar-title" onclick="collection(2)">Cake</h3>
                    </li>
                    <li >
                        <span class="menu-icons">
                            <i class="fa-solid fa-glass-water"></i>
                        </span>
                        <h3 class="header-bar-title" onclick="collection(3)">Milk Tea</h3>
                    </li>
                    <li >
                        <span class="menu-icons">
                            <i class="fa-solid fa-newspaper"></i>
                        </span>
                        <h3 class="header-bar-title" onclick="getNews()">News</h3>
                    </li>
                    <li>
                        <span class="menu-icons">
                            <i class="fa-solid fa-store"></i>
                        </span>
                        <h3 class="header-bar-title" onclick="getStore()">Store</h3>
                    </li>
                    <span class="line">

                    </span>
                </ul>
            </nav>
            <label onclick="handleNavBar(`none`)" for="x" class="modall"></label>
            <div class="header-right col-md-4 col-lg-3 col-sm-4 col-5">
                <div class="header-right-user">
                    <c:choose>
                        <c:when test="${sessionScope.account == null}">
                            <i class="header-right-icons fa-solid fa-user"></i>
                            <div class="header-right-user-info">
                                <ul>
                                    <li>
                                        <a href="login"> <i class="fa-solid fa-right-to-bracket"></i> Sign in</a>
                                    </li>
                                    <li>
                                        <a href="account?method=register"><i class="fa-solid fa-pen-to-square"></i> Sign up</a>
                                    </li>
                                </ul>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <span class="fire user-name">${sessionScope.account.name}</span>
                            <c:if test="${sessionScope.account.picture != null}">
                                <div class="header-right-user-avatar"><img class="avatar" src="${sessionScope.account.picture}" referrerpolicy="no-referrer" alt="avatar"/></div>
                                </c:if>
                                <c:if test="${sessionScope.account.picture == null}">
                                <div class="header-right-user-avatar"><img class="avatar" src="./assets/imgs/default-user.png" referrerpolicy="no-referrer" alt="avatar"/></div>
                                </c:if>

                            <div class="header-right-user-info">
                                <ul>
                                    <li>
                                        <a href="profile"><i class="fa-solid fa-address-card"></i> My account</a>
                                    </li>
                                    <li>
                                        <a href="order"><i class="fa-solid fa-pizza-slice"></i> My orders</a>
                                    </li>
                                    <li>
                                        <a href="transaction"><i class="fa-solid fa-money-bill-transfer"></i> My transactions</a>
                                    </li>
                                    <li>
                                        <a href="account?method=logout"> <img src="./assets/imgs/log_out.png" alt="log_out"/>Log out</a>
                                    </li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <c:if test="${not empty sessionScope.account and sessionScope.account.role eq 'Admin'}">
                    <div class="header-right-admin">
                        <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fa-solid fa-user-secret"></i></a>
                        <div class="header-right-admin-tooltip"><span>Admin</span></div>
                    </div>
                </c:if>


                <div class="header-right-cart">
                    <i class="header-right-icons fa-solid fa-cart-shopping"></i>
                    <div class="header-right-cart-quantity">
                        <c:if test="${empty requestScope.listOrderDetails}">
                            <span>0</span>
                        </c:if>
                        <c:if test="${not empty requestScope.listOrderDetails}">
                            <span>${requestScope.listOrderDetails.size()}</span>
                        </c:if>
                    </div>
                    <c:if test="${empty requestScope.listOrderDetails}">
                        <div class="header-right-cart-info">
                            <img src="./assets/imgs/cart-empty.png" alt="cart-empty image"/>
                            <h3>Your cart is empty</h3>
                            <p>Looks like you haven't made</p>
                            <p>Your choice yet...</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.listOrderDetails}">
                        <div style="padding: 0" class="header-right-cart-info">
                            <div class="header-right-cart-info-header">
                                Your Cart
                            </div>
                            <div class="header-right-cart-info-items">
                                <c:forEach items="${requestScope.listOrderDetails}" var="i">
                                    <div class="header-right-cart-info-item" onclick="cartItem(this, ${i.product.id})">
                                        <input hidden type="text" name="payment" value="${i.payment}"> 
                                        <div class="cart-info-item-img">
                                            <img src="${i.product.image}" alt="alt"/>
                                        </div>
                                        <div class="cart-info-item-content">
                                            <div class="cart-info-item-content-up">
                                                <div class="cart-info-item-name">
                                                    ${i.product.name}
                                                </div>
                                                <div class="cart-info-item-price">
                                                    ${i.formatPrice()}đ
                                                </div>
                                                <div>x</div>
                                                <div class="cart-info-item-quantity">
                                                    ${i.quantity} 
                                                </div>
                                            </div>
                                            <div class="cart-info-item-content-down">
                                                <div class="cart-info-item-sizeAndTopping">
                                                    ${i.size}<c:if test="${not empty i.topping}">;${i.topping}</c:if>
                                                    </div>
                                                    <button class="btn-remove-item" onclick="removeItemCart(${i.oId}, this, event)">
                                                    remove
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                            <c:if test="${requestScope.listOrderDetails.size() <= 1}">
                                <div class="cart-info-total-product">
                                    Total ${requestScope.listOrderDetails.size()} product
                                </div>
                            </c:if>
                            <c:if test="${requestScope.listOrderDetails.size() > 1}">
                                <div class="cart-info-total-product">
                                    Total ${requestScope.listOrderDetails.size()} products
                                </div>
                            </c:if>
                        </div>
                    </c:if>

                </div>
                <div class="header-right-setting">
                    <i class="header-right-icons fa-solid fa-gear"></i>
                    <div class="header-right-setting-info">
                        <div class="header-right-setting-info-header">
                            <div>
                                <h3>Language</h3>
                                <i class="header-right-setting-info-icons fa-solid fa-caret-down"></i>
                            </div>
                            <ul>
                                <li onclick="changeLanguage('vi')">
                                    <i class="fa-solid fa-dragon"></i> <span>Vietnamese</span>
                                </li>
                                <li onclick="changeLanguage('en')">
                                    <i class="fa-solid fa-hippo"></i> <span>English</span>
                                </li>
                                <div hidden id="google_translate_element"></div>
                            </ul>
                        </div>
                        <div class="header-right-setting-info-header">
                            <div>
                                <h3>Theme</h3>
                                <i class="header-right-setting-info-icons fa-solid fa-caret-down"></i>
                            </div>
                            <ul>
                                <li onclick="toggleTheme('Dark')">
                                    <i class="fa-solid fa-sun"></i> <span>Dark</span>
                                </li>
                                <li onclick="toggleTheme('Light')">
                                    <i class="fa-solid fa-moon"></i> <span>Light</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
