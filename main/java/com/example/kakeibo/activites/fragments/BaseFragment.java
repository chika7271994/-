package com.example.kakeibo.activites.fragments;

import android.support.v4.app.Fragment;

import com.example.kakeibo.activites.activites.BaseActivity;

public class BaseFragment extends Fragment {
    public BaseActivity obtainBaseActivity() {
        return (BaseActivity) getActivity();
    }

    /**
     * 指定されたフラグメントへ遷移
     *
     * @param fragment フラグメント
     */
    public void navigateToFragment(BaseFragment fragment) {
        if (fragment == null) {
            return;
        }

        final BaseActivity activity = obtainBaseActivity();
        if (activity == null) {
            return;
        }

        activity.replaceFragment(fragment);
    }
}
