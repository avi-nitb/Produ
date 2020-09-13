package com.productinfo.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.productinfo.R;
import com.productinfo.database.DatabaseHelper;
import com.productinfo.database.DbTask;
import com.productinfo.database.Product;

import java.time.Duration;

public class AddProduct extends AppCompatActivity {
    EditText editTextProductName, editTextProductDesc, editTextProductQty, editTextProductPrice;
    Button buttonSave;
    String productName, productDesc, productQty, productPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addproduct);
        final DatabaseHelper databaseHelper = DatabaseHelper.getInstance(getApplicationContext());

        getSupportActionBar().setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializing Views
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDesc = findViewById(R.id.editTextProductDesc);
        editTextProductQty = findViewById(R.id.editTextProductQty);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validations
                if (editTextProductName.getText().toString().isEmpty()){
                    editTextProductName.setError("Please fill product name");
                } else {
                    productName = editTextProductName.getText().toString().trim();
                    editTextProductName.setError(null);
                }

                if (editTextProductDesc.getText().toString().isEmpty()){
                    editTextProductDesc.setError("Please fill product description");
                } else {
                    productDesc = editTextProductDesc.getText().toString().trim();
                    editTextProductDesc.setError(null);
                }

                if (editTextProductQty.getText().toString().isEmpty()){
                    editTextProductQty.setError("Please fill product quantity");
                } else {
                    productQty = editTextProductQty.getText().toString().trim();
                    editTextProductQty.setError(null);
                }

                if (editTextProductPrice.getText().toString().isEmpty()){
                    editTextProductPrice.setError("Please fill product price");
                } else {
                    productPrice = editTextProductPrice.getText().toString().trim();
                    editTextProductPrice.setError(null);
                }
                //Inserting Product to table
                Product product = new Product(productName, productDesc,Integer.parseInt(productQty)
                        , Float.parseFloat(productPrice));
                DbTask insertTask = new DbTask(AddProduct.this, product, AddProduct.this);
                insertTask.execute();
                editTextProductName.setText("");
                editTextProductDesc.setText("");
                editTextProductQty.setText("");
                editTextProductPrice.setText("");
                Toast.makeText(AddProduct.this, "Product Saved Successfully",Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent =(new Intent(AddProduct.this, MainActivity.class));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
