package com.example.kakeibo.activites.activites;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.fragments.CalendarFragment;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);

        addFragment(CalendarFragment.newInstance());
    }

    /**
     * フラグメントを設定
     *
     * @param fragment フラグメント
     */

    public void addFragment(Fragment fragment) {
        final String fullName = fragment.getClass().getName();
        final String name = fullName.substring(fullName.lastIndexOf(".") + 1);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.base_activity_xml, fragment, name)
                .disallowAddToBackStack()
                .commit();
    }

    /**
     * フラグメントを変更する
     *
     * @param fragment フラグメント
     */

    public void replaceFragment(Fragment fragment) {
        final String fullName = fragment.getClass().getName();
        final String name = fullName.substring(fullName.lastIndexOf(".") + 1);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.base_activity_xml, fragment, name)
                .addToBackStack(name)
                .commit();
    }
}
