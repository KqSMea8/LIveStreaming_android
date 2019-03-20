package com.fanwe.live.model;

import com.fanwe.library.utils.SDCache;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 金山sdk美颜参数配置
 */
public class LiveKSYBeautyConfig implements Serializable
{

    private static final long serialVersionUID = 0L;

    private List<LiveKSYBeautyFilterModel> listFilter;
    private int beautyFilter;

    /**
     * 获得配置
     *
     * @return
     */
    public static LiveKSYBeautyConfig get()
    {
        LiveKSYBeautyConfig config = SDCache.getObjectJson(LiveKSYBeautyConfig.class);
        if (config == null)
        {
            config = new LiveKSYBeautyConfig();
            config.save();
        }

        if (config.getListFilter() == null)
        {
            config.createDefaultValue();
            config.save();
        }

        return config;
    }

    /**
     * 保存配置到本地
     */
    public void save()
    {
        SDCache.setObjectJson(this);
    }

    public void createDefaultValue()
    {
        if (listFilter == null)
        {
            listFilter = new ArrayList<>();
        } else
        {
            listFilter.clear();
        }

        LiveKSYBeautyFilterModel disable = new LiveKSYBeautyFilterModel();
        disable.setName("禁用美颜");
        disable.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE);

        LiveKSYBeautyFilterModel denoise = new LiveKSYBeautyFilterModel();
        denoise.setName("自然");
        denoise.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_DENOISE);

        LiveKSYBeautyFilterModel skinwhiten = new LiveKSYBeautyFilterModel();
        skinwhiten.setName("白肤");
        skinwhiten.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SKINWHITEN);

        LiveKSYBeautyFilterModel illusion = new LiveKSYBeautyFilterModel();
        illusion.setName("柔肤");
        illusion.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_ILLUSION);

        LiveKSYBeautyFilterModel softSharpen = new LiveKSYBeautyFilterModel();
        softSharpen.setName("简单");
        softSharpen.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_SHARPEN);

        LiveKSYBeautyFilterModel softExt = new LiveKSYBeautyFilterModel();
        softExt.setName("轻度嫩肤");
        softExt.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_EXT);

        LiveKSYBeautyFilterModel smooth = new LiveKSYBeautyFilterModel();
        smooth.setName("白皙");
        smooth.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SMOOTH);

        LiveKSYBeautyFilterModel soft = new LiveKSYBeautyFilterModel();
        soft.setName("嫩肤");
        soft.setBeautyFilter(ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT);

        listFilter.add(disable);
        listFilter.add(denoise);
        listFilter.add(skinwhiten);
        listFilter.add(illusion);
        listFilter.add(softSharpen);
        listFilter.add(softExt);
        listFilter.add(smooth);
        listFilter.add(soft);
    }

    public List<LiveKSYBeautyFilterModel> getListFilter()
    {
        return listFilter;
    }

    public void setListFilter(List<LiveKSYBeautyFilterModel> listFilter)
    {
        this.listFilter = listFilter;
    }

    public int getBeautyFilter()
    {
        return beautyFilter;
    }

    public void setBeautyFilter(int beautyFilter)
    {
        this.beautyFilter = beautyFilter;
    }
}
