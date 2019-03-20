package com.fanwe.auction.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.ECreateAuctionSuccess;
import com.fanwe.auction.model.App_auction_createAuctionModel;
import com.fanwe.auction.model.App_pai_podcast_addpaidetailActModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shibx on 2016/8/9.
 */
public class AuctionRealGoodsView extends AuctionGoodsBaseView implements OptionsPickerView.OnOptionsSelectListener
{

    private TextView tv_deposit;//参拍保证金
    private TextView tv_increase;//加价幅度
    private LinearLayout ll_auction_duration;
    private TextView tv_auction_duration;//竞拍时间
    private LinearLayout ll_delay_time;
    private TextView tv_delay_time;//延迟时间
    private LinearLayout ll_delay_times;
    private TextView tv_delay_times;//最大延迟次数

    private ImageView iv_item_goods_pic;
    private TextView tv_item_goods_des;
    private TextView tv_item_goods_price;

    private OptionsPickerView mPickerOption;

    private ArrayList<String> mListAuctionDuration;
    private ArrayList<String> mListDelayTime;
    private ArrayList<String> mListDelayTimes;

    private Map<String,Object> mapParams;//调用接口参数集合
    private final String KEY_IS_TRUE = "is_true";//竞拍类型
    private final String KEY_GOODD_ID = "goods_id";//商品ID
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
    private final String KEY_SHOP_ID = "shop_id";
    private final String KEY_SHOP_NAME = "shop_name";

    private String flag_option;

    private final int is_true = 1;// 0为虚拟竞拍 1为实物竞拍

    private List<String> goodPic = new ArrayList<>();//图片列表
    private int goodId;//商品id
    private String goodImg;//图片
    private String goodName;//竞拍名称
    private String qp_diamonds;//起拍价
    private String bz_diamonds;//竞拍保证金
    private int jj_diamonds;//每次加价
    private float pai_time;//竞拍时长
    private int pai_yanshi;//每次竞拍延时（单位分）
    private int max_yanshi;//最大延时(次)
    private int shop_id;
    private String shop_name;

