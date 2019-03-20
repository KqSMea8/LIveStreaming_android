package com.fanwe.o2o.jshandler;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.dialog.SDProgressDialog;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDDurationBlocker;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveCreateRoomActivity;
import com.fanwe.live.activity.room.LivePushCreaterActivity;
import com.fanwe.live.activity.room.LivePushViewerActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.UserModel;
import com.fanwe.o2o.activity.O2OShoppingLiveMainActivity;
import com.fanwe.o2o.common.O2OShoppingCommonInterface;
import com.fanwe.o2o.constant.Constant;
import com.fanwe.o2o.model.App_shop_getappActModel;
import com.fanwe.o2o.model.App_shop_getvideoActModel;
import com.fanwe.o2o.model.O2OShoppingJsonLiveJsModel;
import com.fanwe.o2o.model.O2OShoppingStartLiveAppJsModel;
import com.fanwe.shop.jshandler.ShopJsHandler;


/**
 * Created by Administrator on 2016/9/24.
 */

public class O2OShoppingLiveJsHander extends ShopJsHandler
{
    private SDDurationBlocker blocker = new SDDurationBlocker();
    private CustomWebView customWebView;

    public O2OShoppingLiveJsHander(Activity activity)
    {
        super(activity);
    }

    public O2OShoppingLiveJsHander(Activity activity, CustomWebView customWebView)
    {
        super(activity);
        this.customWebView = customWebView;
    }

    @JavascriptInterface
    @Override
    public void logout()
    {
        super.logout();
        App.getApplication().logout(true, false, false);
    }

    @JavascriptInterface
    public void shopping_join_live(final String json)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                main_shopping_join_live(json);
            }
        });
    }

    private void main_shopping_join_live(String json)
    {
        try
        {
            final O2OShoppingJsonLiveJsModel model = JSON.parseObject(json, O2OShoppingJsonLiveJsModel.class);

            SDConfig.getInstance().setString(R.string.shopping_session_id, model.getSession_id());
            SDConfig.getInstance().setBoolean(R.bool.is_directly_open_the_live_app, false);

            O2OShoppingCommonInterface.requestGetVideo(model.getPodcast_id(), new AppRequestCallback<App_shop_getvideoActModel>()
            {
                private SDProgressDialog dialog = new SDProgressDialog(getActivity());

                @Override
                protected void onStart()
                {
                    super.onStart();
                    dialog.show();
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        final JoinLiveData joinLiveData = new JoinLiveData();
                        int is_small_screen = model.getIs_small_screen();
                        String creater_id = actModel.getCreaterId();
                        String group_id = actModel.getGroupId();
                        int room_id = actModel.getRoomId();
                        String loadingVideoImageUrl = actModel.getLoadingVideoImageUrl();

                        if (actModel.getUser() != null && actModel.getUser().getUser_id().equals(creater_id))
                        {
                            SDToast.showToast("creater_id==user_id");
                        }

                        UserModel.dealLoginSuccess(actModel.getUser(), true);

                        joinLiveData.setIs_small_screen(is_small_screen);
                        joinLiveData.setCreaterId(creater_id);
                        joinLiveData.setGroupId(group_id);
                        joinLiveData.setRoomId(room_id);
                        joinLiveData.setLoadingVideoImageUrl(loadingVideoImageUrl);

                        AppRuntimeWorker.startContext();
                        AppRuntimeWorker.joinLive(joinLiveData, getActivity());
                    }
                    dialog.dismiss();
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                    dialog.dismiss();
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
            SDToast.showToast("数据解析异常");
        }
    }

    @JavascriptInterface
    public void shopping_start_live_app(final String json)
    {
        if (blocker.block())
        {
            return;
        }

        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                main_shopping_start_live_app(json);
            }
        });
    }

    private void main_shopping_start_live_app(String json)
    {
        try
        {
            final O2OShoppingStartLiveAppJsModel model = JSON.parseObject(json, O2OShoppingStartLiveAppJsModel.class);

            SDConfig.getInstance().setString(R.string.shopping_session_id, model.getSession_id());
            SDConfig.getInstance().setBoolean(R.bool.is_directly_open_the_live_app, true);

            O2OShoppingCommonInterface.requestGetApp(new AppRequestCallback<App_shop_getappActModel>()
            {
                private SDDialogProgress dialog = new SDDialogProgress(getActivity());

                @Override
                protected void onStart()
                {
                    super.onStart();
                    dialog.setTextMsg("正在启动直播应用");
                    dialog.show();
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        if (UserModel.dealLoginSuccess(actModel.getUser(), true))
                        {
                            Intent intent = new Intent(getActivity(), O2OShoppingLiveMainActivity.class);
                            startActivity(intent);
                        } else
                        {
                            SDToast.showToast("user保存失败");
                        }
                    }
                }

                @Override
                protected void onFinish(SDResponse resp)
                {
                    super.onFinish(resp);
                    dialog.dismiss();
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
            SDToast.showToast("数据解析异常");
        }
    }

    @JavascriptInterface
    public void shopping_create_live(final String json)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                main_shopping_create_live(json);
            }
        });
    }

    private void main_shopping_create_live(String json)
    {
        try
        {
            final O2OShoppingStartLiveAppJsModel model = JSON.parseObject(json, O2OShoppingStartLiveAppJsModel.class);

            SDConfig.getInstance().setString(R.string.shopping_session_id, model.getSession_id());

            O2OShoppingCommonInterface.requestOpenVideo(new AppRequestCallback<App_shop_getappActModel>()
            {
                private SDProgressDialog dialog = new SDProgressDialog(getActivity());

                @Override
                protected void onStart()
                {
                    super.onStart();
                    dialog.show();
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        UserModel.dealLoginSuccess(actModel.getUser(), true);

                        AppRuntimeWorker.startContext();
                        Intent intent = new Intent(getActivity(), LiveCreateRoomActivity.class);
                        startActivity(intent);
                    }
                    dialog.dismiss();
                }
            });
        } catch (Exception e)
        {
            e.printStackTrace();
            SDToast.showToast("数据解析异常");
        }
    }

    private int state = 0;

    @JavascriptInterface
    public void js_get_live_type()
    {
        if (SDActivityManager.getInstance().containActivity(LivePushCreaterActivity.class))
        {
            state = 1;
        } else if (SDActivityManager.getInstance().containActivity(LivePushViewerActivity.class))
        {
            state = 1;
        }
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                customWebView.loadJsFunction(Constant.JsFunctionName.JS_LIVE_STATE, state);
            }
        }, 1000);
    }

    @JavascriptInterface
    public void js_shopping_comeback_live_app()
    {
        getActivity().finish();
    }

 /*  js_shopping_join_live(json)
    {
        "status":"0" 失败 1成功
        "data":""
    }
    js_shopping_create_live_app
    {
        "status":"0" 失败 1成功
        "data":""
    }

    js_shopping_create_live
    {
        "status":"0" 失败 1成功
        "data":""
    }*/
}
