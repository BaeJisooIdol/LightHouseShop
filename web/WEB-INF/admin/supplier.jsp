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
                                        <h1 class="col-lg-6 col-md-6">Manage Supplier</h1>
                                        <div class="admin-main-content-search col-lg-6 col-md-6">
                                            <input oninput="searchSuppier(this)" type="text" name="searchSuppier" placeholder="Search for supplier name" >
                                            <button><i class="fa fa-search"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container container-lg admin-main-table-container">
                                    <table class="admin-main-content-body-table">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Suppier Name</th>
                                                <th>Contact Name</th>
                                                <th>Phone</th>
                                                <th>Email</th>
                                                <th>Address</th>
                                                <th class="admin-main-content-body-table-control"><button><a href="supplier?action=create-supplier"><i class="fa-solid fa-plus"></i></a></button></th>
                                            </tr>
                                        </thead>
                                        <tbody class="admin-main-content-body-tbody">
                                            <c:choose>
                                                <c:when test="${empty requestScope.listSuppliers}">
                                                    <tr>
                                                        <td colspan="7" style="text-align: center; font-size: 2rem;">Nothing in suppier found!</td>			
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${listSuppliers}" var="s">
                                                        <tr>
                                                            <td>${s.id}</td>			
                                                            <td>${s.name}</td>
                                                            <td>${s.contactName}</td>
                                                            <td>${s.phone}</td>
                                                            <td>${s.email}</td>
                                                            <td>${s.address}</td>
                                                            <td class="admin-main-content-body-table-control">
                                                                <button>
                                                                    <a href="supplier?action=edit-supplier&id=${s.id}">
                                                                        <i class="fa-solid fa-pen"></i></a>
                                                                </button>
                                                                <button onclick="handelSupplier(event, 'delete-supplier', ${s.id})"><i class="fa-solid fa-trash"></i></button>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                            <tr class="admin-main-content-body-table-notFound">
                                                <td colspan="7" style="text-align: center; font-size: 2rem;">Nothing in suppier found!</td>			
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div class="admin-main-content-body-paging">
                                        <ul>
                                            <c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
                                                <c:if test="${i == 1}"><li class="active" onclick="pagingSupplier(this)">${i}</li></c:if>
                                                <c:if test="${i != 1}"><li onclick="pagingSupplier(this)">${i}</li></c:if>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>   
                    </section>
                </div>
            </div>
        </main> 

        <!-- box modal start -->
        <%-- Box wrong id --%>
        <div class="modall box-modal supplier-wrong-id">
            <div class="box-modal-container">
                <i style="color: grey" class="box-icon fa-regular fa-circle-xmark"></i>
                <h3>Fail</h3>
                <p>${requestScope.wrongID}</p>
                <button style="background-color: grey" class="bttn box-modal-btn" onclick="closeBox('create-supplier')">
                    <span>OK</span>
                </button>
            </div>
        </div>
        <%-- Box delete --%>
        <div class="modall box-modal supplier-delete-id">
            <div class="box-modal-container">
                <i style="color: orangered" class="box-icon fa-solid fa-trash"></i>
                <h3>Delete</h3>
                <p>Do you want to delete supplier width id <b></b>?</p>
                <div class="box-modal-control">
                    <button class="bttn box-modal-btn btn-back" onclick="closeBox('delete-supplier', 'supplier-delete-id')">
                        <span>Back</span>
                    </button>
                    <button class="bttn box-modal-btn" onclick="closeBox('delete-supplier')">
                        <span>OK</span>
                    </button>
                </div>
            </div>
        </div>
        <%-- Box delete success --%>
        <div class="modall box-modal delete-supplier-sccess">
            <div class="box-modal-container">
                <i class="box-icon fa-solid fa-circle-check"></i>
                <h3>Success</h3>
                <p>Detele successfully.</p>
                <button class="bttn box-modal-btn" onclick="closeBox('delete-supplier-sccess')">
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
        <script>
            <c:if test="${not empty requestScope.wrongID}">
                    const box_wrong_id = document.querySelector('.supplier-wrong-id');
                    box_wrong_id.style.display = 'flex';
            </c:if>
            <c:if test="${requestScope.currPage != 1}">
                    const pagingLis = [...document.querySelectorAll('.admin-main-content-body-paging ul li')];
                    const currLi = pagingLis.find(li => li.textContent === "${requestScope.currPage}");
                    if (currLi) {
                        const preLi = document.querySelector('.admin-main-content-body-paging ul li.active');
                        if (preLi)
                            preLi.classList.remove('active');
                        currLi.classList.add('active');
                    }
            </c:if>
        </script>
    </body>
</html>
