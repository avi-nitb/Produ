package com.productinfo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.productinfo.R;
import com.productinfo.database.DatabaseHelper;
import com.productinfo.database.Product;
import com.productinfo.network.ProductAPI;
import com.productinfo.network.RetrofitClientInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonAddProduct, buttonSyncProducts;
    List<Product> productList =new ArrayList<>();
    JSONArray requestArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());
        final ProductAPI retrofitInstance = RetrofitClientInstance.getRetrofitInstance()
                .create(ProductAPI.class);
        //Initializing Views
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonSyncProducts = findViewById(R.id.buttonSyncProducts);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddProduct.class);
                startActivity(intent);
            }
        });

        buttonSyncProducts.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                productList.clear();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        productList.addAll(databaseHelper.productDao().getProducts());
                        Log.d("Data", productList.toString());
                        if (!productList.isEmpty()){
                            prepareList();
                            Call<JSONArray> postCall = retrofitInstance.postProducts(requestArray);
                            postCall.enqueue(new Callback<JSONArray>() {
                                @Override
                                public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                                    AsyncTask.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            databaseHelper.productDao().deleteProducts(productList);
                                            productList.clear();
                                            Log.d("Updated Table", databaseHelper.productDao().getProducts().toString());
                                        }
                                    });

                                    Toast.makeText(MainActivity.this, "Sync Successfull", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<JSONArray> call, Throwable t) {
                                    Toast.makeText(MainActivity.this, "Sync Failed", Toast.LENGTH_LONG).show();

                                }
                            });
                        } else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Please save products before syncing"
                                            , Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void prepareList() {
        requestArray = new JSONArray();
        for(int i =0; i<productList.size(); i++){
            JSONObject requestObject = new JSONObject();
            try {
                requestObject.put("product_name", productList.get(i).productName);
                requestObject.put("product_desc",  productList.get(i).productDesc);
                requestObject.put("product_quantity",  productList.get(i).productQty);
                requestObject.put("product_price",  productList.get(i).productPrice);
                requestObject.put("user_mobile_no",  productList.get(i).mobileNumber);
                requestArray.put(requestObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}