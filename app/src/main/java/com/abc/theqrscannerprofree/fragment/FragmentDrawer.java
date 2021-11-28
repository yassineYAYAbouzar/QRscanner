package com.abc.theqrscannerprofree.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.MainActivity;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.constant.TheColorsConst;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

public class FragmentDrawer  implements View.OnClickListener, InterfaceConstan, TheColorsConst {

    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private LinearLayout navigationViewHeader;
    private LinearLayout navigationViewContainer;
    public static OnClickPathListener mOnClick;

    public interface OnClickPathListener {
        void onClickPath();
    }

    public FragmentDrawer(Context context, DrawerLayout drawerLayout, OnClickPathListener onClick) {
        mContext = context;
        mDrawerLayout = drawerLayout;
        mOnClick = onClick;
        initView();
        setViewStyle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apphome:
                ((MainActivity) mContext).showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
                closePanel();
                break;
            case R.id.btn_qr_camera:
                ((MainActivity) mContext).showFragment(TheNextFragmentQRcode.newInstance());
                closePanel();
                break;
            case R.id.btn_barcode_camera:
                ((MainActivity) mContext).showFragment(TheFirstTheNextFragmentCodBar.newInstance());
                closePanel();
                break;
            case R.id.btn_qr_file:
                mOnClick.onClickPath();
                closePanel();
                break;
            case R.id.btn_qr_generate:
                ((MainActivity) mContext).showFragment(TheNextFragmentGenerate.newInstance());
                closePanel();
                break;
            case R.id.btn_code_list:
                ((MainActivity) mContext).showFragment(TheNextFragmentList.newInstance());
                closePanel();
                break;
            case R.id.btn_settings:
                ((MainActivity) mContext).showFragment(TheNextFragmentSettings.newInstance());
                closePanel();
                break;
            case R.id.btn_share:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_text) + "market://details?id=" + mContext.getPackageName());
                intentShare.setType("text/plain");
                mContext.startActivity(Intent.createChooser(intentShare, mContext.getString(R.string.settings_share)));
                break;
            case R.id.btn_rate_us:
                Intent intentRateUs = new Intent(Intent.ACTION_VIEW);
                intentRateUs.setData(Uri.parse("market://details?id=" + mContext.getPackageName()));
                mContext.startActivity(intentRateUs);
                break;
            case R.id.btn_about_us:
                aboutMyApp();
                break;
        }
    }

    public void openPanel() {
        mDrawerLayout.openDrawer(mNavigationView);
    }

    public void closePanel() {
        if (mDrawerLayout != null && mNavigationView != null && mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawer(mNavigationView);
    }

    public boolean isPanelOpen() {
        return mDrawerLayout.isDrawerOpen(mNavigationView);
    }


    private void fastInitItem(int btnId, int imgId, int txtId) {
        LinearLayout btn = (LinearLayout) mNavigationView.findViewById(btnId);
        if (btn != null) {
            btn.setOnClickListener(this);
            btn.setBackgroundResource(R.drawable.btn_transparent_dark);
        }

        TextView txt = (TextView) mNavigationView.findViewById(txtId);
        if (txt != null)
            txt.setTextColor(mContext.getResources().getColor(R.color.nv_text_theme1));
    }


    private void setViewStyle() {
        if (navigationViewContainer != null)
            navigationViewContainer.setBackgroundColor(mContext.getResources().getColor(R.color.nv_bg_theme1));
        if (navigationViewHeader != null)

             fastInitItem(R.id.btn_apphome, R.id.img_apphome, R.id.txt_apphome);
        fastInitItem(R.id.btn_qr_camera, R.id.img_qr_camera, R.id.txt_qr_camera);
        fastInitItem(R.id.btn_barcode_camera, R.id.img_barcode_camera, R.id.txt_barcode_camera);
        fastInitItem(R.id.btn_qr_file, R.id.img_qr_file, R.id.txt_qr_file);
        fastInitItem(R.id.btn_qr_generate, R.id.img_qr_generate, R.id.txt_qr_file);
        fastInitItem(R.id.btn_code_list, R.id.img_code_list, R.id.txt_code_list);
        fastInitItem(R.id.btn_settings, R.id.img_settings, R.id.txt_settings);
        fastInitItem(R.id.btn_rate_us, R.id.img_rate_us, R.id.txt_rate_us);
        fastInitItem(R.id.btn_share, R.id.img_share, R.id.txt_rate_us);
        fastInitItem(R.id.btn_about_us, R.id.img_about_us, R.id.txt_about_us);
    }

    private void initView() {
        mNavigationView = (NavigationView) mDrawerLayout.findViewById(R.id.navigation_view);
        if (mNavigationView != null) {
            navigationViewHeader = (LinearLayout) mNavigationView.findViewById(R.id.navigation_view_header);
            navigationViewContainer = (LinearLayout) mNavigationView.findViewById(R.id.navigation_view_container);
        }

    }


    private void aboutMyApp() {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=yassine+bouzar")));
    }
}
