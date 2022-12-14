/*
 * Copyright (c) 2015. Roberto  Prato <https://github.com/robertoprato>
 *
 *  *
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *
 *  *    Unless required by applicable law or agreed to in writing, softwaregit statu
 *  *    limitations under the License.
 *
 */

package io.square1.richtextlib.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by roberto on 12/10/15.
 */
public class AspectRatioFrameLayout extends FrameLayout {

    private Double mRatio = Double.NaN;

    public AspectRatioFrameLayout(Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AspectRatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mRatio.isNaN()){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int initialMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec) -
                (getPaddingLeft() + getPaddingRight());

        final int initialMeasuredHeight = MeasureSpec.getSize(heightMeasureSpec) -
                (getPaddingTop() + getPaddingBottom());

        double current = (double)initialMeasuredWidth / (double) initialMeasuredHeight;

        //if not right just yet
        if(mRatio - current != 0){
            double measuredHeight = (int)(initialMeasuredWidth / mRatio) + getPaddingTop() + getPaddingBottom();
            double measuredWidth = initialMeasuredWidth + getPaddingLeft() + getPaddingRight();

            if(MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY  &&
                    MeasureSpec.getSize(heightMeasureSpec) < measuredHeight) {
                measuredWidth = Math.min ( (initialMeasuredHeight * mRatio) + getPaddingLeft() + getPaddingRight() , measuredWidth );
                /// we need to reduce the width otherwise it is too high
                measuredHeight = (int)(initialMeasuredWidth / mRatio) + getPaddingTop() + getPaddingBottom();
            }

            setMeasuredDimension((int)measuredWidth,(int)measuredHeight);

        }else {// nothing to do
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
