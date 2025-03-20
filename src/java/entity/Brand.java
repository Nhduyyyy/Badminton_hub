/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author PC Windows 10
 */
public class Brand {
    private int BrandId;
     private String BrandName;

    public Brand() {
    }

    public Brand(int BrandId, String BrandName) {
        this.BrandId = BrandId;
        this.BrandName = BrandName;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int BrandId) {
        this.BrandId = BrandId;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }

    @Override
    public String toString() {
        return "Brand{" + "BrandId=" + BrandId + ", BrandName=" + BrandName + '}';
    }
     
     
}
