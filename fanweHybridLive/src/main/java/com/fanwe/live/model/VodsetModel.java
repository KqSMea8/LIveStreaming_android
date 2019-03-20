package com.fanwe.live.model;

import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class VodsetModel
{
    private String codeDesc;
    private List<FileSetModel> fileSet;

    public String getCodeDesc()
    {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc)
    {
        this.codeDesc = codeDesc;
    }

    public List<FileSetModel> getFileSet()
    {
        return fileSet;
    }

    public void setFileSet(List<FileSetModel> fileSet)
    {
        this.fileSet = fileSet;
    }
}
