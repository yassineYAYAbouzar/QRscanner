package com.abc.theqrscannerprofree.helper;

import android.content.Context;

import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.fragment.TheNextFragmentList;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

import java.util.ArrayList;
import java.util.List;

import static com.abc.theqrscannerprofree.MainActivity.codeListFragmentAdapter;

public class TheCodeHlp implements InterfaceConstan {
    private static List<TheQrItemCodes> callRecordList = new ArrayList<>();

    public static void updateAllLists(Context context) {
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(context);
        try {
            callRecordList.clear();

            if (codeListFragmentAdapter != null) {
                callRecordList = NamedFactoryHlp.getHelper().getQRCodeDAO().getAllItems();
                codeListFragmentAdapter.setLists(callRecordList);
                codeListFragmentAdapter.notifyDataSetChanged();

                if (callRecordList.size() != 0)
                    TheNextFragmentList.visibleRecycler();
                else
                    TheNextFragmentList.visibleTextView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
