package com.abc.theqrscannerprofree.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.TheQrHiyaReadeActivity;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import com.nononsenseapps.filepicker.Utils;

import java.io.File;
import java.util.List;


public class TheNextFragmentMain extends TheNextFragmentBs implements InterfaceConstan {
    private View rootView;
    private int PICK_REQUEST_CODE = 0;
    private boolean isAutoFocus = true;
    private boolean isSquare = false;
 //   private static FragmentDrawer.OnClickPathListener mOnClick;

    public static Fragment newInstance(boolean isAutoFocus, boolean isSquare) {
        Fragment fragment = new TheNextFragmentMain();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_AUTO_FOCUS, isAutoFocus);
        bundle.putBoolean(IS_SQUARE, isSquare);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        listenerActivity.disableBackButton();
        listenerActivity.visibleHomeButton();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(IS_AUTO_FOCUS) && bundle.containsKey(IS_SQUARE)) {
            isAutoFocus = bundle.getBoolean(IS_AUTO_FOCUS);
            isSquare = bundle.getBoolean(IS_SQUARE);
        }
        //mOnClick = initView;
        initView();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.app_name_label));
        }

        (rootView.findViewById(R.id.home2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentDrawer.mOnClick.onClickPath();

            }
        });
        (rootView.findViewById(R.id.home1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerActivity.showFragment(TheNextFragmentQRcode.newInstance());
            }
        });


        (rootView.findViewById(R.id.home3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerActivity.showFragment(TheFirstTheNextFragmentCodBar.newInstance());
            }
        });

        (rootView.findViewById(R.id.home4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerActivity.showFragment(TheNextFragmentGenerate.newInstance());
            }
        });
        (rootView.findViewById(R.id.home5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listenerActivity.showFragment(TheNextFragmentList.newInstance());
            }
        });
        (rootView.findViewById(R.id.home6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerActivity.showFragment(TheNextFragmentSettings.newInstance());
            }
        });

        SharedPreferencesUtilsLikeFlutter.setMainScreenType(MAIN_APP_HOME);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            List<Uri> files = Utils.getSelectedFilesFromResult(data);
            for (Uri uri: files) {
                File file = Utils.getFileForUri(uri);
                Intent intent = new Intent(listenerActivity, TheQrHiyaReadeActivity.class);
                intent.putExtra(IMG_PATH, file.getAbsolutePath());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}
