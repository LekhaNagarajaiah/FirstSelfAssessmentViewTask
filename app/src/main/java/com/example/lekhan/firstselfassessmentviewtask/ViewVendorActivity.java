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
import android.widget.Toast;

import com.example.lekhan.firstselfassessmentviewtask.Adapter.ViewVendorAdapter;
import com.example.lekhan.firstselfassessmentviewtask.Apiclient.ApiClient;
import com.example.lekhan.firstselfassessmentviewtask.Response.ViewVendorDetailsResponse;
import com.example.lekhan.firstselfassessmentviewtask.Response.ViewVendorResponse;

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

public class ViewVendorActivity extends AppCompatActivity {
    @BindView(R.id.recyclerview_view_vendor_list)
    RecyclerView gVendorListRecyclerView;
    LinearLayoutManager grecyclerviewlayoutmanager;
    ArrayList<ViewVendorResponse> gProductArrayList;

    ViewVendorAdapter gViewvendorAdapter;
    private Dialog gprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vendor);
        ButterKnife.bind(this);

        Toolbar ltoolbar = (Toolbar) findViewById(R.id.view_vendor_toolbar);
        setSupportActionBar(ltoolbar);
        ltoolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setTitle("Vendor Names");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ltoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                }
        });

        gprogress = new Dialog(ViewVendorActivity.this, android.R.style.Theme_Translucent);
        gprogress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        gprogress.setContentView(R.layout.progress_dialog);
        gprogress.setCancelable(true);

        grecyclerviewlayoutmanager = new LinearLayoutManager(this);
        grecyclerviewlayoutmanager.setOrientation(LinearLayoutManager.VERTICAL);
        gVendorListRecyclerView.setLayoutManager(grecyclerviewlayoutmanager);

        ViewVendorList();

    }

    public void ViewVendorList(){
        gprogress.show();
        try{
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://surebotdemo.co/IMS_API/api/IMS_CI/")
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClient lrequest = retrofit.create(ApiClient.class);


        Call<ViewVendorDetailsResponse> call=lrequest.GetVendorDetails();
        call.enqueue(new Callback<ViewVendorDetailsResponse>() {
            @Override
            public void onResponse(Call<ViewVendorDetailsResponse> call, Response<ViewVendorDetailsResponse> response) {

                if(response.isSuccessful()){
                    gprogress.dismiss();

                    ViewVendorDetailsResponse res=response.body();
                    gProductArrayList=new ArrayList<>(Arrays.asList(res.getRecords()));

                    gViewvendorAdapter = new ViewVendorAdapter(ViewVendorActivity.this,gProductArrayList);
                    gVendorListRecyclerView.setAdapter(gViewvendorAdapter);
                }
            }

            @Override
            public void onFailure(Call<ViewVendorDetailsResponse> call, Throwable t) {
                Toast.makeText(ViewVendorActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
      }
            catch (Exception e) {
            e.printStackTrace();
        }


    }
}
