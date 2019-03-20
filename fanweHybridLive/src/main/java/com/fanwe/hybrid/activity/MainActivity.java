package com.fanwe.hybrid.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.baofoo.sdk.vip.BaofooPayActivity;
import com.fanwe.hybrid.common.CommonOpenSDK;
import com.fanwe.hybrid.constant.Constant.JsFunctionName;
import com.fanwe.hybrid.constant.Constant.LoginSdkType;
import com.fanwe.hybrid.constant.Constant.PaymentType;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.dialog.BotPhotoPopupView;
import com.fanwe.hybrid.dialog.DialogCropPhoto;
import com.fanwe.hybrid.dialog.DialogCropPhoto.OnCropBitmapListner;
import com.fanwe.hybrid.event.EApns;
import com.fanwe.hybrid.event.EClipBoardText;
import com.fanwe.hybrid.event.ECutPhoto;
import com.fanwe.hybrid.event.EIsExistInstalled;
import com.fanwe.hybrid.event.EJsLoginSuccess;
import com.fanwe.hybrid.event.ELoginSdk;
import com.fanwe.hybrid.event.EPaySdk;
import com.fanwe.hybrid.event.ERefreshReload;
import com.fanwe.hybrid.event.EShareSdk;
import com.fanwe.hybrid.event.ETencentLocationAddress;
import com.fanwe.hybrid.event.ETencentLocationMap;
import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.jshandler.AppJsHandler;
import com.fanwe.hybrid.listner.PayResultListner;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode.Geo2addressListener;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.hybrid.model.CutPhotoModel;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.model.PaySdkModel;
import com.fanwe.hybrid.model.SdkShareModel;
import com.fanwe.hybrid.umeng.UmengPushManager;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.hybrid.utils.IntentUtil;
import com.fanwe.hybrid.utils.SDImageUtil;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.UriFileUtils;
import com.fanwe.library.webview.CustomWebView;
import com.fanwe.library.webview.DefaultWebChromeClient;
import com.fanwe.library.webview.DefaultWebViewClient;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EWxPayResultCodeComplete;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.wxapi.WXPayEntryActivity;
import com.fanwe.o2o.event.EO2ORefreshH5HomePage;
import com.fanwe.o2o.event.EO2OShoppingLiveMainExist;
import com.fanwe.o2o.jshandler.O2OShoppingLiveJsHander;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject.ReverseAddressResult;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.rtmp.TXLiveBase;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.fanwe.hybrid.jshandler.AppJsHandler.REQUEST_CODE_WEB_ACTIVITY;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-1-5 下午4:08:24 类说明
 */
@SuppressWarnings("deprecation")
public class MainActivity extends BaseActivity implements OnCropBitmapListner, PayResultListner
{
    public static final String SAVE_CURRENT_URL = "url";

    private static final String mPath = "/sdcard/myImage/";
    private static final String mFileName = "avatar.jpg";

    public static final String EXTRA_URL = "extra_url";

    private static final int FILECHOOSER_RESULTCODE = 1;// 选择照片
    private static final int REQUEST_CODE_UPAPP_SDK = 10;// 银联支付
    private static final int REQUEST_CODE_BAOFOO_SDK_RZ = 100;// 宝付支付
    private final static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 200;

    @ViewInject(R.id.ll_fl)
    private FrameLayout mll_fl;
    @ViewInject(R.id.cus_webview)
    private CustomWebView mWebViewCustom;
    @ViewInject(R.id.iv_wel)
    private ImageView iv_wel;

    private BotPhotoPopupView mBotPhotoPopupView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private String mCameraFilePath;
    private String mCurrentUrl;
    private CutPhotoModel mCut_model;
    private SDTencentGeoCode mGeoCode;

    private boolean isFirstLoad = true;
    private boolean isFirstLoadCompleteWhenLoginSuccess = true;

    private String index_url;//首页地址

