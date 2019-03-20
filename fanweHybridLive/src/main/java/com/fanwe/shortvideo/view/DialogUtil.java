package com.fanwe.shortvideo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.live.R;


/**
 * Created by yuejiaoli on 2017/10/16.
 */

public class DialogUtil {

    public static void showDialog(Context context, String title, String content) {
        final Dialog dialog = new Dialog(context, R.style.ConfirmDialogStyle);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_ugc_tip, null);
        dialog.setContentView(v);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_msg);
        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        tvTitle.setText(title);
        tvContent.setText(content);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
