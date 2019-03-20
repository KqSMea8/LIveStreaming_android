package com.weibo.model;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述 举报类型项实体类
 * @作者 Su
 * @创建时间 2017/4/7 9:19
 **/
public class XRReportTypeModel
{

    /**
     * id : 17
     * name : 黄色
     */

    private String id;
    private String name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "XRReportTypeModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
