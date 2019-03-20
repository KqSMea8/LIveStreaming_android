package com.fanwe.auction.appview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.auction.activity.AuctionSaveCropImageActivity;
import com.fanwe.auction.activity.AuctionSelectDeliveryAddressMapActivity;
import com.fanwe.auction.adapter.ItemImgAdapter;
import com.fanwe.auction.adapter.VirtualGoodsTagAdapter;
import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.ECreateAuctionSuccess;
import com.fanwe.auction.event.ESelectDeliveryAddressSuccessData;
import com.fanwe.auction.model.App_auction_createAuctionModel;
import com.fanwe.auction.model.App_auction_goodsTagsModel;
import com.fanwe.auction.model.GoodsTagsModel;
import com.fanwe.auction.model.ImageModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.map.tencent.SDTencentGeoCode;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.ImageCropManage;
import com.fanwe.live.dialog.LiveTimePickerDialog;
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.utils.PhotoBotShowUtils;
import com.jzxiang.pickerview.listener.OnDateSetDialogListener;
import com.sunday.eventbus.SDEventManager;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.LatLng;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shibx on 2016/8/9.
 */
public class AuctionVirtualGoodsView extends AuctionGoodsBaseView implements TextView.OnEditorActionListener, OnDateSetDialogListener, OptionsPickerView.OnOptionsSelectListener
{

    private LinearLayout ll_layout_img;
    private LinearLayout ll_layout_label;
    private LinearLayout ll_choose_time;
    private LinearLayout ll_auction_location;

    private LinearLayout ll_auction_during;
    private LinearLayout ll_delay_time;
    private LinearLayout ll_delay_times;

    private TextView tv_contact_time;
    private TextView tv_contact_place;
    private EditText et_auction_name;
    private EditText et_contact_name;
    private EditText et_contact_mobile;
    private EditText et_at_least;
    private EditText et_increase;
    private EditText et_deposit;

    private TextView tv_auction_duration;
    private TextView tv_delay_time;
    private TextView tv_delay_times;

    private EditText et_goods_des;

    private List<GoodsTagsModel> mListLabel;
    private List<ImageModel> mListImage;
    private VirtualGoodsTagAdapter mAdapterLabel;
    private ItemImgAdapter mAdapterImg;

    private LiveTimePickerDialog mPickerDialog;
    //默认可选择时长 30天
    private final long PICK_DEFAULT_DEADLINE = 1000 * 60 * 60 * 24L * 30;

    private Date mDateTime;//约会时间
    private String dateType;//约会类型
    private TencentLocation mLocation;//腾讯地图定位
    private PhotoHandler mHandler;
    private int mPicNum;//图片数量
    private boolean isFull;//已添加图片是否等于可添加数量

    private OptionsPickerView mPickerOption;

    private ArrayList<String> mListAuctionDuration;
    private ArrayList<String> mListDelayTime;
    private ArrayList<String> mListDelayTimes;

    private Map<String, Object> mapParams;//调用接口参数集合
    private ArrayList<String> mapPic;//图片
    private Map<String, Object> mapLocation;//地理位置参数集合
    private final String KEY_IS_TRUE = "is_true";//竞拍类型
    private final String KEY_IMGS = "imgs";//图片
    private final String KEY_TAGS = "tags";//标签
    private final String KEY_NAME = "name";//商品名称
    private final String KEY_DESCRIPTION = "description";
    private final String KEY_DATE_TIME = "date_time";
    private final String KEY_DISTRICT = "district";
    private final String KEY_PLACE = "place";
    private final String KEY_CONTACT = "contact";
    private final String KEY_MOBILE = "mobile";
    private final String KEY_QP_DIAMONDS = "qp_diamonds";
    private final String KEY_BZ_DIAMONDS = "bz_diamonds";
    private final String KEY_JJ_DIAMONDS = "jj_diamonds";
    private final String KEY_PAI_TIME = "pai_time";
    private final String KEY_PAI_YANSHI = "pai_yanshi";
    private final String KEY_MAX_YANSHI = "max_yanshi";

    private String flag_option;

    private final int is_true = 0;// 0为虚拟竞拍 1为实物竞拍

