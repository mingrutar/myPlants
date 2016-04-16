package com.coderming.myplants;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
//    static final String  photo_tag = "photo";

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
                plant.mImageFilename = String.format("flower_%d.jpg", (i % 4) + 1);
                Log.v(LOG_TAG, plant.toString());
                ret.add(plant);
            }
        } catch (IOException | JSONException ex) {
            Log.e(LOG_TAG, "Error", ex);
        }
        return ret;
    }
    void makeTestList() {
        long total = 4;
        for (long i = 1; i <= total; i++) {
            PlantItem pi = new PlantItem(i, String.format("flower_%d.jpg", i),
                    String.format("flower_%d", i), String.format("%dscientic %d", (8-i), i),
                    String.format("Description of flower %d", i));
            mPlantList.add(pi);
        }
    }
    public MainActivityFragment() {
        mPlantList = new ArrayList<>();
/*        String[][] imageData = new String[][] {{imagePath+"20150813SnowGrassFlat509.jpg", "flower1", "scientic 1", "description 1"}
                ,{imagePath+"20150813SnowGrassFlat510.jpg", "flower2", "scientic 2", "description 2"} }; */
        Log.v(LOG_TAG, "MainActivityFragment() ");
        makeTestList();
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
}
