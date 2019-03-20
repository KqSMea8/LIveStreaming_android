package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/9/29.
 */

public class App_aliyun_stsActModel extends BaseActModel
{
    private String bucket;
    private String endpoint;
    private String imgendpoint;
    private String AccessKeyId;
    private String AccessKeySecret;
    private String Expiration;
    private String SecurityToken;
    private String RequestId;
    private String Code;
    private String Message;
    private String callbackUrl;
    private String callbackBody;
    private String dir;
    private String oss_domain;

    public String getOss_domain()
    {
        return oss_domain;
    }

    public void setOss_domain(String oss_domain)
    {
        this.oss_domain = oss_domain;
    }

    public String getImgendpoint()
    {
        return imgendpoint;
    }

    public void setImgendpoint(String imgendpoint)
    {
        this.imgendpoint = imgendpoint;
    }

    public String getBucket()
    {
        return bucket;
    }

    public void setBucket(String bucket)
    {
        this.bucket = bucket;
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId()
    {
        return AccessKeyId;
    }

    public void setAccessKeyId(String accessKeyId)
    {
        AccessKeyId = accessKeyId;
    }

    public String getAccessKeySecret()
    {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret)
    {
        AccessKeySecret = accessKeySecret;
    }

    public String getExpiration()
    {
        return Expiration;
    }

    public void setExpiration(String expiration)
    {
        Expiration = expiration;
    }

    public String getRequestId()
    {
        return RequestId;
    }

    public void setRequestId(String requestId)
    {
        RequestId = requestId;
    }

    public String getSecurityToken()
    {
        return SecurityToken;
    }

    public void setSecurityToken(String securityToken)
    {
        SecurityToken = securityToken;
    }

    public String getCode()
    {
        return Code;
    }

    public void setCode(String code)
    {
        Code = code;
    }

    public String getMessage()
    {
        return Message;
    }

    public void setMessage(String message)
    {
        Message = message;
    }

    public String getCallbackUrl()
    {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl)
    {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackBody()
    {
        return callbackBody;
    }

    public void setCallbackBody(String callbackBody)
    {
        this.callbackBody = callbackBody;
    }

    public String getDir()
    {
        return dir;
    }

    public void setDir(String dir)
    {
        this.dir = dir;
    }
}
