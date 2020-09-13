package com.productinfo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.productinfo.database.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("Select * from products")
    List<Product> getProducts();

    @Insert
    void insertProduct(Product product);

    @Delete
    void deleteProducts(List<Product> productList);
}
