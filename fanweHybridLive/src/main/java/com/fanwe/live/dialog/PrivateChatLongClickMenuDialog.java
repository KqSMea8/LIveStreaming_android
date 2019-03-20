package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.live.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2.
 */
public class PrivateChatLongClickMenuDialog extends SDDialogBase
{
    private LinearLayout ll_content;
    private SDSimpleAdapter<String> adapter;
    private SDItemClickCallback<String> itemClickCallback;

    public PrivateChatLongClickMenuDialog(Activity activity)
    {
        super(activity, R.style.dialogBase);
        init();
    }

    private void init()
    {
        setContentView(R.layout.dialog_private_chat_long_click_menu);
        setCanceledOnTouchOutside(true);
        paddings(0);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
    }

    public void setItemClickCallback(SDItemClickCallback<String> itemClickCallback)
    {
        this.itemClickCallback = itemClickCallback;
    }

    public void setItems(String... data)
    {
        List<String> listModel = Arrays.asList(data);

        adapter = new SDSimpleAdapter<String>(listModel, getOwnerActivity())
        {
            @Override
            public int getLayoutId(int position, View convertView, ViewGroup parent)
            {
                return R.layout.item_private_chat_long_click_menu;
            }

            @Override
            public void bindData(final int position, View convertView, ViewGroup parent, final String model)
            {
                TextView textView = get(R.id.tv_content, convertView);
                textView.setText(model);

                convertView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (itemClickCallback != null)
                        {
                            itemClickCallback.onItemClick(position, model, v);
                        }
                    }
                });
            }
        };
        bindData();
    }

    private void bindData()
    {
        ll_content.removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++)
        {
            View view = adapter.getView(i, null, ll_content);
            if (view != null)
            {
                ll_content.addView(view);
            }
        }
    }
}
