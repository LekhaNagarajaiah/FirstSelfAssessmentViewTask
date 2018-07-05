package com.example.lekhan.firstselfassessmentviewtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewProductAdapter extends RecyclerView.Adapter<ViewProductAdapter.MyViewHolder> {
    ArrayList<ViewProductsResponse> mArrayList;
    public Context gContext;
    public ViewProductAdapter(Context context, ArrayList<ViewProductsResponse> mArrayList) {
        this.mArrayList = mArrayList;
        this.gContext = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_product_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.gProductName.setText(mArrayList.get(position).getProduct_name());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gProductName;
        public MyViewHolder(View itemView) {
            super(itemView);
            gProductName = (TextView) itemView.findViewById(R.id.productname);

        }
    }
}
