package com.abc.theqrscannerprofree.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.abc.theqrscannerprofree.MainActivity;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

public class TheNextFragmentBs extends Fragment {
    protected MainActivity listenerActivity;
    protected DisplayMetrics metrics;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerActivity = (MainActivity) activity;
        metrics = activity.getResources().getDisplayMetrics();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (listenerActivity.getCurrentFragment() instanceof TheNextFragmentMain) {
                    listenerActivity.getDrawerLayoutHelper().openPanel();
                }
                else if (listenerActivity.getCurrentFragment() instanceof TheNextFragmentQRcode) {
                    listenerActivity.showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
                }else if (listenerActivity.getCurrentFragment() instanceof TheFirstTheNextFragmentCodBar) {
                    listenerActivity.showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
                }
                else {
                    listenerActivity.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
