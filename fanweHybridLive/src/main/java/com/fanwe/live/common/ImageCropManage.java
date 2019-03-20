package com.fanwe.live.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.fanwe.hybrid.app.App;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUpLoadImageOssActivity;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2016/10/21.
 */

public class ImageCropManage
{
    public static void startCropActivity(Activity activity, String path)
    {
        long currentTimeMillis = System.currentTimeMillis();
        Uri uri = Uri.fromFile(new File(path));
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(activity.getCacheDir(), currentTimeMillis + ".png")));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(activity);
    }

    public static UCrop basisConfig(@NonNull UCrop uCrop)
    {
        uCrop = uCrop.withAspectRatio(1, 1);
        return uCrop;
    }

    public static UCrop advancedConfig(@NonNull UCrop uCrop)
    {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setHideBottomControls(true);
        options.setToolbarColor(ContextCompat.getColor(App.getApplication(), R.color.bg_title_bar));
        options.setStatusBarColor(ContextCompat.getColor(App.getApplication(), R.color.bg_title_bar));
        options.setActiveWidgetColor(ContextCompat.getColor(App.getApplication(), R.color.bg_title_bar));
        options.setToolbarWidgetColor(ContextCompat.getColor(App.getApplication(), R.color.text_title_bar));
        return uCrop.withOptions(options);
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == UCrop.REQUEST_CROP)
            {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null)
                {
                    String absolute = new File(resultUri.getPath()).getAbsolutePath();
                    Intent intent = new Intent(activity, LiveUpLoadImageOssActivity.class);
                    intent.putExtra(LiveUpLoadImageOssActivity.EXTRA_IMAGE_URL, absolute);
                    activity.startActivity(intent);
                } else
                {
                    SDToast.showToast("裁剪出错");
                }
            }
        }
    }

    public static void onActivityResultUserImage(Activity activity, int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == UCrop.REQUEST_CROP)
            {
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null)
                {
                    String absolute = new File(resultUri.getPath()).getAbsolutePath();
                    Intent intent = new Intent(activity, LiveUpLoadImageOssActivity.class);
                    intent.putExtra(LiveUpLoadImageOssActivity.EXTRA_IMAGE_URL, absolute);
                    intent.putExtra(LiveUpLoadImageOssActivity.EXTRA_START_TYPE, LiveUpLoadImageOssActivity.ExtraType.EXTRA_UPLOAD_USER_IMAGE);
                    activity.startActivity(intent);
                } else
                {
                    SDToast.showToast("裁剪出错");
                }
            }
        }
    }
}
