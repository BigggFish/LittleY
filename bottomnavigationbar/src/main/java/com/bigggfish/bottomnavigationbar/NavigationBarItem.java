package com.bigggfish.bottomnavigationbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bigggfish on 2017/10/19.
 *
 */
public class NavigationBarItem extends LinearLayout implements Checkable {

    private static final int ANIM_DURATION = 200;

    private int mCheckedTextColor = 0xffffffff;
    private int mUnCheckedTextColor = 0xffffffff;
    private int mBgColor;

    private Drawable mCheckedDrawable;
    private Drawable mUnCheckedDrawable;
    private String mText = "";

    private Context mContext;
    private boolean mChecked = false;
    private LayoutInflater mInflater;
    private View rootView;
    private ImageView ivBarImage;
    private TextView tvBarText;
    private LinearLayout.LayoutParams lp;
    private ValueAnimator checkAnim;
    private ValueAnimator unCheckAnim;

    public NavigationBarItem(Context context) {
        this(context, null);
    }

    public NavigationBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(VERTICAL);
        mInflater = LayoutInflater.from(context);
        rootView = mInflater.inflate(R.layout.item_navigation_bar, this);

        ivBarImage = (ImageView) this.findViewById(R.id.iv_navigation_bar_image);
        tvBarText = (TextView) this.findViewById(R.id.tv_navigation_bar_text);

        lp = new LinearLayout.LayoutParams(DisplayUtils.dp2px(mContext, 24), DisplayUtils.dp2px(mContext, 24));

        initAnimator();
    }

    @Override
    public void setChecked(boolean b) {
        mChecked = b;

        if(b)
            check();
        else
            unCheck();
    }

    private void initAnimator(){
        checkAnim = AnimUtil.checkAnim(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value  = (float)animation.getAnimatedValue();
                int marginTop = (int)(16 - 10 * value);
                lp.setMargins(0, DisplayUtils.dp2px(mContext, marginTop), 0, 0);
                ivBarImage.setLayoutParams(lp);
            }
        });
        checkAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvBarText.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        unCheckAnim = AnimUtil.checkAnim(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float value  = (float)animation.getAnimatedValue();
                int marginTop = (int)(6 + 10 * value);

                lp.setMargins(0, DisplayUtils.dp2px(mContext, marginTop), 0, 0);
                ivBarImage.setLayoutParams(lp);
            }
        });

        unCheckAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvBarText.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        if (!isChecked())
            setChecked(!mChecked);
    }

    private void check(){
        //tvBarText.setVisibility(VISIBLE);
        if(mCheckedDrawable != null)
            ivBarImage.setImageDrawable(mCheckedDrawable);
        tvBarText.setTextColor(mCheckedTextColor);
        checkAnim.start();
        //checkAnim();
    }

    private void unCheck(){
        //tvBarText.setVisibility(GONE);
        if(mUnCheckedDrawable != null)
            ivBarImage.setImageDrawable(mUnCheckedDrawable);
        else if(mCheckedDrawable != null)
            ivBarImage.setImageDrawable(mCheckedDrawable);

        tvBarText.setTextColor(mUnCheckedTextColor);
        unCheckAnim.start();
        //unCheckAnim();
    }

    public void setCheckedDrawable(Drawable drawable) {
        mCheckedDrawable = drawable;
    }

    public void setUnCheckedDrawable(Drawable drawable) {
        mUnCheckedDrawable = drawable;
    }

    public Drawable getCheckedDrawable() {
        return mCheckedDrawable;
    }

    public Drawable getUnCheckedDrawable() {
        return mUnCheckedDrawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        tvBarText.setText(text);
    }

    public int getCheckedTextColor() {
        return mCheckedTextColor;
    }

    public void setCheckedTextColor(int checkedTextColor) {
        mCheckedTextColor = checkedTextColor;
    }

    public int getUnCheckedTextColor() {
        return mUnCheckedTextColor;
    }

    public void setUnCheckedTextColor(int unCheckedTextColor) {
        mUnCheckedTextColor = unCheckedTextColor;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int bgColor) {
        mBgColor = bgColor;
    }
}
