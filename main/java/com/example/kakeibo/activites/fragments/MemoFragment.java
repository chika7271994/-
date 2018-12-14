package com.example.kakeibo.activites.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.database.DatabaseManager;
import com.example.kakeibo.activites.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//メモ一覧

public class MemoFragment extends BaseFragment {

    private static final String TAG = MemoFragment.class.getSimpleName();

    public static MemoFragment newInstance(String day) {
        MemoFragment fragment = new MemoFragment();
        //値を受け取る
        Bundle bundle = new Bundle();
        bundle.putString("data", day);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.memo_Edittext)
    EditText editText;
    @BindView(R.id.textMemo)
    TextView textView;

    private DatabaseManager mDatabase; //データベースクラス
    private String day;  //日付
    private String yy;
    private String mm;
    private String dd;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //日付の値を取得
        Bundle bundle = getArguments();
        day = bundle.getString("data");
    }

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
        //レイアウト作成
        View view = inflater.inflate(R.layout.memo_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.memo_input)
    void onClick() {
        LogUtil.debug(TAG, "onActivityCreated");
        mDatabase = DatabaseManager.getInstance(getActivity());
        addMemo();
    }

    private void addMemo(){
        String memo = editText.getText().toString();
        yy = day.substring(0, 4);
        mm = day.substring(5, 7);
        dd = day.substring(8, 10);
        LogUtil.debug("addMemo", "memoは" + memo);
        LogUtil.debug("addMemo", "yyは"+ yy);
        LogUtil.debug("addMemo", "mmは"+ mm);
        LogUtil.debug("addMemo", "ddは"+ dd);
        int year = Integer.valueOf(yy);
        int month = Integer.valueOf(mm);
        int days = Integer.valueOf(dd);
        //データベースに書き込み
        mDatabase.addMemo(memo, year, month, days);
    }

    //入力したデータベースの出力
    @OnClick(R.id.memo_show)
    void onAddClick() {
        Cursor cursor = mDatabase.retrieveByDateM(yy,mm,dd);
        StringBuilder builder = new StringBuilder();
        if(cursor.moveToFirst()) {
            do {
                builder.append(cursor.getString(1) + "\n");
            } while (cursor.moveToNext());
        }
        textView.setText(builder.toString());
    }

    //CalendarFragmentに戻る
    @OnClick(R.id.memo_back)
    void btnBackClick(){
        navigateToFragment(CalendarFragment.newInstance());
    }
}
