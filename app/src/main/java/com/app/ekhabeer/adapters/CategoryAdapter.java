package com.app.ekhabeer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.screens.DoctorActivity;
import com.app.ekhabeer.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private ArrayList<HashMap<String,String>> listdata;
    private Context context;
    String type;

    // RecyclerView recyclerView;
    public CategoryAdapter(String type, ArrayList<HashMap<String,String>> listdata, Context context) {
        this.type = type;
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.cat_item, parent, false);
        CategoryAdapter.ViewHolder viewHolder = new CategoryAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, final int position) {
        holder.tv_cat_title.setText(listdata.get(position).get(Constants.NAME));

        switch(type){

            case "Medical":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionOne));
                break;
            case "Psycology":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorGreenPsy));
                break;
            case "Diet and Fitness":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionThree));
                break;
            case "Family and Kids":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionFour));
                break;
            case "Bussiness and investements":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionFive));
                break;
            case "Law & Legislation":
                holder.rl_cat.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionSix));
                break;
        }

        holder.rl_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorActivity.class);
                intent.putExtra("sub_cat_id",listdata.get(position).get(Constants.ID));
                intent.putExtra("type",type);
                Log.e("checkDocFlow","setOnClickListener>>" + listdata.get(position).get(Constants.ID));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_cat_title;
        public RelativeLayout rl_cat;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_cat_title = (TextView) itemView.findViewById(R.id.tv_cat_title);
            this.rl_cat = (RelativeLayout) itemView.findViewById(R.id.rl_cat);
        }
    }
}

