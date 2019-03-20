package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/8/11.
 */
public class AuctionTabMeItemModel
{
    public static final class TabMeTag{
        public static final String tag1="Show_user_order";
        public static final String tag2="Show_user_pai";
        public static final String tag3="Show_podcast_order";
        public static final String tag4="Show_podcast_pai";
        public static final String tag5="Show_podcast_goods";
        public static final String tag6="Shop_shopping_cart";
        public static final String tag7="Open_podcast_goods";
    }

    private boolean isBlankPage;
    private String url;
    private String left_text;
    private String right_text;
    private int Image_Res;
    private String str_Tag;

    public boolean isBlankPage()
    {
        return isBlankPage;
    }

    public void setBlankPage(boolean blankPage)
    {
        isBlankPage = blankPage;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getLeft_text()
    {
        return left_text;
    }

    public void setLeft_text(String left_text)
    {
        this.left_text = left_text;
    }

    public String getRight_text()
    {
        return right_text;
    }

    public void setRight_text(String right_text)
    {
        this.right_text = right_text;
    }

    public String getStr_Tag()
    {
        return str_Tag;
    }

    public void setStr_Tag(String str_Tag)
    {
        this.str_Tag = str_Tag;
    }

    public int getImage_Res()
    {
        return Image_Res;
    }

    public void setImage_Res(int image_Res)
    {
        Image_Res = image_Res;
    }
}
