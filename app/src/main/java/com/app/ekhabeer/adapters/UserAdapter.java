package com.app.ekhabeer.adapters;

import android.content.Context;
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
import com.app.ekhabeer.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
//    private ArrayList<HashMap<String,String>> listdata;
    private ArrayList<String> listdata;
    private Context context;
    String type;

    // RecyclerView recyclerView;
    public UserAdapter(ArrayList<String> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.messages_item, parent, false);
        UserAdapter.ViewHolder viewHolder = new UserAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, final int position) {

        Log.e("checkArrayMEssg","onBindViewHolder>>" + listdata.size());

            holder.tv_user_desc.setText(listdata.get(position));
//            holder.tv_user_desc.setText(listdata.get(position).get(Constants.MESSAGE));
//            holder.tv_user_date.setText(listdata.get(position).get(Constants.MESSAGE));
//            holder.tv_user_name.setText(listdata.get(position).get(Constants.MESSAGE));

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user_desc,tv_user_date,tv_user_name;
        public ImageView iv_user;

        public ViewHolder(View itemView) {
            super(itemView);

            this.iv_user = (ImageView) itemView.findViewById(R.id.iv_user);
            this.tv_user_desc = (TextView) itemView.findViewById(R.id.tv_user_desc);
            this.tv_user_date = (TextView) itemView.findViewById(R.id.tv_user_date);
            this.tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
        }
    }
}

