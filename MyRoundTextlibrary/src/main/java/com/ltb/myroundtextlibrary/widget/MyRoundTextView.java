package com.ltb.myroundtextlibrary.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;

import com.ltb.myroundtextlibrary.R;
import com.ltb.myroundtextlibrary.utils.SizeUtils;

public class MyRoundTextView extends androidx.appcompat.widget.AppCompatTextView {

    //边框画笔
    private Paint mPaint;

    private Context mContext;

    private int mHeight;
    private int mWidth;
    //文字宽度
    private int txtWidth;
    //文字长度
    private int txtLength;

    private int strokeWidth = SizeUtils.dp2px(1);

    //起始颜色
    private int startColor;
    //结束颜色
    private int endColor;
    //文字
    private String txt = "";

    private String content1;
    private String content2;

    //判断是否是全渐变背景
    private boolean isFull = false;

    public MyRoundTextView(Context context) {
        super(context);
    }

    public MyRoundTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyRoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();

        startColor = mContext.getResources().getColor(R.color.colorForgetPwd);
        endColor = mContext.getResources().getColor(R.color.colorForgetPwd);

        //获取自定义参数
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MyRoundTextView, 0, 0);
        //获取起始颜色
        startColor = typedArray.getColor(R.styleable.MyRoundTextView_m_gradient_color_start, startColor);
        //获取结束颜色
        endColor = typedArray.getColor(R.styleable.MyRoundTextView_m_gradient_color_end, endColor);
        //获取边框粗细
        strokeWidth = (int) typedArray.getDimension(R.styleable.MyRoundTextView_m_stroke_width, strokeWidth);
        //是否填满
        isFull = typedArray.getBoolean(R.styleable.MyRoundTextView_m_is_full, isFull);

        txt = getText().toString();
        typedArray.recycle();
        //配置边框画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        mWidth = measureWidth(widthMeasureSpec);
        mHeight = measureHeight(heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        //判断是否渐变铺满
        mPaint.setStyle(!isFull ? Paint.Style.STROKE : Paint.Style.FILL);

        //设置渐变参数
        LinearGradient linearGradient = new LinearGradient(
                strokeWidth,
                strokeWidth,
                mWidth,
                mHeight,
                endColor,
                startColor,
                Shader.TileMode.CLAMP);

        mPaint.setShader(linearGradient);

        RectF r2 = new RectF();
        r2.set(strokeWidth * 2,
                strokeWidth * 2,
                mWidth - strokeWidth * 4,
                mHeight - strokeWidth * 4);

        //绘制圆角
        canvas.drawRoundRect(r2, SizeUtils.dp2px(20), SizeUtils.dp2px(20), mPaint);

        super.onDraw(canvas);

        if (txtLength == 0) {
            if (!txtIsEmpty(txt)) {
                setTextViewStyles();
            }
        }

    }

    private boolean txtIsEmpty(String txt) {
        return TextUtils.isEmpty(txt);
    }

    /**
     * 设置文字渐变
     */
    private void setTextViewStyles() {
        txtWidth = (int) getPaint().measureText(txt);
        txtLength = txt.length();

        if (getGravity() != Gravity.CENTER) {
            setGravity(Gravity.CENTER);
        }

        //当全渐变背景时候 默认设置文字颜色为白色 不设置成为渐变
        if (!isFull) {

            int x0 = mWidth / 2 - txtWidth / 2;
            int y0 = getBottom();

            int x1 = mWidth / 2 + txtWidth / 2;

            LinearGradient txtLinearGradient = new LinearGradient(
                    x0,
                    y0,
                    x1,
                    y0,
                    endColor,
                    startColor,
                    Shader.TileMode.CLAMP
            );
            getPaint().setShader(txtLinearGradient);
        } else {
            getPaint().setShader(null);
            setTextColor(Color.WHITE);
            getPaint().setColor(Color.WHITE);
        }
        if (!TextUtils.isEmpty(content1) || !TextUtils.isEmpty(content2)) {
            txt = isFull ? content1 : content2;
        }
        setText(txt);
        invalidate();
    }

    /**
     * 设置状态改变前后的文字信息
     *
     * @param content1 点击前展示的文字
     * @param content2 点击后展示的文字
     */
    public void setContent(String content1, String content2) {
        this.content1 = content1;
        this.content2 = content2;
    }

    /**
     * 设置是点击后的状态还是点击前的状态
     *
     * @param full 是否充满背景
     */
    public void setFull(boolean full) {
        if (TextUtils.isEmpty(content1) || TextUtils.isEmpty(content2)) {
            throw new NullPointerException("content1 and content2 must not be null!!");
        }
        isFull = full;
        txtLength = 0;
        invalidate();
    }

    public boolean isFull() {
        return isFull;
    }

    /**
     * 根据模式计算高度
     *
     * @param heightMeasureSpec
     */
    private int measureHeight(int heightMeasureSpec) {
        //获取模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        switch (heightMode) {
            case MeasureSpec.EXACTLY://固定值或者match_content
                height = heightSize + strokeWidth * 2;
                break;
        }
        return height;
    }

    /**
     * 根据模式计算宽度
     *
     * @param widthMeasureSpec
     */
    private int measureWidth(int widthMeasureSpec) {
        //获取模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.EXACTLY://固定值或者match_content
                width = widthSize + strokeWidth * 2;
                break;
        }
        return width;
    }

}
