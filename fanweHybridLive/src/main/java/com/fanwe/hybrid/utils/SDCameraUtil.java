package com.fanwe.hybrid.utils;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

public class SDCameraUtil
{

	private static final String IMAGE_TYPE = "image/*";

	/**
	 * 打开照相机
	 * 
	 * @param activity
	 *            当前的activity
	 * @param requestCode
	 *            拍照成功时activity forResult 的时候的requestCode
	 * @param photoFile
	 *            拍照完毕时,图片保存的位置
	 */
	public void openCamera(Activity activity, int requestCode, File photoFile)
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 本地照片调用
	 * 
	 * @param activity
	 * @param requestCode
	 */
	public void openPhotos(Activity activity, int requestCode)
	{
		if (openPhotosNormal(activity, requestCode) && openPhotosBrowser(activity, requestCode) && openPhotosFinally(activity))
			;
	}

	/**
	 * PopupMenu打开本地相册.
	 */
	private boolean openPhotosNormal(Activity activity, int actResultCode)
	{
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
		try
		{
			activity.startActivityForResult(intent, actResultCode);

		} catch (android.content.ActivityNotFoundException e)
		{

			return true;
		}

		return false;
	}

	/**
	 * 打开其他的一文件浏览器,如果没有本地相册的话
	 */
	private boolean openPhotosBrowser(Activity activity, int requestCode)
	{
		Toast.makeText(activity, "没有相册软件，运行文件浏览器", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
		intent.setType(IMAGE_TYPE); // 查看类型 String IMAGE_UNSPECIFIED =
									// "image/*";
		Intent wrapperIntent = Intent.createChooser(intent, null);
		try
		{
			activity.startActivityForResult(wrapperIntent, requestCode);
		} catch (android.content.ActivityNotFoundException e1)
		{
			return true;
		}
		return false;
	}

	/**
	 * 这个是找不到相关的图片浏览器,或者相册
	 */
	private boolean openPhotosFinally(Activity activity)
	{
		Toast.makeText(activity, "您的系统没有文件浏览器或则相册支持,请安装！", Toast.LENGTH_LONG).show();
		return false;
	}

	/**
	 * 获取从本地图库返回来的时候的URI解析出来的文件路径
	 * 
	 * @return
	 */
	public static String getPhotoPathByLocalUri(Context context, Intent data)
	{
		Uri selectedImage = data.getData();
		String[] filePathColumn =
		{ MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	/**
	 * 
	 * @param path
	 * @return 返回旋转后的图片
	 */

	public static Bitmap cameraBitmap(String path)
	{
		int degree = readPictureDegree(path);
		BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
		// 设置这两个属性可以减少内存损耗
		opts.inInputShareable = true;
		opts.inPurgeable = true;
		opts.inSampleSize = 2;
		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		Bitmap newbitmap = rotaingImageView(degree, bm);

		return newbitmap;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	private static int readPictureDegree(String path)
	{
		int degree = 0;
		try
		{
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation)
			{
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	private static Bitmap rotaingImageView(int angle, Bitmap bitmap)
	{
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
}
