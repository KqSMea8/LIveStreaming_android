package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveRechargePayDialogAdapter;
import com.fanwe.live.adapter.LiveRechargeRuleAdapter;
import com.fanwe.live.adapter.LiveShouhuDaysAdapter;
import com.fanwe.live.adapter.LiveShouhuTypeAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.App_rechargeActModel;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.model.PayShouhuModel;
import com.fanwe.live.model.RuleItemModel;
import com.umeng.socialize.view.BaseDialog;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import static com.fanwe.live.R.id.list;
import static com.fanwe.live.R.id.lv_payment;

/**
 * Created by shibx on 2017/3/6.
 * 直播间购买守护窗口{@link com.fanwe.live.dialog.LiveRechargeDialog} 充值视图
 */

public class LiveRechargeShouhuDialog extends SDDialogBase implements LiveShouhuTypeAdapter.OnTypeViewClickListener, LiveShouhuDaysAdapter.OnDaysViewClickListener {

    private GridView lv_type;

    private GridView lv_days;

    private TextView tv_account;

    private LiveShouhuTypeAdapter mAdapterType;
    private LiveShouhuDaysAdapter mAdapterDays;
    private String type_id, days_id;
    private Button btn_ok;
    private Activity activity;
    private PayShouhuModel model;
    private String create_id;
    public LiveRechargeShouhuDialog(Activity activity, PayShouhuModel model,String create_id,buyShuohu buyShuohu) {
        super(activity);
        this.activity = activity;
        this.model = model;
        this.create_id=create_id;
        this.buyShuohu=buyShuohu;
        init();
    }

    private void init() {
        setContentView(R.layout.view_recharge_shouhu);
        paddings(0);
        lv_type = (GridView) findViewById(R.id.lv_type);
        lv_days = (GridView) findViewById(R.id.lv_days);
        tv_account = (TextView) findViewById(R.id.tv_account);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        setCanceledOnTouchOutside(true);
        initGridView();
        initData();
        btn_ok.setOnClickListener(this);
        tv_account.setOnClickListener(this);
    }

    private void initGridView() {
        mAdapterType = new LiveShouhuTypeAdapter(listTypes, getActivity());
        mAdapterType.setOnTypeViewClickListener(this);
        mAdapterDays = new LiveShouhuDaysAdapter(listdays, getActivity());
        mAdapterDays.setOnDaysViewClickListener(this);
        lv_type.setAdapter(mAdapterType);
        lv_days.setAdapter(mAdapterDays);
    }

    private Activity getActivity() {
        return activity;
    }

    private void initData() {
        listTypes = new ArrayList<>();
        tv_account.setText(model.getDiamonds());
        for (PayShouhuModel.GuardRuleListBean bean : model.getGuard_rule_list()) {
            if (null != bean.getRules()) {
                listTypes.add(bean);
            }
        }
        if (listTypes != null && listTypes.size() > 0) {
            mAdapterType.updateData(listTypes);
            type_id = listTypes.get(0).getId();
            listdays = listTypes.get(0).getRules();
        }
        if (listdays != null && listdays.size() > 0) {
            days_id = listdays.get(0).getId();
            listdays.get(0).setChecked(true);
            mAdapterDays.updateData(listdays);
        }

    }

    List<PayShouhuModel.GuardRuleListBean> listTypes;
    List<PayShouhuModel.GuardRuleListBean.RulesBean> listdays;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_ok:
                if(null!=type_id&&null!=days_id){
                    requestPay(type_id,days_id,create_id);
                }else{
                    Toast.makeText(getActivity(),"请选择开通时间！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_account:
                clickRechargeDialog();
                break;
        }
    }

    @Override
    public void onClickDaysView(PayShouhuModel.GuardRuleListBean.RulesBean model) {
        if(!model.getId().equals(days_id)){
            model.setChecked(true);
            for (int i=0;i<listdays.size();i++) {
                if(days_id.equals(listdays.get(i).getId())){
                    listdays.get(i).setChecked(false);
                }
            }
            days_id=model.getId();
            mAdapterDays.updateData(listdays);
        }
    }

    @Override
    public void onClickTypeView(PayShouhuModel.GuardRuleListBean model) {
        if (!model.getId().equals(type_id)) {
            model.setChecked(true);
            for (int i=0;i<listTypes.size();i++) {
                if (type_id.equals(listTypes.get(i).getId())) {
                    listTypes.get(i).setChecked(false);
                    listdays = model.getRules();
                    if (listdays != null && listdays.size() > 0) {
                        days_id = listdays.get(0).getId();
                        for(PayShouhuModel.GuardRuleListBean.RulesBean bean:model.getRules()){
                            bean.setChecked(false);
                        }
                        listdays.get(0).setChecked(true);
                    }
                }
            }
            type_id = model.getId();
            mAdapterType.updateData(listTypes);
            mAdapterDays.updateData(listdays);
        }
    }

    private void requestPay(String guard_id, String rule_id,String anchor_id)
    {
        CommonInterface.requestPay(guard_id,rule_id ,anchor_id, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    Toast.makeText(getActivity(),"开通成功！",Toast.LENGTH_SHORT).show();
                    dismiss();
                    buyShuohu.buySuccess();
                }
            }
        });
    }

    public LiveRechargeShouhuDialog.buyShuohu getBuyShuohu() {
        return buyShuohu;
    }

    public void setBuyShuohu(LiveRechargeShouhuDialog.buyShuohu buyShuohu) {
        this.buyShuohu = buyShuohu;
    }
    private LiveRechargeDialog mLiveRechargeDialog;
    public buyShuohu buyShuohu;
    public interface buyShuohu{
        void buySuccess();
    }
    private void clickRechargeDialog()
    {
        if (mLiveRechargeDialog == null)
        {
            mLiveRechargeDialog = new LiveRechargeDialog(getOwnerActivity());
        }
        mLiveRechargeDialog.showCenter();
    }
}
