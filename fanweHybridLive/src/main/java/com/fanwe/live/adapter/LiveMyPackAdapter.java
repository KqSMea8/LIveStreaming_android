package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.MyPackListModel;
import com.fanwe.live.model.ShopModel;
import com.fanwe.live.utils.GlideUtil;
import com.sina.weibo.sdk.api.share.Base;

import java.util.ArrayList;

/**
 * @author wxy
 */
public class LiveMyPackAdapter extends SDSimpleRecyclerAdapter<MyPackListModel.MountsListBean> {

    private Activity activity;
    private int tag = 0;

    public LiveMyPackAdapter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void setTag(int tag) {
        this.tag = tag;
        notifyDataSetChanged();
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<MyPackListModel.MountsListBean> holder, final int position, final MyPackListModel.MountsListBean model) {
        ImageView iv = holder.get(R.id.iv_image);
        TextView tv_name = holder.get(R.id.tv_name);
        TextView tv_end_time = holder.get(R.id.tv_end_time);
        Button btn=holder.get(R.id.btn);
        if(model.getIs_expired()==1){
            btn.setText("已到期");
            btn.setBackgroundResource(R.drawable.btn_corner_5dp_gray);
            btn.setEnabled(false);
        }else if(model.getIs_use()==1){
            btn.setText("使用中");
            btn.setBackgroundResource(R.drawable.btn_corner_5dp_green);
        }else if(model.getIs_use()==0){
            btn.setText("装备");
            btn.setBackgroundResource(R.drawable.btn_corner_5dp_red);
        }
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.btnClick(model);
            }
        });

        GlideUtil.load(model.getIcon()).into(iv);
        SDViewBinder.setTextView(tv_name,model.getName());
        SDViewBinder.setTextView(tv_end_time,"到期时间:"+model.getEnd_time_desc());
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_mypack;
    }
    ButtonClickListener buttonClickListener;

    public ButtonClickListener getButtonClickListener() {
        return buttonClickListener;
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public interface ButtonClickListener{
        void btnClick(MyPackListModel.MountsListBean model);
    }
}
