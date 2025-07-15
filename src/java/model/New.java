/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class New {
    private int nId;
    private String header;
    private String headerInfo;
    private String img;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ArrayList items = new ArrayList();

    public New(int nId, String header, String headerInfo, String img, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.nId = nId;
        this.header = header;
        this.headerInfo = headerInfo;
        this.img = img;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getnId() {
        return nId;
    }

    public void setnId(int nId) {
        this.nId = nId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate.toLocalDate().toString();
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "New{" + "nId=" + nId + ", header=" + header + ", headerInfo=" + headerInfo + ", img=" + img + ", content=" + content + ", startDate=" + startDate + ", endDate=" + endDate + '}';
    }
    
    
}
