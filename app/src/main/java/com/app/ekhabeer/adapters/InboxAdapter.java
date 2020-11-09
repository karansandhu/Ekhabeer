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
import com.app.ekhabeer.screens.DoctorDetailsActivity;
import com.app.ekhabeer.screens.MessagesActivity;
import com.app.ekhabeer.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder>{
    private ArrayList<HashMap<String, String>> listdata;
    private Context context;
    private JSONArray jsonArray;
    String type;

    // RecyclerView recyclerView;
    public InboxAdapter(JSONArray jsonArray,ArrayList<HashMap<String, String>> listdata, Context context) {
        this.jsonArray = jsonArray;
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.inbox_item, parent, false);
        InboxAdapter.ViewHolder viewHolder = new InboxAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InboxAdapter.ViewHolder holder, final int position) {
//        holder.tv_inbox_spec.setText(listdata.get(position).get(Constants.MESSAGE));
//        holder.tv_inbox_desc.setText(listdata.get(position).get(Constants.SPECIAL));

        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_placeholder);
            holder.tv_inbox_title.setText(listdata.get(position).get(Constants.FNAME) + " " + listdata.get(position).get(Constants.LNAME));
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Constants.IMAGE_BASE_URL + listdata.get(position).get(Constants.PROFILE))
                    .into(holder.iv_doc);

            holder.rl_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MessagesActivity.class);
                    intent.putExtra("act","Iadapter");
                    intent.putExtra("api_user_id", listdata.get(position).get(Constants.ID));
//                intent.putExtra("consultant_id",listdata.get(position).get(Constants.ID));
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_inbox_spec,tv_inbox_desc,tv_inbox_title;
        public ImageView iv_doc;
        public RelativeLayout rl_doc;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tv_inbox_spec = (TextView) itemView.findViewById(R.id.tv_inbox_spec);
            this.tv_inbox_desc = (TextView) itemView.findViewById(R.id.tv_inbox_desc);
            this.tv_inbox_title = (TextView) itemView.findViewById(R.id.tv_inbox_title);
            this.iv_doc = (ImageView) itemView.findViewById(R.id.iv_doc);
            this.rl_doc = (RelativeLayout) itemView.findViewById(R.id.rl_doc);
        }
    }
}

