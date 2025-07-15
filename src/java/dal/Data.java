/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import com.google.gson.JsonObject;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import model.New;
import model.Order;
import model.Product;
import model.Review;
import model.Store;
import model.Supplier;
import model.User;
import model.WalletTransaction;

/**
 *
 * @author admin
 */
public class Data {

    public String getInProduct(List<Product> list, int cId, int numberOfPages) {
        String data = "<section id=\"collections\">\n"
                + "                <div class=\"container\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <section class=\"show-filters col-lg-3\">\n"
                + "                          <div class=\"container\">\n"
                + "                            <div class=\"row\">\n"
                + "                            <div class=\"filter-title col-lg-12 col-md-12\">\n"
                + "                                <h2>FILTER</h2>\n"
                + "                                <i class=\"filter-icon fa fa-filter\"></i>\n"
                + "                            </div>\n"
                + "                            <div class=\"filter-search col-lg-12 col-md-6 col-sm-6 col-6\">\n"
                + "                                <h3>Search</h3>\n"
                + "                                <div class=\"filter-search-container\" onclick=\"seacrh(this)\">\n"
                + "                                    <input class=\"filter-search-input\" type=\"type\" name=\"filter-search-input\" oninput=\"doSearch(this, " + cId + ", 'searchByName')\" onblur=\"deteleVal(this)\">\n"
                + "                                    <i class=\"search-icon fa fa-search\"></i>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"filter-price col-lg-12 col-md-6 col-sm-6 col-6\">\n"
                + "                                <h3>Price</h3>\n"
                + "                                <div class=\"filter-price-container\">\n"
                + "                                    <input class=\"filter-price-input\" type=\"text\" name=\"price-from\" placeholder=\"From VND\" oninput=\"formatCurrencyVND(this)\">\n"
                + "                                    <span class=\"filter-price-split\"></span>\n"
                + "                                    <input class=\"filter-price-input\" type=\"text\" name=\"price-to\" placeholder=\"To VND\" oninput=\"formatCurrencyVND(this)\">\n"
                + "                                </div>\n"
                + "                                <p class=\"err-price\">\n"
                + "                                    <span></span>\n"
                + "                                </p>"
                + "                                <button class=\"bttn filter-price-btn\" onclick=\"doSearch(this, " + cId + ", 'searchByPrice')\">\n"
                + "                                    <span>Apply</span>\n"
                + "                                </button>\n"
                + "                            </div>\n"
                + "                            <div class=\"filter-stars col-lg-12 col-md-6 col-sm-6 col-6\">\n"
                + "                                <h3>Average Reviews</h3>\n"
                + "                                <div class=\"filter-stars-container\">\n"
                + "                                    <div class=\"filter-stars-item\" onclick=\"doSearch(5, " + cId + ", 'searchByStar')\">\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-stars-item\" onclick=\"doSearch(4, " + cId + ", 'searchByStar')\">\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-stars-item\" onclick=\"doSearch(3, " + cId + ", 'searchByStar')\">\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-stars-item\" onclick=\"doSearch(2, " + cId + ", 'searchByStar')\">\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-stars-item\" onclick=\"doSearch(1, " + cId + ", 'searchByStar')\">\n"
                + "                                        <i class=\"active fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                        <i class=\" fa fa-star\"></i>\n"
                + "                                    </div>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"filter-discount col-lg-12 col-md-6 col-sm-6 col-6\">\n"
                + "                                <h3>Discount</h3>\n"
                + "                                <div class=\"filter-discount-container\">\n"
                + "                                    <div class=\"filter-discount-item\" onclick=\"doSearch(this, " + cId + ", 'searchByDiscount')\">\n"
                + "                                        <input id=\"10\" type=\"checkbox\" value=\"10\">\n"
                + "                                        <label for=\"10\">Up to 10%</label>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-discount-item\" onclick=\"doSearch(this, " + cId + ", 'searchByDiscount')\">\n"
                + "                                        <input id=\"25\" type=\"checkbox\" value=\"25\">\n"
                + "                                        <label for=\"25\">Up to 25%</label>\n"
                + "                                    </div>\n"
                + "                                    <div class=\"filter-discount-item\" onclick=\"doSearch(this, " + cId + ", 'searchByDiscount')\">\n"
                + "                                        <input id=\"50\" type=\"checkbox\" value=\"50\">\n"
                + "                                        <label for=\"50\">Up to 50%</label>\n"
                + "                                    </div>\n"
                + "                             </div>\n"
                + "                            </div>\n"
                + "                          </div>\n"
                + "                        </section>\n"
                + "                        <section class=\"show-products col-lg-9\">\n"
                + "                            <div class=\"container show-products-container\">\n"
                + "                                <div class=\"row\">\n";
        for (Product p : list) {
            data
                    += "                                <div class=\"products-items col-lg-3 col-md-4 col-sm-6 col-6\">\n";
            String label = p.getLabel();
            if (label == null) {
                label = "";
            }
            switch (label) {
                case "new":
                    data += "<a class=\"products-img ribbon-new\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                case "best":
                    data += "<a class=\"products-img ribbon-best-seller\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                case "sell":
                    data += "<a class=\"products-img ribbon-hot-deal\" data-content=\"" + p.getDiscount() + "%\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                default:
                    data += "<a class=\"products-img\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
            }
            data += "                                        <img src=\"" + p.getImage() + "\" alt=\"new-product-img\"/>\n"
                    + "                                    </a>\n"
                    + "                                    <h3>\n"
                    + "                                        <a href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">" + p.getName() + "</a>\n"
                    + "                                    </h3>\n"
                    + "                                    <div class=\"products-items-info\">\n";
            if (p.getDiscount() == 0.0) {
                data += "                                        <div>\n"
                        + "                                            <span>" + p.formatPrice(p.getPrice()) + "đ</span>\n"
                        + "                                        </div>\n";
            } else {
                data += "<div>\n"
                        + "                                                        <s>" + p.formatPrice(p.getPrice()) + "đ</s>\n"
                        + "                                                        <span>" + p.formatPrice(p.getNewPrice()) + "đ</span>\n"
                        + "                                                    </div>";
            }

            data += "                                        <div class=\"products-items-stars\">\n";
            for (int i = 1; i <= 5; i++) {
                if (i <= p.getRating()) {
                    data += "<i class=\"start-icon fa fa-star\"></i>\n";
                } else {
                    data += "<i class=\"fa fa-star\"></i>\n";
                }
            }
            data += "                                        </div>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n";
        }
        data
                += "                                </div>\n";

        data += "                            </div>\n";
        data
                += "<div class=\"paging-product\">\n"
                + "                                    <ul class=\"paging-list\">\n";
        for (int i = 1; i <= numberOfPages; i++) {
            if (i == 1) {
                data += "<li class=\"paging-item active\" onclick=\"paging(this, " + cId + ", " + i + ")\">\n"
                        + "                                            " + i + "\n"
                        + "                                        </li>\n";
            } else {
                data += "<li class=\"paging-item\" onclick=\"paging(this, " + cId + ", " + i + ")\">\n"
                        + "                                            " + i + "\n"
                        + "                                        </li>\n";
            }
        }
        data += "                                    </ul>\n"
                + "                                </div>\n"
                + "                        </section>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </section>";
        return data;
    }

