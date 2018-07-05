package com.example.lekhan.firstselfassessmentviewtask;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiClient {
    //Get Product Details
    @GET("getProduct")
    Call<ViewProductDetailsResponse> GetProductDetails();

    //Get Vendor Details
    @GET("getVendor")
    Call<ViewVendorDetailsResponse> GetVendorDetails();
}
