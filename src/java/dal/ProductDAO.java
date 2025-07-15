/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import model.Order;
import model.Product;

/**
 *
 * @author admin
 */
public class ProductDAO extends DBContext {

    public List<Product> getNewProducts(int top) {
        List<Product> list = new ArrayList<>();
        String query = "select top " + top + " *, dbo.getRating(pd.ProductID) Rating \n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.[Status] = 'Available' \n"
                + "order by CreatedAt desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = "new";
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err: " + e);
        }
        return list;
    }

    private String getFirstImage(String data) {
        String[] cut = data.split(";");
        return cut[0];
    }

    public List<Product> getProductsByCid(int categoryID, int page, int item) {
        List<Product> list = new ArrayList<>();
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.Status = 'Available' and CategoryID = ?\n"
                + "order by ProductID\n"
                + "offset ? rows fetch next ? rows only";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, categoryID);
            pst.setInt(2, (page - 1) * item);
            pst.setInt(3, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err getProductsByCid: " + e);
        }
        return list;
    }

    public Product getProductByPid(int id, boolean isAllImgs) {
        String query = "select *, dbo.getRating(pd.ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "left join Inventory i on i.ProductID = pd.ProductID\n"
                + "where pd.ProductID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image;
                if (isAllImgs) {
                    image = rs.getString("Image");
                } else {
                    image = getFirstImage(rs.getString("Image"));
                }
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                if (isAllImgs) {
                    // System.out.println(rs.getInt("Quantity"));
                    p.setQuantity(rs.getInt("Quantity"));
                }
                return p;
            }
        } catch (SQLException e) {
            System.out.println("err getProductByPid: " + e);
        }
        return null;
    }

    public List<Product> getTopProducts(int top) {
        List<Product> list = new ArrayList<>();
        List<Integer> listId = getTopProductId(top);
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.Status = 'Available' and pd.ProductID in (";
        for (Integer id : listId) {
            query += id + ",";
        }
        query = query.substring(0, query.length() - 1);
        query += ")";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = "best";
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err getTopProducts: " + e);
        }
        return list;
    }

    public List<Product> getHotProducts() {
        List<Product> list = new ArrayList<>();
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "        where pd.Status = 'Available' and pm.DiscountPercent > 0";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = "hot";
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err getHotProducts: " + e);
        }
        return list;
    }

    private List<Integer> getNewProductId(int top) {
        List<Integer> list = new ArrayList<>();
        String query = "select top " + top + " ProductID \n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.[Status] = 'Available' \n"
                + "order by CreatedAt desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                list.add(pId);
            }
        } catch (SQLException e) {
            System.out.println("err getNewProductId: " + e);
        }
        return list;
    }

    private List<Integer> getTopProductId(int top) {
        List<Integer> list = new ArrayList<>();
        String query = "select top " + top + " od.ProductID, sum(od.Quantity) Sold\n"
                + "from OrderDetails od\n"
                + "join Orders o on o.OrderID = od.OrderID\n"
                + "join Products p on p.ProductID = od.ProductID\n"
                + "where o.[Status] = 'Completed' and p.[Status] = 'Available'\n"
                + "group by od.ProductID\n"
                + "order by Sold desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                list.add(pId);
            }
        } catch (SQLException e) {
            System.out.println("err getTopProductId: " + e);
        }
        return list;
    }

    private List<Integer> getDiscountProductId() {
        List<Integer> list = new ArrayList<>();
        String query = "select ProductID, DiscountPercent \n"
                + "from Products pd\n"
                + "join Promotions pm on pm.PromotionID = pd.PromotionID";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                list.add(pId);
            }
        } catch (SQLException e) {
            System.out.println("err getDiscountProductId: " + e);
        }
        return list;
    }

    private String getLabel(int pId) {
        List<Integer> listNew = getNewProductId(10);
        for (Integer id : listNew) {
            if (pId == id) {
                return "new";
            }
        }
        List<Integer> listTop = getTopProductId(6);
        for (Integer id : listTop) {
            if (pId == id) {
                return "best";
            }
        }
        List<Integer> listDiscount = getDiscountProductId();
        for (Integer id : listDiscount) {
            if (pId == id) {
                return "sell";
            }
        }
        return null;
    }

    public int totalProductByCid(int cId) {
        String query = "select count(*) Total from Products where CategoryID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, cId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("Total");
                return total;
            }
        } catch (SQLException e) {
            System.out.println("err totalProductByCid: " + e);
        }
        return -1;
    }

    public List<Product> searchByName(int id, String value) {
        List<Product> list = new ArrayList<>();
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.[Status] = 'Available' and CategoryID = ? and ProductName like '%" + value + "%'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err searchByName: " + e);
        }
        return list;
    }

    public List<Product> searchByPrice(int id, double from, double to) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT *, dbo.getRating(pd.ProductID) AS Rating,\n"
                + "    ROUND(pd.Price * (1 - ISNULL(pm.DiscountPercent, 0) / 100), 0) AS EffectivePrice\n"
                + "FROM Products pd\n"
                + "LEFT JOIN Promotions pm ON pm.PromotionID = pd.PromotionID\n"
                + "WHERE pd.[Status] = 'Available' \n"
                + "    AND pd.CategoryID = ?\n"
                + "    AND (\n"
                + "        (pm.DiscountPercent IS NOT NULL AND \n"
                + "         ROUND((pd.Price - pd.Price * (pm.DiscountPercent / 100)) / 1000, 0) BETWEEN ? AND ?)\n"
                + "        \n"
                + "        OR (pm.DiscountPercent IS NULL AND \n"
                + "            ROUND(pd.Price / 1000, 0) BETWEEN ? AND ?)\n"
                + "    )\n"
                + "ORDER BY EffectivePrice ASC;";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setDouble(2, from);
            pst.setDouble(3, to);
            pst.setDouble(4, from);
            pst.setDouble(5, to);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err searchByPrice: " + e);
        }
        return list;
    }

    public List<Product> searchByStar(int id, int star) {
        List<Product> list = new ArrayList<>();
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.[Status] = 'Available' and CategoryID = ? and dbo.getRating(ProductID) = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setInt(2, star);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err searchByPrice: " + e);
        }
        return list;
    }

    public List<Product> searchByDiscount(int id, int dis) {
        List<Product> list = new ArrayList<>();
        String query = "select *, dbo.getRating(ProductID) Rating\n"
                + "from Products pd\n"
                + "left join Promotions pm on pm.PromotionID = pd.PromotionID\n"
                + "where pd.[Status] = 'Available' and CategoryID = ? and pm.DiscountPercent >= ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.setInt(2, dis);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int pId = rs.getInt("ProductID");
                int cId = rs.getInt("CategoryID");
                String name = rs.getString("ProductName");
                String description = rs.getString("Description");
                String image = getFirstImage(rs.getString("Image"));
                String size = rs.getString("Size");
                String topping = rs.getString("Topping");
                double price = rs.getDouble("Price");
                LocalDateTime createdAt = rs.getTimestamp("CreatedAt").toLocalDateTime();
                int rating = rs.getInt("Rating");
                String label = getLabel(pId);
                double discount = rs.getDouble("DiscountPercent");
                Product p = new Product(pId, cId, name, description, image, size, topping, price, createdAt, rating, label, discount);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err searchByPrice: " + e);
        }
        return list;
    }

    public double getPriceById(int pId) {
        String query = "select Price from Products where ProductID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, pId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Price");
            }
        } catch (SQLException e) {
            System.out.println("err getPrice: " + e);
        }
        return -1;
    }

    public boolean isValidProduct(int pId, double price, int quantity, String size, String toppings) {
        String query = "select Price, Size, Topping, DiscountPercent\n"
                + "from Products p\n"
                + "left join Promotions pro on pro.PromotionID = p.PromotionID\n"
                + "where p.ProductID = ? and p.[Status] = 'Available'";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, pId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double pPrice = rs.getDouble("Price");
                String pSize = rs.getString("Size");
                String pTopping = rs.getString("Topping");
                double discount = rs.getDouble("DiscountPercent");

                System.out.println("size: " + size + " toppings: " + toppings);
                System.out.println("pSize: " + pSize + " pTopping: " + pTopping);
                System.out.println("discount: " + discount);
                if (!Objects.toString(pSize, "").contains(Objects.toString(size, ""))) {
                    return false;
                }

                if (pTopping != null) {
                    if (toppings != null) {
                        String[] topping = toppings.split(";");
                        for (String t : topping) {
                            if (!pTopping.contains(t)) {
                                return false;
                            }
                        }
                    }
                } else {
                    if (toppings != null) {
                        return false;
                    }
                }

                double currPrice;
                if (discount > 0.0) {
                    currPrice = Math.round((pPrice - (pPrice * (discount / 100))) / 1000) * 1000;
                } else {
                    currPrice = pPrice;
                }
                currPrice *= quantity;
                if (size != null) {
                    currPrice += Integer.parseInt(size.replaceAll("[^0-9]", "")) * 1000 * quantity;
                }
                if (toppings != null) {
                    String[] topping = toppings.split(";");
                    for (String t : topping) {
                        currPrice += (Integer.parseInt(t.replaceAll("[^0-9]", "")) * 1000 * quantity);
                    }
                }
                System.out.println("currPrice: " + currPrice);
                System.out.println("price: " + price);
                if (currPrice != price) {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("err isValidProduct: " + e);
        }
        return true;
    }

    public boolean isAvailableProduct(int id, int quantity) {
        String query = "select i.Quantity\n"
                + "from Products p\n"
                + "join Inventory i on i.ProductID = p.ProductID\n"
                + "where p.ProductID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("Quantity") >= quantity;
            }
        } catch (SQLException e) {
            System.out.println("err isAvailableProduct: " + e);
        }
        return false;
    }

    public int getTotalProduct() {
        String query = "select count(ProductID) Total from Products";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            System.out.println("err getTotalProduct: " + e);
        }
        return -1;
    }

    public List<Order> getTotalProductSold() {
        List<Order> list = new ArrayList<>();
        String query = "select sum(od.Quantity) Total, c.CategoryName\n"
                + "from Orders o\n"
                + "join OrderDetails od on od.OrderID = o.OrderID\n"
                + "join Products p on p.ProductID = od.ProductID\n"
                + "join Categories c on c.CategoryID = p.CategoryID\n"
                + "where o.[Status] = 'Completed'\n"
                + "group by c.CategoryName";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int total = rs.getInt("Total");
                String cName = rs.getString("CategoryName");
                Order o = new Order(total, cName);
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("err getTotalProductSoldBy: " + e);
        }
        return list;
    }

    public List<Product> getTop10Product() {
        List<Product> list = new ArrayList<>();
        String query = "select top 10 p.ProductID, p.ProductName, p.[Image], p.Price, sum(od.Quantity) Sold, dbo.getRating(p.ProductID) Rating\n"
                + "from OrderDetails od\n"
                + "join Orders o on o.OrderID = od.OrderID\n"
                + "join Products p on p.ProductID = od.ProductID\n"
                + "where o.[Status] = 'Completed'\n"
                + "group by p.ProductID, p.ProductName, p.[Image], p.Price\n"
                + "order by Sold desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                String img = getFirstImage(rs.getString("Image"));
                double price = rs.getDouble("Price");
                int sold = rs.getInt("Sold");
                int rating = rs.getInt("Rating");
                Product p = new Product(id, name, img, price, rating, sold);
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("err getTop10Product: " + e);
        }
        return list;
    }

    /// Pham Quoc Tu
    public List<Product> getAllProducts(int page, int item) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT Products.ProductID,Categories.CategoryName,Products.ProductName,Products.[Image],Products.[Status],Products.Price FROM Products\n"
                + "JOIN Categories ON Categories.CategoryID = Products.CategoryID\n"
                + "ORDER BY ProductID\n"
                + "OFFSET ?  ROWS FETCH NEXT ? ROWS ONLY";
        Object[] params = {(page - 1) * item, item};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String CategoryName = rs.getString("CategoryName");
                String ProductName = rs.getString("ProductName");
                String Image = getFirstImage(rs.getString("Image"));
                double Price = rs.getDouble("Price");
                String Status = rs.getString("Status");
                Product product = new Product(id, CategoryName, ProductName, Image, Price, Status);
                list.add(product);
            }
        } catch (SQLException exception) {
            System.out.println("getAllProducts(int page, int item): " + exception);
        }
        return list;
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT\n"
                + "	[dbo].[Products].[ProductID]\n"
                + "   ,[dbo].[Categories].CategoryName\n"
                + "   ,[dbo].[Products].[ProductName]\n"
                + "   ,[dbo].[Products].[Image]\n"
                + "   ,[dbo].[Products].[Price]\n"
                + "   ,[dbo].[Products].[Status]\n"
                + "FROM [dbo].[Products]\n"
                + "JOIN [dbo].[Categories]\n"
                + "	ON [dbo].[Categories].CategoryID = [dbo].[Products].CategoryID";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String CategoryName = rs.getString("CategoryName");
                String ProductName = rs.getString("ProductName");
                String Image = rs.getString("Image");
                double Price = rs.getDouble("Price");
                String Status = rs.getString("Status");
                Product product = new Product(id, CategoryName, ProductName, Image, Price, Status);
                list.add(product);
            }
        } catch (SQLException exception) {
            System.out.println("getAllProducts: " + exception);
        }
        return list;
    }

    public int createNewProduct(Product product) {
        String getNextIdProduct = "SELECT max([ProductID]) + 1 as nextProductID FROM Products";
        try ( ResultSet rs = execSelectQuery(getNextIdProduct)) {
            if (rs.next()) {
                int nextProductId = rs.getInt(1);
                // nếu product thuộc loại "Cake" (category = 2), thì sẽ không thêm 
                if (product.getcId() == 2) {
                    product.setSize(null);
                    product.setTopping(null);
                }
                if (product.getPromotionId() == 0) {
                    String createNewProduct = "INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], SupplierID)\n"
                            + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                    Object[] params = {
                        nextProductId,
                        product.getcId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getSize(),
                        product.getTopping(),
                        product.getPrice(),
                        product.getCreatedAt(),
                        product.getStatus(),
                        product.getSupplierID()};
                    return execQuery(createNewProduct, params);
                } else {
                    String createNewProduct = "INSERT [dbo].[Products] ([ProductID], [CategoryID], [ProductName], [Description], [Image], [Size], [Topping], [Price], [CreatedAt], [Status], [PromotionID], SupplierID)\n"
                            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    Object[] params = {
                        nextProductId,
                        product.getcId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImage(),
                        product.getSize(),
                        product.getTopping(),
                        product.getPrice(),
                        product.getCreatedAt(),
                        product.getStatus(),
                        product.getPromotionId(),
                        product.getSupplierID()};
                    return execQuery(createNewProduct, params);
                }
            }
        } catch (SQLException exception) {
            System.out.println("createNewProduct: " + exception);
        }
        return 0;
    }

    /**
     *
     * @return <h1>number of productId on Products table</h1>
     */
    public int checkIdProduct() {
        int numberOfProductId = 0;
        String query = "SELECT count(*) FROM [dbo].[Products]";
        try ( ResultSet rs = execSelectQuery(query)) {
            if (rs.next()) {
                return numberOfProductId = rs.getInt(1);
            }

        } catch (SQLException exception) {
            System.out.println("checkIdProduct: " + exception);
        }
        return numberOfProductId;
    }

    /**
     *
     * @param productId get a product object by productId
     * @return a product object
     */
    public Product getProductById(int productId) {
        String query = "SELECT CategoryID, ProductName, [Description], [Image], Size, Topping, Price, PromotionID, [Status], SupplierID from Products\n"
                + "where ProductID = ?";
        Object[] params = {productId};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            if (rs.next()) {
                return new Product(
                        rs.getInt("CategoryID"),
                        rs.getString("ProductName"),
                        rs.getString("Description"),
                        rs.getString("Image"),
                        rs.getString("Size"),
                        rs.getString("Topping"),
                        rs.getDouble("Price"),
                        rs.getInt("PromotionId"),
                        rs.getString("Status"),
                        rs.getInt("SupplierID"));

            }

        } catch (Exception e) {
        }
        return null;
    }

    public int editProductCreated(Product product) {
        String query = "UPDATE Products SET CategoryID = ?,ProductName = ?,[Description] = ?,[Image] = ?,Size = ?,Topping = ?,Price = ?,CreatedAt = ?,[Status] = ?,PromotionID = ?,SupplierID = ?\n"
                + "WHERE ProductID = ?";
        try {
            if (product.getPromotionId() == 0) {
                query = "UPDATE Products SET CategoryID = ?,ProductName = ?,[Description] = ?,[Image] = ?,Size = ?,Topping = ?,Price = ?,CreatedAt = ?,[Status] = ?,SupplierID = ?\n"
                        + "WHERE ProductID = ?";
                Object[] params = {
                    product.getcId(),
                    product.getName(),
                    product.getDescription(),
                    product.getImage(),
                    product.getSize(),
                    product.getTopping(),
                    product.getPrice(),
                    product.getCreatedAt(),
                    product.getStatus(),
                    product.getSupplierID(),
                    product.getId()};
                return execQuery(query, params);
            } else {
                Object[] params = {
                    product.getcId(),
                    product.getName(),
                    product.getDescription(),
                    product.getImage(),
                    product.getSize(),
                    product.getTopping(),
                    product.getPrice(),
                    product.getCreatedAt(),
                    product.getStatus(),
                    product.getPromotionId(),
                    product.getSupplierID(),
                    product.getId()};
                return execQuery(query, params);
            }

        } catch (SQLException exception) {
            System.out.println("editProductCreated: " + exception);
        }

        return 0;
    }

    public int deleteProductById(int id) {
        String query = null;
        PreparedStatement pst = null;

        try {
            // Bắt đầu transaction
            con.setAutoCommit(false);

            // Xóa Inventory
            query = "DELETE FROM Inventory WHERE ProductID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

            // Xóa OrderDetails
            query = "DELETE FROM OrderDetails WHERE ProductID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

            // Xóa Reviews
            query = "DELETE FROM Reviews WHERE ProductID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

            // Xóa Product (nếu cần)
            query = "DELETE FROM Products WHERE ProductID = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

            
            // Commit transaction (Xác nhận thay đổi)
            con.commit();

            return 1;
        } catch (SQLException exception) {
            try {
                if (con != null) {
                    con.rollback(); // Quay lại trạng thái trước khi xóa nếu lỗi
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            System.out.println("deleteProductById: " + exception);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                con.setAutoCommit(true); // Bật lại chế độ auto commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public List<Product> searchByNameProduct(String valueSearch) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT Products.ProductID,Categories.CategoryName,Products.ProductName,Products.[Image],Products.Price,Products.[Status] from Products \n"
                + "JOIN Categories on Categories.CategoryID = Products.CategoryID \n"
                + "WHERE Products.ProductName like  ?";
        Object[] params = {"%" + valueSearch + "%"};
        try ( ResultSet rs = execSelectQuery(query, params)) {
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String CategoryName = rs.getString("CategoryName");
                String ProductName = rs.getString("ProductName");
                String Image = getFirstImage(rs.getString("Image"));
                double Price = rs.getDouble("Price");
                String Status = rs.getString("Status");
                Product product = new Product(id, CategoryName, ProductName, Image, Price, Status);
                list.add(product);
            }
        } catch (SQLException exception) {
            System.out.println("searchByNameProduct: " + exception);
        }
        return list;
    }

    public List<Product> getTop10Products() {
        String query = "SELECT TOP 10 P.ProductID, C.CategoryName, P.ProductName,  P.[Image], P.Price, P.[Status], SUM(OD.Quantity) AS TotalQuantity\n"
                + "FROM Products P\n"
                + "JOIN Categories C ON C.CategoryID = P.CategoryID\n"
                + "JOIN OrderDetails OD ON OD.ProductID = P.ProductID\n"
                + "GROUP BY P.ProductID, C.CategoryName, P.ProductName,  P.[Image], P.Price, P.[Status]\n"
                + "ORDER BY TotalQuantity DESC";
        List<Product> list = new ArrayList<>();
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String CategoryName = rs.getString("CategoryName");
                String ProductName = rs.getString("ProductName");
                String Image = rs.getString("Image");
                double Price = rs.getDouble("Price");
                String Status = rs.getString("Status");
                String TotalQuantity = rs.getString("TotalQuantity");
                Product product = new Product(id, CategoryName, ProductName, Image, Price, Status, TotalQuantity);
                list.add(product);
            }

        } catch (SQLException exception) {
            System.out.println("getTop10Products: " + exception);
        }
        return list;
    }

    public int getMaxProductID() {
        String query = "SELECT max([ProductID]) as nextProductID FROM Products";

        try ( ResultSet rs = execSelectQuery(query)) {
            if (rs.next()) {
                return rs.getInt("nextProductID");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        // Get 10 proudcts that new in 10 days
        // Get 6 proudcts that best seller
        List<Product> list = dao.getTop10Product();
        for (Product best : list) {
            System.out.println(best);
        }
    }
}
