<%-- 
    Document   : order
    Created on : Jan 3, 2025, 3:19:13 PM
    Author     : Duy
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
            <div class="orders">
                <div class="container">
                    <div class="row">
                        <h1>My Orders</h1>
                        <div class="order-search">
                            <input type="text" name="orderSearch" oninput="orderSearch(this)" placeholder="Search for product name">
                            <button><i class="fa fa-search"></i></button>
                        </div>
                        <div class="orders-body">
                            <div class="order-items">
                                <c:choose>
                                    <c:when test="${empty requestScope.listOrders}">
                                        <p style="font-size: 2.5rem;" class="order-empty">You have not made any order yet.</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${requestScope.listOrders}" var="o">
                                            <div class="order-item">
                                                <input type="text" name="id" hidden="" value="${o.id}">
                                                <div class="order-item-img">
                                                    <img src="${o.pImage}" alt="product image"/>
                                                </div>
                                                <div class="order-item-content">
                                                    <div class="order-item-upper">
                                                        <h2>${o.pName}</h2>
                                                        <p class="order-item-upper-status">${o.status}</p>
                                                        <div class="order-item-control">
                                                            <button class="bttn order-item-upper-btn" onclick="productDetail(${o.pId})">
                                                                <span>Buy Again</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="order-item-mid">
                                                        <p class="order-item-mid-paydate">${o.payDate}</p>
                                                        <p class="order-item-mid-paymethod">${o.payMethod}</p>
                                                        <div class="order-item-control">
                                                            <button class="bttn order-item-mid-btn ${o.status != "Processing" ? "active" : ""}" onclick="cancelOrder(${o.id}, `${o.pName}`)">
                                                                <span>Cancel</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                    <div class="order-item-lower">
                                                        <p class="order-item-lower-desc">${o.size}${o.topping.isEmpty() ? "" : ";"}${o.topping}</p>
                                                        <p class="order-item-upper-quantity">${o.quantity}</p>
                                                        <span class="order-item-upper-x">x</span>
                                                        <p class="order-item-upper-totalPrice">${o.formatPrice(o.totalPrice)}đ</p>
                                                        <div class="order-item-control">
                                                            <button class="bttn order-item-lower-btn ${o.status == "Processing" ? "disable" : ""}" onclick="deleteOrder(${o.id}, `${o.pName}`)">
                                                                <span>Delete</span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="order-paging">
                                <ul>
                                    <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                        <c:if test="${i == 1}">
                                            <li class="active" onclick="OrderPaging(this)">${i}</li>
                                            </c:if>
                                            <c:if test="${i != 1}">
                                            <li onclick="OrderPaging(this)">${i}</li>
                                            </c:if>
                                        </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- main end -->
        <!-- footer start -->
        <jsp:include page="footer.jsp" />
        <!-- footer start -->

        <!-- box modal start -->
        <%-- Box wrong id --%>
        <div class="modall box-modal order-wrong-id">
            <div class="box-modal-container">
                <i style="color: grey" class="box-icon fa-regular fa-circle-xmark"></i>
                <h3>Fail</h3>
                <p></p>
                <button style="background-color: grey" class="bttn box-modal-btn" onclick="closeBox('close-box', 'order-wrong-id')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <%-- Box cancel order --%>
        <div class="modall box-modal box-large order-cancel">
            <input type="text" name="id" hidden>
            <div class="box-modal-container">
                <i style="color: grey" class="box-icon fa-regular fa-circle-xmark"></i>
                <h3>Cancel</h3>
                <p>Do you want to cancel order with name <b style="font-size: 1.8rem"></b> ?</p>
                <div class="box-modal-control">
                    <button class="bttn box-modal-btn btn-back" onclick="closeBox('close-box', 'order-cancel')">
                        <span>Back</span>
                    </button>
                    <button class="bttn box-modal-btn" onclick="closeBox('cancel-order')">
                        <span>OK</span>
                    </button>
                </div>
            </div>
        </div>
        <%-- Box cancel success --%>
        <div class="modall box-modal cancel-order-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Cancel successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('cancel-order-success')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <%-- Box delete order --%>
        <div class="modall box-modal box-large order-delete">
            <input type="text" name="id" hidden>
            <div class="box-modal-container">
                <i style="color: orangered" class="box-icon fa-solid fa-trash"></i>
                <h3>Delete</h3>
                <p>Do you want to delete order with name <b style="font-size: 1.8rem"></b> ?</p>
                <div class="box-modal-control">
                    <button class="bttn box-modal-btn btn-back" onclick="closeBox('close-box', 'order-delete')">
                        <span>Back</span>
                    </button>
                    <button class="bttn box-modal-btn" onclick="closeBox('delete-order')">
                        <span>OK</span>
                    </button>
                </div>
            </div>
        </div>
        <%-- Box delete success --%>
        <div class="modall box-modal delete-order-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Detele successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('delete-order-success')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        
        <%-- Create an order success --%>
        <div class="modall box-modal create-order-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Create an order success with price is ${sessionScope.cos}đ.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('close-box', 'create-order-success')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <!-- box modal end -->

        <!-- import src js -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="./assets/js/main.js?version=<%= System.currentTimeMillis() %>"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <c:if test="${not empty sessionScope.cos}">
            <c:remove var="cos" scope="session" />
            <script>
                document.querySelector('.create-order-success').style.display = 'flex';
            </script>
        </c:if>
    </body>
</html>
