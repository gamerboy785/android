package com.example.alishahid.androidtraining.controller;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.alishahid.androidtraining.R;
import com.example.alishahid.androidtraining.adapter.MyAdapter;
import com.example.alishahid.androidtraining.api.Service;
import com.example.alishahid.androidtraining.model.Item;
import com.example.alishahid.androidtraining.model.ListItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private static final String BASE_URL = "https://api.github.com";
    private static Call<ListItem> call;
    private static List<Item> items;
    private static String TAG = "TAG";

    private ProgressDialog progressDialog;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching users from GitHub");
        progressDialog.setCancelable(false);
        progressDialog.show();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                recyclerView.scrollToPosition(0);
                swipeRefreshLayout.setRefreshing(false);
            }

        });

        //initiate retrofit object
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        //get service
        Service service = retrofit.create(Service.class);

        call = service.getList();
        call.enqueue(new Callback<ListItem>()
        {
            @Override
            public void onResponse(Call<ListItem> call, Response<ListItem> response)
            {
                if(response.body() == null)
                {
                    Log.d(TAG,"call returned empty response");
                    return;
                }

                items = response.body().getItems();
                recyclerView.scrollToPosition(0);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new MyAdapter(items,MainActivity.this));
                progressDialog.hide();
                //Log.d(TAG,String.valueOf(items.size()));



            }

            @Override
            public void onFailure(Call<ListItem> call, Throwable t)
            {
                Log.d(TAG,"onFailure()");
            }

        });


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        call.cancel(); //cancel the call to rest api

    }

}
