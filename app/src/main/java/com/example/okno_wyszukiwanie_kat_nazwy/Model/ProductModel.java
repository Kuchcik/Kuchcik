package com.example.okno_wyszukiwanie_kat_nazwy.Model;

import cz.msebera.android.httpclient.extras.PRNGFixes;

public class ProductModel {
    private String id;
    private String ProductName;
    private String Quantity;

    public ProductModel (String ProductName, String Quantity,String id) {
        this.ProductName = ProductName;
        this.Quantity = Quantity;
        this.id=id;
    }
    //Get methods


    public String getProductName() {
        return ProductName;
    }

    public String getQuantity() {
        return Quantity;
    }
    public  String getId(){return id;}
    //Set methods
    public void setId(String id){this.id = id;}
    public void setProductName(String ProductName) { this.ProductName = ProductName; }

    public void setQuantity(String Quantity) {
        this.Quantity= Quantity;
    }

}
