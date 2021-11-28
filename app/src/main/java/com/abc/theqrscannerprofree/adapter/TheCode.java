package com.abc.theqrscannerprofree.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;
import com.abc.theqrscannerprofree.helper.MyIconHlp;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.constant.TheColorsConst;
import com.abc.theqrscannerprofree.helper.NamedPaintHlp;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import java.util.ArrayList;
import java.util.List;

public class TheCode extends RecyclerView.Adapter<TheCode.ViewHolder> implements InterfaceConstan, TheColorsConst {
    private final OnClickListener onclick;
    private final int displayHeight;
    private List<TheQrItemCodes> itemsList;
    private Context context;
    private int displayWidth;


    public interface OnClickListener{
        void onClick(TheQrItemCodes callRecordItem, View view, int position);
    }

    public TheCode(Context context, OnClickListener onclcik) {
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
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_codelist, parent, false);
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

        ((ImageView) view.findViewById(R.id.delete_img)).setImageDrawable(NamedPaintHlp.recolorIcon(context, R.drawable.item_delete, COLOR_ICONS[SharedPreferencesUtilsLikeFlutter.getThemeNumber()]));
        callContactPhoto.setImageDrawable(MyIconHlp.getCodeTypeIcon(context, itemsList.get(position).getRecordType()));


        (view.findViewById(R.id.dots_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view.findViewById(R.id.dots_layout), position);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view, position);
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

        public ViewHolder(View v) {
            super(v);
            view = v;
        }
    }


}

