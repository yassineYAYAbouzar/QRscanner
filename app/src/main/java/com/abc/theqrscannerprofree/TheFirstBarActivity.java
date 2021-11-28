package com.abc.theqrscannerprofree;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import com.abc.theqrscannerprofree.utils.ForRstUtils;
import com.abc.theqrscannerprofree.views.TheBarScannerForView;


public class TheFirstBarActivity extends AppCompatActivity implements TheBarScannerForView.ResultHandler, InterfaceConstan {

    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private TheBarScannerForView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(TheFirstBarActivity.this);
        mScannerView = new TheBarScannerForView(this);
        setContentView(mScannerView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAutoFocus = bundle.getBoolean(IS_AUTO_FOCUS);
            isSquare = bundle.getBoolean(IS_SQUARE);
        }

        initView();
    }


    private void initView() {

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
    public void handleResult(ForRstUtils rawResult) {
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