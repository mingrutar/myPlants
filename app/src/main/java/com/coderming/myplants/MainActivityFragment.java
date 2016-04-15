package com.coderming.myplants;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    static final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    String imagePath = "/mnt/sdcard/Photos/";           // Environment.getExternalStorageDirectory()+ "/Photos/";

    private List<PlantItem> mPlantList;
    private GridViewAdapter mAdapter;
    private GridView        mGridView;

    public MainActivityFragment() {
/*        String[][] imageData = new String[][] {{imagePath+"20150813SnowGrassFlat509.jpg", "flower1", "scientic 1", "description 1"}
                ,{imagePath+"20150813SnowGrassFlat510.jpg", "flower2", "scientic 2", "description 2"} }; */
        mPlantList = new ArrayList<>();
        long total = 4;
        for (long i = 1; i <= total; i++ ) {
            PlantItem pi = new PlantItem(i, String.format("flower_%d.jpg", i),
                    String.format("flower%d", i), String.format("scientic %d", i),
                    String.format("Description of flower %d", i));
            mPlantList.add(pi);
        }
        Log.v(LOG_TAG, "MainActivityFragment() ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_main, container, false);
        Context context = getActivity();
//        mAdapter = new MyRecyclerAdapter(context);
        mAdapter = new GridViewAdapter(context, R.layout.grid_item, mPlantList);
        mGridView = (GridView) root.findViewById(R.id.gridView1);
        mGridView.setAdapter(mAdapter);
        return root;
    }
}
