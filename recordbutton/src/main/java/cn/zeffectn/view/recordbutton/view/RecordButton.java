package cn.zeffectn.view.recordbutton.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

import cn.zeffectn.view.recordbutton.R;

/**
 * Created by xuan on 2016/6/8.
 */
public class RecordButton extends android.support.v7.widget.AppCompatButton {
    private final int Volume_What_100 = 100;
    private final int Time_What_101 = 101;
    private final int CancelRecordWhat_102 = 102;
    private String mFilePath = null;
    private OnFinishedRecordListener finishedListener;
    /**
     * 最短录音时间
     **/
    private int MIN_INTERVAL_TIME = 1000;
    /**
     * 最长录音时间
     **/
    private int MAX_INTERVAL_TIME = 1000 * 60;
    private long mStartTime;
    private Dialog mDialog;
    private ImageView mImageView;
    private TextView mTitleTv, mTimeTv;
    private MediaRecorder mRecorder;
    private ObtainDecibelThread mthread;
    private Handler mVolumeHandler;
    private int CANCLE_LENGTH = -200;// 上滑取消距离
    private Context context;

    public RecordButton(Context context) {
        super(context);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 保存路径，为空取默认值
     *
     * @param path
     */
    public void setSavePath(String path) {
        if (TextUtils.isEmpty(path)) {
            mFilePath = path;
        } else {
            setDefaultFilePath();
        }

    }

    /****
     * 设置最大时间。15秒-10分钟
     *
     * @param time 单位秒
     */
    public void setMaxIntervalTime(int time) {
        if (time > 15 && time < 10 * 60) {
            MAX_INTERVAL_TIME = time * 1000;
        }
    }

    private void setDefaultFilePath() {
        File file = new File(Environment.getExternalStorageDirectory(), "media_voince.amr");
        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mFilePath = file.getAbsolutePath();
    }

    /**
     * 录音完成的回调
     *
     * @param listener
     */
    public void setOnFinishedRecordListener(OnFinishedRecordListener listener) {
        finishedListener = listener;
    }


    private void init(Context context) {
        mVolumeHandler = new ShowVolumeHandler();
        this.context = context;
    }

    int startY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                initDialogAndStartRecord();
                break;
            case MotionEvent.ACTION_UP:
                int endY = (int) event.getY();
                if (startY < 0)
                    return true;
                if (endY - startY < CANCLE_LENGTH) {
                    cancelRecord();
                } else {
                    finishRecord();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int tempNowY = (int) event.getY();
                if (startY < 0)
                    return true;
                if (tempNowY - startY < CANCLE_LENGTH) {
                    mTitleTv.setText(getContext().getString(R.string.zeffect_recordbutton_releasing_finger_to_cancal_send));
                } else {
                    mTitleTv.setText(getContext().getString(R.string.zeffect_recordbutton_finger_up_to_cancal_send));
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                cancelRecord();
                break;
        }

        return true;
    }

    private void initDialogAndStartRecord() {
        CANCLE_LENGTH = -this.getMeasuredHeight();
        //
        if (TextUtils.isEmpty(mFilePath)) setDefaultFilePath();
        mStartTime = System.currentTimeMillis();
        mDialog = new Dialog(getContext(), R.style.recordbutton_alert_dialog);
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_recordbutton_alert_dialog, null);
        mImageView = (ImageView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_imageview);
        mTimeTv = (TextView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_time_tv);
        mTitleTv = (TextView) contentView.findViewById(R.id.zeffect_recordbutton_dialog_title_tv);
        mDialog.setContentView(contentView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mDialog.setOnDismissListener(onDismiss);
        if(CheckAudioPermission.isHasPermission(getContext())){
            startRecording();
            mDialog.show();
        }else{
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.zeffect_recordbutton_request_permission), Toast.LENGTH_SHORT).show();
            stopRecording();
        }
    }

    private void finishRecord() {
        stopRecording();
        mDialog.dismiss();
        long intervalTime = System.currentTimeMillis() - mStartTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            if(CheckAudioPermission.isHasPermission(getContext())){
                Toast.makeText(getContext(), getContext().getResources().getString(R.string.zeffect_recordbutton_time_too_short), Toast.LENGTH_SHORT).show();
            }
            File file = new File(mFilePath);
            if (file.exists())
                file.delete();
            return;
        }
        if (finishedListener != null)
            finishedListener.onFinishedRecord(mFilePath);
    }

    private void cancelRecord() {
        stopRecording();
        mDialog.dismiss();
        File file = new File(mFilePath);
        if (file.exists())
            file.delete();
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        mRecorder.setAudioSamplingRate(16000);
        mRecorder.setOutputFile(mFilePath);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        mthread = new ObtainDecibelThread();
        mthread.start();
    }

    private void stopRecording() {
        try {
            if (mthread != null) {
                mthread.exit();
                mthread = null;
            }
            if (mRecorder != null) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private class ObtainDecibelThread extends Thread {

        private volatile boolean running = true;

        public void exit() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mRecorder == null || !running) {
                    break;
                }
                if (System.currentTimeMillis() - mStartTime >= MAX_INTERVAL_TIME) {
                    // 如果超过最长录音时间
                    mVolumeHandler.sendEmptyMessage(CancelRecordWhat_102);
                }
                //发送时间
                mVolumeHandler.sendEmptyMessage(Time_What_101);
                //
                int x = mRecorder.getMaxAmplitude();
                if (x != 0) {
                    int f = (int) (20 * Math.log(x) / Math.log(10));
                    Message msg = new Message();
                    msg.obj = f;
                    msg.what = Volume_What_100;
                    mVolumeHandler.sendMessage(msg);
                }

            }
        }

    }

    private DialogInterface.OnDismissListener onDismiss = new DialogInterface.OnDismissListener() {

        @Override
        public void onDismiss(DialogInterface dialog) {
            stopRecording();
        }
    };

    class ShowVolumeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Volume_What_100:
                    int tempVolumeMax = (int) msg.obj;
                    setLevel(tempVolumeMax);
                    break;
                case Time_What_101:
                    long nowTime = System.currentTimeMillis();
                    int time = ((int) (nowTime - mStartTime) / 1000);
                    int second = time % 60;
                    int mil = time / 60;
                    if (mil < 10) {
                        if (second < 10)
                            mTimeTv.setText("0" + mil + ":0" + second);
                        else
                            mTimeTv.setText("0" + mil + ":" + second);
                    } else if (mil >= 10 && mil < 60) {
                        if (second < 10)
                            mTimeTv.setText(mil + ":0" + second);
                        else
                            mTimeTv.setText(mil + ":" + second);
                    }
                    break;
                case CancelRecordWhat_102:
                    finishRecord();
                    break;
            }
        }
    }

    private void setLevel(int level) {
        if (mImageView != null)
            mImageView.getDrawable().setLevel(3000 + 6000 * level / 100);
    }

    /**
     * 完成录音回调
     */
    public interface OnFinishedRecordListener {
        public void onFinishedRecord(String audioPath);
    }
}
