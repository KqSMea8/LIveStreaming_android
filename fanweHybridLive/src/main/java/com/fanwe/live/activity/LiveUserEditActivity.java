package com.fanwe.live.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.model.SDTaskRunnable;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveEditAffectiveStateDialog;
import com.fanwe.live.dialog.LiveTimePickerDialog;
import com.fanwe.live.dialog.LiveUserEditDialog;
import com.fanwe.live.dialog.LiveUserSexEditDialog;
import com.fanwe.live.event.EUserImageUpLoadComplete;
import com.fanwe.live.model.App_RegionListActModel;
import com.fanwe.live.model.App_userEditActModel;
import com.fanwe.live.model.RegionModel;
import com.fanwe.live.model.SelectLabelModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwei.jubaosdk.common.util.ToastUtil;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetDialogListener;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shibx on 2016/7/12.
 */
public class LiveUserEditActivity extends BaseTitleActivity implements OnDateSetDialogListener
{

    @ViewInject(R.id.ll_edit_head_img)
    private View ll_edit_head_img;
    @ViewInject(R.id.ll_edit_nick_name)
    private View ll_edit_nick_name;
    @ViewInject(R.id.ll_user_id)
    private View ll_user_id;
    @ViewInject(R.id.ll_user_sexy)
    private View ll_user_sexy;
    @ViewInject(R.id.ll_edit_motto)
    private View ll_edit_motto;
    //    @ViewInject(R.id.ll_edit_authenticate)
//    private View ll_edit_authenticate;
    @ViewInject(R.id.ll_edit_birthday)
    private View ll_edit_birthday;
    @ViewInject(R.id.ll_edit_affective_state)
    private View ll_edit_affective_state;
    @ViewInject(R.id.ll_edit_hometown)
    private View ll_edit_hometown;
    @ViewInject(R.id.ll_edit_profession)
    private View ll_edit_profession;
    @ViewInject(R.id.iv_head_img)
    private ImageView iv_head_img;
    @ViewInject(R.id.tv_user_nick_name)
    private TextView tv_user_nick_name;
    @ViewInject(R.id.tv_user_id)
    private TextView tv_user_id;
    @ViewInject(R.id.iv_user_sexy)
    private ImageView iv_user_sexy;
    @ViewInject(R.id.tv_motto)
    private TextView tv_motto;
    //    @ViewInject(R.id.tv_authentication_state)
//    private TextView tv_authentication_state;
    @ViewInject(R.id.tv_birthday)
    private TextView tv_birthday;
    @ViewInject(R.id.tv_affective_state)
    private TextView tv_affective_state;
    @ViewInject(R.id.tv_hometown)
    private TextView tv_hometown;
    @ViewInject(R.id.tv_profession)
    private TextView tv_profession;

    public static final int LENGTH_SHORT = 10;//编辑内容的长度上限
    public static final int LENGTH_LONG = 32;

    public static final int FLAG_NICK_NAME = 0X001;//昵称对应的flag
    public static final int FLAG_MOTTO = 0X002;//签名对应的flag
    public static final int FLAG_PROFESSION = 0X003;//职业对应的flag
    private static final int FLAG_BIRTHDAY = 0X004;//生日对应的flag
    public static final int FLAG_AFFECTIVE_STATE = 0X005;//情感状态对应的flag
    private static final int FLAG_HOMETOWN = 0X006;//家乡对应的flag
    private static final int FLAG_PROVINCE = 0X007;//省份对应的flag
    private static final int FLAG_CITY = 0X008;//省份对应的flag
    public static final int FLAG_SEX = 0X009;//性别对应的flag

    public static final String TITLE_EDIT_NICK_NAME = "编辑昵称";//编辑内容对应的标题名称
    public static final String TITLE_EDIT_MOTTO = "编辑签名";
    public static final String TITLE_EDIT_PROFESSION = "编辑职业";

    private final String KEY_NICK_NAME = "nick_name";//昵称对应的key
    private final String KEY_MOTTO = "signature";//签名对应的key
    private final String KEY_PROFESSION = "job";//职业对应的key
    private final String KEY_BIRTHDAY = "birthday";//生日对应的key
    private final String KEY_AFFECTIVE_STATE = "emotional_state";//情感状态对应的key
    private final String KEY_CITY = "city";//城市对应的key
    private final String KEY_PROVINCE = "province";//省份对应的key
    private final String KEY_SEX = "sex";//性别对应的key

