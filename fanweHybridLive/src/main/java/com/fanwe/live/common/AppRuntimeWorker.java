package com.fanwe.live.common;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.Api_linkModel;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.IMHelper;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.LiveInformation;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveLoginActivity;
import com.fanwe.live.activity.room.LiveKSYPushCreaterActivity;
import com.fanwe.live.activity.room.LivePCPlaybackActivity;
import com.fanwe.live.activity.room.LivePCViewerActivity;
import com.fanwe.live.activity.room.LivePlaybackActivity;
import com.fanwe.live.activity.room.LivePushCreaterActivity;
import com.fanwe.live.activity.room.LivePushViewerActivity;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_InitH5Model;
import com.fanwe.live.model.App_RegionListActModel;
import com.fanwe.live.model.CreateLiveData;
import com.fanwe.live.model.HomeTabTitleModel;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.LiveRoomModel;
import com.fanwe.live.model.PlayBackData;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.LiveNetChecker;

import java.util.List;

import static com.fanwe.library.utils.SDResourcesUtil.getResources;

public class AppRuntimeWorker
{

    /**
     * @return 获得游戏账号余额
     */
    public static String getStrGameAccount()
    {
        return Long.toString(getLonGameAccount());
    }

    /**
     * @return 获得游戏账号余额
     */
    public static long getLonGameAccount()
    {
        UserModel user = UserModelDao.query();
        if (isUseGameCurrency())
        {
            return user.getCoin();
        } else
        {
            return user.getDiamonds();
        }
    }
    /**
     * 获得创建直播分类
     *
     * @return
     */
    public static List<HomeTabTitleModel> getListTags() {
        InitActModel model = InitActModelDao.query();
        if (model != null) {
            return model.getVideo_classified();
        }
        return null;
    }
    /**
     * 创建直播分类是否强制选择
     *
     * @return
     */
    public static int getIs_classify() {
        InitActModel model = InitActModelDao.query();
        if (model != null) {
            return model.getIs_classify();
        }
        return 0;
    }
    /**
     * 获得最大连麦数量
     *
     * @return
     */
    public static int getMaxLinkMicCount()
    {
        int count = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            count = model.getMic_max_num();
        }

