package com.coderming.myplants;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    static final String plantFile = "plant4tests.json";

    static final String plants_tag = "plants";
    static final String ommon_tag = "ommon";
    static final String  scientific_tag = "scientific";
    static final String  genus_tag = "genus";
    static final String  family_tag = "family";
    static final String  description_tag = "description";
    static final String  photo_tag = "photo";

    String imagePath = "/mnt/sdcard/Photos/";           // Environment.getExternalStorageDirectory()+ "/Photos/";

    private List<PlantItem> mPlantList;
    private GridViewAdapter mAdapter;
    private GridView mGridView;

    private List<PlantItem> loadJsonPlants(AssetManager assetManager) {
        List<PlantItem> ret = new ArrayList<>();
        InputStream is = null;
        try {
            is = assetManager.open(plantFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject obj = new JSONObject(json);
            JSONArray m_jArry = obj.getJSONArray(plants_tag);
            PlantItem plant = null;
            for (int i = 0; i < m_jArry.length(); i++) {
                plant = new PlantItem();
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                plant.mCommonName = jo_inside.getString(ommon_tag);
                plant.mScientificName = jo_inside.getString(scientific_tag);
                plant.mGenus = jo_inside.getString(genus_tag);
                plant.mFamily = jo_inside.getString(family_tag);
                plant.mDesciption = jo_inside.getString(description_tag);
                plant.mImageFilename = jo_inside.getString(photo_tag);
                Log.v(LOG_TAG, plant.toString());
                ret.add(plant);
            }
        } catch (IOException | JSONException ex) {
            Log.e(LOG_TAG, "Error", ex);
        }
        return ret;
    }
    public MainActivityFragment() {
        mPlantList = new ArrayList<>();
/*        String[][] imageData = new String[][] {{imagePath+"20150813SnowGrassFlat509.jpg", "flower1", "scientic 1", "description 1"}
                ,{imagePath+"20150813SnowGrassFlat510.jpg", "flower2", "scientic 2", "description 2"} }; */
    }
    private int calcNumColumn() {
        DisplayMetrics metrics = new  DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            return 3;
        }else{
            return 2;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        Context context = getActivity();
        AssetManager assetManager =  getActivity().getAssets();
        mPlantList =  loadJsonPlants(assetManager);

        RadioGroup radioGroup = (RadioGroup) root.findViewById(R.id.radioGroup_display_by);
        int checkedId = radioGroup.getCheckedRadioButtonId();
        boolean displayBy = (checkedId != R.id.radioButton_scientific);
//        mAdapter = new MyRecyclerAdapter(context);
        mAdapter = new GridViewAdapter(context, R.layout.grid_item, displayBy, mPlantList);
        mGridView = (GridView) root.findViewById(R.id.gridView1);
        mGridView.setNumColumns(calcNumColumn());

        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PlantItem item = mAdapter.getItem(position);
                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra("PlantItem", item);
                startActivity(detailIntent);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != R.id.radioButton_scientific) {
                    Collections.sort(mPlantList, new Comparator<PlantItem>() {
                        @Override
                        public int compare(PlantItem p1, PlantItem p2) {
                            return p1.mCommonName.compareTo(p2.mCommonName);
                        }
                    });
                } else {
                    Collections.sort(mPlantList, new Comparator<PlantItem>() {
                        @Override
                        public int compare(PlantItem p1, PlantItem p2) {
                            return p1.mScientificName.compareTo(p2.mScientificName);
                        }
                    });
                }
                mAdapter.resetList(mPlantList, (checkedId != R.id.radioButton_scientific));
            }
        });
        return root;
    }

//    // TODO: did not get it
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == MainActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                if (data != null) {
//                    Log.i(LOG_TAG, "Image saved to:" + data.getData());
//                } else {
//                    Activity activity = getActivity();
//                    if (activity instanceof  MainActivity) {
//                        String path = ((MainActivity) activity).getSavedImagePath(requestCode);
//                        if (path != null ) {
//                            Log.v(LOG_TAG, "image file found at "+path);
//                            //TODO: open dialog and create plantItem
//                        }
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i(LOG_TAG, "User canceled image capturing");
//                // User cancelled the image capture
//            } else {
//                Log.i(LOG_TAG, String.format("Got error code in launching camera: %d", requestCode));
//                // Image capture failed, advise user
//            }
//        }
//    }

}
