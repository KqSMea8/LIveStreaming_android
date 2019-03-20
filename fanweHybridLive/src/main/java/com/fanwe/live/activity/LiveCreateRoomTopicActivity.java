package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.adapter.http.handler.SDRequestHandler;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveTopicListAdapter.TopicClickListener;
import com.fanwe.live.appview.LiveTopicView;
import com.fanwe.live.model.LiveTopicModel;

import org.xutils.view.annotation.ViewInject;

public class LiveCreateRoomTopicActivity extends BaseActivity implements OnEditorActionListener ,TextWatcher {


    @ViewInject(R.id.tv_ok)
    private TextView tv_ok;
    @ViewInject(R.id.view_topic)
    private LiveTopicView view_topic;
    @ViewInject(R.id.et_topic)
    private EditText mEditText;
    @ViewInject(R.id.rl_back)
    private View rl_back;

    private SDRequestHandler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_create_room_topic);
        initView();
        view_topic.search(null);
    }



    private void initView() {
        tv_ok.setOnClickListener(this);
        rl_back.setOnClickListener(this);
        mEditText.setOnEditorActionListener(this);
        mEditText.addTextChangedListener(this);
        view_topic.setOnTopicClickListener(new TopicClickListener() {

            @Override
            public void onTopicClick(LiveTopicModel model) {
                //获取话题返回上一页面
                returnWithData(model.getTitle(),model.getCate_id());
            }
        });
    }

    /**
     * 传递数据返回创建直播间界面
     * @param topic 话题
     * @param cateId 话题ID 话题列表的话题直接使用原有ID，手动填写的话题不论该话题是否存在，ID均使用 0；
     */
    private void returnWithData(String topic,int cateId) {
        Intent data = new Intent(this,LiveCreateRoomActivity.class);
        data.putExtra(LiveCreateRoomActivity.EXTRA_TITLE, topic);
        data.putExtra(LiveCreateRoomActivity.EXTRA_CATE_ID, cateId);
        startActivity(data);
    }

    private String getTopicContent() {
        return mEditText.getText().toString();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ok:
                String topic = getTopicContent().trim();
                if(topic.equals("")) {
                    SDToast.showToast("请输入话题");
                    return;
                }
                returnWithData("#" + topic + "#",0);
                break;
            case R.id.rl_back :
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(mHandler != null) {
            mHandler.cancel();
        }
        mHandler = view_topic.search(mEditText.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
