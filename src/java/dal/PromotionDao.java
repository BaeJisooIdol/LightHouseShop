/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Promotion;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
public class PromotionDao extends DBContext {

    public List<Promotion> getAll() {

        List<Promotion> list = new ArrayList<>();
        String query = "SELECT * FROM PROMOTIONS";
        try ( ResultSet rs = execSelectQuery(query)) {
            while (rs.next()) {
                int idPromotion = rs.getInt(1);
                String namePromotion = rs.getString(2);
                String descriptionPromotion = rs.getString(3);
                double discountPercentPromotion = rs.getDouble(4);
                String startDatePromotion = rs.getDate(5) + "";
                String endDatePromotion = rs.getDate(6) + "";
                boolean statusPromotion = rs.getBoolean(7);
                Promotion promotion = new Promotion(idPromotion, namePromotion, descriptionPromotion, discountPercentPromotion, startDatePromotion, endDatePromotion, statusPromotion);
                list.add(promotion);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PromotionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public static void main(String[] args) {
        PromotionDao dAO = new PromotionDao();
        for (Object object : dAO.getAll()) {
            System.out.println(object);
        }
    }

}
