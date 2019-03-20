package com.fanwe.live.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.games.adapter.LiveCreaterPluginAdapter;
import com.fanwe.games.model.App_plugin_initActModel;
import com.fanwe.games.model.PluginModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTagAdapter;
import com.fanwe.live.appview.LiveCreaterPluginToolView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.App_followActModel;
import com.fanwe.live.model.MemoryModel;
import com.fanwe.live.view.CornerTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.fanwe.library.utils.SDResourcesUtil.getResources;

/**
 * 直播间主播插件窗口
 */
public class LiveAddMemoryDialog extends LiveBaseDialog implements View.OnClickListener, LiveTagAdapter.TagClickListener {
    public LiveAddMemoryDialog(Activity activity, String to_user_id) {
        super(activity);
        this.to_user_id = to_user_id;
        init();
    }

    private LiveTagAdapter adapter;
    private RelativeLayout rl_memory1, rl_memory2, rl_memory3;
    private CornerTextView tv_memory1, tv_memory2, tv_memory3;
    private GridView gv_content;
    private ImageView close_left_corner, close1, close2, close3;
    private TextView tv_done, tv_change,tv_desc;
    private String to_user_id;

    private void init() {
        setContentView(R.layout.dialog_live_add_memory);
        paddings(SDViewUtil.dp2px(10));
        setCanceledOnTouchOutside(true);
        gv_content = (GridView) findViewById(R.id.gv_content);
        rl_memory1 = (RelativeLayout) findViewById(R.id.rl_memory1);
        rl_memory2 = (RelativeLayout) findViewById(R.id.rl_memory2);
        rl_memory3 = (RelativeLayout) findViewById(R.id.rl_memory3);
        tv_memory1 = (CornerTextView) findViewById(R.id.tv_memory1);
        tv_memory2 = (CornerTextView) findViewById(R.id.tv_memory2);
        tv_memory3 = (CornerTextView) findViewById(R.id.tv_memory3);
        close_left_corner = (ImageView) findViewById(R.id.iv_close);
        close1 = (ImageView) findViewById(R.id.close1);
        close2 = (ImageView) findViewById(R.id.close2);
        close3 = (ImageView) findViewById(R.id.close3);
        tv_change = (TextView) findViewById(R.id.tv_change);
        tv_done = (TextView) findViewById(R.id.tv_done);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        adapter = new LiveTagAdapter(new ArrayList<MemoryModel.TagListBean>(), getOwnerActivity());
        adapter.setTagClickListener(this);
        close_left_corner.setOnClickListener(this);
        close1.setOnClickListener(this);
        close2.setOnClickListener(this);
        close3.setOnClickListener(this);
        gv_content.setAdapter(adapter);
        tv_done.setOnClickListener(this);
        requestData();
        tv_change.setOnClickListener(this);
    }

    List<MemoryModel.MySelectedBean> list = new ArrayList<>();

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_change:
                requestData();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.close1:
                list.remove(0);
                updateSelectedTag();
                break;
            case R.id.close2:
                list.remove(1);
                updateSelectedTag();
                break;
            case R.id.close3:
                list.remove(2);
                updateSelectedTag();
                break;
            case R.id.tv_done:
                setTags();
                break;
        }
    }

    private void setTags() {
        String tags="";
        for(int i=0;i<list.size();i++){
            if(Integer.parseInt(list.get(i).getId())>0){
                if(i==0){
                    tags=tags+list.get(i).getId();
                }else{
                    tags=tags+","+list.get(i).getId();
                }
            }
        }
        CommonInterface.setTags(to_user_id,tags, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    Toast.makeText(getOwnerActivity(), "添加印象成功！", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }

    boolean has_init_selected;

    private void requestData() {
        CommonInterface.requestTagList(to_user_id, new AppRequestCallback<MemoryModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    if (!has_init_selected) {
                        list.addAll(actModel.getMy_selected());
                        updateSelectedTag();
                    }
                    adapter.clearData();
                    adapter.updateData(actModel.getTag_list());
                    has_init_selected = true;
                }
            }
        });
    }

    private void updateSelectedTag() {
        rl_memory1.setVisibility(View.INVISIBLE);
        rl_memory2.setVisibility(View.INVISIBLE);
        rl_memory3.setVisibility(View.INVISIBLE);
        if (list.size() > 0) {
            tv_desc.setVisibility(View.GONE);
        }else{
            tv_desc.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                rl_memory1.setVisibility(View.VISIBLE);
                tv_memory1.setText(list.get(i).getName());
                tv_memory1.setfilColor(getResources().getColor(R.color.text_content));
                tv_memory1.setTextColor(Color.BLACK);
            } else if (i == 1) {
                rl_memory2.setVisibility(View.VISIBLE);
                tv_memory2.setText(list.get(i).getName());
                tv_memory2.setfilColor(getResources().getColor(R.color.text_content));
                tv_memory2.setTextColor(Color.BLACK);
            } else if (i == 2) {
                rl_memory3.setVisibility(View.VISIBLE);
                tv_memory3.setText(list.get(i).getName());
                tv_memory3.setfilColor(getResources().getColor(R.color.text_content));
                tv_memory3.setTextColor(Color.BLACK);
            }
        }
    }

    @Override
    public void TagClick(MemoryModel.TagListBean model) {
        if (list.size() < 3) {
            if (model.getIs_selected() == 0) {
                model.setIs_selected(1);
                adapter.notifyDataSetChanged();
                MemoryModel.MySelectedBean bean = new MemoryModel.MySelectedBean();
                bean.setName(model.getName());
                bean.setId(model.getId());
                list.add(bean);
                updateSelectedTag();
            }
        } else {
            Toast.makeText(getOwnerActivity(), "至多添加三个印象", Toast.LENGTH_SHORT).show();
        }
    }
}
