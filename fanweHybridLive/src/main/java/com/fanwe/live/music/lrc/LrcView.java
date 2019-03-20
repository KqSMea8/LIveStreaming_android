package com.fanwe.live.music.lrc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fanwe.live.R;

/**
 * Created by ss on 2015/8/7.
 */
public class LrcView extends View {
    public static final int SHADER_R = 1;
    public static final int POINT_R = 2;
    public static final int POINT_B = 1;
    public static final int POINT_S = POINT_B + POINT_R;
    public static final int SHADER_C = Color.BLACK;
    private List<LrcRow> mLineList = new ArrayList<LrcRow>();// 存放歌词

    private int mViewWidth; // view的宽度
    private int mLrcHeight; // lrc界面的高度
    private int mCurrentLine = 0; // 当前行

    private float mTextSize; // 字体
    private float mDividerHeight; // 行间距

    private Paint mNormalPaint; // 常规的字体
    private Paint mCurrentPaint; // 当前歌词的大小
    private Paint mTimePaint; //
    private Paint mBorderPointPaint;
    private Paint mFillPointPaint;

    private float float1 = 0.0f;//渲染百分比
    private float float2 = 0.01f;
    private boolean isChanging=false;//视图是否正在更新，用处不大
    private int normalTextColor;//歌词颜色
    private int currentTextColor;//当前歌词颜色
    private int timeTextColor;
    private int mCurrentDescent;
    private float mDownLineXPos;
    private long mAllDuringTime, mCurrTime;
    private float mDensity;
    private float mCountDownHeight;

