package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.common.ImageCropManage;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EIsFinishLiveSociatyDetails;
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.model.App_family_createActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.PhotoBotShowUtils;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * 家族个人资料修改编辑
 * Created by Administrator on 2016/9/26.
 */

public class LiveFamilyUpdateEditActivity extends BaseTitleActivity
{
    /**
     * 显示修改界面
     */
    public static final String EXTRA_UPDATE = "extra_update";
    /**
     * 显示修改界面值
     */
    public static final String EXTRA_UPDATE_DATA = "extra_update_data";
    /**
     * 显示修改界（审核被拒的情况）
     */
    public static final String EXTRA_EXAMINE = "extra_examine";
    /**
     * 显示修改界面的家族头像
     */
    public static final String EXTRA_FAMILY_LOGO = "extra_family_logo";
    /**
     * 显示修改界面的家族名称
     */
    public static final String EXTRA_FAMILY_NAME = "extra_family_name";
    /**
     * 显示修改界面的家族长
     */
    public static final String EXTRA_FAMILY_NICK = "extra_family_nick";
    /**
     * 显示修改界面的家族人数
     */
    public static final String EXTRA_FAMILY_NUM = "extra_family_num";
    /**
     * 显示修改界面的家族宣言
     */
    public static final String EXTRA_FAMILY_DECL = "extra_family_decl";

    @ViewInject(R.id.ll_edit_head_img)
    private LinearLayout ll_edit_head_img;
    @ViewInject(R.id.iv_head_img)
    private CircleImageView iv_head_img;
    @ViewInject(R.id.et_fam_name)
    private EditText et_fam_name;//家族名称
    @ViewInject(R.id.txv_fam_name)
    private TextView txv_fam_name;//家族名臣
    @ViewInject(R.id.ll_fam_nick)
    private LinearLayout ll_fam_nick;
    @ViewInject(R.id.txv_fam_nick)
    private TextView txv_fam_nick;//族长
    @ViewInject(R.id.ll_fam_num)
    private LinearLayout ll_fam_num;
    @ViewInject(R.id.txv_fam_num)
    private TextView txv_fam_num;//家族人数
    @ViewInject(R.id.et_fam_decl)
    private EditText et_fam_decl;//家族宣言
    @ViewInject(R.id.txv_sure)
    private TextView txv_sure;//确认

