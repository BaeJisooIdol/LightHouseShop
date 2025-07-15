/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
public class Promotion {

    private int promotionID;
    private String promotionName;
    private String discription;
    private double discountPercent;
    private String startDate;
    private String endDate;
    private boolean status;

    public Promotion(int promotionID, String promotionName, String discription, double discountPercent, String startDate, String endDate, boolean status) {
        this.promotionID = promotionID;
        this.promotionName = promotionName;
        this.discription = discription;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "Promotion{" + "promotionID=" + promotionID + ", promotionName=" + promotionName + ", discription=" + discription + ", discountPercent=" + discountPercent + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + '}';
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
