package com.abc.theqrscannerprofree.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.converse.YouNedToConfirmD;
import com.abc.theqrscannerprofree.converse.OurQrRstSd;
import com.abc.theqrscannerprofree.converse.TheCodSave;
import com.abc.theqrscannerprofree.converse.YouNeedToConfirmS;
import com.abc.theqrscannerprofree.helper.MyIconHlp;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.helper.NamedPaintHlp;

import java.sql.SQLException;

public class TheNextFragmentEdit extends TheNextFragmentBs implements InterfaceConstan, TheNextFragmentType.OnChangeType, YouNeedToConfirmS.OnClickListener,
        TheCodSave.OnClickListener, YouNedToConfirmD.OnClickListener {
    private View rootView;
    private int mId;
    private int mLastFragment;
    private TheQrItemCodes theQrItemCodes;
    private String mCodeName = "";
    private String mCodeResult = "";
    private int mCodeType = 0;
    private TextView codeTypeTV;
    private TextView codeNameTV;
    private boolean isSaved = false;

    public static Fragment newInstance(int id, int lastFragment) {
        Fragment fragment = new TheNextFragmentEdit();
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_ID, id);
        bundle.putInt(CODE_PREVIEW_LAST_FRAGMENT, lastFragment);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CODE_ID) && bundle.containsKey(CODE_PREVIEW_LAST_FRAGMENT)) {
            mId = bundle.getInt(CODE_ID);
            mLastFragment = bundle.getInt(CODE_PREVIEW_LAST_FRAGMENT);
        }
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_edit));
        }

        codeNameTV = (TextView) rootView.findViewById(R.id.preview_code_name_desc);
        codeTypeTV = (TextView) rootView.findViewById(R.id.preview_code_type_desc);
        final TextView codeResultTV = (TextView) rootView.findViewById(R.id.preview_code_result_desc);

        try {
            theQrItemCodes = NamedFactoryHlp.getHelper().getQRCodeDAO().getItem(mId);
            mCodeName = theQrItemCodes.getRecordName();
            mCodeResult = theQrItemCodes.getDesc();
            mCodeType = theQrItemCodes.getRecordType();
            ((ImageView) rootView.findViewById(R.id.preview_code_type_ic)).setImageDrawable(MyIconHlp.getCodeTypeIcon(listenerActivity, theQrItemCodes.getRecordType()));

            codeNameTV.setText(theQrItemCodes.getRecordName());
            codeResultTV.setText(theQrItemCodes.getDesc());
            switch (theQrItemCodes.getRecordType()) {
                case CODE_PREVIEW_TYPE_TEXT:
                    codeTypeTV.setText(getString(R.string.code_type_text));
                    break;
                case CODE_PREVIEW_TYPE_BARCODE:
                    codeTypeTV.setText(getString(R.string.code_type_barcode));
                    break;
                case CODE_PREVIEW_TYPE_WIFI:
                    codeTypeTV.setText(getString(R.string.code_type_wifi));
                    break;
                case CODE_PREVIEW_TYPE_GEO:
                    codeTypeTV.setText(getString(R.string.code_type_geo));
                    break;
                case CODE_PREVIEW_TYPE_WEB_LINK:
                    codeTypeTV.setText(getString(R.string.code_type_web_link));
                    break;
                case CODE_PREVIEW_TYPE_CONTACT:
                    codeTypeTV.setText(getString(R.string.code_type_contact));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        (rootView.findViewById(R.id.preview_code_name_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TheCodSave.showSaveCodeNameDialog(listenerActivity, mCodeName, TheNextFragmentEdit.this);
            }
        });

        (rootView.findViewById(R.id.preview_code_type_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerActivity.showFragment(TheNextFragmentType.newInstance(mCodeType, CODE_PREVIEW_TYPE_BACK_EDIT, TheNextFragmentEdit.this));
            }
        });
        (rootView.findViewById(R.id.preview_code_result_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OurQrRstSd.showQRResultSavedDialog(listenerActivity, theQrItemCodes.getDesc());
            }
        });

        LinearLayout saveBtn = (LinearLayout) rootView.findViewById(R.id.save_layout);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if (codeNameTV != null && codeNameTV.getText() != null) {
                            theQrItemCodes.setRecordName(String.valueOf(codeNameTV.getText()));
                        }
                        theQrItemCodes.setRecordType(mCodeType);
                        NamedFactoryHlp.getHelper().getQRCodeDAO().update(theQrItemCodes);
                        isSaved = true;
                        listenerActivity.onBackPressed();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.code_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                YouNedToConfirmD.showDeleteCodeConfirmDialog(listenerActivity, theQrItemCodes, TheNextFragmentEdit.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setStyle() {
        NamedPaintHlp.setIconsColors(listenerActivity, rootView, R.id.preview_code_name_ic, R.drawable.ic_baseline_text_fields_24);
        NamedPaintHlp.setIconsColors(listenerActivity, rootView, R.id.preview_code_result_ic, R.drawable.preview_code_result_ic);
    }

    @Override
    public void onChange(int codeType, String codeTypeText) {
        mCodeType = codeType;
        codeTypeTV.setText(codeTypeText);
        ((ImageView) rootView.findViewById(R.id.preview_code_type_ic)).setImageDrawable(MyIconHlp.getCodeTypeIcon(listenerActivity, codeType));
    }

    @Override
    public void onClick(boolean isDelete) {
        isSaved = true;
        listenerActivity.onBackPressed();
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void saveDialog() {
        if (codeNameTV != null && codeNameTV.getText() != null)
            YouNeedToConfirmS.showSaveConfirmDialog(listenerActivity, theQrItemCodes, String.valueOf(codeNameTV.getText()), mCodeType, TheNextFragmentEdit.this);
    }

    @Override
    public void onClick(String name) {
        codeNameTV.setText(name);
        mCodeName = name;
    }

    @Override
    public void onDeleteClick(TheQrItemCodes theQrItemCodes) {
        try {
            isSaved = true;
            NamedFactoryHlp.getHelper().getQRCodeDAO().deleteItem(theQrItemCodes.getId());
            listenerActivity.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getmLastFragment() {
        return mLastFragment;
    }
}