    private LiveUserEditDialog mDialogEdit;//编辑 昵称、签名、职业页面
    private LiveEditAffectiveStateDialog mDialogEditAffectiveState;//编辑 情感状态页面
    private LiveUserSexEditDialog mDialogEditSex;//编辑 性别

    private LiveTimePickerDialog mTimePicker;//时间选择器
    private OptionsPickerView mPickerCity;//城市选择器

    private Map<String, String> mOriginElement = new HashMap<>();
    private Map<String, String> mModifyElement = new HashMap<>();
    private List<SelectLabelModel> mListState;//情感状态 描述 集合
    private ArrayList<RegionModel> mListProvince;//省份集合
    private ArrayList<ArrayList<RegionModel>> mListCity;//城市集合


    private String nick_name;//进入昵称编辑页面传递的昵称
    private String motto;//进入签名编辑页面传递的签名
    private String profession;//进入职业编辑页面传递的职业
    private String birthday;//进入生日编辑页面传递的生日
    private String affectiveState;//进入情感状态编辑页面传递的情感状态
    private String city;//进入城市编辑页面传递的城市
    private String province;//进入省份编辑页面传递的省份
    private String homeTown;
    private int sex;//进入性别编辑页面传递的性别
    private int is_edit_sex;//进入性别编辑页面传递的性别编辑次数判断
    private String image_url;//头像地址

