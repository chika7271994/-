package com.example.kakeibo.activites.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.database.DatabaseManager;
import com.example.kakeibo.activites.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//収入ページ

public class IncomeFragment extends BaseFragment{

    private static final String TAG = IncomeFragment.class.getSimpleName();

    public static IncomeFragment newInstance(String day) {
        IncomeFragment fragment = new IncomeFragment();
        //値を受け取る
        Bundle bundle = new Bundle();
        bundle.putString("data", day);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.editText_i)
    EditText editText1;
    @BindView(R.id.editText_i2)
    EditText editText2;
    @BindView(R.id.textIncome)
    TextView textView;

    private DatabaseManager mDatabase; //データベースクラス
    private String day; //日付
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //レイアウト作成
        View view = inflater.inflate(R.layout.income_page, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.income_input)
    void onClick() {
        LogUtil.debug(TAG, "onActivityCreated");
        mDatabase = DatabaseManager.getInstance(getActivity());
        addIncome();
    }

    private void addIncome(){
        //データベースに書き込み
        String category = editText1.getText().toString();
        String price = editText2.getText().toString();

        if (!inputError(category, price)){
            return;
        }

        yy = day.substring(0, 4);
        mm = day.substring(5, 7);
        dd = day.substring(8, 10);
        int i = Integer.valueOf(price);
        int year = Integer.valueOf(yy);
        int month = Integer.valueOf(mm);
        int days = Integer.valueOf(dd);

        mDatabase.addIncome(category, i, year, month, days);

        clearInputFields();
    }

    private boolean inputError(String category, String price){
        if (TextUtils.isEmpty(category)){
            showError("品目");
            return false;
        }

        if (TextUtils.isEmpty(price)){
            showError("金額");
            return false;
        }

        return true;
    }

    private void showError(String type){
        final View view = getView();
        if (view == null || TextUtils.isEmpty(type)){
            return;
        }

        final String message = String.format(getString(R.string.fragment_input_error), type);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearInputFields(){
        //EditTextの初期化
        editText1.setText("");
        editText2.setText("");
    }

    //入力したデータベースの出力
    @OnClick(R.id.income_show)
    void onAddClick() {
        Cursor cursor = mDatabase.retrieveByDateI(yy,mm,dd);
        StringBuilder builder = new StringBuilder();
        if(cursor.moveToFirst()) {
            do {
                builder.append(cursor.getString(1) + " ");
                builder.append(cursor.getString(2) + "\n");
            } while (cursor.moveToNext());
        }
        textView.setText(builder.toString());
    }

    //SpendingFragmentページに戻る
    @OnClick(R.id.income_back)
    void btnBackClick(){
        navigateToFragment(SpendingFragment.newInstance(day));
    }

}
