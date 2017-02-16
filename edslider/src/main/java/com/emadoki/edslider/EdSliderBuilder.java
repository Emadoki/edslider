package com.emadoki.edslider;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class EdSliderBuilder
{
    private Context context;
    public EdSliderManager manager;

    public EdSliderView view;
    public ViewGroup layout;
    public View target;
    public Rect boundary;

    public ArrayList<EdIcon> list;

    public int backgroundResId;
    public int size;
    public int margin;
    public int padding;
    public Align[] aligns;
    public boolean isReversed;

    public EdSliderBuilder(Context context)
    {
        this.context = context;

        view = new EdSliderView(context);
        list = new ArrayList<EdIcon>();

        backgroundResId = R.drawable.background;
        size = (int) Util.dimenToPx(context, 32);
        margin = (int) Util.dimenToPx(context, 4);
        // we use the icon size as padding
        padding = size;
        aligns = new Align[]{ Align.LEFT, Align.TOP};
        isReversed = false;

        layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
    }

    public EdSliderBuilder set(EdSliderManager manager)
    {
        this.manager = manager;
        return this;
    }

    public EdSliderBuilder on(View target)
    {
        this.target = target;
        return this;
    }

    /**
     * Add icon to view
     * @param id drawable id
     * @return continue building
     */
    public EdSliderBuilder addIcon(int id)
    {
        list.add(new EdIcon(id, ""));
        return this;
    }

    /**
     * Icon size, default 32dp
     * @param dimen dp
     * @return continue building
     */
    public EdSliderBuilder setIconSize(float dimen)
    {
        size = (int) Util.dimenToPx(context, dimen);
        padding = size;
        return this;
    }

    /**
     * Margin around each icon, default 4dp
     * @param dimen dp
     * @return continue building
     */
    public EdSliderBuilder setIconMargin(float dimen)
    {
        margin = (int) Util.dimenToPx(context, dimen);
        return this;
    }

    /**
     * Provide a resource drawable for slider background
     * @param id resource id
     * @return continue building
     */
    public EdSliderBuilder setSliderBackground(int id)
    {
        backgroundResId = id;
        return this;
    }

    /**
     * Align sliderview around the target. default LEFT, TOP
     * @param horizontal LEFT, RIGHT, CENTER
     * @param vertical TOP, BOTTOM
     * @return continue building
     */
    public EdSliderBuilder setAlignment(Align horizontal, Align vertical)
    {
        aligns[0] = horizontal;
        aligns[1] = vertical;
        return this;
    }

    /**
     * Finalize the builder
     * @return
     */
    public EdSliderManager build()
    {
        adjustPosition();
        view.build(this);
        manager.set(layout, view);
        return manager;
    }

    /**
     * Adjust the view position around the target.
     */
    private void adjustPosition()
    {
        int left = padding, right = padding;
        int top = padding * 2, bottom = padding * 2;
        // set padding to view
        view.setPadding(left, top, right, bottom);
        // cant get width or height before showing
        // so we need to calculate the size manually
        int rect_size = size + margin + margin;
        this.boundary = new Rect(
                padding, padding,
                padding + (rect_size * list.size()),
                (padding * 4) + rect_size);

        // get the location of target
        int[] coord = new int[2];
        target.getLocationOnScreen(coord);

        float x;
        float y;

        if (coord[0] - boundary.left < layout.getLeft())
        {
            x = -boundary.left;
        }
        else if(coord[0] + boundary.right > layout.getRight())
        {
            x = layout.getRight() - boundary.right;
        }
        else
        {
            switch (aligns[0]){
                case LEFT:
                    x = coord[0] - boundary.left;
                    break;
                case RIGHT:
                    x = coord[0] + target.getWidth() - boundary.right;
                    break;
                case CENTER:
                    x = (coord[0] + (target.getWidth() / 2)) - ((boundary.left + boundary.right) / 2);
                    break;
                default:
                    x = coord[0] - boundary.left;
                    break;
            }
        }

        if (coord[1] - top < layout.getTop())
        {
            y = coord[1] + target.getHeight() - top;
            isReversed = true;
        }
        else if(coord[1] + boundary.bottom > layout.getBottom())
        {
            y = coord[1] - boundary.bottom + bottom;
        }
        else
        {
            switch (aligns[1]){
                case TOP:
                    y = coord[1] - boundary.bottom + bottom;
                    break;
                case BOTTOM:
                    y = coord[1] + target.getHeight() - top;
                    isReversed = true;
                    break;
                default:
                    y = coord[1] - boundary.bottom + bottom;
                    break;
            }
        }

        view.setX(x);
        view.setY(y);
    }
}
