package com.fanwe.live.dialog;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.dialog.SDDialogCustom.SDDialogCustomListener;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveInviteFriendsActivity;

public class LiveAddViewerDialog extends LiveBaseDialog
{

	@ViewInject(R.id.view_share_weixin)
	private View view_share_weixin;

	@ViewInject(R.id.view_share_qq)
	private View view_share_qq;

	@ViewInject(R.id.view_share_friends)
	private View view_share_friends;

	private String strPrivateShare;

	public LiveAddViewerDialog(Activity activity, String privateShare)
	{
		super(activity);
		this.strPrivateShare = privateShare;
		init();
	}

	private void init()
	{
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_live_add_viewer);
		paddings(0);
		x.view().inject(this, getContentView());

		view_share_weixin.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickShareWeixin();
			}
		});

		view_share_qq.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickShareQQ();
			}
		});

		view_share_friends.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickShareFriends();
			}
		});
	}

	protected void clickShareFriends()
	{
		// TODO 打开选择好友界面
		Intent intent = new Intent(getOwnerActivity(),LiveInviteFriendsActivity.class);
		intent.putExtra(LiveInviteFriendsActivity.EXTRA_ROOM_ID, getLiveActivity().getRoomId());
		getOwnerActivity().startActivity(intent);
		dismiss();
	}

	protected void clickShareQQ()
	{
		SDOtherUtil.copyText(strPrivateShare);
		SDDialogConfirm dialog = new SDDialogConfirm(getOwnerActivity());
		dialog.setTextContent("密令复制好啦！快去分享给小伙伴吧！").setTextConfirm("去粘帖").setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				SDPackageUtil.startAPP("com.tencent.mobileqq");
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		}).show();
		dismiss();
	}

	protected void clickShareWeixin()
	{
		SDOtherUtil.copyText(strPrivateShare);
		SDDialogConfirm dialog = new SDDialogConfirm(getOwnerActivity());
		dialog.setTextContent("密令复制好啦！快去分享给小伙伴吧！").setTextConfirm("去粘帖").setmListener(new SDDialogCustomListener()
		{

			@Override
			public void onDismiss(SDDialogCustom dialog)
			{
			}

			@Override
			public void onClickConfirm(View v, SDDialogCustom dialog)
			{
				SDPackageUtil.startAPP("com.tencent.mm");
			}

			@Override
			public void onClickCancel(View v, SDDialogCustom dialog)
			{
			}
		}).show();
		dismiss();
	}

}
