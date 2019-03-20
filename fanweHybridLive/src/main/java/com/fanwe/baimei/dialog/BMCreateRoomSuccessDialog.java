package com.fanwe.baimei.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fanwe.baimei.model.BMGameCreateRoomResponseModel;
import com.fanwe.live.R;

/**
 * 包名: com.fanwe.baimei.dialog
 * 描述: 成功创建房间后信息弹窗
 * 作者: Su
 * 创建时间: 2017/5/17 10:53
 **/
public class BMCreateRoomSuccessDialog extends BMBaseCommonDialog
{
    private TextView mRoomCodeTextView;
    private Button mInviteButton;
    private View mEnterRoomButton;
    private BMCreateRoomSuccessDialogCallback mCallback;
    private BMGameCreateRoomResponseModel mModel;


    public BMCreateRoomSuccessDialog(Activity activity)
    {
        super(activity);

        setTitleText(getContext().getString(R.string.success_create));

        initListener();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        if (mModel != null)
        {
//            getRoomCodeTextView().setText(mModel.getRoom_id());
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getRoomCodeTextView().setText("");
    }

    @Override
    protected int getContentLayoutId()
    {
        return R.layout.bm_dialog_create_room_success;
    }

    private void initListener()
    {
        getInviteButton().setOnClickListener(this);
        getEnterRoomButton().setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (getInviteButton() == v)
        {
            getCallback().onInviteClick(v);
        } else if (getEnterRoomButton() == v)
        {
            getCallback().onEnterRoomClick(v);
        }

    }

    public BMGameCreateRoomResponseModel getModel()
    {
        return mModel;
    }

    public BMCreateRoomSuccessDialog setModel(BMGameCreateRoomResponseModel model)
    {
        mModel = model;

        getRoomCodeTextView().setText(model.getPrivate_key()+"");

        return this;
    }

    private TextView getRoomCodeTextView()
    {
        if (mRoomCodeTextView == null)
        {
            mRoomCodeTextView = (TextView) findViewById(R.id.tv_room_code);
        }
        return mRoomCodeTextView;
    }

    private Button getInviteButton()
    {
        if (mInviteButton == null)
        {
            mInviteButton = (Button) findViewById(R.id.btn_invite);
        }
        return mInviteButton;
    }

    private View getEnterRoomButton()
    {
        if (mEnterRoomButton == null)
        {
            mEnterRoomButton = findViewById(R.id.ll_enter_room);
        }
        return mEnterRoomButton;
    }

    private BMCreateRoomSuccessDialogCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new BMCreateRoomSuccessDialogCallback()
            {
                @Override
                public void onEnterRoomClick(View view)
                {

                }

                @Override
                public void onInviteClick(View view)
                {

                }
            };
        }
        return mCallback;
    }

    public void setCallback(BMCreateRoomSuccessDialogCallback callback)
    {
        mCallback = callback;
    }

    public interface BMCreateRoomSuccessDialogCallback
    {
        void onEnterRoomClick(View view);

        void onInviteClick(View view);
    }

}
