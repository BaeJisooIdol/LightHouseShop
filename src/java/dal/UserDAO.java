/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author admin
 */
public class UserDAO extends DBContext {

    public User getUser(String uName, String password) {
        String query = "select *\n"
                + "from Users u\n"
                + "left join Wallet w on w.UserID = u.UserID\n"
                + "where u.UserName = ? and u.[Password] = ? and u.[Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, uName);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int uId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String pass = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                String picture = rs.getString("Image");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                double balance = rs.getDouble("Balance");
                return new User(uId + "", name, password, email, fullName, phone, address, role, createdAt, picture, balance);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean isExistUserName(String uName) {
        String query = "select * from Users where UserName = ? and [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, uName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isExistEmail(String email) {
        String query = "select * from Users where Email = ? and [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public int insertUser(String uName, String password, String email, String fullName, String phone, String image, String role, LocalDateTime createdAt,
            String token, LocalDateTime expireToken, int isTokenUsed, String verificationCode, int status) {
        String query = "INSERT INTO Users ([Username],[Password],[Email],[FullName],[Phone],[Role],[CreatedAt],[Token],[ExpireToken],[isTokenUsed],[VerificationCode],[Image],[Status],[ResentTokenCount])\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, uName);
            pst.setString(2, password);
            pst.setString(3, email);
            pst.setString(4, fullName);
            pst.setString(5, phone);
            pst.setString(6, role);
            pst.setTimestamp(7, Timestamp.valueOf(createdAt));
            pst.setString(8, token);
            pst.setTimestamp(9, Timestamp.valueOf(expireToken));
            pst.setInt(10, isTokenUsed);
            pst.setString(11, verificationCode);
            pst.setString(12, image);
            pst.setInt(13, status);
            pst.setInt(14, 0);

            pst.executeUpdate();

            // Lấy generated key
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                int newUserId = rs.getInt(1);
                return newUserId;
            }
        } catch (SQLException e) {
            System.out.println("error insertUser: " + e);
        }
        return -1;
    }

    public int getUidByEmail(String email) {
        String query = "select UserID from Users where Email = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("UserID");
            }
        } catch (SQLException e) {
            System.out.println("error getUidByEmail: " + e);
        }
        return -1;
    }

    public List<String> getTokenByEmail(String email) {
        List<String> list = new ArrayList<>();
        String query = "select Token from Users where Email = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Token"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public String getVerificationCodeByEmail(String email) {
        String query = "select VerificationCode from Users where Email = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("VerificationCode");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateToken(String token, String newToken, String email, String code, LocalDateTime expireToken) {
        String query = "UPDATE Users SET Token = ?, VerificationCode = ?, ExpireToken = ? WHERE Email = ?";
        if (!newToken.equals("")) {
            query += " and Token = '" + token + "'";
        }
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, newToken);
            pst.setString(2, code);
            pst.setTimestamp(3, Timestamp.valueOf(expireToken));
            pst.setString(4, email);

            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in updateToken: " + e.getMessage());
        }
    }

    public String isValidCode(String email, String code) {
        String query = "SELECT \n"
                + "    CASE \n"
                + "        WHEN VerificationCode <> ? THEN 'Invalid Verification Code'\n"
                + "        WHEN ExpireToken <= GETDATE() THEN 'Token Expired'\n"
                + "        ELSE 'Valid'\n"
                + "    END AS ValidationResult\n"
                + "FROM Users\n"
                + "WHERE Email = ? and VerificationCode = ?;";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, code);
            pst.setString(2, email);
            pst.setString(3, code);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("ValidationResult");
            }
        } catch (SQLException e) {
            System.out.println("isValidCode: " + e);
        }
        return null;
    }

    public boolean isVerify(String email) {
        String query = "select [Status] from Users where Email = ? and [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void updateStatusUser(String email, String code) {
        String query = "update Users set [Status] = 1, [isTokenUsed] = 1 where Email = ? and VerificationCode = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, code);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteAccountsDuplicate(String email) {
        String query = "delete from Users where Email = ? and [Status] <> 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateNumberResentToken(String email) {
        String query = "update Users set ResentTokenCount = ResentTokenCount + 1 where Email = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updatePasswordByEmail(String password, String email) {
        String query = "update Users set Password = ? where Email = ? and [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, password);
            pst.setString(2, email);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error updatePasswordByEmail: " + e);
        }
    }

    public List<User> getUserByUid(List<Integer> ids) {
        List<User> list = new ArrayList<>();
        String query = "select UserID, UserName, Email, Image from Users where UserID in (";
        for (Integer id : ids) {
            query += id + ",";
        }
        query = query.substring(0, query.length() - 1) + ")";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int uId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String email = rs.getString("Email");
                String picture = rs.getString("Image");
                User u = new User(uId + "", name, email, picture);
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public User getUserByEmail(String email) {
        String query = "select * from Users u left join Wallet w on w.UserID = u.UserID where Email = ? and [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int uId = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String pass = rs.getString("Password");
                String fullName = rs.getString("FullName");
                String uEmail = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                String picture = rs.getString("Image");
                LocalDateTime createdAt = rs.getTimestamp("createdAt").toLocalDateTime();
                double balance = rs.getDouble("Balance");
                return new User(uId + "", name, pass, uEmail, fullName, phone, address, role, createdAt, picture, balance);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void updateInforUser(int uId, String src) {
        String query = "update Users set Image = ? where UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, src);
            pst.setInt(2, uId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateInforUser(int uId, String userName, String password, String fullName, String phone, String address) {
        String query = "update Users set Username = ?, Password = ?, FullName = ?, Phone = ?, Address = ? where UserID = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, password);
            pst.setString(3, fullName);
            pst.setString(4, phone);
            pst.setString(5, address);
            pst.setInt(6, uId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateInforUser: " + e);
        }
    }

    public int getNumberResentToken(String email) {
        String query = "select ResentTokenCount from Users where Email = ?";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("ResentTokenCount");
            }
        } catch (SQLException e) {
            System.out.println("updateInforUser: " + e);
        }
        return -1;
    }

    public int getTotalUser() {
        String query = "select count(UserID) Total\n"
                + "from Users\n"
                + "where [Status] = 1";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException e) {
            System.out.println("getTotalUser: " + e);
        }
        return -1;
    }

    public List<User> getTop5Customer() {
        List<User> list = new ArrayList<>();
        String query = "select top 5 u.UserID, u.Username, u.FullName, u.[Image], u.Email, sum(o.TotalAmount) TotalSpending\n"
                + "from Users u\n"
                + "join Orders o on o.UserID = u.UserID\n"
                + "group by u.UserID, u.Username, u.FullName, u.[Image], u.Email\n"
                + "order by TotalSpending desc";
        try {
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String name = rs.getString("Username");
                String fullName = rs.getString("FullName");
                String img = rs.getString("Image");
                String email = rs.getString("Email");
                double spending = rs.getDouble("TotalSpending");
                User u = new User(id + "", name, email, fullName, img, spending);
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error getTop5Customer: " + e);
        }
        return list;
    }

    //duy
    public List<User> getUserByName(String value) {
        List<User> list = new ArrayList<>();
        String query = "select * from Users where UserName like '%";
        query += value + "%'";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String fullName = rs.getString("FullName");
                String role = rs.getString("Role");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                boolean status = rs.getBoolean("status");
                String picture = rs.getString("Image");
                User u = new User(id + "", name, password, email, fullName, phone, role, status, picture);
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("getUserByName: " + e);
        }
        return list;
    }

    //duy
    public User getUserId(int UserId) {
        String query = "select * from Users  where UserID = ?";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setInt(1, UserId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String fullName = rs.getString("FullName");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                boolean status = rs.getBoolean("status");
                String picture = rs.getString("Image");
                User u = new User(id + "", name, password, email, fullName, phone, role, status, picture);
                return u;
            }

        } catch (SQLException e) {
            System.out.println("isExistUser: " + e);
        }
        return null;
    }

    //duy
    //duy
    public List<User> getAllUsers(int page, int item) {
        List<User> list = new ArrayList<>();
        String query = "select *\n"
                + "from Users\n"
                + "where [Status] = 1\n"
                + "order by [UserID]\n"
                + "offset ? rows fetch next ? rows only";
        try ( PreparedStatement pst = con.prepareStatement(query);) {
            pst.setInt(1, (page - 1) * item);
            pst.setInt(2, item);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("UserID");
                String name = rs.getString("UserName");
                String fullName = rs.getString("FullName");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String picture = rs.getString("image");
                String phone = rs.getString("Phone");
                String role = rs.getString("Role");
                boolean status = rs.getBoolean("status");
                User u = new User(id + "", name, password, email, fullName, phone, role, status, picture);
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("getAllUsers - Paging: " + e);
        }
        return list;
    }

    public void editUser(User user) {
        String query = "UPDATE Users SET UserName = ?, FullName = ?, Role = ?, Email = ?, Image = ?, Status = ?, Phone = ?, Password = ? WHERE UserID = ?";
        try ( PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, user.getName());
            pst.setString(2, user.getFullName());
            pst.setString(3, user.getRole());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getPicture());
            pst.setBoolean(6, user.isStatus());
            pst.setString(7, user.getPhone());
            pst.setString(8, user.getPassword());

            pst.setInt(9, Integer.parseInt(user.getId()));
            // Execute the update query
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("editUser: " + e);
        }
    }

    //duy
    public void deleteUser(int userId) {
        // Delete wallet
        String query = "delete from Wallet where UserID = ?";

        try ( PreparedStatement pstWallet = con.prepareStatement(query);) {
            pstWallet.setInt(1, userId);
            pstWallet.executeUpdate();

            // Delete review
            query = "delete from Reviews where UserID = ?";
            PreparedStatement pstReview = con.prepareStatement(query);
            pstReview.setInt(1, userId);
            pstReview.executeUpdate();

            // Delete orderdetail
            query = "DELETE od\n"
                    + "FROM OrderDetails od\n"
                    + "JOIN Orders o ON o.OrderID = od.OrderID\n"
                    + "JOIN Users u ON u.UserID = o.UserID\n"
                    + "WHERE u.UserID = ?";
            PreparedStatement pstOrderDetail = con.prepareStatement(query);
            pstOrderDetail.setInt(1, userId);
            pstOrderDetail.executeUpdate();

            // Delete order
            query = "delete from Orders where UserID = ?";
            PreparedStatement pstOrder = con.prepareStatement(query);
            pstOrder.setInt(1, userId);
            pstOrder.executeUpdate();

            // Delete User
            query = "delete from Users where UserID = ?";
            PreparedStatement pstUser = con.prepareStatement(query);
            pstUser.setInt(1, userId);
            pstUser.executeUpdate();

        } catch (SQLException e) {
            System.out.println("deteleUser: " + e);
        }
    }

    public static void main(String[] args) {
        UserDAO uDAO = new UserDAO();
        List<User> list = uDAO.getTop5Customer();
        for (User string : list) {
            System.out.println(string);
        }
    }
}