    public String getInProduct(List<Product> list) {
        String data = "<div class=\"row\">\n";
        for (Product p : list) {
            data
                    += "                                <div class=\"products-items col-lg-3 col-md-4 col-sm-6 col-6\">\n";
            String label = p.getLabel();
            if (label == null) {
                label = "";
            }
            switch (label) {
                case "new":
                    data += "<a class=\"products-img ribbon-new\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                case "best":
                    data += "<a class=\"products-img ribbon-best-seller\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                case "sell":
                    data += "<a class=\"products-img ribbon-hot-deal\" data-content=\"" + p.getDiscount() + "%\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
                    break;
                default:
                    data += "<a class=\"products-img\" href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">\n";
            }
            data += "                                        <img src=\"" + p.getImage() + "\" alt=\"new-product-img\"/>\n"
                    + "                                    </a>\n"
                    + "                                    <h3>\n"
                    + "                                        <a href=\"#\" onclick=\"productDetail(" + p.getId() + ")\">" + p.getName() + "</a>\n"
                    + "                                    </h3>\n"
                    + "                                    <div class=\"products-items-info\">\n";
            if (p.getDiscount() == 0.0) {
                data += "                                        <div>\n"
                        + "                                            <span>" + p.getPrice() + "</span>\n"
                        + "                                        </div>\n";
            } else {
                data += "<div>\n"
                        + "                                                        <s>" + p.getPrice() + "</s>\n"
                        + "                                                        <span>" + p.getNewPrice() + "</span>\n"
                        + "                                                    </div>";
            }

            data += "                                        <div class=\"products-items-stars\">\n";
            for (int i = 1; i <= 5; i++) {
                if (i <= p.getRating()) {
                    data += "<i class=\"start-icon fa fa-star\"></i>\n";
                } else {
                    data += "<i class=\"fa fa-star\"></i>\n";
                }
            }
            data += "                                        </div>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n";
        }
        data
                += "                                </div>\n";
        return data;
    }

    public String getInterfaceProductDetail(Product p, List<Review> reviews, List<User> users, int sold, JsonObject datas, String author, int userID) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        String totalPrice = p.formatPrice(p.getPrice());
        if (p.getDiscount() != 0.0) {
            totalPrice = p.formatPrice(p.getNewPrice());
        }
        String quantity = "1";
        String getSize = "";
        String[] getToppings = null;
        String payment = null;
        if (datas.size() != 0) {
            totalPrice = p.formatPrice(Double.parseDouble(datas.get("totalPrice").toString().replaceAll("[^0-9]+", "")));
            quantity = datas.get("quantity").toString().replaceAll("[^0-9]+", "");
            getSize = datas.get("size").toString().replaceAll("\"", "");
            getToppings = datas.get("toppings").toString().split(",");
            payment = datas.get("payment").toString().replaceAll("\"", "");
            if (!payment.equals("Cash") && !payment.equals("Wallet") && !payment.equals("VNPay")) {
                payment = "VNPay";
            }
        }
        String[] imgs = p.getImage().split(";");
        String[] sizes = null;
        if (p.getSize() != null) {
            sizes = p.getSize().split(";");
        }

        String[] toppings = null;
        if (p.getTopping() != null) {
            toppings = p.getTopping().split(";");
        }

        int numberOfReview = reviews.size();
        String data = "<div id=\"product-detail-container\">\n"
                + "                <section class=\"product-detail\">\n"
                + "                    <div class=\"container\">\n"
                + "                        <div class=\"row\">\n"
                + "                            <div class=\"product-detail-imgs col-lg-6 col-md-6\">\n";
        switch (Objects.toString(p.getLabel(), "")) {
            case "new":
                data += "<div class=\"product-detail-img-main ribbon-new\">\n";
                break;
            case "best":
                data += "<div class=\"product-detail-img-main ribbon-best-seller\">\n";
                break;
            case "sell":
                data += "<div class=\"product-detail-img-main ribbon-hot-deal\" data-content=\"" + p.getDiscount() + "%\">\n";
                break;
            default:
                data += "<div class=\"product-detail-img-main\">\n";
        }
        data += "                                    <img src=\"" + imgs[0] + "\" alt=\"alt\"/>\n"
                + "                                </div>\n"
                + "                                <div class=\"thumbImgs\">\n";
        for (int i = 0; i < imgs.length; i++) {
            if (i == 0) {
                data += "<img onclick=\"changeImg(this)\" class=\"active\" src=\"" + imgs[i] + "\" alt=\"alt\"/>\n";
            } else {
                data += "<img onclick=\"changeImg(this)\" src=\"" + imgs[i] + "\" alt=\"alt\"/>\n";
            }
        }
        data += "                        </div>\n"
                + "                            </div>\n"
                + "                            <div class=\"product-detail-info col-lg-6 col-md-6\">\n"
                + "                                <h2 class=\"product-name\">" + p.getName() + "</h2>\n"
                + "                                <h4 class=\"product-info-ex\">\n"
                + "                                    <div class=\"product-info-ex-stars\">\n";
        for (int i = 1; i <= 5; i++) {
            if (i <= p.getRating()) {
                data += "<i class=\"active fa fa-star\"></i>\n";
            } else {
                data += "<i class=\"fa fa-star\"></i>\n";
            }
        }
        data += "                                    </div>\n"
                + "                                    <div class=\"product-info-ex-reviews\">\n";
        if (numberOfReview <= 1) {
            data += numberOfReview + " review\n";
        } else {
            data += numberOfReview + " reviews\n";
        }
        data += "                         </div>\n"
                + "                                    <div class=\"product-info-ex-sold\">\n"
                + "                                        " + sold + " sold\n"
                + "                                    </div>\n"
                + "                                    <div class=\"product-info-ex-quantity\">\n";
        if (p.getQuantity() <= 1) {
            data += p.getQuantity() + " available\n";
        } else {
            data += p.getQuantity() + " availables\n";
        }
        data += "                                    </div>\n"
                + "                                </h4>\n";
        if (p.getDiscount() == 0.0) {
            data += "<h3 class=\"product-price\">" + totalPrice + "đ</h3>\n";
        } else {
            data += "<div class=\"product-allPrice\">\n"
                    + "                                    <s class=\"product-oldPrice\">" + p.formatPrice(p.getPrice()) + "đ</s>\n"
                    + "                                    <h3 class=\"product-price\">" + totalPrice + "đ</h3>\n"
                    + "                                </div>";
        }

