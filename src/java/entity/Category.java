/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author PC Windows 10
 */


import java.util.Date;

public class Category {
    private int cId;
    private String cName;
    private Date createdAt;

    public Category() {}

    public Category(int cId, String cName, Date createdAt) {
        this.cId = cId;
        this.cName = cName;
        this.createdAt = createdAt;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Category{" + "cId=" + cId + ", cName=" + cName + ", createdAt=" + createdAt + '}';
    }
   
}
