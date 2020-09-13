package com.productinfo.database;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.productinfo.dao.ProductDao;

import java.lang.ref.WeakReference;

public final class DbTask extends AsyncTask {

    private WeakReference<Activity> activityWeakReference;
    private Product product;
    private Context context;

    public DbTask(Activity activity, Product product, Context context){
        activityWeakReference = new WeakReference<>(activity);
        this.product = product;
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        ProductDao productDao = DatabaseHelper.getInstance(context).productDao();
        productDao.insertProduct(product);
        return true;
    }
}
