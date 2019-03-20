package com.fanwe.hybrid.utils;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

public class SDIntentUtil
{

	/**
	 * 获得打开本地图库的intent
	 * 
	 * @return
	 */
	public static Intent getSelectLocalImageIntent()
	{
		// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		// intent.setType("image/*");
		// intent.putExtra("crop", true);
		// intent.putExtra("return-data", true);
		Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		return intent;
	}

	/**
	 * 获调用拍照的intent
	 * 
	 * @return
	 */
	public static Intent getTakePhotoIntent(File saveFile)
	{
		if (saveFile == null)
		{
			return null;
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Uri uri = Uri.fromFile(saveFile);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		return intent;
	}

	public static Intent getCallPhoneIntent(String phoneNumber)
	{
		return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
	}

}
