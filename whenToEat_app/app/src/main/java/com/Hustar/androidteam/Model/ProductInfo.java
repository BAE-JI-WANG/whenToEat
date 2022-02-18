package com.Hustar.androidteam.Model;

public class ProductInfo {

    private String custid;
    private String barcode;
    private String product_name;
    private String caution;
    private String productimg;
    private String expiration_date;

    public ProductInfo(String custid, String barcode, String product_name, String caution, String productimg, String expiration_date) {
        this.custid = custid;
        this.barcode = barcode;
        this.product_name = product_name;
        this.caution = caution;
        this.productimg = productimg;
        this.expiration_date = expiration_date;
    }

    public ProductInfo() {
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }
}
