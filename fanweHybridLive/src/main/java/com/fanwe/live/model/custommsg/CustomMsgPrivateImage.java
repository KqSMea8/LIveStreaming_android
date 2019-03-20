package com.fanwe.live.model.custommsg;

import android.graphics.BitmapFactory;

import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDImageUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant.CustomMsgType;
import com.tencent.TIMImageElem;
import com.tencent.TIMMessage;

public class CustomMsgPrivateImage extends CustomMsg
{
    private static final int MAX_WIDTH = SDViewUtil.getScreenWidthPercent(0.4f);
    private static final int MAX_HEIGHT = SDViewUtil.getScreenWidthPercent(0.6f);

    private String path;
    private String url;
    private int width;
    private int height;

    public CustomMsgPrivateImage()
    {
        super();
        setType(CustomMsgType.MSG_PRIVATE_IMAGE);
    }

    public String getAvailableUri()
    {
        String uri = null;
        if (SDFileUtil.isFileExist(path))
        {
            uri = "file://" + path;
        } else
        {
            uri = url;
        }
        return uri;
    }

    public int getViewWidth()
    {
        int calWidth = width > MAX_WIDTH ? MAX_WIDTH : width;
        if (calWidth <= 0)
        {
            calWidth = MAX_WIDTH;
        }
        return calWidth;
    }

    public int getViewHeight()
    {
        int scaleHeight = SDViewUtil.getScaleHeight(width, height, getViewWidth());
        int calHeight = scaleHeight > MAX_HEIGHT ? MAX_HEIGHT : scaleHeight;
        if (calHeight <= 0)
        {
            calHeight = MAX_HEIGHT;
        }
        return calHeight;
    }

    @Override
    public TIMMessage parseToTIMMessage()
    {
        TIMMessage timMessage = super.parseToTIMMessage();
        if (timMessage != null)
        {
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
            timMessage.addElement(elem);
        }
        return timMessage;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        if (path != null)
        {
            this.path = path;
            BitmapFactory.Options options = SDImageUtil.inJustDecodeBounds(path);
            if (options != null)
            {
                setWidth(options.outWidth);
                setHeight(options.outHeight);
            }
        }
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public String getConversationDesc()
    {
        return "[图片]";
    }

}
