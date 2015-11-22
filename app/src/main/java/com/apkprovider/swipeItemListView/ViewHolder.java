package com.apkprovider.swipeItemListView;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Krishna on 22-11-2015.
 */
public class ViewHolder {
    protected TextView text;
    protected ImageView icon;
 //   protected CheckBox checkbox;
    protected int position;
    protected MainActivity.Model model;
    private int color;
    private int imageid;

    public ViewHolder()
    {
        position = 0;
        imageid = R.drawable.circle;
        color = 0xFFFFFFFF;
    }
    public int getColor() {
        return color;
    }

    public int getImageId() {
        return imageid;
    }

    public void setRunning(boolean running) {
        model.setRuning(running);
        if(running)
        {
            color = 0xFF9900; //orange color
            imageid = R.drawable.circle;
        }
        else
        {
            imageid = R.drawable.circle;
            color = 0xFFFFFFFF;
        }
    }
}