    public AuctionRealGoodsView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionRealGoodsView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionRealGoodsView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_create_real_goods);
        initView();
    }

    private void initView()
    {
        initTitle(R.id.title);
        mapParams = new HashMap<>();
        mListAuctionDuration = new ArrayList<>();
        mListDelayTime = new ArrayList<>();
        mListDelayTimes = new ArrayList<>();

        String [] arrayDuration = {"0.1","0.2","0.3","0.5","1.0","1.5","2.0","2.5","3.0","3.5"};
        String [] arrayTime = {"1","2","3","4","5","6","7","8","9"};

        Collections.addAll(mListAuctionDuration, arrayDuration);
        Collections.addAll(mListDelayTime, arrayTime);
        Collections.addAll(mListDelayTimes, arrayTime);

        tv_deposit = find(R.id.tv_deposit);
        tv_increase = find(R.id.tv_increase);
        ll_auction_duration = find(R.id.ll_auction_duration);
        tv_auction_duration = find(R.id.tv_auction_duration);
        ll_delay_time = find(R.id.ll_delay_time);
        tv_delay_time = find(R.id.tv_delay_time);
        ll_delay_times = find(R.id.ll_delay_times);
        tv_delay_times = find(R.id.tv_delay_times);
        iv_item_goods_pic = find(R.id.iv_item_goods_pic);
        tv_item_goods_des = find(R.id.tv_item_goods_des);
        tv_item_goods_price = find(R.id.tv_item_goods_price);

        ll_auction_duration.setOnClickListener(this);
        ll_delay_time.setOnClickListener(this);
        ll_delay_times.setOnClickListener(this);
    }

    public void requestData(String id)
    {
        AuctionCommonInterface.requestShopAddpaiDetail(Integer.parseInt(id), new AppRequestCallback<App_pai_podcast_addpaidetailActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    if (actModel.getData() != null)
                    {
                        goodId = actModel.getData().getGoods_id();
                        goodPic = actModel.getData().getImgs();
                        goodImg = actModel.getData().getImgs().get(0);
                        goodName = actModel.getData().getName();
                        qp_diamonds = actModel.getData().getQp_diamonds();
                        bz_diamonds = actModel.getData().getBz_diamonds();
                        jj_diamonds = actModel.getData().getJj_diamonds();
                        pai_time = actModel.getData().getPai_time();
                        pai_yanshi = actModel.getData().getPai_yanshi();
                        max_yanshi = actModel.getData().getMax_yanshi();
                        shop_id = actModel.getData().getShop_id();
                        shop_name = actModel.getData().getShop_name();

                        GlideUtil.load(goodImg).into(iv_item_goods_pic);
                        SDViewBinder.setTextView(tv_item_goods_des,goodName);
                        SDViewBinder.setTextView(tv_item_goods_price,qp_diamonds);
                        SDViewBinder.setTextView(tv_deposit,bz_diamonds);
                        SDViewBinder.setTextView(tv_increase,jj_diamonds + "");
                        SDViewBinder.setTextView(tv_auction_duration,pai_time + "");
                        SDViewBinder.setTextView(tv_delay_time,pai_yanshi + "");
                        SDViewBinder.setTextView(tv_delay_times,max_yanshi + "");
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_auction_duration:
                chooseAuctionDuration();
                break;
            case R.id.ll_delay_time:
                chooseDelayTime();
                break;
            case R.id.ll_delay_times:
                chooseDelayTimes();
                break;
        }
    }

    /**
     * 选择竞拍时长
     */
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

    /**
     * 选择竞拍延时
     */
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

    /**
     * 选择延时次数
     */
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
    protected void initTitleText()
    {
        mTitle.setMiddleTextTop("星店商品拍卖设置");
        mTitle.getItemRight(0).setTextBot("发布");
    }

    @Override
    protected void clickTitleLeft()
    {
        //退出，有提示
        showDialog("是否放弃新增竞拍商品？","确定","取消");
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
        if(!isParamsComplete())
        {
            return;
        }
        showProgressDialog("商品正在发布");
        AuctionCommonInterface.requestAddAuction(mapParams, new AppRequestCallback<App_auction_createAuctionModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if(actModel.isOk())
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
                if(resp.getThrowable() != null)
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

    @Override
    protected boolean isParamsComplete()
    {
        addParams();
        return true;
    }

    private void addParams() {
        mapParams.clear();
        mapParams.put(KEY_IS_TRUE,is_true);
        mapParams.put(KEY_GOODD_ID,goodId);
        mapParams.put(KEY_IMGS, SDJsonUtil.object2Json(goodPic));
        mapParams.put(KEY_TAGS,"");
        mapParams.put(KEY_NAME,goodName);
        mapParams.put(KEY_DATE_TIME, "");
        mapParams.put(KEY_DISTRICT,"");
        mapParams.put(KEY_PLACE,"");
        mapParams.put(KEY_CONTACT,"");
        mapParams.put(KEY_MOBILE,"");
        mapParams.put(KEY_QP_DIAMONDS,qp_diamonds);
        mapParams.put(KEY_BZ_DIAMONDS,bz_diamonds);
        mapParams.put(KEY_JJ_DIAMONDS,jj_diamonds);
        mapParams.put(KEY_SHOP_ID,shop_id);
        mapParams.put(KEY_SHOP_NAME,shop_name);

        mapParams.put(KEY_PAI_TIME,Double.valueOf(tv_auction_duration.getText().toString()));
        mapParams.put(KEY_PAI_YANSHI,Integer.valueOf(tv_delay_time.getText().toString()));
        mapParams.put(KEY_MAX_YANSHI,Integer.valueOf(tv_delay_times.getText().toString()));

        mapParams.put(KEY_DESCRIPTION,"");
    }

    @Override
    public void onOptionsSelect(int i, int i1, int i2)
    {
        if(flag_option.equals(KEY_PAI_TIME))
            tv_auction_duration.setText(mListAuctionDuration.get(i));
        else if(flag_option.equals(KEY_PAI_YANSHI))
            tv_delay_time.setText(mListDelayTime.get(i));
        else
            tv_delay_times.setText(mListDelayTimes.get(i));
    }
}
