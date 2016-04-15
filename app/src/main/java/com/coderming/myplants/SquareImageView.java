package com.coderming.myplants;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by linna on 4/14/2016.
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }
    public SquareImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min( getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);          //Snap to smallest size
    }}
