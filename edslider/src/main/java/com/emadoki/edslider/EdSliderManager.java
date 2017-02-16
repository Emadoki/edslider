package com.emadoki.edslider;

import android.view.MotionEvent;
import android.view.ViewGroup;

public class EdSliderManager
{
    private ViewGroup layout;
    private EdSliderView view;
    private OnSliderSelectedListener listener;

    public EdSliderManager(OnSliderSelectedListener listener)
    {
        this.listener = listener;
    }

    public boolean dispatched(MotionEvent event)
    {
        if (view == null)
            return false;

        view.dispatchTouchEvent(event);
        return true;
    }

    public void set(ViewGroup layout, EdSliderView view)
    {
        this.layout = layout;
        this.view = view;
    }

    public void show()
    {
        if (layout != null)
            if (view != null)
            {
                this.view.show();
                layout.addView(this.view);
            }
    }

    public void dismiss()
    {
        int index = -1;

        if (layout != null)
            if (view != null)
            {
                index = view.getSelectedIndex();
                layout.removeView(view);
            }

        layout = null;
        view = null;

        if (listener != null)
            listener.OnSelected(index);
    }

    /**
     * Register a listener to get event callback when selected an icon
     * @param listener event callback
     */
    public void setOnSliderSelectedListener(OnSliderSelectedListener listener)
    {
        this.listener = listener;
    }
}
