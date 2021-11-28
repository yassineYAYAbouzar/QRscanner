package com.abc.theqrscannerprofree.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.helper.MyIconHlp;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.constant.TheColorsConst;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;

import java.util.ArrayList;
import java.util.List;

public class RemoveCode extends RecyclerView.Adapter<RemoveCode.ViewHolder> implements InterfaceConstan, TheColorsConst {
    private final OnClickListener onclick;
    private final int displayHeight;
    private List<TheQrItemCodes> itemsList;
    private Context context;
    private int displayWidth;

    boolean[] checked;


    public interface OnClickListener{
        void onClick(TheQrItemCodes callRecordItem, View view, int position);
    }

    public RemoveCode(Context context, OnClickListener onclcik) {
        this.onclick = onclcik;
        this.itemsList = new ArrayList<>();
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();
    }

    public void setLists(List<TheQrItemCodes> itemsList){
        this.itemsList = new ArrayList<>();
        this.itemsList.addAll(itemsList);
        checked = new boolean[itemsList.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(context);
        final View view = holder.view;
        final TextView callName = (TextView) view.findViewById(R.id.call_name);
        final TextView callTime = (TextView) view.findViewById(R.id.call_time);
        final ImageView callContactPhoto = (ImageView) view.findViewById(R.id.img_call_contact_photo);

        callName.setTextColor(context.getResources().getColor(R.color.color_list_text_color));
        callTime.setTextColor(context.getResources().getColor(R.color.color_list_text_desc_color));

        callName.setText(itemsList.get(position).getRecordName());
        callTime.setText(itemsList.get(position).getDesc());

        callContactPhoto.setImageDrawable(MyIconHlp.getCodeTypeIcon(context, itemsList.get(position).getRecordType()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view, position);
            }
        });

        holder.mCheckBox.setChecked(checked[position]);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
            }
        });

        (view.findViewById(R.id.root_item_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view.findViewById(R.id.root_item_layout), position);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (itemsList != null)
            return itemsList.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mCheckBox = (CheckBox) v.findViewById(R.id.delete_checkbox);
        }
    }

    public boolean[] getChecked() {
        return checked;
    }

    public List<TheQrItemCodes> getCheckedList() {
        List<TheQrItemCodes> itemsListReturned = new ArrayList<>();
        for (int i = 0; i < itemsList.size(); i++) {
            if (checked[i])
                itemsListReturned.add(itemsList.get(i));
        }

        return itemsListReturned;
    }

    public void setCheckAllCheckBox(boolean isChecked) {
        for (int i = 0; i < itemsList.size(); i++) {
            checked[i] = isChecked;
        }
        notifyDataSetChanged();
    }

}