    //Add
    private String mNickInfo = "";//昵称底部描述文字

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_user_edit);
        init();
        requestUserInfo();
    }

    private void init()
    {
        initTitle();
        ll_edit_head_img.setOnClickListener(this);
        ll_edit_nick_name.setOnClickListener(this);
        ll_user_id.setOnClickListener(this);
        ll_user_sexy.setOnClickListener(this);
        ll_edit_motto.setOnClickListener(this);
//        ll_edit_authenticate.setOnClickListener(this);
        ll_edit_birthday.setOnClickListener(this);
        ll_edit_affective_state.setOnClickListener(this);
        ll_edit_hometown.setOnClickListener(this);
        ll_edit_profession.setOnClickListener(this);
        mDialogEdit = new LiveUserEditDialog(this);
        mDialogEdit.setOnSaveClickListener(new LiveUserEditDialog.OnSaveClickListener()
        {
            @Override
            public void onSaveClick(int flag, String modifyText, boolean isChanged)
            {
                if (isChanged)
                {
                    mModifyElement.put(getKey(flag), modifyText);
                    if (modifyText.equals(mOriginElement.get(getKey(flag))))
                    {
                        mModifyElement.remove(getKey(flag));
                    }
                    setText(flag, modifyText);
                }
            }
        });
    }

    /**
     * 获取flag对应的在容器Map中的key
     *
     * @param flag
     * @return
     */
    private String getKey(int flag)
    {
        switch (flag)
        {
            case FLAG_NICK_NAME:
                return KEY_NICK_NAME;
            case FLAG_MOTTO:
                return KEY_MOTTO;
            case FLAG_PROFESSION:
                return KEY_PROFESSION;
            case FLAG_BIRTHDAY:
                return KEY_BIRTHDAY;
            case FLAG_AFFECTIVE_STATE:
                return KEY_AFFECTIVE_STATE;
            case FLAG_CITY:
                return KEY_CITY;
            case FLAG_PROVINCE:
                return KEY_PROVINCE;
            case FLAG_SEX:
                return KEY_SEX;
            default:
                return "";
        }
    }

    /**
     * 将修改后的文本显示出来
     *
     * @param flag
     * @param modifyText
     */
    private void setText(int flag, String modifyText)
    {
        switch (flag)
        {
            case FLAG_NICK_NAME:
                nick_name = modifyText;
                tv_user_nick_name.setText(modifyText);
                break;
            case FLAG_MOTTO:
                motto = modifyText;
                tv_motto.setText(modifyText);
                break;
            case FLAG_PROFESSION:
                profession = modifyText;
                tv_profession.setText(modifyText);
                break;
            case FLAG_AFFECTIVE_STATE:
                affectiveState = modifyText;
                tv_affective_state.setText(modifyText);
                break;
            case FLAG_SEX:
                sex = Integer.parseInt(modifyText);
                iv_user_sexy.setImageResource(getSexResId(sex));
                break;
            case FLAG_BIRTHDAY:
                birthday = modifyText;
                tv_birthday.setText(modifyText);
                break;
            case FLAG_HOMETOWN:
                homeTown = modifyText;
                tv_hometown.setText(modifyText);
                break;
            default:
                break;
        }
    }

    private int getSexResId(int targetSex)
    {
        if (targetSex == 2)
        {
            return R.drawable.ic_global_female;
        }
        return R.drawable.ic_global_male;
    }

    /**
     * 调用接口请求用户信息
     */
    private void requestUserInfo()
    {
        CommonInterface.requestUserEditInfo(new AppRequestCallback<App_userEditActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                UserModel user;
                if (actModel.isOk())
                {
                    user = actModel.getUser();
                } else
                {
                    user = UserModelDao.query();
                }
                mNickInfo = actModel.getNick_info();

                mOriginElement.put(KEY_NICK_NAME, user.getNick_name());
                mOriginElement.put(KEY_MOTTO, user.getSignature());
                mOriginElement.put(KEY_PROFESSION, user.getJob());
                mOriginElement.put(KEY_BIRTHDAY, user.getBirthday());
                mOriginElement.put(KEY_AFFECTIVE_STATE, user.getEmotional_state());
                mOriginElement.put(KEY_CITY, user.getCity());
                mOriginElement.put(KEY_PROVINCE, user.getProvince());
                mOriginElement.put(KEY_SEX, String.valueOf(user.getSex()));
                nick_name = user.getNick_name();
                motto = user.getSignature();
                profession = user.getJob();
                birthday = user.getBirthday();
                affectiveState = user.getEmotional_state();
                city = user.getCity();
                province = user.getProvince();
                homeTown = getHomeTown(city, province);
                sex = user.getSex();
                is_edit_sex = user.getIs_edit_sex();
                image_url = user.getHead_image();
                GlideUtil.loadHeadImage(image_url).into(iv_head_img);
                tv_user_nick_name.setText(nick_name);
                tv_user_id.setText(user.getShowId());
                iv_user_sexy.setImageResource(user.getSexResId());
                tv_motto.setText(motto);
//                tv_authentication_state.setText(getAuthenticationState(user.getIs_authentication()));
                tv_birthday.setText(user.getBirthday());
                tv_affective_state.setText(user.getEmotional_state());
                tv_hometown.setText(getHomeTown(user.getProvince(), user.getCity()));
                tv_profession.setText(profession);
            }
        });
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("编辑资料");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("保存");
        mTitle.setOnClickListener(this);
    }

    private String getAuthenticationState(int isAuthenticated)
    {
        if (isAuthenticated == 2)
        {
            return "未认证";
        }
        return "已认证";
    }

    /**
     * 格式化 家乡
     *
     * @param province
     * @param city
     * @return “省份 城市”
     */
    private String getHomeTown(String province, String city)
    {
        if (TextUtils.isEmpty(province) && TextUtils.isEmpty(city))
        {
            return "火星";
        }
        return province + " " + city;
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.ll_edit_head_img:
                Intent intent = new Intent(this, LiveUserPhotoActivity.class);
                intent.putExtra(LiveUserPhotoActivity.EXTRA_USER_IMG_URL, image_url);
                startActivity(intent);
                break;
            case R.id.ll_edit_nick_name:
                mDialogEdit.setDialogContent(nick_name, LENGTH_SHORT, FLAG_NICK_NAME);
                mDialogEdit.setTvTextNickInfoText(mNickInfo);
                mDialogEdit.showBottom();
                break;
            case R.id.ll_user_id:
                SDOtherUtil.copyText(tv_user_id.getText());
                SDToast.showToast("复制成功");
                break;
            case R.id.ll_user_sexy:
                editSex();
                break;
            case R.id.ll_edit_motto:
                mDialogEdit.setDialogContent(motto, LENGTH_LONG, FLAG_MOTTO);
                mDialogEdit.showBottom();
                break;
//            case R.id.ll_edit_authenticate :
//                break;
            case R.id.ll_edit_birthday:
                if (mTimePicker == null)
                {
                    initTimePicker();
                }
                mTimePicker.showBottom();
                break;
            case R.id.ll_edit_affective_state:
                editAffectiveState();
                break;
            case R.id.ll_edit_hometown:
                if (mPickerCity == null)
                {
                    initCityPicker();
                } else
                {
                    mPickerCity.show();
                }
                break;
            case R.id.ll_edit_profession:
                mDialogEdit.setDialogContent(profession, LENGTH_SHORT, FLAG_PROFESSION);
                mDialogEdit.showBottom();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        super.onCLickRight_SDTitleSimple(v, index);
        //保存编辑资料
        requestCommitInfo();
    }

    /**
     * 提交需修改的用户信息
     * (未编辑的信息不包含在内)
     */
    private void requestCommitInfo()
    {
        CommonInterface.requestCommitUserInfo(nick_name,mModifyElement, new AppRequestCallback<BaseActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
            }
        });
        //去除 是否修改原有内容的判断