    /**
     * 销毁保存当前URL
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        String url = mWebViewCustom.getUrl();
        savedInstanceState.putString(SAVE_CURRENT_URL, url);
    }

    /**
     * 恢复时候加载销毁时候的URL
     */
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        String url = savedInstanceState.getString(SAVE_CURRENT_URL);
        mWebViewCustom.get(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        mIsExitApp = true;
        x.view().inject(this);
        init();
        String sdkver = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkver);
    }

    private void init()
    {
        mHandler = new Handler();
        mWebViewCustom.addJavascriptInterface(new O2OShoppingLiveJsHander(this, mWebViewCustom));
        mGeoCode = new SDTencentGeoCode(this);
        getIntentInfo();
        initWebView();
    }

    private void getIntentInfo()
    {
        if (getIntent().hasExtra(EXTRA_URL))
        {
            mCurrentUrl = getIntent().getStringExtra(EXTRA_URL);
        }
    }

    private void initWebView()
    {
        if (!TextUtils.isEmpty(mCurrentUrl))
        {
            this.index_url = mCurrentUrl;
        } else
        {
            InitActModel model = InitActModelDao.query();
            String site_url = model.getSite_url();
            if (!TextUtils.isEmpty(site_url))
            {
                this.index_url = site_url;
            }
        }

        mWebViewCustom.setWebViewClient(new DefaultWebViewClient()
        {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                failingUrl = "file:///android_asset/error_network.html";
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                if (url.contains("show_prog=1"))
                {
                    showProgressDialog("");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                mCurrentUrl = url;
                dismissProgressDialog();
                putCookieSP(url);

                if (!isFirstLoadCompleteWhenLoginSuccess)
                {
                    isFirstLoadCompleteWhenLoginSuccess = true;
                    onFirstLoadCompleteWhenLoginSuccess();
                }

                if (isFirstLoad)
                {
                    isFirstLoad = false;
                    SDViewUtil.setGone(iv_wel);
                }
            }
        });

        mWebViewCustom.setWebChromeClient(new DefaultWebChromeClient()
        {
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
            {
                if (mUploadMessage != null)
                    return;
                mUploadMessage = uploadFile;
                startActivityForResult(createDefaultOpenableIntent(), FILECHOOSER_RESULTCODE);
            }

            /**
             * 5.0+
             */
            public boolean onShowFileChooser(WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams)
            {
                mUploadMessageForAndroid5 = filePathCallback;
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");

                startActivityForResult(chooserIntent,
                        FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
                return true;
            }
        });

        mWebViewCustom.get(index_url);

    }

    private void putCookieSP(String url)
    {
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        if (cookie != null)
        {
            SDConfig.getInstance().setString(R.string.config_webview_cookie, cookie);
        }
    }

    private Intent createDefaultOpenableIntent()
    {
        Intent intentSysAction = IntentUtil.openSysAppAction();
        mCameraFilePath = IntentUtil.getCamerFilePath();
        Intent chooser = IntentUtil.createChooserIntent(IntentUtil.createCameraIntent());
        chooser.putExtra(Intent.EXTRA_INTENT, intentSysAction);
        return chooser;
    }

    /**
     * 先定位在反编译地址 isStartAddressReverse 是否反解析地址
     */
    private void startLocation(final boolean isStartAddressReverse)
    {
        SDTencentMapManager.getInstance().startLocation(new TencentLocationListener()
        {
            @Override
            public void onStatusUpdate(String arg0, int arg1, String arg2)
            {

            }

            @Override
            public void onLocationChanged(TencentLocation location, int error, String reason)
            {
                if (location != null)
                {
                    final double lat = location.getLatitude();
                    final double lng = location.getLongitude();
                    mWebViewCustom.loadJsFunction(JsFunctionName.JS_POSITION, lat, lng);

                    if (isStartAddressReverse)
                    {
                        startAddressRe(lat, lng);
                    }
                } else
                {
                    SDToast.showToast("定位失败");
                }
            }

        });
    }

    /**
     * 反解析地址
     */
    private void startAddressRe(final double lat, final double lng)
    {
        mGeoCode.location(new LatLng(lat, lng)).geo2address(new Geo2addressListener()
        {
            @Override
            public void onSuccess(ReverseAddressResult result)
            {
                if (result.formatted_addresses != null)
                {
                    Map<String, String> map = new HashMap<String, String>();

                    String nation = result.ad_info.nation;
                    String province = result.ad_info.province;
                    String city = result.ad_info.city;
                    String district = result.ad_info.district;
                    String adcode = result.ad_info.adcode;
                    String recommend = result.formatted_addresses.recommend;

                    map.put("nation", nation);
                    map.put("province", province);
                    map.put("city", city);
                    map.put("district", district);
                    map.put("adcode", adcode);
                    map.put("recommend", recommend);
                    String json = JSON.toJSONString(map);

                    mWebViewCustom.loadJsFunction(JsFunctionName.JS_POSITION2, lat, lng, json);
                }
            }

            @Override
            public void onFailure(String msg)
            {
                SDToast.showToast("解析地址失败");
            }
        });
    }

    private void clickll_head()
    {
        if (mBotPhotoPopupView != null)
        {
            if (mBotPhotoPopupView.isShowing())
            {
                mBotPhotoPopupView.dismiss();
            } else
            {
                mBotPhotoPopupView.showAtLocation(mll_fl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
            }
        } else
        {
            mBotPhotoPopupView = new BotPhotoPopupView(this);
            mBotPhotoPopupView.showAtLocation(mll_fl, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        }
    }

    public void openSDKPAY(PaySdkModel model)
    {
        String payCode = model.getPay_sdk_type();
        if (!TextUtils.isEmpty(payCode))
        {
            if (PaymentType.UPAPP.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payUpApp(model, this, this);
            } else if (PaymentType.BAOFOO.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payBaofoo(model, this, REQUEST_CODE_BAOFOO_SDK_RZ, this);
            } else if (PaymentType.ALIPAY.equalsIgnoreCase(payCode))
            {
                CommonOpenSDK.payAlipay(model, this, this);
            } else if (PaymentType.WXPAY.equals(payCode))
            {
                CommonOpenSDK.payWxPay(model, this, this);
            }
        } else
        {
            SDToast.showToast("payCode为空");
            onOther();
        }

    }

    private void openShare(SdkShareModel model)
    {
        String title = model.getShare_title();
        String content = model.getShare_title();
        String imageUrl = model.getShare_imageUrl();
        String clickUrl = model.getShare_url();
        final String key = model.getShare_key();

        UmengSocialManager.openShare(title, content, imageUrl, clickUrl, this, new UMShareListener()
        {

            @Override
            public void onStart(SHARE_MEDIA share_media)
            {

            }

            @Override
            public void onResult(SHARE_MEDIA platform)
            {
                SDToast.showToast(platform + " 分享成功啦");
                mWebViewCustom.loadJsFunction(JsFunctionName.SHARE_COMPLEATE, key);
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t)
            {
                SDToast.showToast(platform + " 分享失败啦");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform)
            {
                SDToast.showToast(platform + " 分享取消了");
            }
        });
    }

    @Override
    public void callbackbitmap(Bitmap bit)
    {
        String base64img = SDImageUtil.bitmapToBase64(bit);
        base64img = "data:image/jpg;base64," + base64img;
        mWebViewCustom.loadJsFunction(JsFunctionName.CUTCALLBACK, base64img);
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case FILECHOOSER_RESULTCODE:
                fileChooserResultcode(data, resultCode);
                break;
            case REQUEST_CODE_BAOFOO_SDK_RZ:
                requestCodeBaofooSdkRz(data);
                break;
            case REQUEST_CODE_UPAPP_SDK:
                requestUpAppSdk(data);
                break;
            case REQUEST_CODE_WEB_ACTIVITY:
                requestCodeWebActivity(data, resultCode);
                break;
            case BotPhotoPopupView.REQUEST_CODE_TAKE_PHOTO:
                requestCodeTakePhoto(data, resultCode);
                break;
            case BotPhotoPopupView.REQUEST_CODE_SELECT_PHOTO:
                requestCodeSelectPhoto(data, resultCode);
                break;
            case AppJsHandler.REQUEST_CODE_QR:
                if (resultCode == MyCaptureActivity.RESULT_CODE_SCAN_SUCCESS)
                {
                    String str_qr = data.getExtras().getString(MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING);
                    mWebViewCustom.loadJsFunction(JsFunctionName.JS_QR_CODE_SCAN, str_qr);
                }
                break;
            case FILECHOOSER_RESULTCODE_FOR_ANDROID_5:
                if (null == mUploadMessageForAndroid5)
                    return;
                SDHandlerManager.getBackgroundHandler().post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        LogUtil.i("FILECHOOSER_RESULTCODE_FOR_ANDROID_5");
                        Uri result = (data == null || resultCode != Activity.RESULT_OK) ? null
                                : data.getData();
                        if (result != null)
                        {
                            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
                        } else
                        {
                            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                        }
                        mUploadMessageForAndroid5 = null;
                    }
                });
                break;
        }
    }

    private void fileChooserResultcode(Intent data, int resultCode)
    {
        if (null == mUploadMessage)
            return;
        Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
        if (result == null && data == null && resultCode == Activity.RESULT_OK)
        {
            File cameraFile = new File(mCameraFilePath);
            if (cameraFile.exists())
            {
                result = Uri.fromFile(cameraFile);
                // Broadcast to the media scanner that we have a new photo
                // so it will be added into the gallery for the user.
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
            }
        }
        if (result == null)
        {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
            return;
        }
        String file_path = UriFileUtils.getPath(this, result);
        if (TextUtils.isEmpty(file_path))
        {
            mUploadMessage.onReceiveValue(null);
            mUploadMessage = null;
            return;
        }
        Uri uri = Uri.fromFile(new File(file_path));

        mUploadMessage.onReceiveValue(uri);
        mUploadMessage = null;
    }

    private void requestCodeBaofooSdkRz(Intent data)
    {
        String result_baofoo = "";
        String msg = "";
        if (data == null || data.getExtras() == null)
        {
            msg = "支付已被取消";
            onCancel();
        } else
        {

            msg = data.getExtras().getString(BaofooPayActivity.PAY_MESSAGE);
            if (!TextUtils.isEmpty(msg))
            {
                SDToast.showToast(msg);
            }
            // -1:失败，0:取消，1:成功，10:处理中
            result_baofoo = data.getExtras().getString(BaofooPayActivity.PAY_RESULT);

            if ("1".equals(result_baofoo))
            {
                onSuccess();
            } else if ("-1".equals(result_baofoo))
            {
                onFail();
            } else if ("0".equals(result_baofoo))
            {
                onCancel();
            } else if ("10".equals(result_baofoo))
            {
                onDealing();
            } else
            {
                onOther();
            }

        }
    }

    private void requestCodeWebActivity(Intent data, int resultCode)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (data != null && data.hasExtra(EXTRA_URL))
            {
                String url = data.getExtras().getString(EXTRA_URL);
                mWebViewCustom.get(url);
            } else
            {
                onCancel();
            }
        }
    }

    private void requestCodeTakePhoto(Intent data, int resultCode)
    {
        if (resultCode == RESULT_OK)
        {
            String path = BotPhotoPopupView.getmTakePhotoPath();
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            dealImageSize(bitmap);
        }
    }

    private void dealImageSize(Bitmap bitmap)
    {
        SDImageUtil.dealImageCompress(mPath, mFileName, bitmap, 100);
        File file = new File(mPath, mFileName);
        if (file != null && file.exists())
        {
            DialogCropPhoto dialog = new DialogCropPhoto(this, file.getPath(), this, mCut_model);
            dialog.show();
        }
        onDismissPop();
    }

    private void requestCodeSelectPhoto(Intent data, int resultCode)
    {
        if (resultCode == RESULT_OK)
        {
            String path = SDImageUtil.getImageFilePathFromIntent(data, this);
            DialogCropPhoto dialog = new DialogCropPhoto(this, path, this, mCut_model);
            dialog.show();
            onDismissPop();
        }
    }

    private void requestUpAppSdk(Intent data)
    {
        // 银联支付回调
        if (data != null)
        {
            Bundle bundle = data.getExtras();
            if (bundle != null)
            {
                /*
                 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
				 */
                String payResult = bundle.getString("pay_result");
                if ("success".equalsIgnoreCase(payResult))
                {
                    SDToast.showToast("支付成功");
                    onSuccess();
                } else if ("fail".equalsIgnoreCase(payResult))
                {
                    SDToast.showToast("支付失败");
                    onFail();
                } else if ("cancel".equalsIgnoreCase(payResult))
                {
                    onCancel();
                } else
                {
                    onOther();
                }
            }
        }
    }

    private void onDismissPop()
    {
        if (mBotPhotoPopupView != null && mBotPhotoPopupView.isShowing())
        {
            mBotPhotoPopupView.dismiss();
        }
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);

        if (getIntent().hasExtra(EXTRA_URL))
        {
            mCurrentUrl = getIntent().getStringExtra(EXTRA_URL);
            mWebViewCustom.get(mCurrentUrl);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            InitActModel model = InitActModelDao.query();
            if (model != null)
            {
                judgeUrlBack(model);
            } else
            {
                exitApp();
            }
        }
        return true;
    }

    /**
     * 判断url是否要退出应用
     */
    private void judgeUrlBack(InitActModel model)
    {
        String curUrl = mWebViewCustom.getUrl();
        if (model.getTop_url() != null && model.getTop_url().size() > 0)
        {
            ArrayList<String> top_url = model.getTop_url();
            boolean isEqualsUrl = false;
            for (int i = 0; i < top_url.size(); i++)
            {
                String url = top_url.get(i);
                if (curUrl.equals(url))
                {
                    exitApp();
                    isEqualsUrl = true;
                    break;
                } else
                {
                    if (i == top_url.size() - 1)
                    {
                        isEqualsUrl = false;
                    }
                }
            }
            if (!isEqualsUrl)
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_BACK, new Object[]{});
            } else
            {
                isEqualsUrl = false;
            }
        } else
        {
            exitApp();
        }
    }

    private Handler mHandler;

    @Override
    public void onSuccess()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 1);
            }
        });
    }

    @Override
    public void onDealing()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 2);
            }
        });
    }

    @Override
    public void onFail()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 3);
            }
        });
    }

    @Override
    public void onCancel()
    {
        mHandler.post(new Runnable()
        {

            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 4);
            }
        });
    }

    @Override
    public void onNetWork()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 5);
            }
        });
    }

    @Override
    public void onOther()
    {
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                mWebViewCustom.loadJsFunction(JsFunctionName.JS_PAY_SDK, 6);
            }
        });
    }

    /**
     * 判断是否安装该应用
     */
    public void onEventMainThread(EIsExistInstalled event)
    {
        String is_exist_sdk = event.packname;
        int is_exist;
        if (SDPackageUtil.isPackageExist(is_exist_sdk))
        {
            is_exist = 1;
        } else
        {
            is_exist = 0;
        }
        mWebViewCustom.loadJsFunction(JsFunctionName.JS_IS_EXIST_INSTALLED, is_exist_sdk, is_exist);
    }

    /**
     * 推送APNS
     */
    public void onEventMainThread(EApns event)
    {
        String dev_token = UmengPushManager.getPushAgent().getRegistrationId();
        mWebViewCustom.loadJsFunction(JsFunctionName.JS_APNS, "android", dev_token);
    }

    public void onEventMainThread(ELoginSdk event)
    {
        String type = event.type;
        showProgressDialog("");
        if (LoginSdkType.WXLOGIN.equals(type))
        {

        } else if (LoginSdkType.QQLOGIN.equals(type))
        {

        }
    }

    /**
     * 反编译地址
     */
    public void onEventMainThread(ETencentLocationAddress event)
    {
        startLocation(true);
    }

    /**
     * 腾讯定位获取经纬度
     */
    public void onEventMainThread(ETencentLocationMap event)
    {
        startLocation(false);
    }

    /**
     * 获得剪切文本
     */
    public void onEventMainThread(EClipBoardText event)
    {
        String text = event.text;
        mWebViewCustom.loadJsFunction(JsFunctionName.GET_CLIP_BOARD, text);
    }

    /**
     * 剪切图片
     */
    public void onEventMainThread(ECutPhoto event)
    {
        mCut_model = event.model;
        clickll_head();
    }

    /**
     * 支付SDK
     */
    public void onEventMainThread(EPaySdk event)
    {
        openSDKPAY(event.model);
    }

    /**
     * 分享SDK
     */
    public void onEventMainThread(EShareSdk event)
    {
        openShare(event.model);
    }

    /**
     * 重新加载首页
     */
    public void onEventMainThread(ERefreshReload event)
    {
        mWebViewCustom.get(index_url);
    }

    protected void onFirstLoadCompleteWhenLoginSuccess()
    {
        CommonInterface.requestUser_apns(null);
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    CommonInterface.requestUsersig(null);
                }
            }
        });
    }


    /**
     * 登录成功
     *
     * @param event
     */
    public void onEventMainThread(EJsLoginSuccess event)
    {
        isFirstLoadCompleteWhenLoginSuccess = false;
    }

    /*微信支付回调返回信息*/
    public void onEventMainThread(final EWxPayResultCodeComplete event)
    {
        switch (event.WxPayResultCode)
        {
            case WXPayEntryActivity.RespErrCode.CODE_CANCEL:
                onCancel();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_FAIL:
                onFail();
                break;
            case WXPayEntryActivity.RespErrCode.CODE_SUCCESS:
                onSuccess();
                break;
        }
    }

    public void onEventMainThread(EO2OShoppingLiveMainExist event)
    {
        mWebViewCustom.loadJsFunction("js_web_shop_logout()");
    }

    public void onEventMainThread(EUnLogin event)
    {
        mWebViewCustom.loadJsFunction("js_web_shop_logout()");
    }

    public void onEventMainThread(EO2ORefreshH5HomePage event)
    {
        mWebViewCustom.get(index_url);
    }

}
