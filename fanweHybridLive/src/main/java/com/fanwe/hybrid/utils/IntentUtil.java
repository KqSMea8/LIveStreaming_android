package com.fanwe.hybrid.utils;

import java.io.File;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-28 上午9:24:09 类说明
 */
public class IntentUtil
{
	/** 打开外置浏览器 */
	public static Intent showHTML(String url)
	{
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		return intent;
	}

	/** 打开设置网络界面 */
	public static void openNetwork(final Context context)
	{
		// 提示对话框
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				context.startActivity(createNetWorkIntent());
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		}).show();
	}

	/** 网络配置 */
	public static Intent createNetWorkIntent()
	{
		Intent intent = null;
		// 判断手机系统的版本 即API大于10 就是3.0或以上版本
		if (android.os.Build.VERSION.SDK_INT > 10)
		{
			intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
		} else
		{
			intent = new Intent();
			ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			intent.setComponent(component);
			intent.setAction("android.intent.action.VIEW");
		}
		return intent;
	}

	public static Intent openSysAppAction()
	{
		// 调用系统程序用
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// 用来指示一个GET_CONTENT意图只希望ContentResolver.openInputStream能够打开URI
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		return intent;
	}

	/** 录视频 */
	public static Intent createCamcorderIntent()
	{
		return new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	}

	/** 录音 */
	public static Intent createSoundRecorderIntent()
	{
		return new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
	}

	/**
	 * Create and return a chooser with the default OPENABLE actions including
	 * the camera, camcorder and sound recorder where available.
	 */
	public static Intent createChooserIntent(String title, Intent... intents)
	{
		// 显示一个供用户选择的应用列表
		Intent chooser = new Intent(Intent.ACTION_CHOOSER);
		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
		if (!TextUtils.isEmpty(title))
		{
			chooser.putExtra(Intent.EXTRA_TITLE, title);
		}
		return chooser;
	}

	public static Intent createChooserIntent(Intent... intents)
	{
		return createChooserIntent("", intents);
	}

	public static String getCamerFilePath()
	{
		return getCamerFilePath("browser-photos");
	}

	public static String getCamerFilePath(String fileName)
	{
		File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator + fileName);
		cameraDataDir.mkdirs();
		return cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
	}

	public static Intent createCameraIntent()
	{
		// MediaStore.ACTION_IMAGE_CAPTURE,拍照到指定目录
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getCamerFilePath())));
		return cameraIntent;
	}
}
