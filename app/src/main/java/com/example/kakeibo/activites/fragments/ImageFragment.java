package com.example.kakeibo.activites.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.database.DatabaseManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFragment extends BaseFragment {

    private static final String TAG = ImageFragment.class.getSimpleName();

    public static ImageFragment newInstance() { return new ImageFragment(); }

    private DatabaseManager mDatabase; //データベースクラス
    @BindView(R.id.image_view)
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showImageView();
    }

    private void showImageView(){
        Cursor cursor = mDatabase.retriveCamera();
        StringBuilder builder = new StringBuilder();
        Uri uri = null;
        String imageUri;
        if (cursor.moveToFirst()) {
            do {
                builder.append(cursor.getString(1));
                uri = Uri.parse(String.valueOf(builder));
            } while (cursor.moveToNext());
        }
        imageView.setImageURI(uri);
    }
}
