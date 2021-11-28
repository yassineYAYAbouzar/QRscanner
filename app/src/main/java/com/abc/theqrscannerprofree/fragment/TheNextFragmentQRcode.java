package com.abc.theqrscannerprofree.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abc.theqrscannerprofree.views.NxtOneUsingScannerView;
import com.google.zxing.Result;
import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.converse.OurQrRst;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

public class TheNextFragmentQRcode extends TheNextFragmentBs implements NxtOneUsingScannerView.ResultHandler, OurQrRst.OnClickListener, InterfaceConstan {
    private View rootView;
    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private NxtOneUsingScannerView mScannerView;
    public static Fragment newInstance() {
        return new TheNextFragmentQRcode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_qr, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_qr_scanner));
        }
        SharedPreferencesUtilsLikeFlutter.setMainScreenType(MAIN_SCREEN_QR);
        mScannerView = (NxtOneUsingScannerView) rootView.findViewById(R.id.zxing_scanner_view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void handleResult(Result rawResult) {
        stopScanner();
        OurQrRst.showQRResultDialog(listenerActivity, rawResult.getText(), TheNextFragmentQRcode.this);
    }

    public void startScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setFormats();
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