    public AuctionVirtualGoodsView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionVirtualGoodsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionVirtualGoodsView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_create_virtual_goods);
        initView();
        initLocation();
    }

    private void initView()
    {
        mPicNum = 5;
        isFull = false;
        mapLocation = new HashMap<>();
        mapParams = new HashMap<>();
        mapPic = new ArrayList<>();

        mListAuctionDuration = new ArrayList<>();
        mListDelayTime = new ArrayList<>();
        mListDelayTimes = new ArrayList<>();
        String[] arrayDuration = {"0.1", "0.2", "0.3", "0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5"};
        String[] arrayTime = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        Collections.addAll(mListAuctionDuration, arrayDuration);
        Collections.addAll(mListDelayTime, arrayTime);
        Collections.addAll(mListDelayTimes, arrayTime);

        initTitle(R.id.title);
        ll_layout_img = find(R.id.ll_layout_img);
        ll_layout_label = find(R.id.ll_layout_label);
        ll_choose_time = find(R.id.ll_choose_time);
        ll_auction_location = find(R.id.ll_auction_location);

        ll_auction_during = find(R.id.ll_auction_duration);
        ll_delay_time = find(R.id.ll_delay_time);
        ll_delay_times = find(R.id.ll_delay_times);

        tv_contact_time = find(R.id.tv_contact_time);
        tv_contact_place = find(R.id.tv_contact_place);
        et_auction_name = find(R.id.et_auction_name);
        et_contact_name = find(R.id.et_contact_name);
        et_contact_mobile = find(R.id.et_contact_mobile);
        et_at_least = find(R.id.et_at_least);
        et_increase = find(R.id.et_increase);
        et_deposit = find(R.id.et_deposit);

        tv_auction_duration = find(R.id.tv_auction_duration);
        tv_delay_time = find(R.id.tv_delay_time);
        tv_delay_times = find(R.id.tv_delay_times);

        tv_auction_duration.setText(mListAuctionDuration.get(2));
        tv_delay_time.setText(mListDelayTime.get(2));
        tv_delay_times.setText(mListDelayTimes.get(2));

        et_goods_des = find(R.id.et_goods_des);
        initGoodsImage();
        requestTagsData();
        mDateTime = new Date();
        tv_contact_time.setText(SDDateUtil.mil2yyyyMMddHHmm(mDateTime.getTime()));
        ll_choose_time.setOnClickListener(this);
        ll_auction_location.setOnClickListener(this);

        ll_auction_during.setOnClickListener(this);
        ll_delay_time.setOnClickListener(this);
        ll_delay_times.setOnClickListener(this);

        et_auction_name.setOnEditorActionListener(this);
        et_contact_name.setOnEditorActionListener(this);
        et_contact_mobile.setOnEditorActionListener(this);
        et_goods_des.setOnEditorActionListener(this);
    }

    @Override
    protected void initTitleText()
    {
        mTitle.setMiddleTextTop("虚拟商品拍卖设置");
        mTitle.getItemRight(0).setTextBot("发布");
    }

    @Override
    protected void clickTitleLeft()
    {
        //退出，有提示
        showDialog("确定放弃新增竞拍商品？", "确定", "取消");
    }

    @Override
    protected void clickTitleMid()
    {

    }

    @Override
    protected void clickTitleRight()
    {
        //发布
        //检查参数完整性
        if (!isParamsComplete())
        {
            return;
        }
        showProgressDialog("商品正在发布");
        AuctionCommonInterface.requestAddAuction(mapParams, new AppRequestCallback<App_auction_createAuctionModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    ECreateAuctionSuccess event = new ECreateAuctionSuccess();
                    event.pai_id = actModel.getPai_id();
                    SDEventManager.post(event);
                    SDToast.showToast("商品发布成功！");
                    getActivity().finish();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                if (resp.getThrowable() != null)
                {
                    SDToast.showToast("网络错误");
                }
            }
        });
    }

    @Override
    protected void clickDialogConfirm()
    {
        getActivity().finish();
    }

    private void initLocation()
    {
        SDTencentMapManager.getInstance().startLocation(new TencentLocationListener()
        {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int error, String s)
            {
                if (TencentLocation.ERROR_OK == error)
                {
                    // 定位成功

                    new SDTencentGeoCode(getActivity()).location(new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude())).geo2address(new SDTencentGeoCode.Geo2addressListener()
                    {
                        @Override
                        public void onSuccess(Geo2AddressResultObject.ReverseAddressResult result)
                        {
                            tv_contact_place.setText(result.formatted_addresses.recommend);
                        }

                        @Override
                        public void onFailure(String msg)
                        {
                            SDToast.showToast("获取地址失败");
                        }

                    });
                    if (mLocation == null)
                    {
                        mLocation = tencentLocation;
                    }

                } else
                {
                    // 定位失败
                    //SDToast.showToast(s);
                    tv_contact_place.setText("定位失败");
                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1)
            {

            }
        });
    }

    private void setLocationParams(TencentLocation tencentLocation)
    {
        mapLocation.clear();
        mapLocation.put("province", tencentLocation.getProvince());
        mapLocation.put("city", tencentLocation.getCity());
        mapLocation.put("area", tencentLocation.getDistrict());
        mapLocation.put("lng", tencentLocation.getLongitude());
        mapLocation.put("lat", tencentLocation.getLatitude());
    }

    @Override
    protected boolean isParamsComplete()
    {
        if (mapPic.size() == 0)
        {
            SDToast.showToast("请添加商品图片");
            return false;
        }
        if (isEtEmpty(et_auction_name))
        {
            SDToast.showToast("请为拍品命名");
            return false;
        }
        if ((mDateTime.getTime() - new Date().getTime()) / (1000 * 60) < 5)
        {
            SDToast.showToast("约定时间至少须比现在晚5分钟");
            return false;
        }
        if (mLocation == null)
        {
            SDToast.showToast("定位失败，请重新定位");
            return false;
        }
        if (TextUtils.isEmpty(tv_contact_place.getText().toString()) || "正在获取地址".equals(tv_contact_place.getText().toString()))
        {
            SDToast.showToast("你需要一个约定地点");
            return false;
        }
        if (isEtEmpty(et_contact_name))
        {
            SDToast.showToast("请填写联系人");
            return false;
        }
        if (isEtEmpty(et_contact_mobile))
        {
            SDToast.showToast("请填写联系电话");
            return false;
        }
        if (isEtEmpty(et_at_least) || Integer.valueOf(getEtContent(et_at_least)) == 0)
        {
            SDToast.showToast("请设定一个起拍价");
            return false;
        }
        if (isEtEmpty(et_deposit) || Integer.valueOf(getEtContent(et_deposit)) == 0)
        {
            SDToast.showToast("请填写保证金额");
            return false;
        }
        if (isEtEmpty(et_increase) || Integer.valueOf(getEtContent(et_increase)) == 0)
        {
            SDToast.showToast("请填写加价幅度");
            return false;
        }
        if (isEtEmpty(et_goods_des))
        {
            SDToast.showToast("请为拍品增加简要描述");
            return false;
        }
        setLocationParams(mLocation);
        addParams();
        return true;
    }

    private void addParams()
    {
        mapParams.clear();
        mapParams.put(KEY_IS_TRUE, is_true);
        mapParams.put(KEY_IMGS, SDJsonUtil.object2Json(mapPic));
        if (TextUtils.isEmpty(dateType))
        {
            mapParams.put(KEY_TAGS, dateType);
        }
        mapParams.put(KEY_NAME, getEtContent(et_auction_name));
        mapParams.put(KEY_DATE_TIME, SDDateUtil.getYYmmddhhmmssFromDate(mDateTime));
        mapParams.put(KEY_DISTRICT, SDJsonUtil.object2Json(mapLocation));
        mapParams.put(KEY_PLACE, tv_contact_place.getText().toString());
        mapParams.put(KEY_CONTACT, getEtContent(et_contact_name));
        mapParams.put(KEY_MOBILE, getEtContent(et_contact_mobile));
        mapParams.put(KEY_QP_DIAMONDS, Integer.valueOf(getEtContent(et_at_least)));
        mapParams.put(KEY_BZ_DIAMONDS, Integer.valueOf(getEtContent(et_deposit)));
        mapParams.put(KEY_JJ_DIAMONDS, Integer.valueOf(getEtContent(et_increase)));

        mapParams.put(KEY_PAI_TIME, Double.valueOf(tv_auction_duration.getText().toString()));
        mapParams.put(KEY_PAI_YANSHI, Integer.valueOf(tv_delay_time.getText().toString()));
        mapParams.put(KEY_MAX_YANSHI, Integer.valueOf(tv_delay_times.getText().toString()));

        mapParams.put(KEY_DESCRIPTION, getEtContent(et_goods_des));
    }

    /**
     * 类型通过接口获取
     */
    private void requestTagsData()
    {
        mListLabel = new ArrayList<>();
        showProgressDialog("");
        AuctionCommonInterface.requestVirtualGoodsTag(new AppRequestCallback<App_auction_goodsTagsModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    mListLabel = actModel.getData().getList();
                    if (mListLabel != null && mListLabel.size() > 0)
                    {
                        ll_layout_label.setVisibility(View.VISIBLE);
                        initAdapter();
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                if (resp.getThrowable() != null)
                {

                }
            }
        });
    }

    private void initAdapter()
    {
        mAdapterLabel = new VirtualGoodsTagAdapter(mListLabel, getActivity());
        mAdapterLabel.getSelectManager().setMode(SDSelectManager.Mode.SINGLE);
//        mAdapterLabel.getSelectManager().setSelected(0,true);//默认选中第一项
//        dateType = mAdapterLabel.getSelectManager().getSelectedItem().getName();

        mAdapterLabel.setItemClickCallback(new SDItemClickCallback<GoodsTagsModel>()
        {
            @Override
            public void onItemClick(int position, GoodsTagsModel item, View view)
            {
                mAdapterLabel.getSelectManager().performClick(item);
                dateType = item.getName();
            }
        });
        getAllLabel();
    }

    private void getAllLabel()
    {
        for (int i = 0; i < mListLabel.size(); i++)
        {
            View view = mAdapterLabel.getView(i, null, ll_layout_label);
            if (view != null)
            {
                ll_layout_label.addView(view);
            }
        }
    }

    private void initGoodsImage()
    {
        mListImage = new ArrayList<>();
        mHandler = new PhotoHandler((FragmentActivity) getActivity());
        mListImage.add(new ImageModel(null));
        mAdapterImg = new ItemImgAdapter(mListImage, getActivity());
        mAdapterImg.setOnItemRemoveListener(new ItemImgAdapter.OnItemRemoveListener()
        {
            @Override
            public void onRemove(int position, ImageModel item, View view)
            {
                if (isFull)
                {
                    mListImage.set(mListImage.size() - 1, new ImageModel(null));
                    isFull = false;
                } else if (mListImage.size() == 1)
                {
                    mListImage.set(0, new ImageModel(null));
                } else
                {
                    mListImage.remove(position);
                }
                //移除参数内的该图片
                // if(mapPic.containsKey(item.getUri())) {
                mapPic.remove(position);
                // }
                mAdapterImg.notifyDataSetChanged();
                getAllGoodsImage();
            }
        });
        mAdapterImg.setItemClickCallback(new SDItemClickCallback<ImageModel>()
        {
            @Override
            public void onItemClick(int position, ImageModel item, View view)
            {
                PhotoBotShowUtils.openBotPhotoView(getActivity(), mHandler, PhotoBotShowUtils.DIALOG_ALBUM);
            }
        });
        getAllGoodsImage();
        mHandler.setListener(new PhotoHandler.PhotoHandlerListener()
        {
            @Override
            public void onResultFromAlbum(File file)
            {
                openCropAct(file);
            }

            @Override
            public void onResultFromCamera(File file)
            {
                openCropAct(file);
            }

            @Override
            public void onFailure(String msg)
            {
            }
        });
    }

    private void openCropAct(File file)
    {
        if (AppRuntimeWorker.getOpen_sts() == 1)
        {
            ImageCropManage.startCropActivity(getActivity(), file.getAbsolutePath());
        } else
        {
            Intent intent = new Intent(getActivity(), AuctionSaveCropImageActivity.class);
            intent.putExtra(AuctionSaveCropImageActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
            intent.putExtra(AuctionSaveCropImageActivity.EXTRA_TYPE, 1);
            getActivity().startActivity(intent);
        }
    }

    /**
     * 获取所有图片
     */
    private void getAllGoodsImage()
    {
        ll_layout_img.removeAllViews();
        for (int i = 0; i < mListImage.size(); i++)
        {
            View view = mAdapterImg.getView(i, null, ll_layout_img);
            if (view != null)
            {
                ll_layout_img.addView(view);
            }
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        SDKeyboardUtil.hideKeyboard(v);
        switch (v.getId())
        {
            case R.id.ll_choose_time:
                if (mPickerDialog == null)
                {
                    initTimePicker();
                }
                mPickerDialog.showBottom();
                break;
            case R.id.ll_auction_duration:
                chooseAuctionDuration();
                break;
            case R.id.ll_delay_time:
                chooseDelayTime();
                break;
            case R.id.ll_delay_times:
                chooseDelayTimes();
                break;
            case R.id.ll_auction_location:
                Intent intent = new Intent(getActivity(), AuctionSelectDeliveryAddressMapActivity.class);
                getActivity().startActivityForResult(intent, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker()
    {
        mPickerDialog = new LiveTimePickerDialog.Builder(getActivity())
                .setCallBack(this)
                .setTitleStringId("请选择约会时间")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + PICK_DEFAULT_DEADLINE)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.main_color))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.text_item_content))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.main_color))
                .build();
    }

    private void chooseAuctionDuration()
    {
        flag_option = KEY_PAI_TIME;
        if (mPickerOption == null)
        {
            initOptionPicker();
        }
        mPickerOption.setPicker(mListAuctionDuration);
        mPickerOption.setCyclic(false);
        mPickerOption.setLabels("小时");
        mPickerOption.setTitle("选择竞拍时长");
        mPickerOption.show();
    }

    private void chooseDelayTime()
    {

        flag_option = KEY_PAI_YANSHI;
        if (mPickerOption == null)
        {
            initOptionPicker();
        }
        mPickerOption.setPicker(mListDelayTime);
        mPickerOption.setCyclic(false);
        mPickerOption.setLabels("分钟");
        mPickerOption.setTitle("选择竞拍延时");
        mPickerOption.show();
    }

    private void chooseDelayTimes()
    {
        flag_option = KEY_MAX_YANSHI;
        if (mPickerOption == null)
        {
            initOptionPicker();
        }
        mPickerOption.setPicker(mListDelayTimes);
        mPickerOption.setCyclic(false);
        mPickerOption.setLabels("次");
        mPickerOption.setTitle("选择延时次数");
        mPickerOption.show();
    }

    private void initOptionPicker()
    {
        mPickerOption = new OptionsPickerView(getActivity());
        mPickerOption.setOnoptionsSelectListener(this);
        mPickerOption.setCancelable(true);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(activity, requestCode, resultCode, data);
        mHandler.onActivityResult(requestCode, resultCode, data);
        ImageCropManage.onActivityResult(getActivity(), requestCode, resultCode, data);
    }

    public void onEventMainThread(ESelectDeliveryAddressSuccessData event)
    {
        tv_contact_place.setText(event.address);
        mLocation = event.location;
    }

    /**
     * 接收图片选择回传地址事件
     *
     * @param event
     */
    public void onEventMainThread(EUpLoadImageComplete event)
    {
        if (!TextUtils.isEmpty(event.server_full_path))
        {
            mapPic.add(event.server_path);

            if (mListImage.size() == mPicNum)
            {
                mListImage.set(mListImage.size() - 1, new ImageModel(event.server_full_path));
                isFull = true;
            } else
            {
                mListImage.add(mListImage.size() - 1, new ImageModel(event.server_full_path));
            }
            mAdapterImg.notifyDataSetChanged();
            getAllGoodsImage();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        SDKeyboardUtil.hideKeyboard(v);
        if (event == null)
        {
            return true;
        }
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    @Override
    public void onOptionsSelect(int i, int i1, int i2)
    {
        if (flag_option.equals(KEY_PAI_TIME))
        {
            tv_auction_duration.setText(mListAuctionDuration.get(i));
        } else if (flag_option.equals(KEY_PAI_YANSHI))
        {
            tv_delay_time.setText(mListDelayTime.get(i));
        } else
        {
            tv_delay_times.setText(mListDelayTimes.get(i));
        }
    }

    @Override
    public void onDateSet(Dialog dialog, long millseconds)
    {
        mDateTime = new Date(millseconds);
        tv_contact_time.setText(SDDateUtil.mil2yyyyMMddHHmm(millseconds));
    }
}
