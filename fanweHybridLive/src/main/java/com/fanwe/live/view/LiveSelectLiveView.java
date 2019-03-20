package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.config.SDConfig;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveSelectCityAdapter;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.IndexSearch_areaActModel;
import com.fanwe.live.model.SelectCityModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据性别和区域筛选热门区域
 * <p>
 * Created by HSH on 2016/7/25.
 */
public class LiveSelectLiveView extends BaseAppView
{
    @ViewInject(R.id.ll_sex_selected_woman)
    private LinearLayout ll_sex_selected_woman;
    @ViewInject(R.id.ll_sex_selected_all)
    private LinearLayout ll_sex_selected_all;
    @ViewInject(R.id.ll_sex_selected_man)
    private LinearLayout ll_sex_selected_man;

    @ViewInject(R.id.iv_sex_selected_woman)
    private ImageView iv_sex_selected_woman;
    @ViewInject(R.id.iv_sex_selected_all)
    private ImageView iv_sex_selected_all;
    @ViewInject(R.id.iv_sex_selected_man)
    private ImageView iv_sex_selected_man;

    @ViewInject(R.id.lv_city)
    private ListView lv_city;

    @ViewInject(R.id.tv_complete)
    private TextView tv_complete;

    private String city;
    private int sex;

    private LiveSelectCityAdapter adapter;
    private List<SelectCityModel> listModel = new ArrayList<SelectCityModel>();
    private LiveTabHotViewListener listener;

    private SDSelectViewManager<View> viewManager = new SDSelectViewManager<View>();

    public LiveSelectLiveView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveSelectLiveView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveSelectLiveView(Context context)
    {
        super(context);
        init();
    }


    protected void init()
    {
        setContentView(R.layout.view_live_select_live);

        initTabSex();
        register();
    }

    /**
     * 请求区域列表
     */
    private void requestData()
    {
        CommonInterface.requestIndexSearch_area(sex, new AppRequestCallback<IndexSearch_areaActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.isOk())
                {
                    listModel.clear();

                    List<SelectCityModel> listNew = actModel.getList();
                    if (listNew != null)
                    {
                        listModel.addAll(listNew);
                    }

                    adapter.updateData(listModel);

                    String city = SDConfig.getInstance().getString(R.string.config_live_select_city, "");

                    int index = findCityIndex(city);
                    if (index < 0 && !listModel.isEmpty())
                    {
                        index = 0;
                        setCity(listModel.get(0).getCity());
                    }
                    adapter.getSelectManager().setSelected(index, true);
                }
            }
        });
    }

    private int findCityIndex(String city)
    {
        int index = -1;
        if (!SDCollectionUtil.isEmpty(listModel) && city != null)
        {
            for (int i = 0; i < listModel.size(); i++)
            {
                if (city.equals(listModel.get(i).getCity()))
                {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private void setCity(String city)
    {
        this.city = city;
        SDConfig.getInstance().setString(R.string.config_live_select_city, city);
    }

    private void register()
    {
        adapter = new LiveSelectCityAdapter(listModel, getActivity());
        lv_city.setAdapter(adapter);
        adapter.setItemClickCallback(new SDItemClickCallback<SelectCityModel>()
        {
            @Override
            public void onItemClick(int position, SelectCityModel item, View view)
            {
                adapter.getSelectManager().setSelected(item, true);
                setCity(item.getCity());
                if (listener != null)
                {
                    listener.success(sex, city);
                }
            }
        });

        tv_complete.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (listener != null)
                {
                    listener.success(sex, city);
                }
            }
        });
    }

    private void initTabSex()
    {
        View[] items = new View[]{ll_sex_selected_all, ll_sex_selected_man, ll_sex_selected_woman};

        viewManager.addSelectCallback(new SDSelectManager.SelectCallback<View>()
        {
            @Override
            public void onNormal(int index, View item)
            {
                switch (index)
                {
                    case 0:
                        setImageResId(iv_sex_selected_all, R.drawable.ic_choice_sex_all_normal);
                        break;
                    case 1:
                        setImageResId(iv_sex_selected_man, R.drawable.ic_choice_sex_male_normal);
                        break;
                    case 2:
                        setImageResId(iv_sex_selected_woman, R.drawable.ic_choice_sex_female_normal);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onSelected(int index, View item)
            {
                switch (index)
                {
                    case 0:
                        setImageResId(iv_sex_selected_all, R.drawable.ic_choice_sex_all_selected);
                        break;
                    case 1:
                        setImageResId(iv_sex_selected_man, R.drawable.ic_choice_sex_male_selected);
                        break;
                    case 2:
                        setImageResId(iv_sex_selected_woman, R.drawable.ic_choice_sex_female_selected);
                        break;
                    default:
                        break;
                }
                sex = index;
                SDConfig.getInstance().setInt(R.string.config_live_select_sex, sex);
                requestData();
            }
        });

        viewManager.setItems(items);
    }

    public void initSelected()
    {
        sex = SDConfig.getInstance().getInt(R.string.config_live_select_sex, 0);
        viewManager.setSelected(sex, true);
    }

    private void setImageResId(ImageView image, int resId)
    {
        image.setImageResource(resId);
    }

    public void setListener(LiveTabHotViewListener listener)
    {
        this.listener = listener;
    }

    public interface LiveTabHotViewListener
    {
        void success(int sex, String city);
    }
}
