package com.bigggfish.bottomnavigationbar;

import android.animation.ValueAnimator;
import android.view.animation.Animation;

/**
 * Created by bigggfish on 2017/10/23.
 */
public class AnimUtil {

    private static final int ANIM_DURATION = 200;

    public static ValueAnimator checkAnim(ValueAnimator.AnimatorUpdateListener listener){
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(ANIM_DURATION);
        anim.addUpdateListener(listener);
        return anim;
    }

/*    public static ValueAnimator unCheckAnim(ValueAnimator.AnimatorUpdateListener listener){
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.addUpdateListener(listener);
        anim.setDuration(ANIM_DURATION);
        return anim;
    }*/

}