        if (sizes != null) {
            data += "                                <p>Select size (required)</p>\n"
                    + "                                <div class=\"product-size\">\n"
                    + "                                    <ul>\n";
            for (String size : sizes) {
                String type;
                String price;
                String isActive = (getSize.equals(size)) ? "active" : "";
                String color = (getSize.equals(size)) ? "#fff" : "#000";
                if (size.startsWith("S")) {
                    type = "Small";
                    price = formatter.format(Integer.parseInt(size.split("-")[1]) * 1000);
                    if (getSize.equals("")) {
                        isActive = "active";
                        color = "#fff";
                    }
                    data += "<li onclick=\"chooseItem(this, 'size')\" class=\"" + isActive + "\">\n"
                            + "                                            <svg fill=\"" + color + "\" style=\"width: 1.2rem; height: 1.6rem\" viewBox=\"0 0 13 16\" xmlns=\"http://www.w3.org/2000/svg\"> <path class=\"shape \" d=\"M11.6511 1.68763H10.3529V0.421907C10.3529 0.194726 10.1582 0 9.93104 0H2.17444C1.94726 0 1.75254 0.194726 1.75254 0.421907V1.65517H0.454361C0.194726 1.68763 0 1.88235 0 2.10953V4.18661C0 4.41379 0.194726 4.60852 0.421907 4.60852H1.33063L1.72008 8.8925L1.78499 9.76876L2.30426 15.6105C2.33671 15.8377 2.49899 16 2.72617 16H9.28195C9.50913 16 9.70385 15.8377 9.70385 15.6105L10.2231 9.76876L10.288 8.8925L10.6775 4.60852H11.5862C11.8134 4.60852 12.0081 4.41379 12.0081 4.18661V2.10953C12.073 1.88235 11.8783 1.68763 11.6511 1.68763ZM2.56389 8.40568H3.50507C3.47262 8.56795 3.47262 8.73022 3.47262 8.8925C3.47262 9.02231 3.47262 9.15213 3.50507 9.28195H2.66126L2.6288 8.92495L2.56389 8.40568ZM9.47667 8.92495L9.44422 9.28195H8.56795C8.60041 9.15213 8.60041 9.02231 8.60041 8.8925C8.60041 8.73022 8.56795 8.56795 8.56795 8.40568H9.50913L9.47667 8.92495ZM7.72414 8.8925C7.72414 9.83367 6.97769 10.5801 6.03651 10.5801C5.09534 10.5801 4.34888 9.83367 4.34888 8.8925C4.34888 7.95132 5.09534 7.20487 6.03651 7.20487C6.97769 7.20487 7.72414 7.95132 7.72414 8.8925ZM8.92495 15.1562H3.18053L2.72617 10.1582H3.82961C4.28398 10.9371 5.09534 11.4564 6.03651 11.4564C6.97769 11.4564 7.8215 10.9371 8.24341 10.1582H9.34686L8.92495 15.1562ZM9.60649 7.52941H8.21095C7.75659 6.81542 6.94523 6.3286 6.03651 6.3286C5.12779 6.3286 4.31643 6.81542 3.86207 7.52941H2.49899L2.23935 4.60852H9.86613L9.60649 7.52941ZM11.1968 3.73225H10.3205H1.75254H0.876268V2.56389H2.17444H2.2069H2.23935H8.27586C8.50304 2.56389 8.69777 2.36917 8.69777 2.14199C8.69777 1.91481 8.50304 1.72008 8.27586 1.72008H\n"
                            + "                                                                                                                                                                 2.6288V0.876268H9.47667V2.10953C9.47667 2.33671 9.6714 2.53144 9.89858 2.53144H11.1968V3.73225Z\"></path> </svg>\n"
                            + "                                            <div class=\"product-size-desc\">" + type + " + " + price + "đ</div>\n"
                            + "                                        </li>\n";
                    continue;
                } else if (size.startsWith("M")) {
                    type = "Medium";
                } else {
                    type = "Large";

                }
                price = formatter.format(Integer.parseInt(size.split("-")[1]) * 1000);
                data += "<li onclick=\"chooseItem(this, 'size')\" class=\"" + isActive + "\">\n"
                        + "                                            <svg fill=\"" + color + "\" style=\"width: 1.2rem; height: 1.6rem\" viewBox=\"0 0 13 16\" xmlns=\"http://www.w3.org/2000/svg\"> <path class=\"shape \" d=\"M11.6511 1.68763H10.3529V0.421907C10.3529 0.194726 10.1582 0 9.93104 0H2.17444C1.94726 0 1.75254 0.194726 1.75254 0.421907V1.65517H0.454361C0.194726 1.68763 0 1.88235 0 2.10953V4.18661C0 4.41379 0.194726 4.60852 0.421907 4.60852H1.33063L1.72008 8.8925L1.78499 9.76876L2.30426 15.6105C2.33671 15.8377 2.49899 16 2.72617 16H9.28195C9.50913 16 9.70385 15.8377 9.70385 15.6105L10.2231 9.76876L10.288 8.8925L10.6775 4.60852H11.5862C11.8134 4.60852 12.0081 4.41379 12.0081 4.18661V2.10953C12.073 1.88235 11.8783 1.68763 11.6511 1.68763ZM2.56389 8.40568H3.50507C3.47262 8.56795 3.47262 8.73022 3.47262 8.8925C3.47262 9.02231 3.47262 9.15213 3.50507 9.28195H2.66126L2.6288 8.92495L2.56389 8.40568ZM9.47667 8.92495L9.44422 9.28195H8.56795C8.60041 9.15213 8.60041 9.02231 8.60041 8.8925C8.60041 8.73022 8.56795 8.56795 8.56795 8.40568H9.50913L9.47667 8.92495ZM7.72414 8.8925C7.72414 9.83367 6.97769 10.5801 6.03651 10.5801C5.09534 10.5801 4.34888 9.83367 4.34888 8.8925C4.34888 7.95132 5.09534 7.20487 6.03651 7.20487C6.97769 7.20487 7.72414 7.95132 7.72414 8.8925ZM8.92495 15.1562H3.18053L2.72617 10.1582H3.82961C4.28398 10.9371 5.09534 11.4564 6.03651 11.4564C6.97769 11.4564 7.8215 10.9371 8.24341 10.1582H9.34686L8.92495 15.1562ZM9.60649 7.52941H8.21095C7.75659 6.81542 6.94523 6.3286 6.03651 6.3286C5.12779 6.3286 4.31643 6.81542 3.86207 7.52941H2.49899L2.23935 4.60852H9.86613L9.60649 7.52941ZM11.1968 3.73225H10.3205H1.75254H0.876268V2.56389H2.17444H2.2069H2.23935H8.27586C8.50304 2.56389 8.69777 2.36917 8.69777 2.14199C8.69777 1.91481 8.50304 1.72008 8.27586 1.72008H\n"
                        + "                                                                                                                                                                 2.6288V0.876268H9.47667V2.10953C9.47667 2.33671 9.6714 2.53144 9.89858 2.53144H11.1968V3.73225Z\"></path> </svg>\n"
                        + "                                            <div class=\"product-size-desc\">" + type + " + " + price + "đ</div>\n"
                        + "                                        </li>\n";
            }
            data += "   </ul>\n"
                    + "                                </div>\n";
        }

