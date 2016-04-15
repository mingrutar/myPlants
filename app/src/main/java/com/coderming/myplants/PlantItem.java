package com.coderming.myplants;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by linna on 4/14/2016.
 */
public class PlantItem {
    private static final String LOG_TAG =  PlantItem.class.getSimpleName();
    private long id;
    private String mCommonName;
    private String mScientificName;
    private String mImageFilename;
    private String mDesciption;

    Drawable mDrawable;

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

    public Uri getImageUri() {
        File imageFile = new File( mImageFilename);
        if ((imageFile.exists())) {
            Log.v(LOG_TAG, String.format("file %s exist", mImageFilename));
            return Uri.fromFile(imageFile);
        } else {
            return null;
        }
    }
    public String getImageFilename() {
        return mImageFilename;
    }

    public String getScientificName() {
        return mScientificName;
    }
}
