package com.fanwe.live.model;

import com.fanwe.library.common.SDSelectManager;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:44:15 类说明
 */
public class PayItemModel implements SDSelectManager.Selectable
{
    private int id;
    private String name;
    private String class_name;
    private String logo;

    //add
    private List<RuleItemModel> rule_list;

    //add
    private boolean selected;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public String getClass_name()
    {
        return class_name;
    }

    public void setClass_name(String class_name)
    {
        this.class_name = class_name;
    }

    public String getLogo()
    {
        return logo;
    }

    public void setLogo(String logo)
    {
        this.logo = logo;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public List<RuleItemModel> getRule_list() {
        return rule_list;
    }

    public void setRule_list(List<RuleItemModel> rule_list) {
        this.rule_list = rule_list;
    }
}
