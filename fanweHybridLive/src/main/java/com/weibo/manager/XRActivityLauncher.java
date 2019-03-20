package com.weibo.manager;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fanwe.hybrid.activity.AppWebViewActivity;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LivePrivateChatActivity;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.shortvideo.appview.mian.VideoSendMsgView;
import com.fanwe.shortvideo.model.VideoCommentListModel;
import com.sunday.eventbus.SDEventManager;
import com.weibo.activity.LiveVideoPlayActivity;
import com.weibo.activity.ViewPagerPhotoActivity;
import com.weibo.model.UpdateFavorite;
import com.weibo.model.XRCommentFavorite;
import com.weibo.model.XRCommentListModel;
import com.weibo.model.XRCommentModel;
import com.weibo.model.XRCommentNetworkImageModel;
import com.weibo.util.RegexUtil;
import com.weibo.widget.XRCommentListDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @包名 com.fanwe.xianrou.manager
 * @描述 界面跳转
 * @作者 Su
 * @创建时间 2017/3/28 17:05
 **/
public class XRActivityLauncher {
    private XRActivityLauncher() {
    }

    public static void launchGallery(@NonNull Activity activity, ArrayList<String> imageModels) {
        Intent intent = new Intent(activity, ViewPagerPhotoActivity.class);
        intent.putStringArrayListExtra("photos", imageModels);
        activity.startActivity(intent);
    }

    public static void launchVideoPlay(@NonNull Activity activity, @NonNull String url,int weibo_id) {
        if (TextUtils.isEmpty(url)) {
            SDToast.showToast("未找到播放地址");
            return;
        }
        if (!RegexUtil.isURL(url)) {
            SDToast.showToast("播放地址出错");
            return;
        }
        Intent intent = new Intent(activity, LiveVideoPlayActivity.class);
        intent.putExtra("video_url", url);
        intent.putExtra("weibo_id", weibo_id);
        activity.startActivity(intent);
    }

    public static void launchUserCenterOthers(@NonNull Activity activity, @NonNull String userId, @NonNull String head_image) {
        Intent intent = new Intent(activity, LiveUserHomeActivity.class);
        intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, userId);
        intent.putExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL, head_image);
        activity.startActivity(intent);
    }

    public static void clickCommnetList(@NonNull Activity activity, final int dynamicId) {
        XRCommentListDialog xrCommentListDialog = new XRCommentListDialog(activity, dynamicId);
        xrCommentListDialog.showBottom();
    }

    public static void clickFavorite(final int weibo_id) {
        CommonInterface.requestDynamicFavorite(2, weibo_id, 0, "", new AppRequestCallback<XRCommentFavorite>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    UpdateFavorite updateFavorite=new UpdateFavorite();
                    updateFavorite.setWeibo_id(weibo_id);
                    updateFavorite.setDigg_count(actModel.getDigg_count());
                    updateFavorite.setHas_digg(actModel.getHas_digg());
                    updateFavorite.setType(UpdateFavorite.FAVORITE);
                    EventBus.getDefault().post(updateFavorite);
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });
    }
}
