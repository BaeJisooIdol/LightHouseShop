<%-- 
    Document   : admin-header
    Created on : Mar 1, 2025, 10:27:28 PM
    Author     : Phạm Hải Đăng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header id="admin-header">
    <div class="container">
        <div class="row admin-header-container">
            <div class="side-bar col-md-4 col-sm-4 col-sm-4 col-4">
                <label onclick="showAdminBar()" for="x" class="bar-icon fa-solid fa-bars"></label>
            </div>
            <div class="header-logo col-md-4 col-lg-6 col-sm-4 col-4">
                <a href="../home">
                    <img src="../assets/imgs/logo.png" alt="logo">
                </a>
            </div>
            <div class="admin-header-info col-lg-6 col-md-4 col-sm-4 col-4">
                <div class="header-right-user admin-header-right-user">

                    <span class="fire user-name">${sessionScope.account.name}</span>
                    <c:if test="${sessionScope.account.picture != null}">
                        <div class="header-right-user-avatar"><img class="avatar" src=".${sessionScope.account.picture}" referrerpolicy="no-referrer" alt="avatar"/></div>
                        </c:if>
                        <c:if test="${sessionScope.account.picture == null}">
                        <div class="header-right-user-avatar"><img class="avatar" src="../assets/imgs/default-user.png" referrerpolicy="no-referrer" alt="avatar"/></div>
                        </c:if>

                    <div class="header-right-user-info">
                        <ul>
                            <li>
                                <a href="../profile"><i class="fa-solid fa-address-card"></i> My account</a>
                            </li>
                            <li>
                                <a href="../order"><i class="fa-solid fa-pizza-slice"></i> My orders</a>
                            </li>
                            <li>
                                <a href="../transaction"><i class="fa-solid fa-money-bill-transfer"></i> My transactions</a>
                            </li>
                            <li>
                                <a href="../account?method=logout"> <img src="../assets/imgs/log_out.png" alt="log_out"/>Log out</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="header-right-setting admin-header-right-setting">
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

