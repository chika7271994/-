package com.example.kakeibo.activites.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.constants.Common;
import com.example.kakeibo.activites.database.DatabaseManager;
import com.example.kakeibo.activites.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//Mainフラグメントとする

public class KakeiboFragment extends BaseFragment{

    public static KakeiboFragment newInstance(String currentDate) {
        KakeiboFragment fragment = new KakeiboFragment();
        //日付の値を取得
        Bundle bundle = new Bundle();
        bundle.putString("data", currentDate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.memo_index_day)
    TextView textDay;
    @BindView(R.id.memo_index_te)
    TextView textView;
    @BindView(R.id.memo_index_te2)
    TextView textView2;
    @BindView(R.id.memo_index_te3)
    TextView textView3;

    private DatabaseManager mDatabase; //データベースクラス
    private String day;        //日付
    private String data;       //日付表示形式変更後
    private String yy;
    private String mm;
    private String dd;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memo_index, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        day = bundle.getString("data");
        data = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(new Date(day));
        yy = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date(day));
        mm = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date(day));
        dd = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date(day));
        textDay.setText(data);

        mDatabase = DatabaseManager.getInstance(getActivity());

        showSpendingData();
        showIncomeData();
        showMemoData();
    }


    private void showSpendingData(){
        //支出データ呼び出し
        Cursor cursor = mDatabase.retrieveByDate(yy, mm, dd);
        StringBuilder text = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                text.append(cursor.getString(1) + " ");
                text.append(cursor.getInt(2) + "\n");
            } while (cursor.moveToNext());
        }
        textView.setText(text);
    }

    private void showIncomeData(){
        //収入データ呼び出し
        Cursor iCursor = mDatabase.retrieveByDateI(yy, mm, dd);
        StringBuilder incomeText = new StringBuilder();
        if (iCursor.moveToFirst()) {
            do {
                incomeText.append(iCursor.getString(1) + " ");
                incomeText.append(iCursor.getInt(2) + "\n");
            } while (iCursor.moveToNext());
        }
        textView2.setText(incomeText);
    }

    private void showMemoData(){
        //メモデータ呼び出し
        Cursor mCursor = mDatabase.retrieveByDateM(yy, mm, dd);
        StringBuilder memoText = new StringBuilder();
        if (mCursor.moveToFirst()) {
            do {
                memoText.append(mCursor.getString(1) + " ");
                memoText.append("\n");
            } while (mCursor.moveToNext());
        }
        textView3.setText(memoText);
    }


    //収支入力ページ移行
    @OnClick(R.id.syuusi)
    void btnSyuusiClick(){
        navigateToFragment(SpendingFragment.newInstance(data));
    }

    //メモページ移行
    @OnClick(R.id.memo)
    void btnMemoClick() { navigateToFragment(MemoFragment.newInstance(data)); }

    //カレンダーページ移行
    @OnClick(R.id.kakeibo_back)
    void btnClick() { navigateToFragment(CalendarFragment.newInstance()); }

}