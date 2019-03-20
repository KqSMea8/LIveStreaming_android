package com.fanwe.hybrid.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.live.R;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.shortvideo.editor.bubble.utils.ScreenUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2015-12-16 下午4:39:42 类说明 启动页
 */
public class InitActivity extends BaseActivity {
    private InitBusiness mInitBusiness;
    HorizontalScrollView sv;
    ImageView iv_wel;
    TextView tv_skip;
    int position = 0;
    boolean gotoend;
    boolean direction = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen(true);
        setContentView(R.layout.act_init);
        sv = find(R.id.scroll);
        iv_wel = find(R.id.iv_wel);
        tv_skip = find(R.id.btn_skip);
        mInitBusiness = new InitBusiness();
        mInitBusiness.init(InitActivity.this);
        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelayRunnable.removeDelay();
                mDelayRunnable.runDelay(1);
            }
        });
        startMove();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (direction) {
                position += 3;
            } else {
                position -= 3;
            }
            sv.scrollTo(position, 0);
            if (position + ScreenUtils.getScreenWidth(getActivity()) > iv_wel.getMeasuredWidth() - 3) {
                gotoend = true;
                direction = false;
            } else if (position < 3) {
                direction = true;
            }
        }
    };
    private Runnable mRunnable = new Runnable() {
        public void run() {
            while (!finish) {
                try {
                    Thread.sleep(50);
                    if (null != handler) {
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void startMove() {
        iv_wel.scrollTo(0, 0);//重新定位到顶端
        new Thread(mRunnable).start();
    }

    private SDDelayRunnable mDelayRunnable = new SDDelayRunnable() {
        @Override
        public void run() {
            InitBusiness.dealInitLaunchBusiness(getActivity());
            finish = true;
        }
    };
    boolean finish;

    public void onEventMainThread(ERetryInitSuccess event) {
        int init_delayed_time = getActivity().getResources().getInteger(R.integer.init_delayed_time);
        mDelayRunnable.runDelay(init_delayed_time);
        tv_skip.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mInitBusiness.onDestroy();
        mInitBusiness = null;
        if (null != handler) {
            handler.removeCallbacks(null);
        }
    }
}
