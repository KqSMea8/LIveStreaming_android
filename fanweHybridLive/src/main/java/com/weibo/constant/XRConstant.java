package com.weibo.constant;

import android.os.Environment;

import java.io.File;

/**
 * @包名 com.fanwe.xianrou.constant
 * @描述
 * @作者 Su
 * @创建时间 2017/3/19 11:02
 **/
public class XRConstant
{
    public static final String PATH_SAVE_IMAGE = Environment.getExternalStorageDirectory() + File.separator + "fanwe" + File.separator + "image";

    /*分页请求首页坐标*/
    public static final int REQUEST_FIRST_PAGE = 1;

    /*一次最大上传图片数*/
    public static final int MAX_PHOTO_UPLOAD_COUNT = 9;

    /*用户聊天价格下限*/
    public static final int FLOOR_USER_CHAT_PRICE = 0;

    /*用户聊天价格上限*/
    public static final int CEILING_USER_CHAT_PRICE = 100;

    public static final String KEY_EXTRA_USER_ID = "KEY_EXTRA_USER_ID";
    public static final String KEY_EXTRA_DYNAMIC_ID = "KEY_EXTRA_DYNAMIC_ID";
    public static final String KEY_EXTRA_DYNAMIC_POSITION = "KEY_EXTRA_DYNAMIC_POSITION";
    public static final String KEY_EXTRA_URL_USER_DYNAMIC_GOODS = "KEY_EXTRA_URL_USER_DYNAMIC_GOODS";
    public static final String KEY_EXTRA_NOTICE_ID = "KEY_EXTRA_NOTICE_ID";
    public static final String KEY_EXTRA_ALIPAY_ACCOUNT = "KEY_EXTRA_ALIPAY_ACCOUNT";
    public static final String KEY_EXTRA_ALIPAY_NAME = "KEY_EXTRA_ALIPAY_NAME";
    public static final String KEY_EXTRA_ALIPAY_BINDING_IS_EDIT = "KEY_EXTRA_ALIPAY_BINDING_IS_EDIT";


    /*用户个人中心展示图片*/
    public static final int REQUEST_CODE_PICK_PHOTO_FOR_USER_CENTER_SHOWCASE = 1000;

    /*用户个人中心顶部背景图片*/
    public static final int REQUEST_CODE_PICK_PHOTO_FOR_USER_CENTER_TOP_BACKGROUND = 1001;

    public static final class PayResultStatus
    {
        //支付成功 1, 支付中 2,支付失败 3 取消支付4 网络原因 5 其他原因 6
        public static final int PAYRESULT_STATUS_SUCCESS = 1;
        public static final int PAYRESULT_STATUS_DEALING = 2;
        public static final int PAYRESULT_STATUS_FAIL = 3;
        public static final int PAYRESULT_STATUS_CANCEL = 4;
        public static final int PAYRESULT_STATUS_NETWORK = 5;
        public static final int PAYRESULT_STATUS_OTHER = 6;
    }

    public static final float DIALOG_PADDING_PERCENT_OF_SCREEN = 0.1f;

    public static final class UserAuthenticationStatus
    {
        // 0指未认证 1指待审核 2指认证 3指审核不通过
        public static final String UNAUTHENTICATED = "0";
        public static final String AUTHENTICATING = "1";
        public static final String AUTHENTICATED = "2";
        public static final String AUTHENTICATION_REJECTED = "3";

        public static final int UNAUTHENTICATED_INT = Integer.valueOf(UNAUTHENTICATED);
        public static final int AUTHENTICATING_INT = Integer.valueOf(AUTHENTICATING);
        public static final int AUTHENTICATED_INT = Integer.valueOf(AUTHENTICATED);
        public static final int AUTHENTICATION_REJECTED_INT = Integer.valueOf(AUTHENTICATION_REJECTED);
    }

    public static final class DetailTypeCate
    {
        public static final String TYPE_CATE_IMAGETEXT = "imagetext";
        public static final String TYPE_CATE_RED_PHOTO = "red_photo";
        public static final String TYPE_CATE_WEIXIN = "weixin";
        public static final String TYPE_CATE_VIDEO = "video";
        public static final String TYPE_CATE_GOODS = "goods";
        public static final String TYPE_CATE_PHOTO = "photo";
    }
}
