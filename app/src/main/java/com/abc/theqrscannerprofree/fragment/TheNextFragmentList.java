package com.abc.theqrscannerprofree.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.adapter.TheCode;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.converse.YouNedToConfirmD;
import com.abc.theqrscannerprofree.helper.TheCodeHlp;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.helper.NamedPaintHlp;

import java.util.ArrayList;
import java.util.List;

import static com.abc.theqrscannerprofree.MainActivity.codeListFragmentAdapter;

public class TheNextFragmentList extends TheNextFragmentBs implements InterfaceConstan, TheCode.OnClickListener, YouNedToConfirmD.OnClickListener {
    private View rootView;
    private static RecyclerView recyclerView;
    private static RelativeLayout noRecordLayout;
    private List<TheQrItemCodes> callRecordList = new ArrayList<>();

    public static Fragment newInstance() {
        return new TheNextFragmentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_codelist, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_code_list));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        noRecordLayout = (RelativeLayout) rootView.findViewById(R.id.no_record_call_layout);
        updateList();

    }

    private void updateList() {
        if (recyclerView != null) {
            try {
                callRecordList = NamedFactoryHlp.getHelper().getQRCodeDAO().getAllItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                codeListFragmentAdapter = new TheCode(listenerActivity, TheNextFragmentList.this);
                recyclerView.setAdapter(codeListFragmentAdapter);
                codeListFragmentAdapter.setLists(callRecordList);
                codeListFragmentAdapter.notifyDataSetChanged();
                if (callRecordList.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    (rootView.findViewById(R.id.no_record_call_layout)).setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.delete_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                listenerActivity.showFragment(TheNextFragmentDelete.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(TheQrItemCodes callRecordItem, View view, int position) {
        switch (view.getId()) {
            case R.id.dots_layout:
                YouNedToConfirmD.showDeleteCodeConfirmDialog(listenerActivity, callRecordItem, TheNextFragmentList.this);

                break;
            case R.id.root_item_layout:
                listenerActivity.showFragment(TheNextFragmentEdit.newInstance(callRecordItem.getId(), CODE_PREVIEW_LAST_FRAGMENT_MAIN));
                break;
            default:
                break;
        }
    }

    public static void visibleRecycler() {
        recyclerView.setVisibility(View.VISIBLE);
        noRecordLayout.setVisibility(View.GONE);
    }
    public static void visibleTextView() {
        recyclerView.setVisibility(View.GONE);
        noRecordLayout.setVisibility(View.VISIBLE);
    }

    private void setStyle() {
        NamedPaintHlp.setIconsColors(listenerActivity, rootView, R.id.main_screen_folder_ic, R.drawable.ic_baseline_folder_24);
        NamedPaintHlp.setIconsColors(listenerActivity, rootView, R.id.main_screen_folder_ic, R.drawable.ic_baseline_folder_24);
    }

    @Override
    public void onDeleteClick(TheQrItemCodes theQrItemCodes) {
        try {
            NamedFactoryHlp.getHelper().getQRCodeDAO().deleteItem(theQrItemCodes.getId());
            TheCodeHlp.updateAllLists(listenerActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
