package com.fanwe.hybrid.dialog;

import com.fanwe.live.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class SDProgressDialog extends Dialog
{

	private TextView mTxtMsg = null;

	public TextView getmTxtMsg()
	{
		return mTxtMsg;
	}

	public void setmTxtMsg(TextView mTxtMsg)
	{
		this.mTxtMsg = mTxtMsg;
	}

	public void setMessage(String msg)
	{
		if (msg != null)
		{
			this.mTxtMsg.setText(msg);
		}
	}

	public SDProgressDialog(Context context)
	{
		super(context, R.style.MainDialog);
		init();
	}

	private void init()
	{
		View view = View.inflate(getContext(), R.layout.dialog_custom_loading, null);
		mTxtMsg = (TextView) view.findViewById(R.id.dialog_custom_loading_txt_progress_msg);

		this.setContentView(view);

	}

}
