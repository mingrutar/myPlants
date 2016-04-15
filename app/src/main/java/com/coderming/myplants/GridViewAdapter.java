package com.coderming.myplants;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by linna on 4/14/2016.
 */
public class GridViewAdapter extends ArrayAdapter {
    private static final String LOG_TAG = GridViewAdapter.class.getSimpleName();

//    List<PlantItem> data;
    private Context context;
    private int layoutResourceId;

    public GridViewAdapter(Context context, int layoutResourceId, List<PlantItem> list ) {
        super(context,  layoutResourceId);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        for (PlantItem item : list) {
            item.mDrawable = getDrawableImage(item.getImageFilename());
        }
        super.addAll(list);
    }

    private Drawable getDrawableImage(String imageFilename) {
        InputStream is =  null;
        try {
            is = context.getAssets().open(imageFilename);
            return Drawable.createFromStream(is, null);
        } catch (IOException ex) {
            if (is != null) {
                try { is.close();} catch (Exception e) { }
            }
            Log.w(LOG_TAG, "default image error", ex);
        }
        return null;
    }


    @Override
    public PlantItem getItem(int position) {
        return (PlantItem) super.getItem(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CustomerViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new CustomerViewHolder(row);
            holder.mImageView = (ImageView) row.findViewById(R.id.imageView2);
            row.setTag(holder);
        } else {
            holder = (CustomerViewHolder) row.getTag();
        }

        PlantItem item = this.getItem(position);
        holder.mImageView.setImageDrawable(item.mDrawable);
        holder.mTextView.setText(item.getTitle());
        return row;
    }

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView2);
            mTextView = (TextView) itemView.findViewById((R.id.textView));
        }
    }
}
