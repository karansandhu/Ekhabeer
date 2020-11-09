package com.app.ekhabeer.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
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
import com.app.ekhabeer.screens.MessagesActivity;
import com.app.ekhabeer.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class ConsultationsAdapter extends RecyclerView.Adapter<ConsultationsAdapter.ViewHolder>{
    private ArrayList<HashMap<String,String>> listdata;
    private Context context;

    // RecyclerView recyclerView;
    public ConsultationsAdapter(ArrayList<HashMap<String,String>> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public ConsultationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.consultations_item, parent, false);
        ConsultationsAdapter.ViewHolder viewHolder = new ConsultationsAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConsultationsAdapter.ViewHolder holder, final int position) {
        holder.tv_consul_name.setText(listdata.get(position).get(Constants.FNAME) + " " + listdata.get(position).get(Constants.LNAME));
        holder.tv_consul_email.setText(listdata.get(position).get(Constants.EMAIL));
        holder.tv_consul_spec.setText(listdata.get(position).get(Constants.DAYS));
        holder.tv_consul_time.setText(listdata.get(position).get(Constants.FROM_TIME));
        holder.tv_consul_city.setText(listdata.get(position).get(Constants.CITY));
        holder.tv_consul_fee.setText("$" + listdata.get(position).get(Constants.FEE));
        holder.tv_consul_date.setText(getDate(Long.parseLong(listdata.get(position).get(Constants.DATE))));

//        Log.e("checkdate",">>" + getDate(Long.parseLong(listdata.get(position).get(Constants.DATE))));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(Constants.IMAGE_BASE_URL + listdata.get(position).get(Constants.PROFILE))
                .into(holder.iv_consul);


        holder.iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessagesActivity.class);
                intent.putExtra("act","Cadapter");
                intent.putExtra("receiver_id",listdata.get(position).get(Constants.CONSULTANT_ID));
//                intent.putExtra("type",type);
                Log.e("checkDocFlow","setOnClickListener>>" + listdata.get(position).get(Constants.CONSULTANT_ID));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_consul_name,tv_consul_email,tv_consul_spec,tv_consul_time,tv_consul_date,tv_consul_city,tv_consul_fee,tv_consul_desc;
        public RelativeLayout rl_cat;
        public ImageView iv_consul,iv_chat;

        public ViewHolder(View itemView) {
            super(itemView);

            this.iv_consul = (ImageView) itemView.findViewById(R.id.iv_consul);
            this.iv_chat = (ImageView) itemView.findViewById(R.id.iv_chat);
            this.tv_consul_name = (TextView) itemView.findViewById(R.id.tv_consul_name);
            this.tv_consul_email = (TextView) itemView.findViewById(R.id.tv_consul_email);
            this.tv_consul_spec = (TextView) itemView.findViewById(R.id.tv_consul_spec);
            this.tv_consul_time = (TextView) itemView.findViewById(R.id.tv_consul_time);
            this.tv_consul_date = (TextView) itemView.findViewById(R.id.tv_consul_date);
            this.tv_consul_city = (TextView) itemView.findViewById(R.id.tv_consul_city);
            this.tv_consul_fee = (TextView) itemView.findViewById(R.id.tv_consul_fee);
            this.tv_consul_desc = (TextView) itemView.findViewById(R.id.tv_consul_desc);
            this.rl_cat = (RelativeLayout) itemView.findViewById(R.id.rl_cat);
        }
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MMM-yyyy", cal).toString();
        return date;
    }
}

