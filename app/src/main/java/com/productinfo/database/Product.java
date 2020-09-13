package com.productinfo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "productName")
    public String productName;
    @ColumnInfo(name = "productDesc")
    public String productDesc;
    @ColumnInfo(name = "productQty")
    public int productQty;
    @ColumnInfo(name = "productPrice")
    public float productPrice;
    @ColumnInfo(name = "mobileNumber")
    public String mobileNumber = "9876543210";

    @Ignore
    public Product(String productName, String productDesc, int productQty, float productPrice){
        this.productName = productName;
        this.productDesc = productDesc;
        this.productQty = productQty;
        this.productPrice = productPrice;
    }


    public Product(String productName, String productDesc, int productQty, float productPrice, String mobileNumber){
        this.productName = productName;
        this.productDesc = productDesc;
        this.productQty = productQty;
        this.productPrice = productPrice;
        this.mobileNumber = mobileNumber;
    }

}
