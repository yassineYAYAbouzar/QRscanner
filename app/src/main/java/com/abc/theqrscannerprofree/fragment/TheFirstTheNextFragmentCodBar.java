package com.abc.theqrscannerprofree.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.converse.OurQrRst;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import com.abc.theqrscannerprofree.utils.ForRstUtils;
import com.abc.theqrscannerprofree.views.TheBarScannerForView;

public class TheFirstTheNextFragmentCodBar extends TheNextFragmentBs implements TheBarScannerForView.ResultHandler, OurQrRst.OnClickListener, InterfaceConstan {
    private View rootView;
    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private TheBarScannerForView mScannerView;

    public static Fragment newInstance() {
        return new TheFirstTheNextFragmentCodBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_barcode, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();

        initView();
        return rootView;
    }


    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_barcode_scanner));
        }
        SharedPreferencesUtilsLikeFlutter.setMainScreenType(MAIN_SCREEN_BARCODE);
        mScannerView = (TheBarScannerForView) rootView.findViewById(R.id.zxing_scanner_view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void handleResult(ForRstUtils rawResult) {
        stopScanner();
        OurQrRst.showQRResultDialog(listenerActivity, rawResult.getContents(), TheFirstTheNextFragmentCodBar.this);

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

    public void resumeScanner() {
        mScannerView.resumeCameraPreview(this);
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
    public void onClick() {
        startScanner();
    }
}
