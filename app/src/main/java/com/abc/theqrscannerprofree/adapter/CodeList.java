package com.abc.theqrscannerprofree.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.helper.NamedFactoryHlp;
import com.abc.theqrscannerprofree.db.ItemFormCode;
import com.abc.theqrscannerprofree.constant.InterfaceConstan;
import com.abc.theqrscannerprofree.utils.SharedPreferencesUtilsLikeFlutter;
import java.util.ArrayList;
import java.util.List;

public class CodeList extends RecyclerView.Adapter<CodeList.ViewHolder> implements InterfaceConstan {
    private final OnClickListener onclick;
    private final int displayHeight;
    private List<ItemFormCode> itemsList;
    private Context context;
    private int displayWidth;

    boolean[] checked;


    public interface OnClickListener{
        void onClick(ItemFormCode itemFormCode, View view, int position);
    }

    public CodeList(Context context, OnClickListener onclcik) {
        this.onclick = onclcik;
        this.itemsList = new ArrayList<>();
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();
    }

    public void setLists(List<ItemFormCode> itemsList){
        this.itemsList = new ArrayList<>();
        this.itemsList.addAll(itemsList);
        checked = new boolean[itemsList.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_format, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SharedPreferencesUtilsLikeFlutter.initSharedReferences(context);
        final View view = holder.view;
        final TextView callName = (TextView) view.findViewById(R.id.call_name);

        callName.setTextColor(context.getResources().getColor(R.color.color_list_text_color));
        callName.setText(itemsList.get(position).getFormatName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view, position);
            }
        });

        checked[position] = itemsList.get(position).isActive();

        holder.mCheckBox.setChecked(checked[position]);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
                itemsList.get(position).setActive(checked[position]);
                saveCheck(itemsList.get(position));
            }
        });

        (view.findViewById(R.id.root_item_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked[position] = !checked[position];
                holder.mCheckBox.setChecked(checked[position]);
                itemsList.get(position).setActive(checked[position]);
                saveCheck(itemsList.get(position));
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

   private void saveCheck(ItemFormCode itemFormCode) {
        try {
            NamedFactoryHlp.getHelper().getDaoCodeForm().update(itemFormCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

