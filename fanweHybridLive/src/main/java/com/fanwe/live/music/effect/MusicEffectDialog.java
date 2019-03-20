package com.fanwe.live.music.effect;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fanwe.library.utils.SDCache;
import com.fanwe.live.R;
import com.fanwe.live.view.VerticalSeekBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/20.
 */
public class MusicEffectDialog extends Dialog implements View.OnClickListener
{
    public static final int MAX_TONE = 4;
    public static final int MIN_TONE = -4;
    private View mView;
    private LinearLayout mGallery;
    private List<Button> mItems;
    private VerticalSeekBar mBzSeekbar, mMicSeekbar;
    private TextView mTvTone;
    private PlayCtrlListener mPlayCtrlListener;
    private PlayCtrl mPlayCtrl;
    private HorizontalScrollView mScrollView;

    public MusicEffectDialog(Context context)
    {
        this(context, R.style.dialogBaseBlur);
    }

    public MusicEffectDialog(Context context, int themeResId)
    {
        super(context, themeResId);

    }

    protected void initView()
    {

        mGallery = (LinearLayout) mView.findViewById(R.id.ll_bottom);
        mItems = new ArrayList<Button>();
        mItems.add(createItem("原声"));
        mItems.add(createItem("圆润"));
        mItems.add(createItem("流行"));
        mItems.add(createItem("低沉"));
        mItems.add(createItem("高坑"));
        mItems.add(createItem("空灵"));
        mItems.add(createItem("说唱"));

        setClick(R.id.iv_reset);
        setClick(R.id.iv_close);
        setClick(R.id.iv_add);
        setClick(R.id.iv_sub);

        mBzSeekbar = (VerticalSeekBar) findViewById(R.id.vs_bz);
        mMicSeekbar = (VerticalSeekBar) findViewById(R.id.vs_mic);
        mTvTone = (TextView) findViewById(R.id.tv_tone_value);
        mScrollView = (HorizontalScrollView) findViewById(R.id.bottom_horizontal);

        mPlayCtrl = SDCache.getObject(PlayCtrl.class);
        if (mPlayCtrl == null)
        {
            mPlayCtrl = new PlayCtrl();
        }


        mBzSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPlayCtrl.bzVol = progress;
                updatePlayCtrl();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        mMicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mPlayCtrl.micVol = progress;
                updatePlayCtrl();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        initViewData();
    }

    protected void initViewData()
    {
        mBzSeekbar.setProgress(mPlayCtrl.bzVol);
        mMicSeekbar.setProgress(mPlayCtrl.micVol);
        mTvTone.setText("" + mPlayCtrl.pitchShift);
        selectItem(mItems.get(mPlayCtrl.eqModel));

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                float density = getContext().getResources().getDisplayMetrics().density;
                mScrollView.smoothScrollTo((int) (density * (55 + 16) * mPlayCtrl.eqModel), 0);
            }
        }, 500);
    }

    @Override
    public void show()
    {
        super.show();
    }

    protected void setClick(int layoutId)
    {
        mView.findViewById(layoutId).setOnClickListener(this);
    }

    protected Button createItem(String text)
    {
        Button v = (Button) LayoutInflater.from(getContext()).inflate(R.layout.view_room_effect_item, null);
        v.setText(text);
        float density = getContext().getResources().getDisplayMetrics().density;
        int size = (int) (55 * density);
        int margin = (int) (8 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.setMargins(margin, 0, margin, 0);
        mGallery.addView(v);
        v.setLayoutParams(params);
        v.setOnClickListener(this);
        v.setTag("effect");
        return v;
    }

    public void setPlayCtrlListener(PlayCtrlListener playCtrlListener)
    {
        mPlayCtrlListener = playCtrlListener;
    }

    public void initDialog()
    {
        getWindow().setGravity(Gravity.BOTTOM);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_music_effect, null);
        addContentView(mView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        onWindowAttributesChanged(wl);
        getWindow().setWindowAnimations(R.style.anim_bottom_bottom);
        initView();
    }

    public void showBottom()
    {

        show();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_reset:
                mPlayCtrl = new PlayCtrl();
                initViewData();
                updatePlayCtrl();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_add:
                mPlayCtrl.pitchShift += 1;
                updatePitchShift();
                break;
            case R.id.iv_sub:
                mPlayCtrl.pitchShift -= 1;
                updatePitchShift();
                break;
            default:
                selectItem(view);
                break;
        }
    }

    protected void updatePitchShift()
    {
        if (mPlayCtrl.pitchShift < MIN_TONE)
        {
            mPlayCtrl.pitchShift = MIN_TONE;
        } else if (mPlayCtrl.pitchShift > MAX_TONE)
        {
            mPlayCtrl.pitchShift = MAX_TONE;
        }
        mTvTone.setText(mPlayCtrl.pitchShift + "");
        updatePlayCtrl();
    }

    protected void selectItem(View v)
    {
        if (v.getTag() == null)
        {
            return;
        }
        String tag = v.getTag().toString();

        if (TextUtils.isEmpty(tag) || "effect".compareTo(tag) != 0)
        {
            return;
        }
        for (int i = 0; i < mItems.size(); i++)
        {
            if (v == mItems.get(i))
            {
                mItems.get(i).setBackgroundResource(R.drawable.bg_room_effect_circle_item_sel);
//                mItems.get(i).setBackground(getContext().getDrawable(R.drawable.bg_room_effect_circle_item_sel));
                mItems.get(i).setTextColor(Color.WHITE);
                if (mPlayCtrl.eqModel != i)
                {
                    mPlayCtrl.eqModel = i;
                    updatePlayCtrl();
                }
            } else
            {
//                mItems.get(i).setBackground(getContext().getDrawable(R.drawable.bg_room_effect_circle_item_nor));
                mItems.get(i).setBackgroundResource(R.drawable.bg_room_effect_circle_item_nor);
                mItems.get(i).setTextColor(0xff3d4658);
            }
        }
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        SDCache.setObject(mPlayCtrl);
    }

    protected void updatePlayCtrl()
    {
        if (mPlayCtrlListener != null)
        {
            mPlayCtrlListener.onPlayCtrl(mPlayCtrl);
        }
    }

    public interface PlayCtrlListener
    {
        void onPlayCtrl(PlayCtrl playCtrl);
    }
}
