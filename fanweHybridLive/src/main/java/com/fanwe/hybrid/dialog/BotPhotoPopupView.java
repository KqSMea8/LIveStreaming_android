package com.fanwe.hybrid.dialog;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.utils.SDIntentUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;

/**
 * 
 * @author yhz
 * @create time 2014-11-17
 */
public class BotPhotoPopupView extends BasePopupBottomView implements OnClickListener
{

	private Button btn_takephoto, btn_album, btn_cancel;

	public static final int REQUEST_CODE_TAKE_PHOTO = 8;
	public static final int REQUEST_CODE_SELECT_PHOTO = 9;

	private static String mTakePhotoPath;

	public static String getmTakePhotoPath()
	{
		return mTakePhotoPath;
	}

	public BotPhotoPopupView(Activity activity)
	{
		super(activity);
		// TODO Auto-generated constructor stub
		View view = mInflater.inflate(R.layout.acatar_modify_popuoview, null);
		initPopupView(view);
		register(view);
	}

	private void register(View view)
	{
		btn_takephoto = (Button) view.findViewById(R.id.btn_takephoto);
		btn_takephoto.setOnClickListener(this);
		btn_album = (Button) view.findViewById(R.id.btn_album);
		btn_album.setOnClickListener(this);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.btn_takephoto:
			clicktakephoto();
			break;
		case R.id.btn_album:
			clickalbum();
			break;
		case R.id.btn_cancel:
			cancel();
			break;

		}
	}

	private void clicktakephoto()
	{

		File saveFileDir = App.getApplication().getExternalCacheDir();
		if (saveFileDir == null)
		{
			SDToast.showToast("得到图片缓存目录失败!");
		} else
		{
			mTakePhotoPath = saveFileDir.getPath() + "/" + getPicturename();
			File mFileImage = new File(saveFileDir, getPicturename());
			Intent intent = SDIntentUtil.getTakePhotoIntent(mFileImage);
			mActivity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
		}
		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// mActivity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
	}

	private void clickalbum()
	{
		Intent intent = SDIntentUtil.getSelectLocalImageIntent();
		mActivity.startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
	}

	private void cancel()
	{
		dismiss();
	}

	private String getPicturename()
	{
		// 取当前时间为照片名
		String pictureFile = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
		return pictureFile;
	}
}