    public LrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public LrcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }
    // 初始化操作
    private void initViews(AttributeSet attrs) {
        // <begin>
        // 解析自定义属性
        TypedArray ta = getContext().obtainStyledAttributes(attrs,
                R.styleable.Lrc);
        mTextSize = ta.getDimension(R.styleable.Lrc_textSize, 50.0f);
        mDividerHeight = ta.getDimension(R.styleable.Lrc_dividerHeight, 0.0f);

        normalTextColor = ta.getColor(R.styleable.Lrc_normalTextColor,
                Color.WHITE);
        currentTextColor = ta.getColor(R.styleable.Lrc_currentTextColor,
                Color.YELLOW);
        timeTextColor = ta.getColor(R.styleable.Lrc_timeTextColor, Color.GRAY);
        ta.recycle();

        DisplayMetrics metric = getContext().getResources().getDisplayMetrics();
        mDensity = metric.density;

        mCountDownHeight = Dp2Px(POINT_S*2 + 3);

        mLrcHeight = (int)(mTextSize * 2 + mDividerHeight + mCountDownHeight);
        mNormalPaint = new Paint();
        mCurrentPaint = new Paint();
        mTimePaint = new Paint();
        // 初始化paint
        mNormalPaint.setTextSize(mTextSize);
        mNormalPaint.setColor(normalTextColor);
        mNormalPaint.setAntiAlias(true);
//        mNormalPaint.setFakeBoldText(true);
        mNormalPaint.setShadowLayer(1, SHADER_R, SHADER_R, SHADER_C);

        mCurrentPaint.setTextSize(mTextSize);
        mCurrentPaint.setColor(currentTextColor);
        mCurrentPaint.setAntiAlias(true);
//        mCurrentPaint.setFakeBoldText(true);
//        mCurrentPaint.setShadowLayer(1, SHADER_R, SHADER_R, SHADER_C);

        mTimePaint.setTextSize(mTextSize - Dp2Px(2));
        mTimePaint.setColor(timeTextColor);
        mTimePaint.setShadowLayer(1, SHADER_R, SHADER_R, SHADER_C);

        mBorderPointPaint = new Paint();
        mBorderPointPaint.setStrokeWidth(Dp2Px(POINT_B));
        mBorderPointPaint.setAntiAlias(true);
        mBorderPointPaint.setStyle(Paint.Style.STROKE);
        mBorderPointPaint.setColor(Color.WHITE);
        mBorderPointPaint.setShadowLayer(1, SHADER_R, SHADER_R, SHADER_C);

        mFillPointPaint = new Paint();
        mFillPointPaint.setAntiAlias(true);
        mFillPointPaint.setStyle(Paint.Style.FILL);
        mFillPointPaint.setColor(0xFF75C8E8);


        mDownLineXPos = mTimePaint.measureText("00:00");
        FontMetricsInt fm = mCurrentPaint.getFontMetricsInt();
        mCurrentDescent = Math.abs(fm.descent);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 获取view宽度
        mViewWidth = getMeasuredWidth();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 重新设置view的高度
        int measuredHeight = MeasureSpec.makeMeasureSpec(mLrcHeight, MeasureSpec.AT_MOST);
        setMeasuredDimension(widthMeasureSpec, measuredHeight);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        // 圈出可视区域
        canvas.clipRect(0, 0, mViewWidth, mLrcHeight);

        if (!mLineList.isEmpty()) {
            // 先画自己
            drawLine(canvas, mCurrentLine);
            drawLine(canvas, mCurrentLine+1);
            drawPlayTimeText(canvas);
        }else {
//            drawSongName(canvas);
        }

        canvas.restore();
    }

    protected void drawLine(Canvas canvas, int lineIndex) {
        if (lineIndex >= mLineList.size()) {
            return;
        }
        float currentX = 0.0f;
        float currentY = mCountDownHeight;
        String currentLrc = mLineList.get(lineIndex).content;
        canvas.save();
        if (lineIndex % 2 != 0) {
            currentX = getLastLineXPos(currentLrc);
            currentY += mTextSize + mDividerHeight;
        }

        if (lineIndex == mCurrentLine){
//            // 获得字符串的"长度"
//            float len = mCurrentPaint.getTextSize() * currentLrc.length();
//            // 参数color数组表示参与渐变的集合
//            // 参数float数组表示对应颜色渐变的位置
//            int[] a = new int[]{currentTextColor, normalTextColor};
//            float[] f = new float[]{float1, float2};
//            Shader shader = new LinearGradient(currentX,
//                    currentY,
//                    len + currentX,
//                    currentY + mTextSize,
//                    a, f, Shader.TileMode.CLAMP);
//            mCurrentPaint.setShader(shader);
//            // 画当前行
//            canvas.drawText(currentLrc, currentX, currentY, mCurrentPaint);
        	drawCurrLine(canvas, currentLrc, currentX, currentY );
        }else {
        	float maxWidth = mViewWidth - mDownLineXPos;
        	canvas.clipRect(currentX, 0, currentX + maxWidth, currentY + mTextSize);
            canvas.drawText(currentLrc, currentX, currentY + mTextSize - mCurrentDescent, mNormalPaint);
        }
        canvas.restore();
    }
    
    protected void drawCurrLine(Canvas canvas, String currentLrc, float currentX, float currentY) {
		
		float maxWidth = mViewWidth - mDownLineXPos;
		float len = mCurrentPaint.getTextSize() * currentLrc.length();
		if (maxWidth < len){
            canvas.clipRect(currentX, 0, maxWidth + currentX, currentY + mTextSize );
            float playLen = float1 * len;
            float lastLen = len - playLen;
            if (playLen <= maxWidth/2){
//                canvas.translate(0, 0);
            }else
            if (lastLen <= maxWidth/2){
                canvas.translate(0 - (len-maxWidth), 0);
            }else {
                canvas.translate(0 - (playLen - maxWidth/2), 0);
            }

        }else {
        }
		
		 int[] a = new int[]{currentTextColor, normalTextColor};
	        float[] f = new float[]{float1, float2};
	        Shader shader = new LinearGradient(currentX,
	                0,
	                len + currentX,
	                mTextSize,
	                a, f, Shader.TileMode.CLAMP);
	        mCurrentPaint.setShader(shader);
	        
	        // 画当前行
	        canvas.drawText(currentLrc, currentX, currentY + mTextSize - mCurrentDescent, mNormalPaint);
	        canvas.drawText(currentLrc, currentX, currentY + mTextSize - mCurrentDescent, mCurrentPaint);
	}

    protected void drawSongName(Canvas canvas) {
        String lrc =  "这是我的主打歌";
        float x = 0.0f;
        float y = mDividerHeight + mTextSize - mCurrentDescent;

        mNormalPaint.setShadowLayer(SHADER_R, SHADER_R, SHADER_R, SHADER_C);
        canvas.drawText(lrc, x, y, mNormalPaint);
    }

    protected void drawDownCountPoint(Canvas canvas, float x){
        float xPos = x + Dp2Px(POINT_S);
        canvas.drawCircle(xPos, Dp2Px(POINT_S), Dp2Px(POINT_R), mBorderPointPaint);
        canvas.drawCircle(xPos, Dp2Px(POINT_S), Dp2Px(POINT_R), mFillPointPaint);
    }

    protected float getLastLineXPos(String lrc){
        float leftPadding = mDownLineXPos;
        float x = mViewWidth - leftPadding;
        float width = mNormalPaint.measureText(lrc);
        if (width > x){
            return leftPadding;
        }
        return mViewWidth - width;
    }

    protected String formatTime(long time) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("m:ss");
        Date date=new Date(time);
        return dateFormater.format(date);
    }
    protected void drawPlayTimeText(Canvas canvas) {
        float x = 0.0f;
        float y = mTextSize * 2 + mDividerHeight - mCurrentDescent + mCountDownHeight + Dp2Px(2);

        if (mCurrTime < 500){
            return;
        }
        canvas.drawText(formatTime(mCurrTime), x, y, mTimePaint);
        
        long firstTime = mLineList.get(0).time;
        if (firstTime < 3000){
            return;
        }

        long time = mAllDuringTime - mCurrTime;
        // 还有一秒开始放，或者大于4秒开始放 不显示倒计时
        if (time + 1000 >= firstTime || time + 4000 <firstTime){
            return;
        }
        drawDownCountPoint(canvas, Dp2Px(2));
        if (time + 3000 < firstTime){
            drawDownCountPoint(canvas, Dp2Px(40 + 2));
        }
        if (time + 2000 < firstTime) {
            drawDownCountPoint(canvas, Dp2Px(20 + 2));
        }

    }

    protected void resizeView(){
        ViewGroup.LayoutParams lp = this.getLayoutParams();
        if (mLineList.size() > 0){
            mLrcHeight = (int)(mTextSize * 2 + mDividerHeight + mCountDownHeight);

        }else {
            mLrcHeight = (int)(mTextSize + mDividerHeight*2);
        }
        lp.height = mLrcHeight;
        this.setLayoutParams(lp);
        postInvalidate();
    }
    // 设置lrc的路径
    public void setLrcPath(String path) {
        mLineList.clear();
        mAllDuringTime = 0;
        mCurrTime = 0;
        List<LrcRow> list = LrcParser.parseLrcRows(path);
        if (list != null && list.size() > 0){
            mLineList.addAll(list);
            mAllDuringTime = mLineList.get(mLineList.size()-1).endTime;

        }
        resizeView();
    }

    public void setLrcRows(List<LrcRow> list){
        mLineList.clear();
        mAllDuringTime = 0;
        mCurrTime = 0;
        if (list != null && list.size() > 0){
            mLineList.addAll(list);
            mAllDuringTime = mLineList.get(mLineList.size()-1).endTime;

        }
    }

    // 传入当前播放时间
    public  void changeCurrent(long time) {
        if (isChanging || mLineList.size() <= 0) {
            return;
        }
        isChanging=true;
        if (time > mAllDuringTime){
            mCurrTime = 0;
        }else {
            mCurrTime = mAllDuringTime - time;
        }
        // 每次进来都遍历存放的时间
        LrcRow kl=null;
        for (int i = 0; i < mLineList.size(); i++) {
            // 遍历歌词列表来获取当前行和当前字
            mCurrentLine = i;
            float1=0.0f;
            float2=0.001f;
            kl = mLineList.get(i);
            if (kl.time<=time && time<=kl.endTime) {
                //当前行
                int len = kl.content.length();
                float1 = (float)((time - kl.time) * 1.0/(kl.endTime-kl.time));
                float2 = float1>0.99f ? float1:(float1+0.01f);

                break;
            }else if(kl.time>=time){
                break;
            }
        }
        //更新视图
        postInvalidate();
        isChanging=false;
    }

    protected int Dp2Px(float dp) {
        return (int) (dp * mDensity + 0.5f);
    }
}
