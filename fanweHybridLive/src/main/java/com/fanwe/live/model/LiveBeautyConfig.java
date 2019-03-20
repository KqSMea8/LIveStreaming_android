package com.fanwe.live.model;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.utils.SDCache;

/**
 * 腾讯sdk美颜相关参数配置
 */
public class LiveBeautyConfig
{
    /**
     * 美颜百分比
     */
    private int beautyProgress = -1;
    /**
     * 美白百分比
     */
    private int whitenProgress;
    /**
     * 美颜滤镜图片id
     */
    private int beautyFilter;

    private LiveBeautyConfig()
    {

    }

    public static LiveBeautyConfig get()
    {
        LiveBeautyConfig config = SDCache.getObjectJson(LiveBeautyConfig.class);
        if (config == null)
        {
            config = new LiveBeautyConfig();

            //检查是否需要设置初始化返回的美颜百分比
            int beauty = config.getBeautyProgress();
            if (beauty < 0)
            {
                InitActModel initActModel = InitActModelDao.query();
                if (initActModel != null)
                {
                    beauty = initActModel.getBeauty_android();
                }
            }
            if (beauty < 0)
            {
                beauty = 0;
            }
            config.setBeautyProgress(beauty);

            config.save();
        }
        return config;
    }

    public void save()
    {
        SDCache.setObjectJson(this);
    }

    public int getBeautyProgress()
    {
        return beautyProgress;
    }

    public LiveBeautyConfig setBeautyProgress(int beautyProgress)
    {
        this.beautyProgress = beautyProgress;
        return this;
    }

    public int getWhitenProgress()
    {
        return whitenProgress;
    }

    public LiveBeautyConfig setWhitenProgress(int whitenProgress)
    {
        this.whitenProgress = whitenProgress;
        return this;
    }

    public int getBeautyFilter()
    {
        return beautyFilter;
    }

    public LiveBeautyConfig setBeautyFilter(int beautyFilter)
    {
        this.beautyFilter = beautyFilter;
        return this;
    }
}
