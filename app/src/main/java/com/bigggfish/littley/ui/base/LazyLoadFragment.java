package com.bigggfish.littley.ui.base;

/**
 * Created by yuxianglong on 2017/10/17.
 */

public abstract class LazyLoadFragment extends BaseFragment {

    protected boolean isVisible = false;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared = false;

    private boolean isLoaded = false;

    protected abstract void lazyLoadData();

    @Override
    protected void loadData() {
        super.loadData();
        isPrepared = true;
        lazyLoad();
        isLoaded = true;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected void onInvisible(){}

    private void lazyLoad() {
        if(!isPrepared || !isVisible || isLoaded) {
            return;
        }
        lazyLoadData();
    }
}
