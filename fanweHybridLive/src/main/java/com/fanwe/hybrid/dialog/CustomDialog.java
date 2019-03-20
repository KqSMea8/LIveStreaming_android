package com.fanwe.hybrid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fanwe.hybrid.app.App;
import com.fanwe.live.R;

/**
 * 自定义Dialog
 * 
 * @author wjm
 * 
 */
public class CustomDialog
{

	public static Dialog confirm(Activity context, String contentText, String confirmText, String cancelText, final OnConfirmListener confirmListener, final OnCancelsListener cancelListener)
	{
		final Dialog dialog = new Dialog(context, R.style.MainDialog);
		View layout = LayoutInflater.from(App.getApplication()).inflate(R.layout.view_dialog_cc, null);
		TextView content = (TextView) layout.findViewById(R.id.content);
		TextView confirm = (TextView) layout.findViewById(R.id.confirm);
		TextView cancel = (TextView) layout.findViewById(R.id.cancel);
		content.setText(contentText);
		confirm.setText(confirmText);
		cancel.setText(cancelText);
		confirm.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (confirmListener != null)
				{
					confirmListener.onConfirmListener();
				}
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (cancelListener != null)
				{
					cancelListener.onCancelListener();
				}
				dialog.dismiss();
			}
		});
		dialog.setContentView(layout);
		dialog.show();

		return dialog;
	}

	public static Dialog alert(Activity context, String contentText, String confirmText, final OnConfirmListener confirmListener)
	{
		final Dialog dialog = new Dialog(context, R.style.MainDialog);
		View layout = LayoutInflater.from(App.getApplication()).inflate(R.layout.view_dialog_c, null);
		TextView content = (TextView) layout.findViewById(R.id.content);
		TextView confirm = (TextView) layout.findViewById(R.id.confirm);
		content.setText(contentText);
		confirm.setText(confirmText);
		confirm.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (confirmListener != null)
				{
					confirmListener.onConfirmListener();
				}
				dialog.dismiss();
			}
		});

		dialog.setContentView(layout);
		dialog.show();

		return dialog;
	}

	public interface OnConfirmListener
	{
		void onConfirmListener();
	}

	public interface OnCancelsListener
	{
		void onCancelListener();
	}
}
