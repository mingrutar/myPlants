package com.coderming.myplants;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by linna on 4/14/2016.
 */
public class PlantItem implements Serializable {
    private static final String LOG_TAG =  PlantItem.class.getSimpleName();
    private transient long id;
    public String mCommonName;
    public String mScientificName;
    public String mImageFilename;
    public String mDesciption;

    transient Bitmap mDrawable;

    public PlantItem() {}
    public PlantItem( long id, String imagePath,  String commonName, String scientificName, String desciption) {
        mCommonName = commonName;
        mScientificName = scientificName;
        mImageFilename = imagePath;
        mDesciption = desciption;
        this.id = id;
    }

    public String getTitle() {
        //TODO:  check preference
        return mCommonName;
    }
    public String getCommonName() {
        return mCommonName;
    }
    public long getId() {
        return id;
    }

    public String getDesciption() {
        return mDesciption;
    }

    public String getImageFilename() {
        return mImageFilename;
    }

    public String getScientificName() {
        return mScientificName;
    }
}
