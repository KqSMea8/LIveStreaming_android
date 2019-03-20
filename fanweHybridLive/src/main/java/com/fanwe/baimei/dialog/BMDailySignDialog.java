package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fanwe.baimei.adapter.BMDailyTasksAdapter;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.model.BMDailyTaskResponseModel;
import com.fanwe.baimei.model.BMDailyTasksAwardAcceptResponseModel;
import com.fanwe.baimei.model.BMDailyTasksListModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveSignAdapter;
import com.fanwe.live.adapter.LiveTipoffTypeAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_tipoff_typeActModel;
import com.fanwe.live.model.App_tipoff_typeModel;
import com.fanwe.live.model.SignDailyModel;
import com.fanwe.shortvideo.model.SignModel;
import com.scottsu.stateslayout.StatesLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名: com.fanwe.baimei.dialog
 * 描述: 每日任务弹窗
 * 作者: Su
 * 创建时间: 2017/5/25 11:28
 **/
public class BMDailySignDialog extends SDDialogBase {
    private Button button_sign;
    private GridView gridView;
    private ImageView mCloseImageView;
    private LiveSignAdapter adapter;
    List<SignDailyModel.ListBean>list=new ArrayList<>();
    public BMDailySignDialog(Activity activity,SignDailyModel model) {
        super(activity);
        if(null!=model.getList()&&model.getList().size()>0){
            list.clear();
            list.addAll(model.getList());
        }
        init(model);
    }

    public BMDailySignDialog(Activity activity, int theme,SignDailyModel model) {
        super(activity, theme);
        init(model);
    }

    private void init(SignDailyModel model) {
        setCanceledOnTouchOutside(true);
        setAnimations(R.style.anim_top_top);
        setContentView(R.layout.bm_dialog_daily_sign);
        padding(0, 0, 0, 0);
        button_sign = (Button) findViewById(R.id.btn_sign);
        gridView = (GridView) findViewById(R.id.gv_content);
        adapter = new LiveSignAdapter(getContext(),list);
        setListViewHeight();
        gridView.setNumColumns(4);
        gridView.setAdapter(adapter);
        getCloseImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (has_sign_today) {
                    SDToast.showToast("今天已经签到了");
                } else {
                    requestSign();
                }
            }
        });
        bindData(model);
    }
    boolean has_sign_today = true;
    private void bindData(SignDailyModel model) {
        if (null!=model&&model.getList().size()>0) {
            if (1 == model.getNow_is_sign()) {
                has_sign_today = true;
                button_sign.setBackgroundResource(R.drawable.btn_corner_5dp_gray);
                button_sign.setText("已签到");
            } else
                has_sign_today = false;
        }
    }
    private void requestData() {
        CommonInterface.requestSignDayList(new AppRequestCallback<SignDailyModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    bindData(actModel);
                    list.clear();
                    list.addAll(actModel.getList());
                    adapter.notifyDataSetChanged();
                    setListViewHeight();
                } else {
                    SDToast.showToast("列表为空");
                }
            }
        });
    }
    private void requestSign() {
        CommonInterface.requestSignDay(new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("签到成功！");
                    requestData();
                } else {
                    SDToast.showToast(actModel.getError());
                }
            }
        });
    }

    private void setListViewHeight() {
        if (adapter == null) {
            return;
        }
        if (adapter.getCount() <= 0) {
            return;
        }
        View itemView = adapter.getView(0, null, gridView); //获取其中的一项
        //进行这一项的测量，为什么加这一步，具体分析可以参考 https://www.jianshu.com/p/dbd6afb2c890这篇文章
        itemView.measure(0, 0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = adapter.getCount() / 4;//得到总的项数
        if (adapter.getCount() % 4 != 0) {
            itemCount++;
        }
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if (itemCount <= 2) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * itemCount);
        } else if (itemCount > 2) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 2);
        }
        gridView.setLayoutParams(layoutParams);
    }

    private ImageView getCloseImageView() {
        if (mCloseImageView == null) {
            mCloseImageView = (ImageView) findViewById(R.id.iv_close_bm_dialog_daily_tasks);
        }
        return mCloseImageView;
    }


}
