package com.fanwe.live.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.span.utils.SDPatternUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.Video_check_statusActModel;

import java.util.List;

/**
 * Created by L on 2016/8/28.
 */
public class LiveVideoChecker
{
    private Activity activity;
    private int roomId;
    private String strPrivateKey;
    private boolean isPrivateKeyMode;

    public LiveVideoChecker(Activity activity)
    {
        this.activity = activity;
    }

    public void check(int roomId)
    {
        if (roomId <= 0)
        {
            return;
        }
        this.roomId = roomId;
        isPrivateKeyMode = false;
        check();
    }

    public void check(String copyContent)
    {
        if (TextUtils.isEmpty(copyContent))
        {
            return;
        }

        String tag = LiveConstant.LIVE_PRIVATE_KEY_TAG;
        List<Integer> listPosition = SDPatternUtil.findPosition(copyContent, tag);
        if (listPosition == null)
        {
            return;
        }
        if (listPosition.size() != 2)
        {
            return;
        }

        String key = copyContent.substring(copyContent.indexOf(tag) + tag.length(), copyContent.lastIndexOf(tag));
        if (TextUtils.isEmpty(key))
        {
            return;
        }

        this.strPrivateKey = key;
        SDOtherUtil.copyText("");

        isPrivateKeyMode = true;
        check();
    }

    private void check()
    {
        if (activity == null)
        {
            return;
        }

        tryCheck();
    }

    protected void tryCheck()
    {
        CommonInterface.requestCheckVideoStatus(roomId, strPrivateKey, new AppRequestCallback<Video_check_statusActModel>()
        {

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    int newRoomId = actModel.getRoom_id();
                    int oldRoomId = LiveInformation.getInstance().getRoomId();

                    if (actModel.getLive_in() == 1)
                    {
                        if (oldRoomId > 0)
                        {
                            if (LiveInformation.getInstance().isCreater())
                            {
                                // 当前正在直播
                                if (isPrivateKeyMode)
                                {
                                    // 如果是检测密钥触发
                                } else
                                {
                                    // 如果是点击推送触发
                                    SDToast.showToast("正在直播中，退出后才可以进入别的直播间");
                                }
                            } else
                            {
                                // 当前正在观看直播
                                if (oldRoomId == newRoomId)
                                {
                                    if (isPrivateKeyMode)
                                    {
                                        // 如果是检测密钥触发
                                    } else
                                    {
                                        // 如果是点击推送触发
                                        SDToast.showToast("已经在直播间中");
                                    }
                                } else
                                {
                                    showCheckDialog(actModel);
                                }
                            }
                        } else
                        {
                            showCheckDialog(actModel);
                        }
                    } else
                    {
                        showCheckDialog(actModel);
                    }
                }
            }
        });
    }

    private void showCheckDialog(final Video_check_statusActModel actModel)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(activity);
        dialog.setTextContent(actModel.getContent()).setTextCancel("取消");
        if (actModel.getLive_in() == 1)
        {
            dialog.setTextConfirm("立即查看");
        } else
        {
            dialog.setTextConfirm("立即前往");
        }
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                clickConfirm(actModel);
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }
        }).show();
    }

    protected void clickConfirm(Video_check_statusActModel actModel)
    {
        if (actModel.getLive_in() == 1)
        {
            // TODO 跳到直播页面
            JoinLiveData data = new JoinLiveData();
            data.setRoomId(actModel.getRoom_id());
            data.setGroupId(actModel.getGroup_id());
            data.setCreaterId(actModel.getUser_id());
            data.setLoadingVideoImageUrl(actModel.getLive_image());
            data.setPrivateKey(strPrivateKey);
            data.setSdkType(actModel.getSdk_type());
            AppRuntimeWorker.joinLive(data, activity);
        } else
        {
            // TODO 跳到用户首页
            Intent intent = new Intent(activity, LiveUserHomeActivity.class);
            intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, actModel.getUser_id());
            activity.startActivity(intent);
        }
    }


}
