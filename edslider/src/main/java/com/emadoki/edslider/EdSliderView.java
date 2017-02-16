package com.emadoki.edslider;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class EdSliderView extends RelativeLayout
{
    private EdSliderManager manager;
    private Rect boundary;
    private int index;
    private boolean[] flags;
    private boolean isReversed;

    public EdSliderView(Context context)
    {
        super(context);

        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setClipChildren(false);
        setClipToPadding(false);

        index = -1;
        isReversed = false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                Process(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                dismiss();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * Generate the view
     * @param builder the configs
     */
    public void build(EdSliderBuilder builder)
    {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundResource(builder.backgroundResId);
        layout.setClipChildren(false);
        layout.setClipToPadding(false);

        for (EdIcon edIcon : builder.list)
        {
            params = new LinearLayout.LayoutParams(builder.size, builder.size);
            params.setMargins(builder.margin, builder.margin, builder.margin, builder.margin);
            ImageView image = new ImageView(getContext());
            image.setLayoutParams(params);
            image.setImageResource(edIcon.drawableid);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);

            layout.addView(image);
        }

        this.addView(layout);
        this.manager = builder.manager;
        this.flags = new boolean[builder.list.size()];
        this.boundary = builder.boundary;
        this.isReversed = builder.isReversed;
    }

    /**
     * Animate the views too look more lively
     */
    public void show()
    {
        LinearLayout layout = (LinearLayout) getChildAt(0);
        layout.setScaleY(0);
        layout.animate().scaleY(1).setDuration(150).start();

        for (int i = 0; i < layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);
            v.setScaleX(0);
            v.setScaleY(0);
            v.animate().scaleX(1).scaleY(1).setDuration(100)
                    .setStartDelay((80 * i) + 150)
                    .setInterpolator(new OvershootInterpolator())
                    .start();
        }
    }

    /**
     * Animate the views too look more lively
     */
    public void dismiss()
    {
        LinearLayout layout = (LinearLayout) getChildAt(0);

        for (int i = 0; i < layout.getChildCount(); i++)
        {
            View v = layout.getChildAt(i);
            v.setScaleX(1);
            v.setScaleY(1);
            v.animate().cancel();
            v.animate().translationY(isReversed ? -v.getHeight() :v.getHeight())
                    .scaleX(0).scaleY(0).setDuration(100)
                    .setStartDelay(80 * i)
                    .setInterpolator(null)
                    .start();
        }
        // hide view when finish animating
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                manager.dismiss();
            }
        }, 150 * layout.getChildCount());
    }

    /**
     * Animate when touch move
     * @param x touch x
     * @param y touch y
     */
    private void Process(float x, float y)
    {
        index = -1;

        x = x - getX();
        y = y - getY();
        // if touch within the icon
        if (boundary.contains((int) x, (int) y))
        {
            // get the index in list
            index = (int) Math.floor(x / (getWidth() / flags.length));

            if (index == -1 || index >= flags.length)
                return;
            // enlarge
            if (!flags[index])
            {
                // avoid duplicate
                flags[index] = true;
                View v = ((LinearLayout) getChildAt(0)).getChildAt(index);
                v.animate().translationY(isReversed ? v.getHeight() : -v.getHeight())
                        .scaleX(2).scaleY(2).setDuration(150)
                        .setStartDelay(0).setInterpolator(null).start();
            }
        }

        // reduce any enlarged icon
        for (int i = 0; i < flags.length; i++)
        {
            // don't reduce the current icon
            if (i == index)
                continue;

            if (flags[i])
            {
                flags[i] = false;
                View v = ((LinearLayout) getChildAt(0)).getChildAt(i);
                v.animate().translationY(0).scaleX(1).scaleY(1).setDuration(150)
                        .setStartDelay(0).setInterpolator(null).start();
            }
        }
    }

    public int getSelectedIndex()
    {
        return index;
    }
}
