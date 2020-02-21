package com.dengyun.baselibrary.widgets.guide;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * @titile  使用引导功能的工具类，
 **
 * @desc Created by seven on 2018/5/15.
 */

public class GuideUtil implements View.OnKeyListener{
    private List<GuideView> viewList = new ArrayList<>();//维护引导功能图的队列
    private int currentIndex = 0;//当前正在显示的引导功能图的下标

    /**
     * @param guideOptionArr    不定数量的guide
     */
    public void showGuide(GuideOptions... guideOptionArr){
        for (int i = 0; i < guideOptionArr.length; i++) {
            GuideOptions guideOptions = guideOptionArr[i];
            GuideView guideView = guideOptions.getGuideView();
            if(guideOptions.getListener()==null&&guideOptions.isDefultListener()){
                guideView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNext();
                    }
                });
            }
            guideOptions.getGuideView().setOnKeyListener(this);
            viewList.add(guideOptions.getGuideView());
        }
        show();
    }

    public void showNext() {
        GuideView guideView = viewList.get(currentIndex);
        Animation exitAnim = guideView.getExitAnim();
        if(exitAnim!=null){
            exitAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hideCurrentGuide();
                    currentIndex++;
                    show();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            guideView.startAnimation(exitAnim);

        }else {
            hideCurrentGuide();
            currentIndex++;
            show();
        }
    }


    /**
     * 按照队列显示引导图，其中某个若已经显示过，则跳过直接显示下一个
     */
    private void show() {
        if (viewList.size() > currentIndex) {
            if (!viewList.get(currentIndex).show()) {
                currentIndex++;
                show();
            }
        } else {
            viewList.clear();
            viewList = null;
        }
    }

    /**
     * 去除当前的guide
     */
    private void hideCurrentGuide(){
        viewList.get(currentIndex).hide();
    }

    /**
     * 去除所有的guide
     */
    private void hideAllGuide(){
        hideCurrentGuide();
        viewList.clear();
        viewList = null;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d("----tt=","onKey");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if(null != viewList){
                hideAllGuide();
                return true;
            }
        }
        return false;
    }
}