    private PhotoHandler mHandler;
    private String mPic, decl;
    private String update;
    private int family_id;
    private String family_notice;
    private String familyName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_family_data_update_edit);
        initView();
    }

    private void initView()
    {
        initTitle();

        UserModel dao = UserModelDao.query();

        update = getIntent().getStringExtra(EXTRA_UPDATE);
        if (TextUtils.isEmpty(update))
        {
            update = "";
        }

        family_id = dao.getFamily_id();
        String family_logo = getIntent().getStringExtra(EXTRA_FAMILY_LOGO);
        String family_name = getIntent().getStringExtra(EXTRA_FAMILY_NAME);
        String family_decl = getIntent().getStringExtra(EXTRA_FAMILY_DECL);
        String family_nick = getIntent().getStringExtra(EXTRA_FAMILY_NICK);
        String family_num = getIntent().getStringExtra(EXTRA_FAMILY_NUM);
        boolean examine = getIntent().getBooleanExtra(EXTRA_EXAMINE, true);

        if (update.equals(EXTRA_UPDATE_DATA))
        {
            mPic = family_logo;
            decl = family_decl;
            if (examine)
            {
                familyName = family_name;
                et_fam_name.setVisibility(View.VISIBLE);
                txv_fam_name.setVisibility(View.GONE);
                et_fam_name.setGravity(Gravity.CENTER);
                SDViewBinder.setTextView(et_fam_name, family_name);
            } else
            {
                et_fam_name.setVisibility(View.GONE);
                txv_fam_name.setVisibility(View.VISIBLE);
            }
            ll_fam_nick.setVisibility(View.VISIBLE);
            ll_fam_num.setVisibility(View.VISIBLE);
            GlideUtil.loadHeadImage(family_logo).into(iv_head_img);
            SDViewBinder.setTextView(txv_fam_name, family_name);
            SDViewBinder.setTextView(txv_fam_nick, family_nick);
            SDViewBinder.setTextView(txv_fam_num, family_num + "人");
            SDViewBinder.setTextView(et_fam_decl, family_decl);
        }

        mHandler = new PhotoHandler(this);

        ll_edit_head_img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PhotoBotShowUtils.openBotPhotoView(LiveFamilyUpdateEditActivity.this, mHandler, PhotoBotShowUtils.DIALOG_BOTH);
            }
        });

        mHandler.setListener(new PhotoHandler.PhotoHandlerListener()
        {
            @Override
            public void onResultFromAlbum(File file)
            {
                openCropAct(file);
            }

            @Override
            public void onResultFromCamera(File file)
            {
                openCropAct(file);
            }

            @Override
            public void onFailure(String msg)
            {
                SDToast.showToast(msg);
            }
        });

        txv_sure.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                requestJudge();
            }
        });
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("家族资料修改");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setOnClickListener(this);
    }

    private void openCropAct(File file)
    {
        if (AppRuntimeWorker.getOpen_sts() == 1)
        {
            ImageCropManage.startCropActivity(this, file.getAbsolutePath());
        } else
        {
            Intent intent = new Intent(this, LiveUploadImageActivity.class);
            intent.putExtra(LiveUploadImageActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mHandler.onActivityResult(requestCode, resultCode, data);
        ImageCropManage.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * 接收图片选择回传地址事件
     *
     * @param event
     */
    public void onEventMainThread(EUpLoadImageComplete event)
    {
        mPic = event.server_path;
        GlideUtil.loadHeadImage(event.local_path).into(iv_head_img);
    }

    private void requestJudge()
    {
        String name = et_fam_name.getText().toString().trim();
        decl = et_fam_decl.getText().toString().trim();
        familyName = name;
        if (TextUtils.isEmpty(mPic))
        {
            SDToast.showToast("请编辑头像");
            return;
        }

        if (!update.equals(EXTRA_UPDATE_DATA))
        {
            if (TextUtils.isEmpty(name))
            {
                SDToast.showToast("请输入家族名称");
                return;
            }
        }

        if (TextUtils.isEmpty(decl))
        {
            SDToast.showToast("请输入家族宣言");
            return;
        }

        if (update.equals(EXTRA_UPDATE_DATA))
            requestFamilyUpdate(family_id, picPath(mPic), decl, family_notice, familyName);
        else
            requestFamilyCreate(mPic, name, decl, family_notice);
    }

    /**
     * 截取图片地址
     *
     * @param path
     * @return
     */
    private String picPath(String path)
    {
        String site_url = null;
        InitActModel model = InitActModelDao.query();
        if (model != null)
        {
            site_url = model.getSite_url();
        }
        String[] str = path.split(site_url);
        return str[str.length - 1];
    }

    /**
     * 创建家族
     *
     * @param mPic
     * @param name
     * @param decl
     */
    private void requestFamilyCreate(String mPic, String name, String decl, String notice)
    {
        CommonInterface.requestFamilyCreate(mPic, name, decl, notice, new AppRequestCallback<App_family_createActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    finish();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

    /**
     * 修改家族信息
     *
     * @param family_id
     * @param family_logo
     * @param family_manifesto
     * @param family_notice
     */
    private void requestFamilyUpdate(int family_id, String family_logo, String family_manifesto, String family_notice, String family_name)
    {
        CommonInterface.requestFamilyUpdate(family_id, family_logo, family_manifesto, family_notice, family_name, new AppRequestCallback<App_family_createActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    SDToast.showToast(actModel.getError().toString());
                    SDEventManager.post(new EIsFinishLiveSociatyDetails());
                    finish();
                    startActivity(new Intent(LiveFamilyUpdateEditActivity.this, LiveFamilyDetailsActivity.class));
                }
            }
        });
    }
}
