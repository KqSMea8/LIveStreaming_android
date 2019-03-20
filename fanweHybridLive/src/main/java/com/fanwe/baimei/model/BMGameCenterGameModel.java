package com.fanwe.baimei.model;

/**
 * 包名 com.fanwe.baimei.model
 * 描述
 * 作者 Su
 * 创建时间 2017/5/16 11:27
 **/
public class BMGameCenterGameModel
{

    /**
     * id : 1
     * child_id : 1
     * name : 炸金花
     * image : http://liveimage.fanwe.net/public/attachment/201706/06/10/5936137df184b.png
     * index_image : http://liveimage.fanwe.net/public/attachment/201706/08/16/593904b4c205f.png
     * type : 2
     * class_name : game
     */

    private String id;
    private String child_id;
    private String name;
    private String image;
    private String index_image;
    private String type;
    private String class_name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getChild_id()
    {
        return child_id;
    }

    public void setChild_id(String child_id)
    {
        this.child_id = child_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getIndex_image()
    {
        return index_image;
    }

    public void setIndex_image(String index_image)
    {
        this.index_image = index_image;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getClass_name()
    {
        return class_name;
    }

    public void setClass_name(String class_name)
    {
        this.class_name = class_name;
    }
}
