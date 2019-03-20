package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class FileSetModel
{
    private String vid;
    private String fileId;
    private String fileName;
    private String duration;
    private String status;
    private String image_url;

    private List<PlaySetModel> playSet;

    public String getVid()
    {
        return vid;
    }

    public void setVid(String vid)
    {
        this.vid = vid;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public List<PlaySetModel> getPlaySet()
    {
        return playSet;
    }

    public void setPlaySet(List<PlaySetModel> playSet)
    {
        this.playSet = playSet;
    }
}
