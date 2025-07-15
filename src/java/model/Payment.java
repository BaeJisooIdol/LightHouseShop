/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Pham Quoc Tu - CE181513
 */
public class Payment {

    private int paymentID;
    private String paymentMethod;
    private String description;

    public Payment(int paymentID, String paymentMethod, String description) {
        this.paymentID = paymentID;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Payment{");
        sb.append("paymentID=").append(paymentID);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", description=").append(description);
        sb.append('}');
        return sb.toString();
    }

}
