package com.fanwe.live.common;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.fanwe.hybrid.app.App;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.model.App_aliyun_stsActModel;

/**
 * Created by Administrator on 2016/9/30.
 */

public class AliyunOssManage
{
    private static AliyunOssManage instance;

    private AliyunOssManage()
    {
    }

    public static synchronized AliyunOssManage getInstance()
    {
        if (instance == null)
        {
            instance = new AliyunOssManage();
        }
        return instance;
    }

    public OSS init(App_aliyun_stsActModel actModel)
    {
        return getOss(actModel);
    }

    public ClientConfiguration getDefaultClientConfiguration()
    {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        return conf;
    }

    public OSS getOss(App_aliyun_stsActModel actModel)
    {
        String accessKeyId = actModel.getAccessKeyId();
        if (TextUtils.isEmpty(accessKeyId))
        {
            SDToast.showToast("accessKeyId为空");
            return null;
        }

        String accessKeySecret = actModel.getAccessKeySecret();
        if (TextUtils.isEmpty(accessKeySecret))
        {
            SDToast.showToast("accessKeySecret为空");
            return null;
        }

        String securityToken = actModel.getSecurityToken();
        if (TextUtils.isEmpty(securityToken))
        {
            SDToast.showToast("securityToken为空");
            return null;
        }

        String endPoint = actModel.getEndpoint();
        if (TextUtils.isEmpty(endPoint))
        {
            SDToast.showToast("endPoint为空");
            return null;
        }

        String bucket = actModel.getBucket();
        if (TextUtils.isEmpty(bucket))
        {
            SDToast.showToast("bucket为空");
            return null;
        }

        String expiration = actModel.getExpiration();
        if (TextUtils.isEmpty(expiration))
        {
            SDToast.showToast("expiration为空");
            return null;
        }

        String imgendpoint = actModel.getImgendpoint();
        if (TextUtils.isEmpty(imgendpoint))
        {
            SDToast.showToast("imgendpoint为空");
            return null;
        }

        String endpoint = actModel.getEndpoint();
        if (TextUtils.isEmpty(endpoint))
        {
            SDToast.showToast("endpoint为空");
            return null;
        }

        STSGetter credentialProvider = new STSGetter(accessKeyId, accessKeySecret, securityToken, expiration);
        OSS oss = new OSSClient(App.getApplication().getApplicationContext(), endpoint, credentialProvider, getDefaultClientConfiguration());
        return oss;
    }

    class STSGetter extends OSSFederationCredentialProvider
    {
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;
        private String expiration;

        public STSGetter(String accessKeyId, String accessKeySecret, String securityToken, String expiration)
        {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
            this.securityToken = securityToken;
            this.expiration = expiration;
        }

        public OSSFederationToken getFederationToken()
        {
            return new OSSFederationToken(accessKeyId, accessKeySecret, securityToken, expiration);
        }
    }
}
