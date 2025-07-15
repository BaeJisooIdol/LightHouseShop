<%-- 
    Document   : create-product
    Created on : Mar 3, 2025, 11:12:42 PM
    Author     : Pham Quoc Tu - CE181513
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <title>Admin - Create Product</title>
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
                        <div class="admin-main-content-container admin-main-content-container-long">
                            <div class="admin-main-content-header">
                                <div class="container">
                                    <div class="row">
                                        <h1>Create New Product</h1>
                                    </div>
                                </div>
                            </div>

                            <div class="admin-main-content-body">
                                <div class="container admin-main-content-body-form">
                                    <form method="POST" action="product?method=create" class="admin-main-content-body-form" enctype="multipart/form-data">

                                        <div class="admin-main-content-body-group">
                                            <label for="product_name">Product Name</label>
                                            <input type="text" name="product_name" id="product_name" required>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="supplier_name">Supplier</label>
                                            <select id="supplier_name" name="supplier_name">
                                                <c:forEach items="${listSupplier}" var="spl">
                                                    <option value="${spl.id}">${spl.name} - ${spl.address}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_category">Product Category</label>
                                            <select onchange="handleCategory(this)" id="product_category" name="product_category">
                                                <c:forEach items="${listCategory}" var="ls">
                                                    <option value="${ls.categoryID}">${ls.categoryName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_quantity">Quantity Product</label>
                                            <input type="number" min="10" max="1000" step="1" name="product_quantity" id="product_quantity" value="10" required>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_description">Product Description </label>
                                            <p style="color: red">Ex: A refreshing and creamy blend of ripe mangoes, yogurt, and honey, served chilled for a sweet and tropical taste.</p>
                                            <textarea id="product_description" name="product_description" rows="5" cols="10" required></textarea>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_image">Product Image</label>
                                            <input type="file" name="product_image" id="product_image" required>
                                            <p id="fileError" style="color: red;"></p>
                                        </div>

                                        <div class="admin-main-content-body-group">

                                            <label>Product Size</label>

                                            <div class="form-check">
                                                <label class="form-check-label" for="sizeM">Size M</label>
                                                <input onchange="handleSizePrice(this)" class="form-check-input" type="checkbox" id="sizeM" name="sizeM" value="M-">
                                                <input type="hidden" min="0" max="100" step="1" id="priceM" name="priceM" value="0">
                                            </div>

                                            <div class="form-check">
                                                <label class="form-check-label" for="sizeL">Size L</label>
                                                <input onchange="handleSizePrice(this)" class="form-check-input" type="checkbox" id="sizeL" name="sizeL" value="L-">
                                                <input type="hidden" min="0" max="100" step="1" id="priceL" name="priceL" value="0">
                                            </div>

                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_multiTopping">Product Topping</label>
                                            <p style="color: red">Ex: Peach Pieces-10;Coffee Jelly-10;Cream Cheese Macchiato-15</p>
                                            <textarea rows="5" cols="10"  id="product_multiTopping" name="product_multiTopping" required></textarea>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_price">Product Price</label>
                                            <input type="number" min="10000" max="1000000" step="1000" name="product_price" id="product_price" value="10000" required>
                                        </div>

                                        <div class="admin-main-content-body-group">
                                            <label for="product_status">Product Status</label>
                                            <select id="product_status" name="product_status" required>
                                                <option value="Available">Available</option>
                                                <option value="Unavailable">Unavailable</option>
                                            </select>
                                        </div>


                                        <label for="promotionTable"><strong> <h3>Table Promotion</h3></strong></label>
                                        <table class="table" id="promotionTable">
                                            <thead>
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col">Name</th>
                                                    <th scope="col">Description</th>
                                                    <th scope="col">Discount (%)</th>
                                                    <th scope="col">Start</th>
                                                    <th scope="col">End</th>
                                                    <th scope="col">Status</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${listPromotion}" var="lp"> 
                                                    <tr>
                                                        <th scope="col">${lp.promotionID}</th>
                                                        <th scope="col">${lp.promotionName}</th>
                                                        <th scope="col">${lp.discription}</th>
                                                        <th scope="col">${lp.discountPercent}</th>
                                                        <th scope="col">${lp.startDate}</th>
                                                        <th scope="col">${lp.endDate}</th>
                                                        <th scope="col"><input type="checkbox" disabled ${(lp.status) ? "checked": ""}></th>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>


                                        <div class="admin-main-content-body-group">
                                            <label for="product_promotion">Product Promotion</label>
                                            <select id="product_promotion" name="product_promotion" required>
                                                <option value="0">Not Promotion</option>
                                                <c:forEach items="${listPromotion}" var="lp">
                                                    <option value="${lp.promotionID}">${lp.promotionName} - ${lp.discountPercent}%</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                        <div class="admin-main-content-body-form-control">


                                            <a class="bttn" href="product"><span>Back</span></a>

                                            <button id="createBtn" class="bttn">
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
        <script src="../assets/js/main.js?version=// <%= System.currentTimeMillis() %>"></script>
        <script src="../assets/js/validation.js?version=// <%= System.currentTimeMillis() %>"></script> 
    </body>

</html>
