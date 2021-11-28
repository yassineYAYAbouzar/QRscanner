package com.abc.theqrscannerprofree;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abc.theqrscannerprofree.views.NxtOneUsingScannerView;
import com.google.zxing.Result;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

public class NextQrActActivity extends AppCompatActivity implements NxtOneUsingScannerView.ResultHandler, InterfaceConstan {

    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private NxtOneUsingScannerView mScannerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(NextQrActActivity.this);
        mScannerView = new NxtOneUsingScannerView(this);
        setContentView(R.layout.activity_test_camera);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAutoFocus = bundle.getBoolean(IS_AUTO_FOCUS);
            isSquare = bundle.getBoolean(IS_SQUARE);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_main_toolbar_bg));
        toolbar.setTitle(getString(R.string.app_name_label));
        setSupportActionBar(toolbar);
        initView();
    }


    private void initView() {
        mScannerView = (NxtOneUsingScannerView) findViewById(R.id.zxing_scanner_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        startScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopScanner();
    }

    @Override
    public void handleResult(Result rawResult) {

        stopScanner();
    }

    public void startScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setAutoFocus(isAutoFocus);
        mScannerView.setSquareViewFinder(isSquare);
    }

    public void stopScanner() {
        mScannerView.stopCamera();
    }

}