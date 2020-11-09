package com.app.ekhabeer.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.app.ekhabeer.utils.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{
//    private ArrayList<HashMap<String,String>> listdata;
    private ArrayList<Model> listdata;
    private Context context;
    String type;

    // RecyclerView recyclerView;
    public MessagesAdapter(ArrayList<Model> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.messages_item, parent, false);
        MessagesAdapter.ViewHolder viewHolder = new MessagesAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder holder, final int position) {
        SharedPreferences prefs = context.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        Log.e("checkArrayMEssg","onBindViewHolder>>" + listdata.size() + ">>" + prefs.getString("user_id",""));

        if (listdata.get(position).getSender_id().equals(prefs.getString("user_id",""))){

            Log.e("checkArrayMEssg","onBindViewHolder>IF>" + listdata.size());
            holder.rl_right_broadcast.setVisibility(View.VISIBLE);
            holder.rl_left_broadcast.setVisibility(View.GONE);
            holder.tv_chat_name_right.setText(listdata.get(position).getMessage());
            holder.tv_chat_msg_right.setText(listdata.get(position).getTime());

        }else{

            Log.e("checkArrayMEssg","onBindViewHolder>ELSE>" + listdata.size());
            holder.rl_right_broadcast.setVisibility(View.GONE);
            holder.rl_left_broadcast.setVisibility(View.VISIBLE);
            holder.tv_chat_left_name.setText(listdata.get(position).getMessage());
            holder.tv_chat_left_msg.setText(listdata.get(position).getTime());

        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_chat_left_name,tv_chat_left_msg,tv_chat_msg_right,tv_chat_name_right;
        public RelativeLayout rl_right_broadcast,rl_left_broadcast;

        public ViewHolder(View itemView) {
            super(itemView);

            this.rl_right_broadcast = (RelativeLayout) itemView.findViewById(R.id.rl_right_broadcast);
            this.rl_left_broadcast = (RelativeLayout) itemView.findViewById(R.id.rl_left_broadcast);
            this.tv_chat_name_right = (TextView) itemView.findViewById(R.id.tv_chat_name_right);
            this.tv_chat_msg_right = (TextView) itemView.findViewById(R.id.tv_chat_msg_right);
            this.tv_chat_left_msg = (TextView) itemView.findViewById(R.id.tv_chat_left_msg);
            this.tv_chat_left_name = (TextView) itemView.findViewById(R.id.tv_chat_left_name);
        }
    }
}

