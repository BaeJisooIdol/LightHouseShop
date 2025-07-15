/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoryDAO;
import dal.Data;
import dal.InventoryDAO;
import dal.ProductDAO;
import dal.PromotionDao;
import dal.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import model.Category;
import model.Inventory;
import model.Product;
import model.Promotion;
import model.Supplier;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet(name = "AdminProductServlet", urlPatterns = {"/admin/product"})
public class AdminProductServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/imgs";
    private int item = 5;
    private int numberOfPages = 0;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /// Pham Quoc Tu
        String action = (request.getParameter("action")) != null ? request.getParameter("action") : "";
        ProductDAO productDAO = new ProductDAO();
        int totalNumberOfProducts = productDAO.checkIdProduct();
        if (totalNumberOfProducts != -1) {
            numberOfPages = (int) Math.ceil((double) totalNumberOfProducts / item);
        }
        switch (action) {
            case "create":
                showCreateProduct(request, response);
                break;
            case "edit":
                showEditProduct(request, response);
                break;
            case "delete":
                showDeleteProduct(request, response);
                break;
            case "search":
                searchProduct(request, response);
            case "paging":
                pagingProduct(request, response);
                break;
            case "top10product":
                top10ProductCreated(request, response);
                break;
            default:
                showListProduct(request, response);
        }
        /// Pham Quoc Tu

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method") != null ? request.getParameter("method") : "";

        switch (method) {
            case "create":
                createNewProduct(request, response);
                break;
            case "edit":
                editProductCreated(request, response);
                break;
            default:
                showListProduct(request, response);
        }

    }

    /// Pham Quoc Tu
    private void showCreateProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SupplierDAO supplierDAO = new SupplierDAO();
        CategoryDAO categoryDao = new CategoryDAO();
        List<Category> listCategory = categoryDao.getAllCategory();
        List<Supplier> listSupplier = supplierDAO.getAllSuppliers();
        PromotionDao promotionDao = new PromotionDao();
        List<Promotion> listPromotion = promotionDao.getAll();

        request.setAttribute("listPromotion", listPromotion);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listSupplier", listSupplier);
        request.getRequestDispatcher("../WEB-INF/admin/create-product.jsp").forward(request, response);
    }

    private void showDeleteProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InventoryDAO inventoryDAO = new InventoryDAO();

        ProductDAO productDAO = new ProductDAO();
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException exception) {
            System.out.println("showDeleteProduct: " + exception);
        }
        if (id == 0) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Delete failure!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
            return;
        }

        int result = productDAO.deleteProductById(id);

        if ((result == 1)) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Deleted successfully!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Delete failure!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        }
    }

    private void showEditProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InventoryDAO inventoryDAO = new InventoryDAO();

        ProductDAO productDAO = new ProductDAO();
        Product product;
        int id = 0;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException exception) {
            System.out.println("showEditProduct: " + exception);
        }
        if (id == 0) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('This product does not exist!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
            return;
        }

        product = productDAO.getProductById(id);

        if (product == null) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('This product does not exist..!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
            return;
        }

        if (product.getSize() != null) {
            String[] sizes = new String[3];
            sizes[0] = product.getSize().split(";")[0];
            for (String split : product.getSize().split(";")) {
                if (split.contains("M")) {
                    sizes[1] = split;
                }
                if (split.contains("L")) {
                    sizes[2] = split;
                }
            }
            request.setAttribute("sizes", sizes);
        }

        if (product.getTopping() != null) {
            request.setAttribute("toppings", product.getTopping());
        }

        Inventory inventory = inventoryDAO.getInventoryByproductID(id);

        CategoryDAO categoryDao = new CategoryDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        PromotionDao promotionDao = new PromotionDao();
        List<Category> listCategory = categoryDao.getAllCategory();
        List<Supplier> listSupplier = supplierDAO.getAllSuppliers();
        List<Promotion> listPromotion = promotionDao.getAll();
        request.setAttribute("inventory", inventory);
        request.setAttribute("listSupplier", listSupplier);
        request.setAttribute("product", product);
        request.setAttribute("id", id);
        request.setAttribute("listCategory", listCategory);
        request.setAttribute("listPromotion", listPromotion);
        request.getRequestDispatcher("../WEB-INF/admin/edit-product.jsp").forward(request, response);
    }

    private void showListProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        List<Product> listProduct = dao.getAllProducts(1, item);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("listProduct", listProduct);
        request.getRequestDispatcher("../WEB-INF/admin/product.jsp").forward(request, response);
    }

    private void createNewProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO productDAO = new ProductDAO();
        InventoryDAO inventoryDAO = new InventoryDAO();
        String product_name = request.getParameter("product_name");
        int product_category = Integer.parseInt(request.getParameter("product_category"));
        String product_description = request.getParameter("product_description");
        String product_image = "";

        // Xác định thư mục lưu ảnh trong build
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Tạo thư mục nếu chưa có
        }
        // Lấy file từ request
        Part filePart = request.getPart("product_image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        if (fileName != null && !fileName.isEmpty()) {
            // Đường dẫn lưu file trong build/web/assets/imgs/
            String filePath = uploadPath + File.separator + fileName;
            // Lưu file vào thư mục build/web
            filePart.write(filePath);
            // Lưu đường dẫn tương đối vào database
            product_image = "./assets/imgs/" + fileName;
        }
        String sizeS_priceS = "S-0"; // vì mỗi loại cafe hoặc trà sữa, ... đều có size và giá mặc định bằng S-0.
        String sizeM_priceM = (request.getParameter("sizeM") != null) ? ";" + request.getParameter("sizeM") + request.getParameter("priceM") : "";
        String sizeL_priceL = (request.getParameter("sizeL") != null) ? ";" + request.getParameter("sizeL") + request.getParameter("priceL") : "";
        String totalSize = sizeS_priceS + sizeM_priceM + sizeL_priceL;
        String product_multiTopping = request.getParameter("product_multiTopping");
        double product_price = Double.parseDouble(request.getParameter("product_price"));
        String product_status = request.getParameter("product_status");
        int product_promotion = Integer.parseInt(request.getParameter("product_promotion"));
        int supplier_name = Integer.parseInt(request.getParameter("supplier_name"));

        int product_quantity = Integer.parseInt(request.getParameter("product_quantity"));

        int result = productDAO.createNewProduct(new Product(product_category, product_name, product_description, product_image, totalSize, product_multiTopping, product_price, LocalDateTime.now(), product_promotion, product_status, supplier_name));
        int resultCreateInventory = inventoryDAO.createNewInventory(new Inventory(productDAO.getMaxProductID(), product_quantity, LocalDateTime.now()));
        if ((result == 1) && (resultCreateInventory == 1)) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Create successfully!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Create failure!');");
            out.println("window.location.href='product?action=create';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        }
    }

    private void editProductCreated(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InventoryDAO inventoryDAO = new InventoryDAO();

        ProductDAO productDAO = new ProductDAO();
        int product_id = Integer.parseInt(request.getParameter("product_id"));
        String product_name = request.getParameter("product_name");
        int product_category = Integer.parseInt(request.getParameter("product_category"));
        String product_description = request.getParameter("product_description");

        Part filePart = request.getPart("product_image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Lấy ảnh cũ từ input ẩn
        String oldImage = request.getParameter("old_product_image");
        String finalImagePath;

        if (fileName == null || fileName.isEmpty()) {
            // Nếu không có file mới, giữ nguyên ảnh cũ
            finalImagePath = oldImage;
        } else {
            // Nếu có file mới, lưu file mới và cập nhật đường dẫn
            String uploadPath = getServletContext().getRealPath("") + File.separator + "assets/imgs";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            finalImagePath = "./assets/imgs/" + fileName;
        }

        String sizeS_priceS = "S-0"; // vì mỗi loại cafe hoặc trà sữa, ... đều có size và giá mặc định bằng S-0.
        String sizeM_priceM = (request.getParameter("sizeM") != null) ? ";" + request.getParameter("sizeM") + request.getParameter("priceM") : "";
        String sizeL_priceL = (request.getParameter("sizeL") != null) ? ";" + request.getParameter("sizeL") + request.getParameter("priceL") : "";
        String totalSize = sizeS_priceS + sizeM_priceM + sizeL_priceL;
        if (product_category == 2) {
            totalSize = null;
        }
        String product_multiTopping = request.getParameter("product_multiTopping");
        double product_price = Double.parseDouble(request.getParameter("product_price"));
        String product_status = request.getParameter("product_status");
        int product_promotion = Integer.parseInt(request.getParameter("product_promotion"));
        int supplier_name = Integer.parseInt(request.getParameter("supplier_name"));
        /// 
        int product_quantity = Integer.parseInt(request.getParameter("product_quantity"));
        ///
        int result = productDAO.editProductCreated(new Product(product_id, product_category, product_name, product_description, finalImagePath, totalSize, product_multiTopping, product_price, LocalDateTime.now(), product_promotion, product_status, supplier_name));
        int resultEditInventory = inventoryDAO.editInventoryCreatedByProductID(product_quantity, LocalDateTime.now(), product_id);
        if ((result == 1) && (resultEditInventory == 1)) {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Edit successfully!');");
            out.println("window.location.href='product';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert('Edit failure!');");
            out.println("window.location.href='product?action=edit&id=" + product_id + "';"); // Chuyển hướng sau khi bấm OK
            out.println("</script>");
        }
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String value = request.getParameter("value");
        ProductDAO productDAO = new ProductDAO();
        List<Product> listProduct;
        String data;
        Data d = new Data();
        if (value.isEmpty()) {
            listProduct = productDAO.getAllProducts(1, item);
        } else {
            listProduct = productDAO.searchByNameProduct(value);

        }
        data = d.getInterfaceProduct(listProduct);
        response.getWriter().write(data);
    }

    private void pagingProduct(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO productDAO = new ProductDAO();
        String value = request.getParameter("value");
        List<Product> listProduct = productDAO.getAllProducts(Integer.parseInt(value), item);
        Data d = new Data();
        String data = d.getInterfaceProduct(listProduct);
        response.getWriter().write(data);
    }

    private void top10ProductCreated(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        List<Product> listTop10 = productDAO.getTop10Products();
        request.setAttribute("listTop10", listTop10);
        request.getRequestDispatcher("../WEB-INF/admin/top10product.jsp").forward(request, response);
    }
    /// Pham Quoc Tu
}
