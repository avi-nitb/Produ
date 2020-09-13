package com.productinfo.network;

import com.google.gson.JsonArray;
import com.productinfo.database.Product;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductAPI {
    @POST("addNewProduct")
    Call<JSONArray> postProducts(@Body JSONArray product);
}
