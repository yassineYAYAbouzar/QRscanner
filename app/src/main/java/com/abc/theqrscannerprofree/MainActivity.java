package com.abc.theqrscannerprofree;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.theqrscannerprofree.fragment.TheNextFragmentMain;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.zxing.BarcodeFormat;
import com.abc.theqrscannerprofree.adapter.TheCode;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.fragment.TheFirstTheNextFragmentCodBar;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentDelete;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentCode;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentList;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentEdit;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentSave;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentType;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentQRcode;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentFile;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentGenerate;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentSettings;
import com.abc.theqrscannerprofree.fragment.FragmentDrawer;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;

import java.io.File;
import java.util.List;


import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.OnClickPathListener, InterfaceConstan {



    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;

    public static TheCode codeListFragmentAdapter;

    private Toolbar toolbar;
    private static Fragment currentFragment;
    private static Fragment backCodePreviewTypeFragment;
    private FragmentDrawer mDrawerLayoutHelper;
    private DrawerLayout drawerLayout;
    private int PICK_REQUEST_CODE = 0;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public Fragment getCurrentFragment() {
        return currentFragment;
    }
    public FragmentDrawer getDrawerLayoutHelper() {
        return mDrawerLayoutHelper;
    }

    final InterstitialAd mInterstitial = new InterstitialAd(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(MainActivity.this);
        setContentView(R.layout.activity_main);
        mInterstitial.setAdUnitId("ca-app-pub-4838372793527105/4949227399");
        mInterstitial.loadAd(new AdRequest.Builder().build());
        mInterstitial.setAdListener(new AdListener(){
            public void onAdLoaded(){
                if (mInterstitial.isLoaded()){
                }
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mInterstitial.show();
                RunAd();
            }
        },10000);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_main_toolbar_bg));
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3};


        addBottomDots(0);
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        initView();
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);


            if (position == layouts.length - 1) {

                //   btnNext.setText(">>");
                //   btnSkip.setVisibility(View.GONE);
            } else {

                //   btnNext.setText(">>");
                //   btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void initView() {
        ConnectivityManager conMgr  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if(info != null && info.isConnected())
        {
            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdMobAdView.loadAd(adRequest);

            // internet is there.
        }
        else
        {

            RelativeLayout height = (RelativeLayout) findViewById(R.id.ads);
            final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
            int px = (int) (210 * scale + 0.5f);
            height.getLayoutParams().height = px;

            LinearLayout ades = (LinearLayout) findViewById(R.id.adss);
            ades.setVisibility(View.GONE);

            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            mAdMobAdView.setVisibility(View.GONE);
            // internet is not there.
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (checkPermission()) {


        } else {
            requestPermission();

        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_frame);
        if (fragment == null) {
            currentFragment = fragment;
            showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));

        } else {
            showFragment(currentFragment);
        }

        setCodeFormats();

    }

    public void showFragment(Fragment nextFragment) {
        try {
            currentFragment = nextFragment;

            if (currentFragment instanceof TheNextFragmentSave || currentFragment instanceof TheNextFragmentEdit
                    || currentFragment instanceof TheNextFragmentFile)
                backCodePreviewTypeFragment = currentFragment;

            if (currentFragment instanceof TheNextFragmentMain)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            else
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (currentFragment instanceof TheNextFragmentType)
                fragmentTransaction.add(R.id.content_frame, nextFragment, "");
            else
                fragmentTransaction.replace(R.id.content_frame, nextFragment, "");
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showFragment(Fragment nextFragment, int anim) {
        try {
            currentFragment = nextFragment;

            if (currentFragment instanceof TheNextFragmentSave || currentFragment instanceof TheNextFragmentEdit
                    || currentFragment instanceof TheNextFragmentFile)
                backCodePreviewTypeFragment = currentFragment;

            if (currentFragment instanceof TheNextFragmentMain)
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            else
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction();
            switch (anim) {
                case ANIM_FORWARD:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_forward_in_left, R.anim.slide_forward_in_right);
                    break;
                case ANIM_BACKWARD:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_backward_in_left, R.anim.slide_backward_in_right);
                    break;
            }
            if (currentFragment instanceof TheNextFragmentType)
                fragmentTransaction.add(R.id.content_frame, nextFragment, "");
            else
                fragmentTransaction.replace(R.id.content_frame, nextFragment, "");
            fragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mDrawerLayoutHelper = new FragmentDrawer(this, drawerLayout, MainActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDrawerLayoutHelper.closePanel();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager conMgr  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        if(info != null && info.isConnected())
        {
            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdMobAdView.loadAd(adRequest);

            // internet is there.
        }
        else
        {

            RelativeLayout height = (RelativeLayout) findViewById(R.id.ads);
            final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
            int px = (int) (210 * scale + 0.5f);
            height.getLayoutParams().height = px;

            LinearLayout ades = (LinearLayout) findViewById(R.id.adss);
            ades.setVisibility(View.GONE);

            AdView mAdMobAdView = (AdView) findViewById(R.id.admob_adview);
            mAdMobAdView.setVisibility(View.GONE);
            // internet is not there.
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayoutHelper.isPanelOpen()) {
            mDrawerLayoutHelper.closePanel();
        } else {
            if (currentFragment instanceof TheNextFragmentMain) {
                if (mDrawerLayoutHelper.isPanelOpen())
                    mDrawerLayoutHelper.closePanel();
                else
                    super.onBackPressed();
            }else if (currentFragment instanceof TheNextFragmentQRcode) {
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheFirstTheNextFragmentCodBar) {
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentFile) {
                if (!((TheNextFragmentFile) currentFragment).isSaved())
                    ((TheNextFragmentFile) currentFragment).saveDialog();
                else
                    showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentGenerate) {
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentSettings) {
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentList) {
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentSave) {
                if (!((TheNextFragmentSave) currentFragment).isSaved())
                    ((TheNextFragmentSave) currentFragment).saveDialog();
                else
                    showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
            } else if (currentFragment instanceof TheNextFragmentEdit) {
                if (!((TheNextFragmentEdit) currentFragment).isSaved())
                    ((TheNextFragmentEdit) currentFragment).saveDialog();
                else {
                    if (((TheNextFragmentEdit) currentFragment).getmLastFragment() == CODE_PREVIEW_LAST_FRAGMENT_MAIN)
                        showFragment(TheNextFragmentList.newInstance());
                    else
                        showFragment(TheNextFragmentDelete.newInstance());
                }
            } else if (currentFragment instanceof TheNextFragmentType) {
                getSupportFragmentManager().beginTransaction().remove(currentFragment).commitAllowingStateLoss();
                currentFragment = backCodePreviewTypeFragment;
            } else if (currentFragment instanceof TheNextFragmentCode) {
                showFragment(TheNextFragmentSettings.newInstance());
            } else if (currentFragment instanceof TheNextFragmentDelete) {
                showFragment(TheNextFragmentList.newInstance());
            }
        }
    }

    public void visibleBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Drawable homeButton = getResources().getDrawable(R.drawable.back_button_ic);
            if (homeButton != null) {
                homeButton.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(homeButton);
        }
    }

    public void visibleHomeButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Drawable homeButton = getResources().getDrawable(R.drawable.ic_navigation_menu);
            if (homeButton != null) {
                homeButton.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(homeButton);
        }
    }


    public void disableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public void onClickPath() {
        Intent i = new Intent(MainActivity.this, FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
        startActivityForResult(i, PICK_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<Uri> files = Utils.getSelectedFilesFromResult(data);
            for (Uri uri : files) {
                File file = Utils.getFileForUri(uri);
                showFragment(TheNextFragmentFile.newInstance(file.getAbsolutePath()));
            }
        }
    }

    private void showMainFragment() {
        switch (SharedPreferencesUtilsLikeFlutter.getMainScreenType()) {
            case MAIN_APP_HOME:
                showFragment(TheNextFragmentMain.newInstance(SharedPreferencesUtilsLikeFlutter.isAutoFocus(), SharedPreferencesUtilsLikeFlutter.isSqare()));
                break;
        }
    }

    private void setCodeFormats() {
        try {
            if (NamedFactoryHlp.getHelper().getDaoCodeForm().getAllItems().size() == 0) {
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.QR_CODE.ordinal(), BarcodeFormat.QR_CODE.name(), true);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.AZTEC.ordinal(), BarcodeFormat.AZTEC.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.CODABAR.ordinal(), BarcodeFormat.CODABAR.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.CODE_39.ordinal(), BarcodeFormat.CODE_39.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.CODE_93.ordinal(), BarcodeFormat.CODE_93.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.CODE_128.ordinal(), BarcodeFormat.CODE_128.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.DATA_MATRIX.ordinal(), BarcodeFormat.DATA_MATRIX.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.EAN_8.ordinal(), BarcodeFormat.EAN_8.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.EAN_13.ordinal(), BarcodeFormat.EAN_13.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.ITF.ordinal(), BarcodeFormat.ITF.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.MAXICODE.ordinal(), BarcodeFormat.MAXICODE.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.PDF_417.ordinal(), BarcodeFormat.PDF_417.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.RSS_14.ordinal(), BarcodeFormat.RSS_14.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.RSS_EXPANDED.ordinal(), BarcodeFormat.RSS_EXPANDED.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.UPC_A.ordinal(), BarcodeFormat.UPC_A.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.UPC_E.ordinal(), BarcodeFormat.UPC_E.name(), false);
                NamedFactoryHlp.getHelper().addCodeFormatItemDB(MainActivity.this, BarcodeFormat.UPC_EAN_EXTENSION.ordinal(), BarcodeFormat.UPC_EAN_EXTENSION.name(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted)
                        Toast.makeText(this, "Permission Granted, Now you can access this app.", Toast.LENGTH_LONG).show();

                    else {
                        Toast.makeText(this, "Permission Denied, You cannot use this app.", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    public void RunAd(){
        mInterstitial.loadAd(new AdRequest.Builder().build());
    }
}

