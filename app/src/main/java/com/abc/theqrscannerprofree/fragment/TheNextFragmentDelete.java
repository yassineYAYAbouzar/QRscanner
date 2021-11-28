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

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.adapter.RemoveCode;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.converse.ConfirmDeleteCheck;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;

import java.util.ArrayList;
import java.util.List;

public class TheNextFragmentDelete extends TheNextFragmentBs implements InterfaceConstan, RemoveCode.OnClickListener, View.OnClickListener, ConfirmDeleteCheck.OnClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private RemoveCode deleteCodeListFragmentAdapter;
    private List<TheQrItemCodes> callRecordList = new ArrayList<>();
    private boolean isChecked = true;

    public static Fragment newInstance() {
        return new TheNextFragmentDelete();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delete, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_code_delete_list));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        (rootView.findViewById(R.id.btn_delete)).setOnClickListener(TheNextFragmentDelete.this);
        updateList();

    }

    private void updateList() {
        if (recyclerView != null) {
            try {
                callRecordList = NamedFactoryHlp.getHelper().getQRCodeDAO().getAllItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                deleteCodeListFragmentAdapter = new RemoveCode(listenerActivity, TheNextFragmentDelete.this);
                recyclerView.setAdapter(deleteCodeListFragmentAdapter);
                deleteCodeListFragmentAdapter.setLists(callRecordList);
                deleteCodeListFragmentAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.delete_select_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_check_all:
                deleteCodeListFragmentAdapter.setCheckAllCheckBox(isChecked);
                isChecked = !isChecked;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setStyle() {
    }

    @Override
    public void onClick(TheQrItemCodes callRecordItem, View view, int position) {
        switch (view.getId()) {
            case R.id.root_item_layout:
                listenerActivity.showFragment(TheNextFragmentEdit.newInstance(callRecordItem.getId(), CODE_PREVIEW_LAST_FRAGMENT_DELETE));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                List<TheQrItemCodes> itemsListReturned = deleteCodeListFragmentAdapter.getCheckedList();
                if (itemsListReturned != null && itemsListReturned.size() != 0)
                    ConfirmDeleteCheck.showDeleteCheckCodeConfirmDialog(listenerActivity, itemsListReturned, TheNextFragmentDelete.this);
                break;
        }
    }

    @Override
    public void onClick(List<TheQrItemCodes> callRecordItemList) {
        try {
            if (callRecordItemList != null && callRecordItemList.size() != 0) {
                for (TheQrItemCodes callRecordItem : callRecordItemList) {
                    NamedFactoryHlp.getHelper().getQRCodeDAO().deleteItem(callRecordItem.getId());
                }
                updateAdapter();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAdapter() {
        try {
            callRecordList.clear();
            callRecordList = NamedFactoryHlp.getHelper().getQRCodeDAO().getAllItems();
            if (deleteCodeListFragmentAdapter == null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                deleteCodeListFragmentAdapter = new RemoveCode(listenerActivity, TheNextFragmentDelete.this);
                recyclerView.setAdapter(deleteCodeListFragmentAdapter);
            }
            deleteCodeListFragmentAdapter.setLists(callRecordList);
            deleteCodeListFragmentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
