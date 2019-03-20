package com.fanwe.libgame.wawa.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.libgame.wawa.WawaManager;
import com.fanwe.libgame.wawa.adapter.AutoPollAdapter;
import com.fanwe.libgame.wawa.model.WawaItemModel;
import com.fanwe.library.media.player.SDMediaPlayer;
import com.fanwe.library.utils.SDToast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 2018/3/10.
 */

public class WawaGameView extends BaseGameView implements View.OnClickListener {
    private RelativeLayout rootView;
    private AutoPollRecyclerView mRecyclerView;
    private AutoPollRecyclerView mRecyclerView2;
    private LinearLayoutManager mLineManager;
    private AutoPollAdapter adapter1;
    private FrameLayout bottom_view;
    private ImageView bottom_iv;
    private ImageView wawa_line;
    private ImageView wawa_stub;
    private ImageView start_grab_animation;
    private ImageView select_coin_bg;
    private ImageView select_coin_bg_bottom;
    private ImageView img_chongzhi;
    private ImageView img_coin;
    private TextView txt_coin;
    private RadioGroup radioGroup;
    private List<WawaItemModel> list = new ArrayList<>();
    private WawaGameViewCallback mCallback;
    private WawaManager mManager;
    private Context mContext;
    private int winType = 2;//1赢2输
    private int coin = 100;//投注金额
    private int times = 1;//投注倍数

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int viewId = v.getId();
        if (viewId == R.id.img_chongzhi) {
            mCallback.onClickRecharge();
        } else if (viewId == R.id.start_grab_animation || viewId == R.id.select_coin_bg || viewId == R.id.select_coin_bg_bottom) {
            if (coin > Integer.valueOf(txt_coin.getText().toString())) {
                SDToast.showToast("余额不足，请充值！");
            } else {
                mCallback.onClickBetView(coin, times, 2, true);
                disableRadioGroup();
            }
        }
    }

    public WawaManager getManager() {
        if (mManager == null) {
            mManager = new WawaManager();
            mManager.setCallback(mDiceManagerCallback);
        }
        return mManager;
    }

    public void setTopView(ImageView line, ImageView stub, FrameLayout bottom_view, ImageView bottom_iv) {
        this.wawa_line = line;
        this.wawa_stub = stub;
        this.bottom_view = bottom_view;
        this.bottom_iv = bottom_iv;
    }

    public void disableRadioGroup() {
        start_grab_animation.setClickable(false);
        select_coin_bg.setClickable(false);
        select_coin_bg_bottom.setClickable(false);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup() {
        start_grab_animation.setClickable(true);
        select_coin_bg.setClickable(true);
        select_coin_bg_bottom.setClickable(true);
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }

    private WawaManager.WawaManagerCallback mDiceManagerCallback = new WawaManager.WawaManagerCallback() {
        @Override
        public void onStart(long countTime) {

        }

        @Override
        public void onCountDownTick(long leftTime) {

        }

        @Override
        public void onCountDownFinish() {

        }

        @Override
        public void onShowCountDown(boolean show) {

        }

        @Override
        public void onMarkWinPosition(int winPosition) {

        }

        @Override
        public void onUpdateBetMultiple(List<String> listData) {

        }

        @Override
        public void onUpdateTotalBet(List<Integer> listData) {

        }

        @Override
        public void onUpdateUserBet(List<Integer> listData) {

        }

        @Override
        public void onUpdateBetCoinsOption(List<GameBetCoinsOptionModel> listData) {

        }

        @Override
        public void onCanBetChanged(boolean canBet) {

        }

        @Override
        public void onBetSuccess(int betPosition, long betCoins) {

        }

        @Override
        public void onUpdateUserCoins(long userCoins) {
            txt_coin.setText(userCoins + "");
        }

        @Override
        public void onGameStateChanged(int oldState, int newState) {

        }

        @Override
        public void onHasAutoStartMode(boolean hasAutoStartMode) {

        }

        @Override
        public void onAutoStartModeChanged(boolean isAutoStartMode) {

        }

        @Override
        public void onUserCoinsImageRes(int res) {

        }

        @Override
        public void onShowResult(List<Integer> listData) {

        }
    };

    public void setCallback(WawaGameViewCallback callback) {
        mCallback = callback;
    }

    public WawaGameView(@NonNull Context context) {
        this(context, null);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WawaGameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_wawa_game, this);
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_02, 3));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_03, 5));
        list.add(new WawaItemModel(R.drawable.prizeitem_04, 10));
        list.add(new WawaItemModel(R.drawable.prizeitem_05, 25));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_02, 3));
        list.add(new WawaItemModel(R.drawable.prizeitem_01, 2));
        list.add(new WawaItemModel(R.drawable.prizeitem_03, 5));
        list.add(new WawaItemModel(R.drawable.prizeitem_04, 10));
        list.add(new WawaItemModel(R.drawable.prizeitem_06, 99));

        initView();
    }

    private void initView() {
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        start_grab_animation = (ImageView) findViewById(R.id.start_grab_animation);
        select_coin_bg = (ImageView) findViewById(R.id.select_coin_bg);
        select_coin_bg_bottom = (ImageView) findViewById(R.id.select_coin_bg_bottom);
        img_coin = (ImageView) findViewById(R.id.img_coin);
        img_chongzhi = (ImageView) findViewById(R.id.img_chongzhi);
        txt_coin = (TextView) findViewById(R.id.txt_coin);
        setOnClickListener(img_chongzhi, this);
        setOnClickListener(start_grab_animation, this);
        setOnClickListener(select_coin_bg, this);
        setOnClickListener(select_coin_bg_bottom, this);
        start_grab_animation.setClickable(true);
        select_coin_bg.setClickable(true);
        select_coin_bg_bottom.setClickable(true);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        enableRadioGroup();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn_0) {
                    coin = 100;
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_2);

                } else if (checkedId == R.id.btn_1) {
                    coin = 1000;
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_3);

                } else if (checkedId == R.id.btn_2) {
                    coin = 10000;
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_4);

                } else if (checkedId == R.id.btn_3) {
                    coin = 100000;
                    select_coin_bg.setBackgroundResource(R.drawable.prizeclaw_docker_5);

                }
            }
        });

        mRecyclerView = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView);
        mRecyclerView.setXY(2, 2);
        mRecyclerView2 = (AutoPollRecyclerView) findViewById(R.id.rv_recycleView2);
        mRecyclerView2.setXY(-2, -2);
        AutoPollAdapter adapter = new AutoPollAdapter(getContext(), list, true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(adapter);
        adapter1 = new AutoPollAdapter(getContext(), list, false);
        mLineManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        mRecyclerView2.setLayoutManager(mLineManager);
        mRecyclerView2.setAdapter(adapter1);
        if (true) //保证itemCount的总个数宽度超过屏幕宽度->自己处理
        {
            mRecyclerView.start();
            mRecyclerView2.start();
        }
    }

    public ImageView getImg_coin() {
        return img_coin;
    }

    public void setTxtCoin(String money) {
        txt_coin.setText(money);
    }

    public void startClick() {

        // 1先下来
        final int lineLocation[] = new int[2];
        wawa_line.getLocationInWindow(lineLocation);
        final int recycle2Location[] = new int[2];
        mRecyclerView2.getLocationInWindow(recycle2Location);
        ValueAnimator animator = ValueAnimator.ofFloat(lineLocation[1], recycle2Location[1] - mRecyclerView2.getHeight() / 2);
        animator.setDuration(2500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentY = (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = wawa_line.getLayoutParams();
                params.height = (int) currentY;
                wawa_line.setLayoutParams(params);
                wawa_stub.setTranslationY(currentY - wawa_stub.getHeight() / 2);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                SDMediaPlayer.getInstance().setDataRawResId(R.raw.pz_hit,mContext);
                SDMediaPlayer.getInstance().performRestartPlayStop();
                int position = getCurrentViewIndex();
                int firstItemPosition = mLineManager.findFirstVisibleItemPosition();
                getGameData(position);
                if (position - firstItemPosition >= 0) {
                    View view = mRecyclerView2.getChildAt((position - firstItemPosition) % list.size());
                    AutoPollAdapter.BaseViewHolder viewHolder = (AutoPollAdapter.BaseViewHolder) mRecyclerView2.getChildViewHolder(view);
                    viewHolder.sort_icon.setVisibility(View.GONE);
                }
                bottom_view.setVisibility(View.VISIBLE);
                bottom_iv.setImageResource(list.get(position % list.size()).wawaDrawable);
                swingAnimation(bottom_view, recycle2Location[1] - mRecyclerView2.getHeight() / 2, lineLocation[1]);
                ValueAnimator animator1 = ValueAnimator.ofFloat(recycle2Location[1] - mRecyclerView2.getHeight() / 2, lineLocation[1]);
                animator1.setDuration(2000);
                animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentY = (float) animation.getAnimatedValue();
                        ViewGroup.LayoutParams params = wawa_line.getLayoutParams();
                        params.height = (int) currentY;
                        wawa_line.setLayoutParams(params);
                        wawa_stub.setTranslationY(currentY - wawa_stub.getHeight() / 2);
                    }
                });
                animator1.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        adapter1.notifyDataSetChanged();
                        if (winType == 2) {
                            SDMediaPlayer.getInstance().setDataRawResId(R.raw.pz_lose,mContext);
                            SDMediaPlayer.getInstance().performRestartPlayStop();
                            final int bottomLocation[] = new int[2];
                            bottom_view.getLocationInWindow(bottomLocation);
                            TranslateAnimation trananimation = new TranslateAnimation(0, 0, bottomLocation[1], 1000f);
                            trananimation.setDuration(1500);
                            AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
                            alphaAnimation.setDuration(1500);
                            AnimationSet set = new AnimationSet(true);
                            set.addAnimation(trananimation);
                            set.addAnimation(alphaAnimation);
                            set.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    bottom_view.setVisibility(INVISIBLE);
                                    enableRadioGroup();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            bottom_view.startAnimation(set);
                        } else {
                            mCallback.onClickBetView(coin, times, winType, false);
                        }

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator1.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();

    }

    private void getGameData(int position) {
        int randomInt = (int) (Math.random() * 100 + 1);
        switch (position % list.size()) {
            case 0:
            case 2:
            case 6:
            case 8:
                times = 2;
                if (randomInt <= 50) {
                    winType = 1;
                } else {
                    winType = 2;
                }
                break;
            case 1:
            case 7:
                times = 3;
                if (randomInt <= 33) {
                    winType = 1;
                } else {
                    winType = 2;
                }
                break;
            case 3:
            case 9:
                times = 5;
                if (randomInt <= 20) {
                    winType = 1;
                } else {
                    winType = 2;
                }
                break;
            case 4:
            case 10:
                times = 10;
                if (randomInt <= 10) {
                    winType = 1;
                } else {
                    winType = 2;
                }
                break;
            case 5:
                times = 25;
                if (randomInt <= 4) {
                    winType = 1;
                } else {
                    winType = 2;
                }
                break;
            case 11:
                times = 99;
                winType = 2;
                break;
            default:
                break;
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }


    public void swingAnimation(View v, int startY, int endY) {
        startShakeByPropertyAnim(v, 0.8f, 1.2f, 10f, 1000, startY, endY);
    }

    public int getCurrentViewIndex() {
        int firstVisibleItem = mLineManager.findFirstVisibleItemPosition();
        int lastVisibleItem = mLineManager.findLastVisibleItemPosition();

        double midleCount = (firstVisibleItem + lastVisibleItem) / 2;

        BigDecimal b = new BigDecimal(new Double(midleCount));
        int currentIndex = b.setScale(3, BigDecimal.ROUND_HALF_UP).intValue();

        return currentIndex;
    }
    ///sdgaa
//    public int getCurrentViewIndex() {
//        int firstVisibleItem = mLineManager.findFirstVisibleItemPosition();
//        int lastVisibleItem = mLineManager.findLastVisibleItemPosition();
//        int currentIndex = firstVisibleItem;
//        int lastHeight = 0;
//        for (int i = firstVisibleItem; i <= lastVisibleItem; i++) {
//            View view = mLineManager.getChildAt(i - firstVisibleItem);
//            if (null == view) {
//                continue;
//            }
//
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//
//            Rect localRect = new Rect();
//            view.getLocalVisibleRect(localRect);
//
//            int showHeight = localRect.bottom - localRect.top;
//            if (showHeight > lastHeight) {
//                currentIndex = i;
//                lastHeight = showHeight;
//            }
//        }
//
//        if (currentIndex < 0) {
//            currentIndex = 0;
//        }
//
//        return currentIndex;
//    }

    private void startShakeByPropertyAnim(final View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration, int startY, int endY) {
        if (view == null) {
            return;
        }

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f));

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "translationY", 0, endY - startY);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(objectAnimator).with(objectAnimator1);
        animatorSet.start();
    }


    public interface WawaGameViewCallback {
        //抓娃娃修改金额
        void onClickBetView(int coin, int times, int type, boolean isAnination);

        void onClickRecharge();

    }
}
