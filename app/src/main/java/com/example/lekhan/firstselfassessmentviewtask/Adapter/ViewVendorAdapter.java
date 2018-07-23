package com.example.lekhan.firstselfassessmentviewtask.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lekhan.firstselfassessmentviewtask.R;
import com.example.lekhan.firstselfassessmentviewtask.Response.ViewVendorResponse;

import java.util.ArrayList;

public class ViewVendorAdapter  extends RecyclerView.Adapter<ViewVendorAdapter.MyViewHolder> {
    public Context gContext;
    ArrayList<ViewVendorResponse> mArrayList;
    public ViewVendorAdapter(Context context, ArrayList<ViewVendorResponse> mArrayList) {
        this.mArrayList = mArrayList;
        this.gContext = context;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_vendor_list, parent, false);
        return new ViewVendorAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.gVendorName.setText(mArrayList.get(position).getVendor_name());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gVendorName;
        public MyViewHolder(View itemView) {
            super(itemView);
            gVendorName = (TextView) itemView.findViewById(R.id.vendorname);
        }
    }
}
