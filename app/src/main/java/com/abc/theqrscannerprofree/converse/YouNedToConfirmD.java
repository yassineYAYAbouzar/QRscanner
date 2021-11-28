package com.abc.theqrscannerprofree.converse;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.abc.theqrscannerprofree.R;
import com.abc.theqrscannerprofree.db.TheQrItemCodes;

public class YouNedToConfirmD {
    private static View view;
    private static TextView textView;
    private static OnClickListener mOnClick;

    public interface OnClickListener {
        void onDeleteClick(TheQrItemCodes theQrItemCodes);
    }

    public static void showDeleteCodeConfirmDialog(final Context context, final TheQrItemCodes ignoreContactItem, OnClickListener onClick) {
        mOnClick = onClick;
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(getView(context))
                .setPositiveButton(context.getString(R.string.apply_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnClick.onDeleteClick(ignoreContactItem);
                    }
                }).setCancelable(false)

                .setNegativeButton(context.getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).create();

        alert.show();
    }

    private static View getView(Context context) {
        view = View.inflate(context, R.layout.dialog_confirm, null);

        textView = (TextView) view.findViewById(R.id.edit_text_pass);
        textView.setText(context.getString(R.string.dialog_delete_code_confirm));
        return view;
    }

}
