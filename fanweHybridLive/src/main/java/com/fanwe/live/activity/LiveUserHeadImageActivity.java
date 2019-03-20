package com.fanwe.live.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

public class LiveUserHeadImageActivity extends BaseActivity
{

    @ViewInject(R.id.iv_leave)
    private ImageView iv_leave;
    @ViewInject(R.id.iv_head_img)
    private ImageView iv_head_img;
    @ViewInject(R.id.tv_save)
    private TextView tv_save;

    private String url;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_user_head_image);
        iv_leave.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        getIntentData();
        displayImage();
    }

    /**
     * 获取activity传递的数据
     */
    private void getIntentData()
    {
        user_id = getIntent().getStringExtra(LiveUserHomeActivity.EXTRA_USER_ID);
        url = getIntent().getStringExtra(LiveUserHomeActivity.EXTRA_USER_IMG_URL);
    }

    /**
     * 显示头像大图
     */
    private void displayImage()
    {
        GlideUtil.load(url).into(iv_head_img);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_leave:
                finish();
                break;
            case R.id.tv_save:
                savePicture();
                break;
            default:
                break;
        }
    }

    /**
     * 保存图片
     */
    private void savePicture()
    {
        showProgressDialog("正在保存图片");
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
        String path = dir.getAbsolutePath() + File.separator + user_id + ".jpg";

        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(path);
        params.setAutoRename(false);
        params.setAutoResume(false);
        x.http().get(params, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onSuccess(File result)
            {
                dismissProgressDialog();
                SDToast.showToast("保存成功");
                folderScan(dirPath);
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
                                  boolean isDownloading)
            {

            }

        });
    }

    /**
     * 扫描文件
     *
     * @param filePath
     */
    private void fileScan(String filePath)
    {
        Uri data = Uri.parse("file://" + filePath);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * 遍历文件夹中的文件，挨个扫描
     *
     * @param dirPath 文件夹路径
     */
    private void folderScan(String dirPath)
    {
        File file = new File(dirPath);
        if (file.isDirectory())
        {
            File[] array = file.listFiles();
            Log.d("FILE", array.length + "个文件");
            for (int i = 0; i < array.length; i++)
            {
                File f = array[i];
                if (f.isFile())
                {
                    fileScan(f.getAbsolutePath());
                } else
                {
                    folderScan(f.getAbsolutePath());
                }
            }
        }
    }
}