        if (toppings != null) {
            data += "                                <p>Topping</p>\n"
                    + "                                <div class=\"product-topping\">\n"
                    + "                                    <ul>\n";
            for (String topping : toppings) {
                String name = topping.split("-")[0];
                String price = formatter.format(Integer.parseInt(topping.split("-")[1]) * 1000);
                String isActive = "";
                if (getToppings != null) {
                    for (String t : getToppings) {
                        if (topping.equals(t.replaceAll("\"", ""))) {
                            isActive = "active";
                            break;
                        }
                    }
                }

                data += "<li onclick=\"chooseItem(this, 'topping')\" class=\"" + isActive + "\">\n"
                        + "                                            <div class=\"product-topping-desc\">" + name + " + " + price + "đ</div>\n"
                        + "                                        </li>\n";
            }
            data += "</ul>\n"
                    + "                                </div>\n";
        }
        String isDisable = (quantity.equals("1")) ? "disable" : "";
        data += "       <p>Quantity</p>\n"
                + "                                <div class=\"product-quantity\">\n"
                + "                                    <button class=\"product-quantity-btn " + isDisable + "\" onclick=\"changeQuantity('desc', this)\">\n"
                + "                                        -\n"
                + "                                    </button>\n"
                + "                                    <span>" + quantity + "</span>\n"
                + "                                    <button class=\"product-quantity-btn\" onclick=\"changeQuantity('insc')\">\n"
                + "                                        +\n"
                + "                                    </button>\n"
                + "                                </div>\n"
                + "     <p>Payment</p>\n"
                + "                                <select class=\"product-payment\">\n"
                + "                                    <option " + ("Cash".equals(payment) ? "selected" : "") + " value=\"0\">Cash</option>\n"
                + "                                    <option " + ("Wallet".equals(payment) ? "selected" : "") + " value=\"1\">Wallet</option>\n"
                + "                                    <option " + ("VNPay".equals(payment) ? "selected" : "") + " value=\"2\">VNPay</option>\n"
                + "                                </select>"
                + "                                <div class=\"product-detail-controll\">\n"
                + "                                    <div class=\"product-detail-btn-control\">\n"
                + "                                        <button class=\"bttn btn-addToCart\" onclick=\"adToCart(this, '" + imgs[0] + "', " + p.getId() + ")\">\n"
                + "                                            <span>Add to cart</span>\n"
                + "                                        </button>\n"
                + "                                    </div>\n"
                + "                                    <div>\n"
                + "                                        <button class=\"bttn btn-addToCart\" onclick=\"buyProduct(" + p.getId() + ")\">\n"
                + "                                            <span>Buy now</span>\n"
                + "                                        </button>\n"
                + "                                    </div>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </section>\n"
                + "                <section id=\"product-describe\">\n"
                + "                    <div class=\"container product-describe-container\">\n"
                + "                        <div class=\"row\">\n"
                + "                            <h2>Product Description</h2>\n"
                + "                            <p>" + p.getDescription() + "</p>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </section>\n"
                + "                <section id=\"product-review\">\n"
                + "                    <div class=\"container product-review-container\">\n"
                + "                        <div class=\"review-header\">\n"
                + "                            <h2>Product Reviews</h2>\n";
            data += "                            <button class=\"bttn product-review-add\" onclick=\"addComment()\">\n"
                    + "                                <span><i class=\"fa-solid fa-plus\"></i></span>\n"
                    + "                            </button>\n";
        data += "                        </div>\n";
        if (reviews.size() <= 1) {
            data += "<div class=\"total-review\">Total " + reviews.size() + " review</div>\n";
        } else {
            data += "<div class=\"total-review\">Total " + reviews.size() + " reviews</div>\n";
        }
        data += "<div class=\"product-reviews\">\n";
        for (Review r : reviews) {
            data += "<div class=\"product-review-body\">\n"
                    + "                        <div>\n"
                    + "                            <div class=\"product-review-header\">\n"
                    + "                                <div class=\"product-review-user\">\n";
            for (User user : users) {
                if (r.getuId() == Integer.parseInt(user.getId())) {
                    if (user.getPicture() == null) {
                        data += "<img class=\"avatar\" src=\"./assets/imgs/default-user.png\" referrerpolicy=\"no-referrer\" alt=\"avatar\"/>";
                    } else {
                        data += "<img class=\"avatar\" src=\"" + user.getPicture() + "\" referrerpolicy=\"no-referrer\" alt=\"avatar\"/>";
                    }
                    data += "                                </div>\n"
                            + "                                <div class=\"product-review-info\">\n"
                            + "                                    <div class=\"product-review-name\">\n"
                            + "                                        " + user.getName() + "\n"
                            + "                                    </div>\n"
                            + "                                    <div class=\"product-review-ex\">\n"
                            + "                                        <div class=\"product-review-date\">\n"
                            + "                                            " + r.getReviewDate() + "\n"
                            + "                                        </div>\n";
                    break;
                }
            }
            data += "                                        <div class=\"product-review-start\">\n";
            for (int i = 1; i <= 5; i++) {
                if (i <= r.getRating()) {
                    data += "<i class=\"active fa fa-star\"></i>\n";
                } else {
                    data += "<i class=\"fa fa-star\"></i>\n";
                }
            }
            data += "                                        </div>\n"
                    + "                                    </div>\n"
                    + "                                </div>\n"
                    + "                            </div>\n"
                    + "                            <div class=\"product-review-content\">\n"
                    + "                                <span>" + r.getComment() + "</span>\n"
                    + "                            </div>\n"
                    + "                        </div>\n";
            if (author.equals("Admin") || userID == r.getuId()) {
                data += "                            <div class=\"product-review-remove\">\n"
                        + "                                <button class=\"bttn product-review-remove\" onclick=\"deleteComment(this, '" + r.getrId() + "')\">\n"
                        + "                                    <span><i class=\"fa-solid fa-trash\"></i></span>\n"
                        + "                                </button>\n"
                        + "                            </div>";
            }
            data += "                      </div>\n";
        }
        data += "</div>\n";