        if (count < 0)
        {
            count = 0;
        } else if (count > LiveConstant.MAX_LINK_MIC_COUNT)
        {
            count = LiveConstant.MAX_LINK_MIC_COUNT;
        }
        return count;
    }
    /**
     * 是否开启我的小店
     *
     * @return
     */
    public static int getOpen_podcast_goods()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            return initActModel.getOpen_podcast_goods();
        }
        return 0;
    }

    /**
     * @return 是否用H5打开页面
     */
    public static boolean getIsOpenWebviewMain()
    {
        return getResources().getBoolean(R.bool.is_open_webview_main);
    }

    /**
     * @return 按场付费总开关
     */
    public static int getLive_pay_scene()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            return initActModel.getLive_pay_scene();
        }
        return 0;
    }

    /**
     * @return 按时付费总开关
     */
    public static int getLive_pay_time()
    {
        InitActModel initModel = InitActModelDao.query();
        if (initModel != null)
        {
            return initModel.getLive_pay_time();
        }
        return 0;
    }

    /**
     * 获得视频分辨率
     *
     * @return
     */
    public static int getVideoResolutionType()
    {
        InitActModel actModel = InitActModelDao.query();
        if (actModel != null)
        {
            return actModel.getVideo_resolution_type();
        } else
        {
            return LiveConstant.VideoQualityType.VIDEO_QUALITY_STANDARD;
        }
    }

    /**
     * @return 登录ID
     */
    public static String getLoginUserID()
    {
        String user_id = "";
        UserModel user = UserModelDao.query();
        if (user != null)
        {
            user_id = user.getUser_id();
        }
        return user_id;
    }

    /**
     * 返回im的sdkappid
     *
     * @return
     */
    public static String getSdkappid()
    {
        InitActModel model = InitActModelDao.query();
        if (model == null)
        {
            return null;
        } else
        {
            return model.getSdkappid();
        }
    }

    /**
     * 是否隐藏购物功能
     *
     * @return
     */
    public static int getShopping_goods()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getShopping_goods();
        }
        return 0;
    }

    /**
     * 是否开启私信功能，开关字段由服务器下发
     *
     * @return true-开启
     */
    public static boolean hasPrivateChat()
    {
        InitActModel actModel = InitActModelDao.query();
        if (actModel != null)
        {
            return actModel.getHas_private_chat() == 1;
        }
        return true;
    }

    /**
     * @return 按时付费输入最大值
     */
    public static int getLivePayMax()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getLive_pay_max();
        }
        return 0;
    }

    /**
     * @return 按时付费输入最小值
     */
    public static int getLivePayMin()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getLive_pay_min() <= 0 ? 1 : init.getLive_pay_min();
        }
        return 1;
    }

    /**
     * @return 按场付费输入最大值
     */
    public static int getLivePaySceneMax()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getLive_pay_scene_max();
        }
        return 0;
    }

    /**
     * @return 按场付费输入最小值
     */
    public static int getLivePaySceneMin()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getLive_pay_scene_min() <= 0 ? 1 : init.getLive_pay_scene_min();
        }
        return 1;
    }

    /**
     * 设置页面是否显示推荐按钮
     *
     * @return
     */
    public static int getDistribution_module()
    {
        InitActModel init = InitActModelDao.query();
        if (init != null)
        {
            return init.getDistribution_module();
        }
        return 0;
    }


    /**
     * @return 商品管理URL
     */
    public static String getUrl_podcast_goods()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            App_InitH5Model app_InitH5Model = initActModel.getH5_url();
            if (app_InitH5Model != null)
            {
                return app_InitH5Model.getUrl_podcast_goods();
            }
        }
        SDToast.showToast("url_podcast_goods为空");
        return "";
    }

    //是否开启分销
    public static int getDistribution()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            return initActModel.getDistribution();
        } else
        {
            return 0;
        }
    }

    /**
     * 获得最大连麦数量
     *
     * @return
     */
    public static int getMic_max_num()
    {
        int result = 0;

        InitActModel actModel = InitActModelDao.query();
        if (actModel != null)
        {
            result = actModel.getMic_max_num();
        }
        if (result <= 0)
        {
            result = 3;
        }

        return result;
    }

    //是否付费模式
    public static int getLive_pay()
    {
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            return initActModel.getLive_pay();
        } else
        {
            return 0;
        }
    }

    /**
     * 是否开启vip模块
     *
     * @return
     */
    public static boolean isOpenVip()
    {
        InitActModel actModel = InitActModelDao.query();
        return actModel != null && actModel.getOpen_vip() == 1;
    }

    /**
     * 是否使用游戏币，true-游戏币，false-秀豆
     *
     * @return
     */
    public static boolean isUseGameCurrency()
    {
        InitActModel actModel = InitActModelDao.query();
        return actModel != null && actModel.getOpen_game_module() == 1 && actModel.getOpen_diamond_game_module() == 0;
    }

    /**
     * 获得游戏币单位
     *
     * @return
     */
    public static String getGameCurrencyUnit()
    {
        if (isUseGameCurrency())
        {
            return SDResourcesUtil.getString(R.string.game_currency);
        } else
        {
            return "秀豆";
        }
    }

    /**
     * 是否打开私聊送游戏币
     *
     * @return
     */
    public static boolean isOpenSendCoinsModule()
    {
        InitActModel actModel = InitActModelDao.query();
        return actModel != null && actModel.getOpen_send_coins_module() == 1;
    }

    /**
     * 是否开启游戏上庄功能
     *
     * @return
     */
    public static boolean isOpenBankerModule()
    {
        InitActModel actModel = InitActModelDao.query();
        return actModel != null && actModel.getOpen_banker_module() == 1;
    }

    //竞拍协议
    public static String getUrl_auction_agreement()
    {
        String url = "";
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            App_InitH5Model h5Model = initActModel.getH5_url();
            if (h5Model != null)
            {
                url = h5Model.getUrl_auction_agreement();
            }
        }
        return url;
    }

    //帮助和反馈
    public static String getUrl_help_feedback()
    {
        String url = "";
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            App_InitH5Model h5Model = initActModel.getH5_url();
            if (h5Model != null)
            {
                url = h5Model.getUrl_help_feedback();
            }
        }
        return url;
    }

    //关于我们
    public static String getUrl_about_us()
    {
        String url = "";
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            App_InitH5Model h5Model = initActModel.getH5_url();
            if (h5Model != null)
            {
                url = h5Model.getUrl_about_we();
            }
        }
        return url;
    }


    //我的等级
    public static String getUrl_my_grades()
    {
        String url = "";
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            App_InitH5Model h5Model = initActModel.getH5_url();
            if (h5Model != null)
            {
                url = h5Model.getUrl_my_grades();
            }
        }
        return url;
    }


    /**
     * 是否开启Oss上传图片
     *
     * @return 0 不开启 1 开启
     */
    public static int getOpen_sts()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getOpen_sts();
        } else
        {
            return 0;
        }
    }

    /**
     * 是否隐藏我的家族
     *
     * @return 0 不显示 1 显示
     */
    public static int getOpen_family_module()
    {
        if (getFamily_btn() == 1)
            return 1;
        else
            return 0;
    }

    public static int getFamily_btn()
    {
        int family_btn = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            family_btn = model.getOpen_family_module();
        }
        return family_btn;
    }

    /**
     * 是否隐藏我的公会
     *
     * @return 0 不显示 1 显示
     */
    public static int getOpen_sociaty_module()
    {
        if (getSociaty_btn() == 1)
            return 1;
        else
            return 0;
    }

    public static int getSociaty_btn()
    {
        int sociaty_btn = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            sociaty_btn = model.getOpen_society_module();
        }
        return sociaty_btn;
    }

    /**
     * 是否隐藏所有竞拍功能
     *
     * @return
     */
    public static int getShow_hide_pai_view()
    {
        boolean is_show = getResources().getBoolean(R.bool.show_hide_pai_view);
        if (is_show)
        {
            return 1;
        } else
        {
            InitActModel model = InitActModelDao.query();
            if (model == null)
            {
                return 0;
            }

            return model.getOpen_pai_module();
        }
    }

    /**
     * 直播间是否显示虚拟竞拍按钮
     *
     * @return
     */
    public static int getPai_virtual_btn()
    {
        int pai_virtual_btn = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            pai_virtual_btn = model.getPai_virtual_btn();
        } else
        {
            pai_virtual_btn = SDResourcesUtil.getResources().getInteger(R.integer.pai_virtual_btn);
        }
        return pai_virtual_btn;
    }

    /**
     * 直播间是否显示实物竞拍按钮
     *
     * @return
     */
    public static int getPai_real_btn()
    {
        int pai_real_btn = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            pai_real_btn = model.getPai_real_btn();
        }
        return pai_real_btn;
    }

    /**
     * @return true-可编辑美颜,false-开关模式
     */
    public static boolean isBeautyEditMode()
    {
        boolean result = true;
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            if (initActModel.getBeauty_close() == 1)
            {
                //开关模式
                result = false;
            }
        }
        return result;
    }

    /**
     * 当为1时,启用脏子过滤;默认0时不过滤
     *
     * @return
     */
    public static int getHas_dirty_words()
    {
        int result = 0;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            result = model.getHas_dirty_words();
        }
        return result;
    }

    /**
     * 获得当前城市
     *
     * @return
     */
    public static String getCity_name()
    {
        String cityname = null;

        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            cityname = model.getCity();
        }
        return cityname;
    }

    /**
     * 获得钱票字符串
     *
     * @return
     */
    public static String getTicketName()
    {
        String result = null;
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            result = initActModel.getTicket_name();
        }
        if (TextUtils.isEmpty(result))
        {
            result = SDResourcesUtil.getString(R.string.live_ticket);
        }
        return result;
    }

    /**
     * 获得帐号字符串
     *
     * @return
     */
    public static String getAccountName()
    {
        String result = null;
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            result = initActModel.getAccount_name();
        }
        if (TextUtils.isEmpty(result))
        {
            result = SDResourcesUtil.getString(R.string.live_account);
        }
        return result;
    }

    /**
     * 获得app短名称字符串
     *
     * @return
     */
    public static String getAppShortName()
    {
        String result = null;
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            result = initActModel.getShort_name();
        }
        if (TextUtils.isEmpty(result))
        {
            result = getAppName();
        }
        return result;
    }

    /**
     * 获得app名称字符串
     *
     * @return
     */
    public static String getAppName()
    {
        String result = null;
        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            result = initActModel.getApp_name();
        }
        if (TextUtils.isEmpty(result))
        {
            result = SDResourcesUtil.getString(R.string.app_name);
        }
        return result;
    }

    public static App_RegionListActModel getRegionListActModel()
    {
        App_RegionListActModel actModel = null;
        int localVersion = SDConfig.getInstance().getInt(R.string.config_region_version, 0);
        if (localVersion != 0)
        {
            InitActModel initActModel = InitActModelDao.query();
            if (initActModel != null)
            {
                if (initActModel.getRegion_versions() > localVersion)
                {
                    //需要升级
                } else
                {
                    actModel = SDCache.getObject(App_RegionListActModel.class);
                }
            }
        }
        return actModel;
    }

    public static boolean isLogin(Activity activity)
    {
        boolean result = false;
        UserModel user = UserModelDao.query();
        if (user != null && !TextUtils.isEmpty(user.getUser_id()))
        {
            result = true;
        } else
        {
            result = false;
            if (activity != null)
            {
                Intent intent = new Intent(activity, LiveLoginActivity.class);
                activity.startActivity(intent);
            }
        }
        return result;
    }

    public static String getApiUrl(String ctl, String act)
    {
        if (!TextUtils.isEmpty(ctl) && !TextUtils.isEmpty(act))
        {
            InitActModel model = InitActModelDao.query();
            if (model != null)
            {
                String key = ctl + "_" + act;
                List<Api_linkModel> listApi = model.getApi_link();
                if (listApi != null)
                {
                    for (Api_linkModel api : listApi)
                    {
                        if (key.equals(api.getCtl_act()))
                        {
                            String url = api.getApi();
                            return url;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static String getLiveRoleCreater()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getSpear_live();
        } else
        {
            return "user";
        }
    }

    public static String getLiveRoleViewer()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getSpear_normal();
        } else
        {
            return "user";
        }
    }

    public static String getLiveRoleVideoViewer()
    {
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            return model.getSpear_interact();
        } else
        {
            return "user";
        }
    }

    public static void setUsersig(String usersig)
    {
        if (usersig == null)
        {
            usersig = "";
        }
        SDConfig.getInstance().setString(R.string.config_live_usersig, usersig);
    }

    public static String getUsersig()
    {
        return SDConfig.getInstance().getString(R.string.config_live_usersig, null);
    }

    /**
     * IM登录
     *
     * @return
     */
    public static boolean startContext()
    {
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return false;
        }
        String identifier = user.getUser_id();
        if (TextUtils.isEmpty(identifier))
        {
            SDToast.showToast("用户id为空");
            return false;
        }

        String usersig = getUsersig();
        if (TextUtils.isEmpty(usersig))
        {
            CommonInterface.requestUsersig(null);
            return false;
        }

        IMHelper.loginIM(identifier, usersig);
        return true;
    }

    /**
     * IM退出登录
     */
    public static void logout()
    {
        IMHelper.logoutIM(null);
    }

    /**
     * 打开主播界面
     *
     * @param activity
     */
    public static void openLiveCreaterActivity(Activity activity)
    {
        if (activity != null)
        {
            int sdkType = LiveInformation.getInstance().getSdkType();
            if (sdkType == 0)
            {
                Intent intent = new Intent(activity, LivePushCreaterActivity.class);
                activity.startActivity(intent);
            } else
            {
                Intent intent = new Intent(activity, LiveKSYPushCreaterActivity.class);
                activity.startActivity(intent);
            }
        }
    }

    /**
     * 启动回放界面
     *
     * @param data
     * @param activity
     */
    public static void startPlayback(final PlayBackData data, final Activity activity)
    {
        if (activity == null || data == null)
        {
            return;
        }
        if (LiveInformation.getInstance().getRoomId() > 0)
        {
            SDToast.showToast("当前有视频正在播放");
            return;
        }

        LiveNetChecker.check(activity, new LiveNetChecker.CheckResultListener()
        {
            @Override
            public void onAccepted()
            {
                startPlaybackInside(data, activity);
            }

            @Override
            public void onRejected()
            {
            }
        });
    }

    private static void startPlaybackInside(PlayBackData data, Activity activity)
    {
        Intent intent = new Intent(activity, LivePlaybackActivity.class);
        intent.putExtra(LivePlaybackActivity.EXTRA_ROOM_ID, data.getRoomId());
        activity.startActivity(intent);
    }

    private static void startPCPlayBack(PlayBackData data, Activity activity)
    {
        Intent intent = new Intent(activity, LivePCPlaybackActivity.class);
        intent.putExtra(LivePCPlaybackActivity.EXTRA_ROOM_ID, data.getRoomId());
        activity.startActivity(intent);
    }

    /**
     * 创建直播间
     */
    public static void createLive(final CreateLiveData data, final Activity activity)
    {
        if (!isLogin(activity))
        {
            return;
        }
        if (activity == null && data == null)
        {
            return;
        }

        LiveNetChecker.check(activity, new LiveNetChecker.CheckResultListener()
        {
            @Override
            public void onAccepted()
            {
                createPushLiveInside(data, activity);
            }

            @Override
            public void onRejected()
            {
            }
        });
    }

    /**
     * 创建推流直播间
     */
    private static void createPushLiveInside(CreateLiveData data, Activity activity)
    {
        int sdkType = data.getSdkType();
        if (sdkType == LiveConstant.LiveSdkType.TENCENT)
        {
            Intent intent = new Intent(activity, LivePushCreaterActivity.class);
            intent.putExtra(LivePushCreaterActivity.EXTRA_ROOM_ID, data.getRoomId());
            intent.putExtra(LivePushCreaterActivity.EXTRA_IS_CLOSED_BACK, data.getIsClosedBack());
            activity.startActivity(intent);
        } else if (sdkType == LiveConstant.LiveSdkType.KSY)
        {
            Intent intent = new Intent(activity, LiveKSYPushCreaterActivity.class);
            intent.putExtra(LiveKSYPushCreaterActivity.EXTRA_ROOM_ID, data.getRoomId());
            intent.putExtra(LiveKSYPushCreaterActivity.EXTRA_IS_CLOSED_BACK, data.getIsClosedBack());
            activity.startActivity(intent);
        }

        LogUtil.i("createPushLiveInside:" + data);
    }

    /**
     * 加入直播间
     *
     * @param data
     * @param activity
     */
    public static void joinLive(final JoinLiveData data, final Activity activity)
    {
        if (!isLogin(activity))
        {
            return;
        }
        if (activity == null && data == null)
        {
            return;
        }

        LiveNetChecker.check(activity, new LiveNetChecker.CheckResultListener()
        {
            @Override
            public void onAccepted()
            {
                joinPushLiveInside(data, activity);
            }

            @Override
            public void onRejected()
            {
            }
        });
    }

    /**
     * 加入推流直播间
     */
    private static void joinPushLiveInside(JoinLiveData data, Activity activity)
    {

        UserModel user = UserModelDao.query();
        if (user != null && user.getUser_id().equals(data.getCreaterId()))
        {
            CreateLiveData createLiveData = new CreateLiveData();
            createLiveData.setRoomId(data.getRoomId());
            createLiveData.setIsClosedBack(1);
            createLive(createLiveData, activity);
            return;
        }

        Intent intent = new Intent(activity, LivePushViewerActivity.class);
        intent.putExtra(LivePushViewerActivity.EXTRA_ROOM_ID, data.getRoomId());
        intent.putExtra(LivePushViewerActivity.EXTRA_PRIVATE_KEY, data.getPrivateKey());
        intent.putExtra(LivePushViewerActivity.EXTRA_GROUP_ID, data.getGroupId());
        intent.putExtra(LivePushViewerActivity.EXTRA_CREATER_ID, data.getCreaterId());
        intent.putExtra(LivePushViewerActivity.EXTRA_LOADING_VIDEO_IMAGE_URL, data.getLoadingVideoImageUrl());
        activity.startActivity(intent);
        LogUtil.i("joinPushLiveInside");
    }

    /**
     * 加入推流直播间
     */
    private static void joinPushPCLiveInside(JoinLiveData data, Activity activity)
    {
        Intent intent = new Intent(activity, LivePCViewerActivity.class);
        intent.putExtra(LivePushViewerActivity.EXTRA_ROOM_ID,  data.getRoomId());
        intent.putExtra(LivePushViewerActivity.EXTRA_PRIVATE_KEY, data.getPrivateKey());
        intent.putExtra(LivePushViewerActivity.EXTRA_GROUP_ID, data.getGroupId());
        intent.putExtra(LivePushViewerActivity.EXTRA_CREATER_ID, data.getCreaterId());
        intent.putExtra(LivePushViewerActivity.EXTRA_LOADING_VIDEO_IMAGE_URL, data.getLoadingVideoImageUrl());
        activity.startActivity(intent);
        LogUtil.i("joinPushPCLiveInside");
    }

    /**
     * 加入直播间
     *
     * @param model
     * @param activity
     */
    public static void joinRoom(LiveRoomModel model, Activity activity)
    {
        if (model.getLive_in() == 1)
        {
            //直播
            JoinLiveData data = new JoinLiveData();
            data.setCreaterId(model.getUser_id());
            data.setGroupId(model.getGroup_id());
            data.setLoadingVideoImageUrl(model.getLive_image());
            data.setRoomId(model.getRoom_id());
            data.setSdkType(model.getSdk_type());
            data.setCreate_type(model.getCreate_type());
            if(model.getRoom_type()==9)
            {
                data.setPrivateKey(model.getPassword());
            }
                joinLive(data, activity);
        } else if (model.getLive_in() == 3)
        {
            //回放
            PlayBackData data = new PlayBackData();
            data.setRoomId(model.getRoom_id());
            data.setCreate_type(model.getCreate_type());
            startPlayback(data, activity);
        }
    }
}
