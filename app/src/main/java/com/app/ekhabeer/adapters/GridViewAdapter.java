package com.app.ekhabeer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<String> amenitiesLabelList = null;

    private LayoutInflater layoutInflater;
    private RelativeLayout ll_grid_item;
    private TextView tv_grid_time;

    // Constructor
    public GridViewAdapter(Context c,ArrayList<String> amenitiesLabelList) {
        this.mContext = c;
        this.amenitiesLabelList = amenitiesLabelList;
    }

    public int getCount() {
        Log.e("checkFianll","getCount>>" + amenitiesLabelList.size());
        return amenitiesLabelList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e("checkFianll","getView>>" + amenitiesLabelList.get(position));

        if (layoutInflater==null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
        }
        tv_grid_time = convertView.findViewById(R.id.tv_grid_time);
        ll_grid_item = convertView.findViewById(R.id.ll_grid_item);
        tv_grid_time.setText(amenitiesLabelList.get(position));
        return convertView;

    }

    // Keep all Images in array
//    public Integer[] mThumbIds = {
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7,
//            R.drawable.sample_0, R.drawable.sample_1,
//            R.drawable.sample_2, R.drawable.sample_3,
//            R.drawable.sample_4, R.drawable.sample_5,
//            R.drawable.sample_6, R.drawable.sample_7
//    };
}

