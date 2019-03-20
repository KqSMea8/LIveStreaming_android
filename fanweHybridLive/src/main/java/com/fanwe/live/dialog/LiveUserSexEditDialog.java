package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.title.SDTitleSimple;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;

/**
 * Created by shibx on 2016/7/16.
 */
public class LiveUserSexEditDialog extends SDDialogBase{

    private SDTitleSimple mTitle;

    private View ll_choose_male;
    private View ll_choose_female;
    private ImageView iv_male;
    private ImageView iv_female;

    private static final int SEX_MALE = 1;
    private static final int SEX_FEMALE = 2;

    private int flag;
    private int mSex;

    private OnChooseSexListener mListener;

    public LiveUserSexEditDialog(Activity activity) {
        super(activity);
        setContentView(R.layout.dialog_edit_sex);
        iniView();
        setFullScreen();
    }

    private void iniView() {
        mTitle = (SDTitleSimple) findViewById(R.id.title);
        ll_choose_male = findViewById(R.id.ll_choose_male);
        ll_choose_female = findViewById(R.id.ll_choose_female);
        iv_male = (ImageView) findViewById(R.id.iv_male);
        iv_female = (ImageView) findViewById(R.id.iv_female);
        ll_choose_male.setOnClickListener(this);
        ll_choose_female.setOnClickListener(this);
        mTitle.setMiddleTextTop("性别");
        mTitle.setLeftImageLeft(R.drawable.ic_arrow_left_white);
        mTitle.setmListener(new SDTitleSimple.SDTitleSimpleListener() {
            @Override
            public void onCLickLeft_SDTitleSimple(SDTitleItem v) {
                dismiss();
            }

            @Override
            public void onCLickMiddle_SDTitleSimple(SDTitleItem v) {

            }

            @Override
            public void onCLickRight_SDTitleSimple(SDTitleItem v, int index) {

            }
        });
    }

    public void setOnChooseSexListener(OnChooseSexListener listener) {
        this.mListener = listener;
    }

    public void showBottom(int flag ,int sex) {
        setSex(flag,sex);
        showBottom();
        SDToast.showToast("性别只能编辑一次");
    }

    private void setSex(int flag ,int sex) {
        this.mSex = sex;
        this.flag = flag;
        if(sex == SEX_MALE) {
            iv_male.setImageResource(R.drawable.icon_me_male);
            iv_female.setImageResource(R.drawable.icon_me_female_default);
        }else if(sex == SEX_FEMALE) {
            iv_female.setImageResource(R.drawable.icon_me_female);
            iv_male.setImageResource(R.drawable.icon_me_male_default);
        } else {
            iv_female.setImageResource(R.drawable.icon_me_female_default);
            iv_male.setImageResource(R.drawable.icon_me_male_default);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()) {
            case R.id.ll_choose_male :
                mListener.chooseSex(flag ,SEX_MALE ,isEdited(SEX_MALE));
                dismiss();
                break;
            case R.id.ll_choose_female :
                mListener.chooseSex(flag ,SEX_FEMALE ,isEdited(SEX_FEMALE));
                dismiss();
                break;
            default:
                break;
        }
    }

    private boolean isEdited(int choseSex) {
        if(choseSex == mSex) {
            return false;
        }
        return true;
    }

    public interface OnChooseSexListener{
        void chooseSex(int flag ,int sex ,boolean isChanged);
    }

}
