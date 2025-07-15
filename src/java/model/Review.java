/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author admin
 */
public class Review {
    private int rId;
    private int pId;
    private int uId;
    private String img;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private String date;

    public Review(int rId, int pId, int uId, int rating, String comment, LocalDate reviewDate) {
        this.rId = rId;
        this.pId = pId;
        this.uId = uId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Review(int rId, String img, int rating, String comment, String date) {
        this.rId = rId;
        this.img = img;
        this.rating = rating;
        this.comment = comment;
        this.date = date;
    }

    
    

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" + "rId=" + rId + ", pId=" + pId + ", uId=" + uId + ", rating=" + rating + ", comment=" + comment + ", reviewDate=" + reviewDate + '}';
    }
    
}
