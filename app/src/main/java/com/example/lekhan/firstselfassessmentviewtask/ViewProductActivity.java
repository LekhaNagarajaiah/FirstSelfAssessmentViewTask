package com.example.lekhan.firstselfassessmentviewtask;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewProductActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_view_product_list)
    RecyclerView gProductListRecyclerView;
    ViewProductAdapter  gViewProductAdapter;

    ArrayList<ViewProductsResponse> gProductArrayList;
    LinearLayoutManager layoutmanager;
    private Dialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.view_product_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setTitle("Product Names");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        progress = new Dialog(ViewProductActivity.this, android.R.style.Theme_Translucent);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress.setContentView(R.layout.progress_dialog);
        progress.setCancelable(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                }
        });


        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        gProductListRecyclerView.setLayoutManager(layoutmanager);
        ViewProductList();
    }

    public void ViewProductList(){
        progress.show();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://surebotdemo.co/IMS_API/api/IMS_CI/")
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient request = retrofit.create(ApiClient.class);
        String Username = "admin";
        String Password = "1234";

        Call<ViewProductDetailsResponse> call=request.GetProductDetails();
        call.enqueue(new Callback<ViewProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewProductDetailsResponse> call, Response<ViewProductDetailsResponse> response) {
                System.out.println("Entering onResponse");
            if(response.isSuccessful()){
                progress.dismiss();
                System.out.println("Entering Successfull message");
                ViewProductDetailsResponse res=response.body();
                gProductArrayList=new ArrayList<>(Arrays.asList(res.getRecords()));
                System.out.println("Entering Arraylist inside"+gProductArrayList);
                gViewProductAdapter = new ViewProductAdapter(ViewProductActivity.this,gProductArrayList);
                gProductListRecyclerView.setAdapter(gViewProductAdapter);
             }
            }

            @Override
            public void onFailure(Call<ViewProductDetailsResponse> call, Throwable t) {
                Toast.makeText(ViewProductActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });




    }

}
