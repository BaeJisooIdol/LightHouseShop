<%-- 
    Document   : transaction
    Created on : Jan 3, 2025, 3:19:13 PM
    Author     : Phạm Hải Đăng
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
            <div class="transactions">
                <div class="container">
                    <div class="row">
                        <h1>My Transactions</h1>
                        <div class="transactions-search">
                            <input type="text" name="transactionSearch" oninput="transactionSearch(this)" placeholder="Search for transaction date">
                            <button><i class="fa fa-search"></i></button>
                        </div>
                        <div class="transactions-body">
                            <div class="transaction-items">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Describe</th>
                                            <th>Date</th>
                                            <th>Amount</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${empty requestScope.listWalletTransactions}">
                                                <tr>
                                                    <td style="font-size: 2.5rem" colspan="4">You have not made any transactions yet.</td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${requestScope.listWalletTransactions}" var="w">
                                                <tr>
                                                    <td hidden><input type="type" name="id" value="${w.id}"></td>
                                                    <td>${w.note}</td>
                                                    <td>${w.transactionDate}</td>
                                                    <td>${w.note == 'Pay for the bill' ? "-" : "+"} ${w.formatPrice(w.amount)}đ</td>
                                                    <td>
                                                        <button onclick="deleteTransaction(`${w.id}`, `${w.transactionDate}`)">
                                                            <i class="fa-solid fa-trash"></i>
                                                        </button>
                                                    </td>
                                                </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>

                            </div>
                            <div class="transaction-paging">
                                <ul>
                                    <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                        <c:if test="${i == 1}">
                                            <li class="active" onclick="transactionPaging(this)">${i}</li>
                                            </c:if>
                                            <c:if test="${i != 1}">
                                            <li onclick="transactionPaging(this)">${i}</li>
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
        <div class="modall box-modal transaction-wrong">
            <div class="box-modal-container">
                <i style="color: grey" class="box-icon fa-regular fa-circle-xmark"></i>
                <h3>Fail</h3>
                <p></p>
                <button style="background-color: grey" class="bttn box-modal-btn" onclick="closeBox('close-box', 'transaction-wrong')">
                    <span>OK</span>
                </button>
            </div>
        </div>

        <%-- Box delete order --%>
        <div class="modall box-modal box-large transaction-delete">
            <input type="text" name="id" hidden>
            <div class="box-modal-container">
                <i style="color: orangered" class="box-icon fa-solid fa-trash"></i>
                <h3>Delete</h3>
                <p>Do you want to delete transaction with date <b style="font-size: 1.8rem"></b> ?</p>
                <div class="box-modal-control">
                    <button class="bttn box-modal-btn btn-back" onclick="closeBox('close-box', 'transaction-delete')">
                        <span>Back</span>
                    </button>
                    <button class="bttn box-modal-btn" onclick="closeBox('delete-transaction')">
                        <span>OK</span>
                    </button>
                </div>
            </div>
        </div>
        <%-- Box delete success --%>
        <div class="modall box-modal delete-transaction-success">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Detele an transaction successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('delete-transaction-success')">
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
    </body>
</html>
