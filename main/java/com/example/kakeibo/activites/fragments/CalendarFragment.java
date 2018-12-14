package com.example.kakeibo.activites.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.kakeibo.R;
import com.example.kakeibo.activites.activites.CalendarAdapter;
import com.example.kakeibo.activites.utils.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static final String TAG = CalendarFragment.class.getSimpleName();


    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.calendarGridView)
    GridView calendarGridView;

    private CalendarAdapter mCalendarAdapter;
    private Bundle bundle = new Bundle();
    private String month;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mCalendarAdapter = new CalendarAdapter(this.getContext());
        calendarGridView.setAdapter(mCalendarAdapter);
        titleText.setText(mCalendarAdapter.getTitle());
        calendarGridView.setOnItemClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.action, menu);
        menu.findItem(R.id.menu_spending);
        menu.findItem(R.id.menu_income);
        menu.findItem(R.id.menu_memo);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //int id = item.getItemId();
        month = mCalendarAdapter.getTitle();
        LogUtil.debug("CalendarFragment", "日付は"+ month);
        bundle.putString("month", month);
        switch (item.getItemId()){
            case R.id.menu_spending:
                //SpendingDataFragmentに遷移
                SpendingDataFragment fragment = new SpendingDataFragment();
                fragment.setArguments(bundle);
                navigateToFragment(SpendingDataFragment.newInstance(month));
                break;

            case R.id.menu_income:
                //IncomeDataFragmentに遷移
                IncomeDataFragment incomeDataFragment = new IncomeDataFragment();
                incomeDataFragment.setArguments(bundle);
                navigateToFragment(IncomeDataFragment.newInstance(month));
                break;
        }

        return true;
    }

    @OnClick(R.id.prevButton)
    void onClickPrev () {
        mCalendarAdapter.prevMonth();
        titleText.setText(mCalendarAdapter.getTitle());
    }

    @OnClick(R.id.nextButton)
    void onClickNext () {
        mCalendarAdapter.nextMonth();
        titleText.setText(mCalendarAdapter.getTitle());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //KakeiboListFragmentに送る日付データ
        Bundle bundle = new Bundle();
        String data = mCalendarAdapter.getItem(position).toString();
        bundle.putString("data", data);
        KakeiboFragment fragment = new KakeiboFragment();
        fragment.setArguments(bundle);
        navigateToFragment(KakeiboFragment.newInstance(data));
    }
}