//        if(mModifyElement.size() != 0) {
//
//        }
        finish();
    }

    /**
     * 编辑情感状态
     */
    private void editAffectiveState()
    {
        if (mDialogEditAffectiveState == null)
        {
            mListState = new ArrayList<>();
            SelectLabelModel model;
            for (String state : getResources().getStringArray(R.array.affective_state))
            {
                model = new SelectLabelModel(state);
                mListState.add(model);
            }
            mDialogEditAffectiveState = new LiveEditAffectiveStateDialog(mListState, this);
            mDialogEditAffectiveState.setChooseAffectiveStateListener(new LiveEditAffectiveStateDialog.ChooseAffectiveStateListener()
            {
                @Override
                public void onChooseState(int flag, SelectLabelModel item, boolean isChanged)
                {
                    if (isChanged)
                    {
                        mModifyElement.put(getKey(flag), item.getLabel());
                        if (item.getLabel().equals(mOriginElement.get(getKey(flag))))
                        {
                            mModifyElement.remove(getKey(flag));
                        }
                        setText(flag, item.getLabel());
                    }
                }
            });
        }
        mDialogEditAffectiveState.showBottom(getModelPosition(affectiveState));
    }

    /**
     * 编辑性别
     */
    private void editSex()
    {
        if (mDialogEditSex == null)
        {
            mDialogEditSex = new LiveUserSexEditDialog(this);
            mDialogEditSex.setOnChooseSexListener(new LiveUserSexEditDialog.OnChooseSexListener()
            {
                @Override
                public void chooseSex(int flag, int chosesex, boolean isChanged)
                {
                    if (is_edit_sex == 1)
                    {
                        if (isChanged)
                        {
                            mModifyElement.put(getKey(flag), String.valueOf(chosesex));
                            setText(flag, String.valueOf(chosesex));
                        }
                    } else
                    {
                        SDToast.showToast("性别不能多次编辑");
                    }
                }
            });
        }
        mDialogEditSex.showBottom(FLAG_SEX, sex);
    }

    /**
     * 获取情感状态所在集合的position
     *
     * @param state
     * @return
     */
    private int getModelPosition(String state)
    {
        for (SelectLabelModel model : mListState)
        {
            if (TextUtils.equals(model.getLabel(), state))
            {
                return mListState.indexOf(model);
            }
        }
        return 0;
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker()
    {
        mTimePicker = new LiveTimePickerDialog.Builder(getActivity())
                .setCallBack(this)
                .setTitleStringId("请选择出生日期")
                .setCyclic(false)
                .setType(Type.YEAR_MONTH_DAY)
                .setMinMillseconds(1)//1970年1月1日的时间戳
                .setMaxMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.main_color))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.text_item_content))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.main_color))
                .build();
    }

    /**
     * 初始化城市选择器
     */
    private void initCityPicker()
    {

        mPickerCity = new OptionsPickerView(this);
        mListProvince = new ArrayList<>();
        mListCity = new ArrayList<>();
        mPickerCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener()
        {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3)
            {
                province = mListProvince.get(options1).getName();
                city = mListCity.get(options1).get(option2).getName();
                String modifyText = getHomeTown(province, city);
                if (!TextUtils.equals(modifyText, homeTown))
                {
                    mModifyElement.put(getKey(FLAG_PROVINCE), province);
                    mModifyElement.put(getKey(FLAG_CITY), city);
                    if (province.equals(mOriginElement.get(getKey(FLAG_PROVINCE))))
                    {
                        mModifyElement.remove(getKey(FLAG_PROVINCE));
                    }
                    if (city.equals(mOriginElement.get(getKey(FLAG_CITY))))
                    {
                        mModifyElement.remove(getKey(FLAG_CITY));
                    }
                    setText(FLAG_HOMETOWN, modifyText);
                }
            }
        });
        mPickerCity.setCancelable(true);
        checkRegionVersion();
    }

    /**
     * 检查地区版本、更新保存数据
     */
    private void checkRegionVersion()
    {

        App_RegionListActModel regionActModel = AppRuntimeWorker.getRegionListActModel();
        if (regionActModel == null)
        {
            CommonInterface.requestRegionList(new AppRequestCallback<App_RegionListActModel>()
            {
                @Override
                protected void onSuccess(SDResponse resp)
                {
                    if (actModel.isOk())
                    {
                        SDCache.setObject(actModel);
                        SDConfig.getInstance().setInt(R.string.config_region_version, actModel.getRegion_versions());
                        handleCityData(actModel.getRegion_list());
                    }
                }

                @Override
                protected void onError(SDResponse resp)
                {
                    super.onError(resp);
                }
            });
        } else
        {
            handleCityData(regionActModel.getRegion_list());
        }
    }

    /**
     * 分类省份及其对应的城市集合
     *
     * @param regionModelArrayList
     */
    private void handleCityData(final ArrayList<RegionModel> regionModelArrayList)
    {
        SDHandlerManager.getBackgroundHandler().post(new SDTaskRunnable<String>()
        {
            @Override
            public String onBackground()
            {
                initCityData(regionModelArrayList);
                return null;
            }

            @Override
            public void onMainThread(String result)
            {
                mPickerCity.setPicker(mListProvince, mListCity, true);
                mPickerCity.setCyclic(false);
                mPickerCity.setSelectOptions(getProvincePosition(), getCityPosition(getProvincePosition()));//默认选中
                mPickerCity.show();
            }
        });
    }

    /**
     * 初始化城市数据
     *
     * @param regionModelArrayList
     */
    private void initCityData(ArrayList<RegionModel> regionModelArrayList)
    {
        Iterator<RegionModel> it = regionModelArrayList.iterator();
        while (it.hasNext())
        {
            RegionModel item = it.next();
            if (item.getRegion_level() == 2)
            {
                if(item.getId()!=2){
                    mListProvince.add(item);
                }
                it.remove();
            }
        }

        if (mListProvince != null)
        {
            for (RegionModel model : mListProvince)
            {
                ArrayList<RegionModel> listCity = new ArrayList<>();
                for (RegionModel item : regionModelArrayList)
                {
                    if (item.getPid() == model.getId())
                    {
                        listCity.add(item);
                    }
                }
                mListCity.add(listCity);
            }
        }
    }

    /**
     * 遍历获取集合内省份的position
     *
     * @return
     */
    private int getProvincePosition()
    {
        if (TextUtils.isEmpty(province))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListProvince)
            {
                if (TextUtils.equals(province, model.getName()))
                {
                    return mListProvince.indexOf(model);
                }
            }
            return 0;
        }
    }

    /**
     * 遍历获取集合内省份对应集合的城市的position
     *
     * @param province_position 省份所在集合对应的position
     * @return
     */
    private int getCityPosition(int province_position)
    {
        if (TextUtils.isEmpty(city))
        {
            return 0;
        } else
        {
            for (RegionModel model : mListCity.get(province_position))
            {
                if (TextUtils.equals(city, model.getName()))
                {
                    return mListCity.get(province_position).indexOf(model);
                }
            }
            return 0;
        }
    }

    public void onEventMainThread(EUserImageUpLoadComplete event)
    {
        image_url = event.head_image;
        GlideUtil.loadHeadImage(image_url).into(iv_head_img);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mDialogEdit != null)
        {
            SDEventManager.unregister(mDialogEdit);
            mDialogEdit = null;
        }
        if (mDialogEditAffectiveState != null)
        {
            mDialogEditAffectiveState = null;
        }
        if (mPickerCity != null)
        {
            mPickerCity = null;
        }
        if (mTimePicker != null)
        {
            mTimePicker = null;
        }
    }

    @Override
    public void onDateSet(Dialog dialog, long millseconds)
    {
        String modifyText = SDDateUtil.getYYmmddFromDate(new Date(millseconds));
        if (!TextUtils.equals(modifyText, birthday))
        {
            mModifyElement.put(getKey(FLAG_BIRTHDAY), modifyText);
            if (modifyText.equals(mOriginElement.get(getKey(FLAG_BIRTHDAY))))
            {
                mModifyElement.remove(getKey(FLAG_BIRTHDAY));
            }
            setText(FLAG_BIRTHDAY, modifyText);
        }
    }
}