        data += "<form class=\"form-review-container\" method=\"post\">\n"
                + "                            <input type=\"text\" name=\"id\" hidden value=\"" + p.getId() + "\">"
                + "                            <div class=\"form-review\">\n"
                + "                                <label for=\"message\">Enter your comment:</label>\n"
                + "                                <textarea id=\"message\" name=\"message\" required></textarea>\n"
                + "                            </div>\n"
                + "                            <div class=\"form-review\">\n"
                + "                                <label for=\"\">Rating:</label>\n"
                + "                                <input type=\"number\" name=\"rating\" id=\"rating\" min=\"0\" max=\"5\" required>\n"
                + "                            </div>\n"
                + "                            <button class=\"bttn review-comment-btn\" type=\"submit\" onclick=\"doComment(event)\">\n"
                + "                                <span>Submit</span>\n"
                + "                            </button>\n"
                + "                        </form>";

        data += "                    </div>\n"
                + "                </section>\n"
                + "            </div>";
        data += "<div class=\"modall box-modal error-data\">\n"
                + "            <div class=\"box-modal-container\">\n"
                + "                <i style=\"color: grey\" class=\"box-icon fa-regular fa-circle-xmark\"></i>\n"
                + "                <h3>Fail</h3>\n"
                + "                <p>There's something was wrong!</p>\n"
                + "                <button style=\"background-color: grey\" class=\"bttn box-modal-btn\" onclick=\"closeBox('close-box', 'error-data')\">\n"
                + "                    <span>OK</span>\n"
                + "                </button>\n"
                + "            </div>\n"
                + "        </div>";
        data += "<div class=\"modall box-modal box-large address-require\">\n"
                + "            <div class=\"box-modal-container\">\n"
                + "                <i style=\"color: #ff1100\" class=\"box-icon fa-solid fa-circle-exclamation\"></i>\n"
                + "                <h3>Require</h3>\n"
                + "                <p>Please enter your address to continue your experience.</p>\n"
                + "                <div class=\"box-modal-control\">\n"
                + "                    <button class=\"bttn box-modal-btn btn-back\" onclick=\"closeBox('close-box', 'address-require')\">\n"
                + "                        <span>Back</span>\n"
                + "                    </button>\n"
                + "                    <button class=\"bttn box-modal-btn\" onclick=\"closeBox('address-require')\">\n"
                + "                        <span>OK</span>\n"
                + "                    </button>\n"
                + "                </div>\n"
                + "            </div>\n"
                + "        </div>";
        data += "<div class=\"modall box-modal insufficient-balance\">\n"
                + "            <div class=\"box-modal-container\">\n"
                + "                <i style=\"color: grey\" class=\"box-icon fa-regular fa-circle-xmark\"></i>\n"
                + "                <h3>Fail</h3>\n"
                + "                <p>Your account balance is insufficient!</p>\n"
                + "                <button style=\"background-color: grey\" class=\"bttn box-modal-btn\" onclick=\"closeBox('close-box', 'insufficient-balance')\">\n"
                + "                    <span>OK</span>\n"
                + "                </button>\n"
                + "            </div>\n"
                + "        </div>";
        data += "<div class=\"modall box-modal insufficient-quantity\">\n"
                + "            <div class=\"box-modal-container\">\n"
                + "                <i style=\"color: grey\" class=\"box-icon fa-regular fa-circle-xmark\"></i>\n"
                + "                <h3>Fail</h3>\n"
                + "                <p>Product is not enough or out of stock!</p>\n"
                + "                <button style=\"background-color: grey\" class=\"bttn box-modal-btn\" onclick=\"closeBox('close-box', 'insufficient-quantity')\">\n"
                + "                    <span>OK</span>\n"
                + "                </button>\n"
                + "            </div>\n"
                + "        </div>";
        return data;
    }

    public String getItemInCart(int oId, Product p, double price, int quantity, String size, String toppings, String payment) {
        String getPrice = p.formatPrice(price);
        String data = "<div class=\"header-right-cart-info-item\" onclick=\"cartItem(this, " + p.getId() + ")\">\n"
                + "                                                <input hidden type=\"text\" name=\"payment\" value=\"" + payment + "\">"
                + "                                                <div class=\"cart-info-item-img\">\n"
                + "                                                    <img src=\"" + p.getImage() + "\" alt=\"alt\"/>\n"
                + "                                                </div>\n"
                + "                                                <div class=\"cart-info-item-content\">\n"
                + "                                                    <div class=\"cart-info-item-content-up\">\n"
                + "                                                        <div class=\"cart-info-item-name\">\n"
                + "                                                            " + p.getName() + "\n"
                + "                                                        </div>\n"
                + "                                                        <div class=\"cart-info-item-price\">\n"
                + "                                                            " + getPrice + "đ\n"
                + "                                                        </div>\n"
                + "                                                        <div>x</div>\n"
                + "                                                        <div class=\"cart-info-item-quantity\">\n"
                + "                                                            " + quantity + " \n"
                + "                                                        </div>\n"
                + "                                                    </div>\n"
                + "                                                    <div class=\"cart-info-item-content-down\">\n"
                + "                                                        <div class=\"cart-info-item-sizeAndTopping\">\n";
        if (size != null) {
            data += size;
        }
        if (toppings != null) {
            data += ";" + toppings;
        }
        data += "                                                            </div>\n"
                + "                                                            <button class=\"btn-remove-item\" onclick=\"removeItemCart(" + oId + ", this, event)\">\n"
                + "                                                                remove\n"
                + "                                                            </button>\n"
                + "                                                        </div>\n"
                + "                                                    </div>\n"
                + "                                                </div>";
        return data;
    }

    public String getInterfaceSupplier(List<Supplier> list) {
        String data = "";
        for (Supplier s : list) {
            data += "<tr>\n"
                    + "                                                    <td>" + s.getId() + "</td>			\n"
                    + "                                                    <td>" + s.getName() + "</td>\n"
                    + "                                                    <td>" + s.getContactName() + "</td>\n"
                    + "                                                    <td>" + s.getPhone() + "</td>\n"
                    + "                                                    <td>" + s.getEmail() + "</td>\n"
                    + "                                                    <td>" + s.getAddress() + "</td>\n"
                    + "                                                    <td class=\"admin-main-content-body-table-control\">\n"
                    + "                                                        <button>\n"
                    + "                                                            <a href=\"supplier?action=edit-supplier&id=" + s.getId() + "\">\n"
                    + "                                                                <i class=\"fa-solid fa-pen\"></i></a>\n"
                    + "                                                        </button>\n"
                    + "                                                        <button onclick=\"handelSupplier(event, 'delete-supplier', " + s.getId() + ")\"><i class=\"fa-solid fa-trash\"></i></button>\n"
                    + "                                                    </td>\n"
                    + "                                                </tr>";
        }
        return data;
    }

    public String getInRegister() {
        String data = "<h1 class=\"title\">Register</h1>\n"
                + "                                    <p>Create an account free and enjoy it</p>\n"
                + "                                    <form class=\"form-group\">\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"text\" name=\"fullname\" required>\n"
                + "                                            <span>Full name</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"text\" name=\"username\" required>\n"
                + "                                            <span>User name</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"email\" name=\"email\" required>\n"
                + "                                            <span>Email</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"number\" name=\"phone\" required>\n"
                + "                                            <span>Phone</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"password\" name=\"password\" required>\n"
                + "                                            <span>Password</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                            <span class=\"eye\" onclick=\"displayPass(this)\"><i class=\"eye-icon fa-solid fa-eye-slash\"></i></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"password\" name=\"confirm-password\" required>\n"
                + "                                            <span>Confirm password</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                            <span class=\"eye\" onclick=\"displayPass(this)\"><i class=\"eye-icon fa-solid fa-eye-slash\"></i></span>\n"
                + "                                        </div>\n"
                + "                                        <button type='submit' class=\"account-btn bttn\">\n"
                + "                                            <span>Register</span>\n"
                + "                                        </button>\n"
                + "                                        </form>";
        return data;
    }

    public String getInVerifyEmail(String email, String expireTime, String token) {
        String data = "<h1 class=\"title\">Verify Your Email</h1>\n"
                + "                                    <p style=\"margin: 0\">A verification code has been send to</p>\n"
                + "                                    <p class=\"email\">" + email + "</p>\n"
                + "                                    <p style=\"color: #666\">Please check your inbox and enter the verification code below to verify your email address. The code will expire in " + expireTime + ".</p>\n"
                + "                                    <div class=\"input-group\">\n"
                + "                                        <input type=\"text\" id=\"code1\" maxlength=\"1\" oninput=\"moveFocus(1)\" required>\n"
                + "                                        <input type=\"text\" id=\"code2\" maxlength=\"1\" oninput=\"moveFocus(2)\" required>\n"
                + "                                        <input type=\"text\" id=\"code3\" maxlength=\"1\" oninput=\"moveFocus(3)\" required>\n"
                + "                                        <input type=\"text\" id=\"code4\" maxlength=\"1\" oninput=\"moveFocus(4)\" required>\n"
                + "                                        <input type=\"text\" id=\"code5\" maxlength=\"1\" oninput=\"moveFocus(5)\" required>\n"
                + "                                        <input type=\"text\" id=\"code6\" maxlength=\"1\" oninput=\"moveFocus(6)\" required>\n"
                + "                                    </div>\n"
                + "                                    <div style=\"display: flex; justify-content: space-between; align-items: center; margin-top: 2rem\">\n"
                + "                                        <button onclick='verifyCode(\"" + email + "\")' style=\"margin: 0\" type='submit' class=\"account-btn bttn\">\n"
                + "                                            <span>Verify</span>\n"
                + "                                        </button>\n"
                + "                                        <span onclick='resentCode(\"" + email + "\", \"" + token + "\")' style=\"color: orange; font-size: 1.5rem\" class=\"resent-pass discrupt\">Resent code (30s)</span>\n"
                + "                                    </div>";
        return data;
    }

    public String getInVerifyEmailToResetPassword(String email, String expireTime) {
        String data = "<h1 class=\"title\">Verify Your Email To Reset PassWord</h1>\n"
                + "                                    <p style=\"margin: 0\">A verification code has been send to</p>\n"
                + "                                    <p class=\"email\">" + email + "</p>\n"
                + "                                    <p style=\"color: #666\">Please check your inbox and enter the verification code below to verify your email address. The code will expire in " + expireTime + ".</p>\n"
                + "                                    <div class=\"input-group\">\n"
                + "                                        <input type=\"text\" id=\"code1\" maxlength=\"1\" oninput=\"moveFocus(1)\" required>\n"
                + "                                        <input type=\"text\" id=\"code2\" maxlength=\"1\" oninput=\"moveFocus(2)\" required>\n"
                + "                                        <input type=\"text\" id=\"code3\" maxlength=\"1\" oninput=\"moveFocus(3)\" required>\n"
                + "                                        <input type=\"text\" id=\"code4\" maxlength=\"1\" oninput=\"moveFocus(4)\" required>\n"
                + "                                        <input type=\"text\" id=\"code5\" maxlength=\"1\" oninput=\"moveFocus(5)\" required>\n"
                + "                                        <input type=\"text\" id=\"code6\" maxlength=\"1\" oninput=\"moveFocus(6)\" required>\n"
                + "                                    </div>\n"
                + "                                    <div style=\"display: flex; justify-content: space-between; align-items: center; margin-top: 2rem\">\n"
                + "                                        <button onclick='verifyCodeToResetPassword(\"" + email + "\")' style=\"margin: 0\" type='submit' class=\"account-btn bttn\">\n"
                + "                                            <span>Verify</span>\n"
                + "                                        </button>\n"
                + "                                        <span onclick='resentCodeToResetPassword(\"" + email + "\")' style=\"color: orange; font-size: 1.5rem\" class=\"resent-pass discrupt\">Resent code (30s)</span>\n"
                + "                                    </div>";
        return data;
    }

    public String getInChangePassword() {
        String data = "<h1 class=\"title\">Forgot Password</h1>\n"
                + "                                    <p>For recover your password</p>\n"
                + "                                    <form class=\"form-group\" action=\"forgot-password\" method=\"post\">\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"password\" name=\"password\" required=\"\">\n"
                + "                                            <span>Password</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                            <span class=\"eye\" onclick=\"displayPass(this)\"><i class=\"eye-icon fa-solid fa-eye-slash\"></i></span>\n"
                + "                                        </div>\n"
                + "                                        <div class=\"form-input\">\n"
                + "                                            <input type=\"password\" name=\"confirm-password\" required=\"\">\n"
                + "                                            <span>Confirm password</span>\n"
                + "                                            <span class=\"message\"></span>\n"
                + "                                            <span class=\"eye\" onclick=\"displayPass(this)\"><i class=\"eye-icon fa-solid fa-eye-slash\"></i></span>\n"
                + "                                        </div>\n"
                + "                                    </form>\n"
                + "\n"
                + "                                    <div style=\"display: flex;\n"
                + "                                         justify-content: space-between;\n"
                + "                                         align-items: center;\n"
                + "                                         margin-top: 2rem\">\n"
                + "                                        <button style=\"margin: 0\" type='submit' class=\"account-btn bttn\">\n"
                + "                                            <span>Change</span>\n"
                + "                                        </button>\n"
                + "                                    </div>";
        return data;
    }

    public String getInOrder(List<Order> listTransactions) {
        String data = "";
        for (Order o : listTransactions) {
            String seperate = (o.getTopping().isEmpty()) ? "" : ";";

            data += "<div class=\"order-item\">\n"
                    + "                                                <input type=\"text\" name=\"id\" hidden value=\"" + o.getId() + "\">\n"
                    + "                                                <div class=\"order-item-img\">\n"
                    + "                                                    <img src=\"" + o.getpImage() + "\" alt=\"product image\"/>\n"
                    + "                                                </div>\n"
                    + "                                                <div class=\"order-item-content\">\n"
                    + "                                                    <div class=\"order-item-upper\">\n"
                    + "                                                        <h2>" + o.getpName() + "</h2>\n"
                    + "                                                        <p class=\"order-item-upper-status\">" + o.getStatus() + "</p>\n"
                    + "                                                        <div class=\"order-item-control\">\n"
                    + "                                                            <button class=\"bttn order-item-upper-btn\" onclick=\"productDetail('" + o.getpId() + "')\">\n"
                    + "                                                                <span>Buy Again</span>\n"
                    + "                                                            </button>\n"
                    + "                                                        </div>\n"
                    + "                                                    </div>\n"
                    + "                                                    <div class=\"order-item-mid\">\n"
                    + "                                                        <p class=\"order-item-mid-paydate\">" + o.getPayDate() + "</p>\n"
                    + "                                                        <p class=\"order-item-mid-paymethod\">" + o.getPayMethod() + "</p>\n"
                    + "                                                        <div class=\"order-item-control\">\n"
                    + "                                                            <button class=\"bttn order-item-mid-btn " + ((!o.getStatus().equals("Processing")) ? "active" : "") + "\" onclick=\"cancelOrder('" + o.getId() + "', '" + o.getpName() + "')\">\n"
                    + "                                                                <span>Cancel</span>\n"
                    + "                                                            </button>\n"
                    + "                                                        </div>\n"
                    + "                                                    </div>\n"
                    + "                                                    <div class=\"order-item-lower\">\n"
                    + "                                                        <p class=\"order-item-lower-desc\">" + o.getSize() + "" + seperate + "" + o.getTopping() + "</p>\n"
                    + "                                                        <p class=\"order-item-upper-quantity\">" + o.getQuantity() + "</p>\n"
                    + "                                                        <span class=\"order-item-upper-x\">x</span>\n"
                    + "                                                        <p class=\"order-item-upper-totalPrice\">" + o.formatPrice(o.getTotalPrice()) + "đ</p>\n"
                    + "                                                        <div class=\"order-item-control\">\n"
                    + "                                                            <button class=\"bttn order-item-lower-btn " + ((o.getStatus().equals("Processing")) ? "disable" : "") + "\" onclick=\"deleteOrder('" + o.getId() + "', '" + o.getpName() + "')\">\n"
                    + "                                                                <span>Delete</span>\n"
                    + "                                                            </button>\n"
                    + "                                                        </div>"
                    + "                                                    </div>\n"
                    + "                                                </div>\n"
                    + "                                            </div>";
        }

        return data;
    }

    public String getInTransaction(List<WalletTransaction> list) {
        String data = "";
        for (WalletTransaction w : list) {
            data += "<tr>\n"
                    + "       <td hidden><input type=\"type\" name=\"id\" value=\"" + w.getId() + "\"></td>"
                    + "       <td>" + w.getNote() + "</td>\n"
                    + "       <td>" + w.getTransactionDate() + "</td>\n"
                    + "       <td>" + (w.getNote().equals("Pay for the bill") ? "-" : "+") + " " + w.formatPrice(w.getAmount()) + "đ</td>\n"
                    + "       <td>\n"
                    + "          <button onclick=\"deleteTransaction(`" + w.getId() + "`, `" + w.getTransactionDate() + "`)\">\n"
                    + "             <i class=\"fa-solid fa-trash\"></i>\n"
                    + "          </button>\n"
                    + "       </td>\n"
                    + "</tr>";
        }
        return data;
    }

    // Duy
    public String getInterfaceUser(List<User> listUsers) {
        String data = "";
        for (User listUser : listUsers) {
            data += "<tr>\n"
                    + "                                                            <td>" + listUser.getId() + "</td>			\n"
                    + "                                                            <td>" + listUser.getName() + "</td>\n"
                    + "                                                            <td>" + listUser.getFullName() + "</td>\n"
                    + "                                                            <td>" + listUser.getEmail() + "</td>\n"
                    + "                                                            <td>\n"
                    + "                                                                <div>\n"
                    + "                                                                    <img class=\"rounded\" style=\"object-fit: cover\" src=\"." + listUser.getPicture() + "\" alt=\"customer img\"/>\n"
                    + "                                                                </div>\n"
                    + "                                                            </td>\n"
                    + "                                                            <td>" + listUser.getRole() + "</td>\n"
                    + "                                                            <td>" + listUser.isStatus() + "</td>\n"
                    + "                                                            <td class=\"admin-main-content-body-table-control\">\n"
                    + "                                                                <button>\n"
                    + "                                                                    <a href=\"account?action=edit-account&id=" + listUser.getId() + "\">\n"
                    + "                                                                        <i class=\"fa-solid fa-pen\"></i></a>\n"
                    + "                                                                </button>\n"
                    + "                                                                <button onclick=\"handleAccount(event, 'delete-account', " + listUser.getId() + ")\"><i class=\"fa-solid fa-trash\"></i></button>\n"
                    + "                                                            </td>\n"
                    + "                                                        </tr>";
        }

        return data;
    }

    public String getInterfaceProduct(List<Product> list) {
        String data = "";
        if (list.isEmpty()) {
            return "<tr>There is no product with that name</tr>";
        }
        for (Product product : list) {
            String status = product.getStatus().equals("Available") ? "fa-toggle-on" : "fa-toggle-off";
            data += "<tr>\n"
                    + "<td>" + product.getId() + "</td>\n"
                    + "<td>" + product.getCategoryName() + "</td>\n"
                    + "<td>" + product.getName() + "</td>\n"
                    + "<td><img src=\"." + product.getImage() + "\" class=\"rounded\" alt=\"\"/></td>\n"
                    + "<td>" + String.format("%.0f", product.getPrice()) + "</td>\n"
                    + "<td><i class=\"fa-solid " + status + "\"> </i></td>\n"
                    + "<td class=\"admin-main-content-body-table-control\">\n"
                    + "<button><a href=\"product?action=edit&id=" + product.getId() + "\"><i class=\"fa-solid fa-pen\"></i></a></button>\n"
                    + "<button><a href=\"product?action=delete&id=" + product.getId() + "\" onclick=\"return confirm('Are you sure you want to delete?');\"><i class=\"fa-solid fa-trash\"></i></a></button>\n"
                    + "</td>\n"
                    + "</tr>";

        }
        return data;
    }

    public String getInterfaceOrder(List<Order> listOrder) {
        String data = "";
        if (listOrder.isEmpty()) {
            return "<tr>There is no order with that user name</tr>";
        }
        for (Order order : listOrder) {
            data += "<tr>\n"
                    + "<td>" + order.getOrderID() + "</td>\n"
                    + "<td>" + order.getUserName() + "</td>\n"
                    + "<td>" + order.getProductName() + "</td>\n"
                    + "<td>x" + order.getQuantity() + "</td>\n"
                    + "<td>" + order.getOrderDate() + "</td>\n"
                    + "<td>" + order.getPaymentMethod() + "</td>\n"
                    + "<td>" + String.format("%.0f", order.getTotalAmount()) + "</td>\n"
                    + "<td>" + order.getStatus() + "</td>\n"
                    + "<td class=\"admin-main-content-body-table-control\">\n";
            if (!order.getStatus().equals("Completed")) {
                data += "<button><a href=\"order?action=editOrder&id=" + order.getOrderID() + "&productID=" + order.getProductID() + "\"><i class=\"fa-solid fa-pen\"></i></a></button>\n";
            }

            data += "</td>\n"
                    + "</tr>";
        }
        return data;
    }

    public String getInNews(New n) {
        String data = "<div class=\"transactions\">\n"
                + "                <div class=\"container\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <h1>News</h1>\n"
                + "                        <div class=\"news-body\">\n"
                + "                            <div class=\"news-item\">\n"
                + "                                <h2>" + n.getHeader() + "</h2>\n"
                + "                                <p>" + n.getHeaderInfo() + "</p>\n"
                + "                                <div class=\"news-item-img\">\n"
                + "                                    <img src=\"" + n.getImg() + "\" alt=\"news img\"/>\n"
                + "                                </div>\n"
                + "                                <desc>" + n.getContent() + "\n"
                + "                                </desc>\n"
                + "                            </div>\n"
                + "                        </div>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>";

        return data;
    }

    public String getInAllNews(List<New> listNews) {
        String data = "";
        data += "<div class=\"transactions\">\n"
                + "                <div class=\"container\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <h1>News</h1>\n"
                + "                        <!-- news start -->\n"
                + "                        <section id=\"news\">\n"
                + "                            <div class=\"news-body\">\n"
                + "                                <div class=\"container\">\n"
                + "                                    <div class=\"row\">\n";
        for (New n : listNews) {
            data += "                                            <div class=\"news-items-container col-lg-4 col-md-6 col-sm-6 col-12\">\n"
                    + "                                                <div class=\"news-items\">\n"
                    + "                                                    <div class=\"news-items-img\" onclick=\"getNews('" + n.getnId() + "')\">\n"
                    + "                                                        <img src=\"" + n.getImg() + "\" alt=\"alt\"/>\n"
                    + "                                                    </div>\n"
                    + "                                                    <p>" + n.getStartDate() + "</p>\n"
                    + "                                                    <h3>\n"
                    + "                                                        <span onclick=\"getNews('" + n.getnId() + "')\">" + n.getHeader() + "</span>\n"
                    + "                                                    </h3>\n"
                    + "                                                    <p class=\"news-items-content\">" + n.getContent() + "</p>\n"
                    + "                                           </div>\n"
                    + "                                    </div>\n";
        }
        data += "                                </div>\n"
                + "                            </div>\n"
                + "                        </section>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>";
        return data;
    }

    public String getInStore(List<Store> listStores) {
        String data = "<div class=\"store\">\n"
                + "                <div class=\"container\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <h1>Our Stores</h1>\n"
                + "                        <!-- news start -->\n"
                + "                        <section id=\"store\">\n"
                + "                            <div class=\"store-body\">\n"
                + "                                <div class=\"container\">\n"
                + "                                    <div class=\"row store-container\">\n";
        for (Store s : listStores) {
            data += "                                        <div class=\"store-items-container col-lg-6 col-md-6\">\n"
                    + "                                            <div class=\"store-item\">\n"
                    + "                                                <div class=\"store-img\">\n"
                    + "                                                    <img src=\"" + s.getImg() + "\" alt=\"store image\"/>\n"
                    + "                                                </div>\n"
                    + "                                                <div class=\"store-content\">\n"
                    + "                                                    <h2 class=\"store-header\">" + s.getHeader() + "</h2>\n"
                    + "                                                    <p class=\"store-open\">" + s.getTime() + "</p>\n"
                    + "                                                    <desc class=\"store-info\">" + s.getInfo() + "</desc>\n"
                    + "                                                </div>\n"
                    + "                                            </div>\n"
                    + "                                        </div>\n";
        }

        data += "                                    </div>\n"
                + "                                </div>\n"
                + "                            </div>\n"
                + "                        </section>\n"
                + "                    </div>\n"
                + "                </div>\n"
                + "            </div>";
        return data;
    }
}
