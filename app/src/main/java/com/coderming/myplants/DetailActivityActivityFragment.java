package com.coderming.myplants;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityActivityFragment extends Fragment {

    public DetailActivityActivityFragment() {
    }

    private void setText(EditText editText, String text) {
        editText.setText(text);
        editText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText editText = (EditText) v;
                editText.setFocusableInTouchMode(true);
                return false;
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null ) {
            PlantItem plant = (PlantItem) intent.getSerializableExtra("PlantItem");
            if (plant.mDrawable == null ) {
                InputStream is =  null;
                try {
                    is = getContext().getAssets().open(plant.getImageFilename());
                    plant.mDrawable = BitmapFactory.decodeStream(is);
                } catch (IOException ex) {
                    if (is != null) {
                        try { is.close();} catch (Exception e) { }
                    }
                }
            }
            ImageView imageView = (ImageView) root.findViewById(R.id.imageView);
            imageView.setImageBitmap(plant.mDrawable);
            EditText text = (EditText) root.findViewById(R.id.commanNameText);
            setText(text, plant.mCommonName);
            text = (EditText) root.findViewById(R.id.scientificNameText);
            setText(text, plant.mScientificName);
            text = (EditText) root.findViewById(R.id.descriptionText);
            setText(text, plant.mDesciption);
            text = (EditText) root.findViewById(R.id.genusText);
            setText(text, plant.mGenus);
            text = (EditText) root.findViewById(R.id.familyText);
            setText(text, plant.mFamily);
        }
        return root;
    }
}
