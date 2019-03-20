package com.fanwe.live.adapter.viewholder.privatechat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.utils.SDDialogUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.dialog.PrivateChatLongClickMenuDialog;
import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.CustomMsgPrivateText;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MsgTextLeftViewHolder extends PrivateChatViewHolder
{

    private TextView tv_text;
    private LinearLayout ll_content;
    private SDSpannableStringBuilder sb = new SDSpannableStringBuilder();

    public MsgTextLeftViewHolder(View itemView)
    {
        super(itemView);

        tv_text = find(R.id.tv_text);
        ll_content = find(R.id.ll_content);
    }

    @Override
    protected void bindCustomMsg(int position, CustomMsg customMsg)
    {
        final CustomMsgPrivateText msg = (CustomMsgPrivateText) customMsg;

        ll_content.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                final PrivateChatLongClickMenuDialog dialog = new PrivateChatLongClickMenuDialog(getAdapter().getActivity());
                dialog.setItems("复制");
                dialog.setItemClickCallback(new SDItemClickCallback<String>()
                {
                    @Override
                    public void onItemClick(int position, String item, View view)
                    {
                        SDOtherUtil.copyText(msg.getText());
                        SDToast.showToast("已复制");
                        dialog.dismiss();
                    }
                });
                SDDialogUtil.setDialogTopAlignCenter(dialog, v, 10, 0);
                dialog.show();
                return false;
            }
        });

        // 文字
        sb.clear();
        sb.append(msg.getText());
        tv_text.setText(sb);
    }
}
