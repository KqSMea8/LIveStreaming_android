package com.fanwe.shop.jshandler.request;

import android.app.Activity;
import android.os.Environment;

import com.fanwe.library.utils.SDToast;
import com.fanwe.live.utils.StorageFileUtils;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by yhz on 2017/4/26.
 */

public class RequestHandler
{
    private Activity activity;

    public RequestHandler(Activity activity)
    {
        this.activity = activity;
    }

    /**
     * 保存图片
     */
    public void savePicture(String url)
    {
        File dir = null;
        String dirName = x.app().getPackageName();
        if (FileUtil.existsSdcard())
        {
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else
        {
            dir = new File(Environment.getDataDirectory(), dirName);
        }
        final String dirPath = dir.getAbsolutePath();
        String path = dir.getAbsolutePath() + File.separator + url + ".jpg";

        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);
        x.http().get(params, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onSuccess(File result)
            {
                SDToast.showToast("保存成功");
                StorageFileUtils.folderScan(dirPath, activity);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback)
            {
                SDToast.showToast("保存失败");
            }

            @Override
            public void onCancelled(CancelledException cex)
            {

            }

            @Override
            public void onFinished()
            {

            }

            @Override
            public void onWaiting()
            {

            }

            @Override
            public void onStarted()
            {

            }

            @Override
            public void onLoading(long total, long current,
                    boolean isDownloading
            )
            {

            }

        });
    }


}
