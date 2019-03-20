package com.fanwe.live.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.fanwe.library.utils.SDPackageUtil;

import org.xutils.common.util.FileUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/9/18.
 */
public class StorageFileUtils
{
    public static File createDirPackageName()
    {
        File dir;
        String dirName = SDPackageUtil.getPackageName();
        if (FileUtil.existsSdcard())
        {
            dir = new File(Environment.getExternalStorageDirectory(), dirName);
        } else
        {
            dir = new File(Environment.getDataDirectory(), dirName);
        }
        return dir;
    }

    public static String CROP_IMAGEFlODER = "cropimage";
    public static File createCropImageDir()
    {
        File crop_file;
        String dirName = SDPackageUtil.getPackageName();
        if (FileUtil.existsSdcard())
        {
            File dir_frist = new File(Environment.getExternalStorageDirectory(), dirName);
            if (!dir_frist.exists())
            {
                dir_frist.mkdir();
            }

            String crop_dir = dir_frist + File.separator + CROP_IMAGEFlODER;
            crop_file = new File(crop_dir);
            if (!crop_file.exists())
            {
                crop_file.mkdir();
            }
        } else
        {
            File dir_frist = new File(Environment.getDataDirectory(), dirName);
            if (!dir_frist.exists())
            {
                dir_frist.mkdir();
            }

            String crop_dir = dir_frist + File.separator + CROP_IMAGEFlODER;
            crop_file = new File(crop_dir);
            if (!crop_file.exists())
            {
                crop_file.mkdir();
            }
        }
        return crop_file;
    }

    /**
     * 删除剪切图片目录
     */
    public static void deleteCrop_imageFile()
    {
        deleteFile(createCropImageDir());
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void deleteFile(File file)
    {
        if (file.exists() == false)
        {
            return;
        } else
        {
            if (file.isFile())
            {
                file.delete();
                return;
            }
            if (file.isDirectory())
            {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0)
                {
                    file.delete();
                    return;
                }
                for (File f : childFile)
                {
                    deleteFile(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 扫描文件
     *
     * @param filePath
     */
    public static void fileScan(String filePath,Activity activity)
    {
        Uri data = Uri.parse("file://" + filePath);
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, data));
    }

    /**
     * 遍历文件夹中的文件，挨个扫描
     *
     * @param dirPath 文件夹路径
     */
    public static void folderScan(String dirPath,Activity activity)
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
                    fileScan(f.getAbsolutePath(),activity);
                } else
                {
                    folderScan(f.getAbsolutePath(),activity);
                }
            }
        }
    }
}
