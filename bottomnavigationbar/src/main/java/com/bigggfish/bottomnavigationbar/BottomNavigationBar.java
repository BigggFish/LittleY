package com.bigggfish.bottomnavigationbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigggfish on 2017/10/19.
 *
 */
public class BottomNavigationBar extends LinearLayout {

    private Context mContext;

    private List<NavigationBarItem> mNavigationBarItems = new ArrayList<>();
    private int mBgColor = 0xffff6600;

    private int mCheckedPosition = 0;
    private int mOldCheckedPos = 0;

    private int mCheckedItemWidth = 0;
    private int mUnCheckedItemWidth = 0;
    private int screenWidth = 0;

    private boolean isRippling = false;//是否波纹正在扩散
    private int mClickX = 0;//点击点X
    private int mClickY = 0;//点击点Y
    private int mRadius = 0;//波纹半径
    private Paint mBgPaint;

    @Nullable
    private OnItemCheckChangedListener mChangedListener;

    public BottomNavigationBar(Context context) {
        this(context, null);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(HORIZONTAL);
        if (mContext instanceof Activity) {
            screenWidth = DisplayUtils.getScreenWidthPixels((Activity) mContext);
        } else
            throw new IllegalArgumentException("context must extends activity");

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                int clickPos = measureClickPos(x);
                if (mCheckedPosition == clickPos) {
                    return true;
                } else {
                    if (mChangedListener != null) {
                        mChangedListener.onItemCheckChanged(clickPos, mCheckedPosition);
                    }
                    mClickX = (int) x;
                    mClickY = (int) y;
                    unSelect(mCheckedPosition);
                    select(clickPos);
                    mBgColor = mNavigationBarItems.get(clickPos).getBgColor();
                    createRippleAnim();
                }
                return false;
            }
        });

    }

    public void setBottomNavigationList(List<NavigationBarItem> navigationList) {
        this.mNavigationBarItems = navigationList;
    }

    public void clearBottomNavigationList() {
        mNavigationBarItems.clear();
    }

    public void addBottomNavigation(NavigationBarItem navigationBarItem) {
        mNavigationBarItems.add(navigationBarItem);
    }

    public void notifyDataChange() {
        mCheckedItemWidth = screenWidth / getWidthSizeNum() * 3;
        mUnCheckedItemWidth = screenWidth / getWidthSizeNum() * 2;
        Log.e("==>OUT", "mCheckedItemWidth:" + mCheckedItemWidth
                + "mUnCheckedItemWidth:" + mUnCheckedItemWidth);
        removeAllViews();
        for (int i = 0; i < mNavigationBarItems.size(); i++) {
            ViewGroup.LayoutParams lp;
            if (i == mCheckedPosition) {
                lp = new ViewGroup.LayoutParams(mCheckedItemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                setBackgroundColor(mNavigationBarItems.get(i).getBgColor());
                mNavigationBarItems.get(i).setChecked(true);
            } else {
                lp = new ViewGroup.LayoutParams(mUnCheckedItemWidth, ViewGroup.LayoutParams.MATCH_PARENT);
                mNavigationBarItems.get(i).setChecked(false);
            }

            mNavigationBarItems.get(i).setLayoutParams(lp);
            addView(mNavigationBarItems.get(i));
        }
    }

    /**
     * 选择某一个item
     *
     * @param position
     */
    public void select(int position) {
       // getChildAt(position).setBackgroundResource(R.color.red);
        ((NavigationBarItem) getChildAt(position)).setChecked(true);
    /*    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mCheckedItemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        //lp.setMargins(0, DisplayUtils.dp2px(mContext, 16), 0, 0);
        getChildAt(position).setLayoutParams(lp);*/
        checkAnim(getChildAt(position));
        mCheckedPosition = position;
    }


    private void checkAnim(final View childView){

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                int width = (int)((mCheckedItemWidth - mUnCheckedItemWidth) * value + mUnCheckedItemWidth);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)childView.getLayoutParams();
                lp.width = width;
                childView.setLayoutParams(lp);

            }
        });
        anim.start();
    }

    private void unCheckAnim(final View childView){

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(200);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                int width = (int)(mCheckedItemWidth - (mCheckedItemWidth - mUnCheckedItemWidth) * value);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)childView.getLayoutParams();
                lp.width = width;
                childView.setLayoutParams(lp);
            }
        });
        anim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isRippling){
            Log.e("===>OUT", "isRippling:" + isRippling);
            canvas.drawCircle(mClickX, mClickY, mRadius, mBgPaint);
        }
    }


    private void createRippleAnim(){
        isRippling = true;
        Log.e("===>OUT", "mBgColor1:" + mBgColor);
        mBgPaint.setColor(mBgColor);

        ValueAnimator valueAnimator = checkAnim(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadius = (int) (screenWidth * (float)animation.getAnimatedValue());
                invalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isRippling = false;
                Log.e("===>OUT", "mBgColor2:" + mBgColor);
                setBackgroundColor(mBgColor);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();
    }

    public static ValueAnimator checkAnim(ValueAnimator.AnimatorUpdateListener listener){
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(500);
        anim.addUpdateListener(listener);
        return anim;
    }

    /**
     * 取消一项的选中状态
     *
     * @param position
     */
    private void unSelect(int position) {
        mOldCheckedPos = mCheckedPosition;
        ((NavigationBarItem) getChildAt(position)).setChecked(false);
       // getChildAt(position).setBackgroundResource(R.color.blue);
       /* LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(mUnCheckedItemWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        getChildAt(position).setLayoutParams(lp);*/
        unCheckAnim(getChildAt(position));
    }

    public void addNormalView(View view) {
        mNavigationBarItems.add(new NavigationBarItem(mContext));
        mCheckedItemWidth = screenWidth / getWidthSizeNum() * 3;
        mUnCheckedItemWidth = screenWidth / getWidthSizeNum() * 2;
        addView(view);
    }

    public void deleteBottomNavigation(int position) {
        mNavigationBarItems.remove(position);
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public void setCheckedPosition(int checkedPosition) {
        if (mCheckedPosition != checkedPosition) {
            mOldCheckedPos = mCheckedPosition;
            mCheckedPosition = checkedPosition;
        }
    }

    private int getWidthSizeNum() {
        return mNavigationBarItems.size() * 2 + 1;
    }

    private int measureClickPos(float x) {

        int pWidth = screenWidth / getWidthSizeNum();

        int pNum = ((int) x / pWidth) + 1;

        if (pNum <= mCheckedPosition * 2)
            return pNum % 2 == 0 ? pNum / 2 - 1 : pNum / 2;
        else if (pNum <= mCheckedPosition * 2 + 3)
            return mCheckedPosition;
        else
            return pNum / 2 - 1;

    }

    interface OnItemCheckChangedListener {

        void onItemCheckChanged(int curPos, int oldPos);
    }

    public OnItemCheckChangedListener getChangedListener() {
        return mChangedListener;
    }

    public void setChangedListener(OnItemCheckChangedListener changedListener) {
        mChangedListener = changedListener;
    }
}
