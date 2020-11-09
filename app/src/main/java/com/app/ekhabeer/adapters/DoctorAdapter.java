package com.app.ekhabeer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.screens.DoctorDetailsActivity;
import com.app.ekhabeer.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder>{
    private ArrayList<HashMap<String, String>> listdata;
    private Context context;
    String type;

    // RecyclerView recyclerView;
    public DoctorAdapter(String type,ArrayList<HashMap<String, String>> listdata, Context context) {
        this.type = type;
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.doc_item, parent, false);
        DoctorAdapter.ViewHolder viewHolder = new DoctorAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DoctorAdapter.ViewHolder holder, final int position) {
        holder.tv_doc_title.setText(listdata.get(position).get(Constants.FNAME));
        holder.tv_doc_spec.setText(listdata.get(position).get(Constants.SPECIAL));
        holder.tv_doc_address.setText(listdata.get(position).get(Constants.ADDRESS));
        holder.tv_doc_rating.setText("4.2");

        Glide.with(context).load(Constants.IMAGE_BASE_URL + listdata.get(position).get(Constants.PROFILE)).into(holder.iv_doc);

        switch(type){

            case "Medical":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionOne));
                break;
            case "Psycology":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorGreenPsy));
                break;
            case "Diet and Fitness":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionThree));
                break;
            case "Family and Kids":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionFour));
                break;
            case "Bussiness and investements":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionFive));
                break;
            case "Law & Legislation":
                holder.rl_doc.setBackgroundColor(context.getResources().getColor(R.color.colorHomeOptionSix));
                break;
        }

        holder.rl_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetailsActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("consultant_id",listdata.get(position).get(Constants.ID));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_doc_title,tv_doc_spec,tv_doc_address,tv_doc_rating;
        public RelativeLayout rl_doc;
        public ImageView iv_doc;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_doc_rating = (TextView) itemView.findViewById(R.id.tv_doc_rating);
            this.tv_doc_title = (TextView) itemView.findViewById(R.id.tv_doc_title);
            this.tv_doc_address = (TextView) itemView.findViewById(R.id.tv_doc_address);
            this.tv_doc_spec = (TextView) itemView.findViewById(R.id.tv_doc_spec);
            this.iv_doc = (ImageView) itemView.findViewById(R.id.iv_doc);
            this.rl_doc = (RelativeLayout) itemView.findViewById(R.id.rl_doc);
        }
    }
}

